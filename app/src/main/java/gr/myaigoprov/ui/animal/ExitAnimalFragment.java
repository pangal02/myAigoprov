package gr.myaigoprov.ui.animal;

import android.app.DatePickerDialog;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

import gr.myaigoprov.R;
import gr.myaigoprov.database.DatabaseHelper;
import gr.myaigoprov.manager.UserManager;
import gr.myaigoprov.model.*;


public class ExitAnimalFragment extends Fragment {

    private Spinner spinnerAnimalType, spinnerAnimalGender, spinnerExitType;
    private EditText strNumOfAnimals;
    private Button selectDateButton;
    private TextView textViewDate;
    private Button saveButton;
    private Farmer farmer;

    public ExitAnimalFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Έλεγχος αν το Fragment είναι ήδη συνημμένο πριν το χρησιμοποιήσεις
        if (getContext() != null) {
            farmer = UserManager.getInstance(getContext()).getFarmer();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_exit_animal, container, false);

        // Σύνδεση UI στοιχείων
        spinnerAnimalType = root.findViewById(R.id.spinnerAnimalType);
        spinnerAnimalGender = root.findViewById(R.id.spinnerAnimalGender);
        strNumOfAnimals = root.findViewById(R.id.etAnimalCount);
        spinnerExitType = root.findViewById(R.id.spinnerExitType);
        textViewDate = root.findViewById(R.id.textViewDate);
        selectDateButton = root.findViewById(R.id.btnSelectDate);
        saveButton = root.findViewById(R.id.btnSave);

        // Ρύθμιση επιλογών για το Spinner τύπος ζώου
        ArrayAdapter<CharSequence> typeAnimalAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.animal_types, android.R.layout.simple_spinner_item);
        typeAnimalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimalType.setAdapter(typeAnimalAdapter);
        if(farmer.getAnimalsType().equalsIgnoreCase("ΓΙΔΙΑ")){
            spinnerAnimalType.setSelection(0);
        } else {
            spinnerAnimalType.setSelection(1);
        }

        // Ρύθμιση επιλογών για το Spinner γένους ζώου
        ArrayAdapter<CharSequence> genderAnimalAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.animal_genders, android.R.layout.simple_spinner_item);
        genderAnimalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimalGender.setAdapter(genderAnimalAdapter);
        spinnerAnimalGender.setSelection(0);

        // Ρύθμιση επιλογών για το Spinner τύπος εξόδου
        ArrayAdapter<CharSequence> typeExitAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.exit_types, android.R.layout.simple_spinner_item);
        typeExitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExitType.setAdapter(typeExitAdapter);
        spinnerExitType.setSelection(0);

        // Ρύθμιση του DatePicker
        selectDateButton.setOnClickListener(v -> openDatePicker());

        saveButton.setOnClickListener(v -> {
            String animalType = spinnerAnimalType.getSelectedItem().toString();
            String gender = spinnerAnimalGender.getSelectedItem().toString();
            String exitType = spinnerExitType.getSelectedItem().toString();
            String date = textViewDate.getText().toString();
            String strNumberAnimals = strNumOfAnimals.getText().toString();

            // Έλεγχοι εγκυρότητας
            if(animalType.equalsIgnoreCase("ΑΡΝΙΑ") && farmer.getAnimalsType().equalsIgnoreCase("ΓΙΔΙΑ")){
                Toast.makeText(requireContext(), "Δεν μπορείτε να αφαιρέσετε " + animalType + " ενώ έχετε " + farmer.getAnimalsType() + "!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(animalType.equalsIgnoreCase("ΚΑΤΣΙΚΙΑ") && farmer.getAnimalsType().equalsIgnoreCase("ΠΡΟΒΑΤΑ")){
                Toast.makeText(requireContext(), "Δεν μπορείτε να αφαιρέσετε " + animalType + " ενώ έχετε " + farmer.getAnimalsType() + "!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(strNumberAnimals.isEmpty()){
                strNumOfAnimals.setError("Δεν μπορεί να είναι κενό");
                return;
            }

            if(date.isEmpty()){
                textViewDate.setError("Δεν μπορεί να είναι κενό");
                return;
            }

            int numOfAnimals = Integer.parseInt(strNumberAnimals);
            if(numOfAnimals <= 0){
                strNumOfAnimals.setError("Το πλήθος των ζώων πρέπει να είναι μεγαλύτερο του 0");
                return;
            }

            DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();



//            new Thread(() -> {
//                DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
//                SQLiteDatabase db = dbHelper.getReadableDatabase();
//                for (Animal animal : bornAnimals){
//                    long result = dbHelper.removeAnimal(animal);
//                    animal.setId((int) result);
//                    Log.d("SETID", "ID: " + animal.getId());
//                }
//                requireActivity().runOnUiThread(() ->
//                        Snackbar.make(v, "Τα ζώα αποθηκεύτηκαν στα διεγραμμένα επιτυχώς!", Snackbar.LENGTH_LONG).show());
//            }).start();
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
                    textViewDate.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }


}
