package gr.myaigoprov.ui.animal;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

import gr.myaigoprov.R;
import gr.myaigoprov.database.DatabaseHelper;
import gr.myaigoprov.manager.UserManager;
import gr.myaigoprov.model.*;


public class AddBornAnimalsFragment extends Fragment {

    private Spinner spinnerAnimalType;
    private EditText strNumOfAnimals;
    private Button selectDateButton;
    private TextView textViewBirthDate;
    private Button saveButton;


    public AddBornAnimalsFragment() {
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
        View root = inflater.inflate(R.layout.fragment_add_born_animals, container, false);
        // Σύνδεση UI στοιχείων
        spinnerAnimalType = root.findViewById(R.id.spinnerAnimalType);
        strNumOfAnimals = root.findViewById(R.id.etAnimalCount);
        selectDateButton = root.findViewById(R.id.btnSelectDate);
        textViewBirthDate = root.findViewById(R.id.textViewDate);
        saveButton = root.findViewById(R.id.btnSave);

        // Ρύθμιση επιλογών για το Spinner
        ArrayAdapter<CharSequence> typeAnimalAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.born_animal_types, android.R.layout.simple_spinner_item);
        typeAnimalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimalType.setAdapter(typeAnimalAdapter);

        selectDateButton.setOnClickListener(v -> openDatePicker());

        saveButton.setOnClickListener(v -> {
            String animalType = spinnerAnimalType.getSelectedItem().toString();
            String birthdate = textViewBirthDate.getText().toString();
            String strNumberAnimals = strNumOfAnimals.getText().toString();
            Farmer farmer = UserManager.getInstance(requireContext()).getFarmer();

            if(animalType.equalsIgnoreCase("ΑΡΝΙΑ") &&
                    farmer.getAnimalsType().equalsIgnoreCase("ΓΙΔΙΑ")){
                Toast.makeText(requireContext(), "Δεν μπορείτε να προσθέσετε " + animalType + " ενώ έχετε " + farmer.getAnimalsType()+ "!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(animalType.equalsIgnoreCase("ΚΑΤΣΙΚΙΑ") &&
                    farmer.getAnimalsType().equalsIgnoreCase("ΠΡΟΒΑΤΑ")){
                Toast.makeText(requireContext(), "Δεν μπορείτε να προσθέσετε " + animalType + " ενώ έχετε " + farmer.getAnimalsType()+ "!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(strNumberAnimals.isEmpty() || strNumberAnimals == null){
                strNumOfAnimals.setError("Δεν μπορεί να είναι κενό");
                return;
            }

            if(birthdate.isEmpty() || birthdate == null){
                textViewBirthDate.setError("Δεν μπορεί να είναι κενό");
                return;
            }
            int numOfAnimals = Integer.parseInt(strNumberAnimals);

            ArrayList<Animal> bornAnimals = new ArrayList<>();
            if(animalType.equalsIgnoreCase("ΑΡΝΙΑ")){
                for(int i = 0; i < numOfAnimals; i++) {
                    bornAnimals.add(new Sheep(farmer.getFarmerCode(), -1, Gender.OTHER, birthdate));
                }
            }
            else if(animalType.equalsIgnoreCase("ΚΑΤΣΙΚΙΑ")){
                for(int i = 0; i < numOfAnimals; i++) {
                    bornAnimals.add(new Goat(farmer.getFarmerCode(), -1, Gender.OTHER, birthdate));
                }
            }

            new Thread(()->{
                DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                for (Animal animal : bornAnimals){
                    long result = dbHelper.addAnimal(animal);
                    animal.setId((int) result);
                    Log.d("SETID", "ID: "+animal.getId());
                }
                requireActivity().runOnUiThread(() ->
                        Snackbar.make(v, "Τα ζώα αποθηκεύτηκαν επιτυχώς!", Snackbar.LENGTH_LONG).show());
            }).start();

        });

        return root;
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
                    textViewBirthDate.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}