package gr.myaigoprov.ui.animal;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

import gr.myaigoprov.R;
import gr.myaigoprov.database.DatabaseHelper;
import gr.myaigoprov.manager.UserManager;
import gr.myaigoprov.model.*;


public class ExitBornAnimal extends Fragment {

    private Spinner spinnerAnimalType, spinnerExitType;
    private EditText strNumOfAnimals;
    private Button selectDateButton;
    private TextView textViewDate;
    private Button saveButton;

    public ExitBornAnimal() {
        super(R.layout.fragment_add_born_animals);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_exit_animal, container, false);
        // Σύνδεση UI στοιχείων
        spinnerAnimalType = root.findViewById(R.id.spinnerAnimalType);
        spinnerExitType = root.findViewById(R.id.spinnerExitType);
        strNumOfAnimals = root.findViewById(R.id.etAnimalCount);
        selectDateButton = root.findViewById(R.id.btnSelectDate);
        textViewDate = root.findViewById(R.id.textViewDate);
        saveButton = root.findViewById(R.id.btnSave);

        // Ρύθμιση επιλογών για το Spinner ANIMAL TYPES
        ArrayAdapter<CharSequence> typeAnimalAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.born_animal_types, android.R.layout.simple_spinner_item);
        typeAnimalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimalType.setAdapter(typeAnimalAdapter);

        // Ρύθμιση επιλογών για το Spinner EXIT ANIMALS
        ArrayAdapter<CharSequence> typeExitAdapter= ArrayAdapter.createFromResource(requireContext(), R.array.exit_types, android.R.layout.simple_spinner_item);
        typeExitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExitType.setAdapter(typeExitAdapter);

        selectDateButton.setOnClickListener(v -> openDatePicker());

        saveButton.setOnClickListener(v -> {
            String animalType = spinnerAnimalType.getSelectedItem().toString();
            Integer numOfAnimals = null;
            try{
                numOfAnimals = Integer.parseInt(strNumOfAnimals.getText().toString().trim());
            }
            catch (NumberFormatException nfe){
                nfe.printStackTrace();
                strNumOfAnimals.setError("Κενή τιμή");
                return;
            }

            String date = textViewDate.getText().toString();
            String exitType = spinnerExitType.getSelectedItem().toString();
            Farmer farmer = UserManager.getInstance(requireContext()).getFarmer();

            if(animalType.equalsIgnoreCase("ΑΡΝΙΑ") &&
                    farmer.getAnimalsType().equalsIgnoreCase("ΓΙΔΙΑ")){
                Toast.makeText(requireContext(), "Δεν μπορείτε να αφαιρέσετε " + animalType + " ενώ έχετε " + farmer.getAnimalsType()+ "!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(animalType.equalsIgnoreCase("ΚΑΤΣΙΚΙΑ") &&
                    farmer.getAnimalsType().equalsIgnoreCase("ΠΡΟΒΑΤΑ")){
                Toast.makeText(requireContext(), "Δεν μπορείτε να αφαιρέσετε " + animalType + " ενώ έχετε " + farmer.getAnimalsType()+ "!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(numOfAnimals == null){
                strNumOfAnimals.setError("κενό");
                return;
            }
            if (numOfAnimals.intValue() < 1){
                strNumOfAnimals.setError("Αριθμός κάτω από 1");
                return;
            }
            if(date.isEmpty() || date == null){
                Toast.makeText(requireContext(), "Η ημερομηνία είναι κενή!", Toast.LENGTH_SHORT).show();
                return;
            }
            DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
            int bornInDatabase = dbHelper.getCountBornGoats();
            if(numOfAnimals > bornInDatabase){
                Toast.makeText(requireContext(), "Δεν μπορείτε να αφαιρέσετε " + numOfAnimals + " "+ animalType + " ενώ έχετε (" + bornInDatabase + ") ", Toast.LENGTH_LONG).show();
                strNumOfAnimals.setError("ΕΧΕΙΣ " + bornInDatabase);
                return;
            }


            ArrayList<DeletedAnimal> exitAnimals = new ArrayList<>();

            if(animalType.equalsIgnoreCase("ΑΡΝΙΑ")){
                Cursor cursor = dbHelper.getBornSheeps();
                if(cursor != null && cursor.moveToFirst()){
                    int cnt = 0;
                    do{
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                        int tag = cursor.getInt(cursor.getColumnIndexOrThrow("tag_number"));
                        String birthdate = cursor.getString(cursor.getColumnIndexOrThrow("birthdate"));
                        boolean youngMom = cursor.getInt(cursor.getColumnIndexOrThrow("young_mom")) == 1 ? true : false;

                        Sheep sheep = new Sheep(id, UserManager.getInstance(requireContext()).getFarmer().getFarmerCode(),
                                tag, Gender.OTHER, birthdate);
                        sheep.setYoungMom(youngMom);

                        exitAnimals.add(new DeletedAnimal(sheep, "ΑΡΝΙ", exitType));
                        cnt++;
                    }while (cursor.moveToNext() && cnt < numOfAnimals);
                    cursor.close();
                }
            }
            else if(animalType.equalsIgnoreCase("ΚΑΤΣΙΚΙΑ")){
                Cursor cursor = dbHelper.getBornGoats();
                if(cursor != null && cursor.moveToFirst()){
                    int cnt = 0;
                    do{
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                        int tag = cursor.getInt(cursor.getColumnIndexOrThrow("tag_number"));
                        String birthdate = cursor.getString(cursor.getColumnIndexOrThrow("birthdate"));
                        boolean youngMom = cursor.getInt(cursor.getColumnIndexOrThrow("young_mom")) == 1 ? true : false;

                        Goat goat = new Goat(id, UserManager.getInstance(requireContext()).getFarmer().getFarmerCode(),
                                tag, Gender.OTHER, date);
                        DeletedAnimal deletedAnimal = new DeletedAnimal(goat, "ΚΑΤΣΙΚΙ", exitType);
                        exitAnimals.add(deletedAnimal);
                        cnt++;
                        Log.d("GOAT", "ID: " + deletedAnimal.getAnimal().getId());
                    }while (cursor.moveToNext() && cnt < numOfAnimals);
                    cursor.close();
                }
            }

            new Thread(()->{
                int count = 0;
                for (DeletedAnimal animal : exitAnimals){
                    int del = dbHelper.deleteAnimal(animal.getAnimal());
                    long r = dbHelper.setAsRemoved(animal, exitType);
                    if(del > 0 && r > 0){
                        Log.d("GOD", "DELETED ID: " + animal.getAnimal().getId());
                    }
                }
                    requireActivity().runOnUiThread(() ->
                            Snackbar.make(v, "Τα ζώα αποθηκεύτηκαν επιτυχώς!", Snackbar.LENGTH_LONG).show());

            }).start();
        dbHelper.close();
        });


        return root;
    }

    private void load(){
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
            Cursor cursor = dbHelper.getBornGoats();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    int tag = cursor.getInt(cursor.getColumnIndexOrThrow("tag_number"));
                    String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
                    String bdate = cursor.getString(cursor.getColumnIndexOrThrow("birthdate"));


                    Log.d("GOAT" , "{ID: " + id + ",TAG:" + tag + ",GENDER:" + gender + ",BDATE:"+bdate+"}");

                } while (cursor.moveToNext());
            }
        }
        catch (Exception e){}
    }

    private void openDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    textViewDate.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}