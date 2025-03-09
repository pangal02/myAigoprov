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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import gr.myaigoprov.R;

import gr.myaigoprov.database.*;
import gr.myaigoprov.manager.UserManager;
import gr.myaigoprov.model.*;


public class AddAnimalFragment extends Fragment {

    private Spinner spinnerAnimalType;
    private Spinner spinnerAnimalGender;
    private EditText editTextTagNumber;
    private TextView textBirthDate;
    private Button buttonSelectDate;
    private Button buttonSaveAnimal;


    public AddAnimalFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_animal, container, false);

        // Σύνδεση UI στοιχείων
        spinnerAnimalType = root.findViewById(R.id.spinnerAnimalType);
        spinnerAnimalGender = root.findViewById(R.id.spinnerAnimalGender);
        editTextTagNumber = root.findViewById(R.id.editTextTagNumber);
        textBirthDate = root.findViewById(R.id.textViewDate);
        buttonSelectDate = root.findViewById(R.id.buttonSelectDate);
        buttonSaveAnimal = root.findViewById(R.id.buttonSaveAnimal);

        // Ρύθμιση επιλογών για το Spinner
        ArrayAdapter<CharSequence> typeAnimalAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.animal_types, android.R.layout.simple_spinner_item);
        typeAnimalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimalType.setAdapter(typeAnimalAdapter);

        ArrayAdapter<CharSequence> genderAnimalAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.animal_genders, android.R.layout.simple_spinner_item);
        genderAnimalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimalGender.setAdapter(genderAnimalAdapter);

        // Ρύθμιση επιλογής ημερομηνίας
        buttonSelectDate.setOnClickListener(v -> openDatePicker());

        buttonSaveAnimal.setOnClickListener(v -> {
            String animalType = spinnerAnimalType.getSelectedItem().toString();
            String genderString = spinnerAnimalGender.getSelectedItem().toString();
            String tagNumberString = editTextTagNumber.getText().toString();
            String birthdate = textBirthDate.getText().toString();
            String farmerCode = UserManager.getInstance(requireContext()).getFarmer().getFarmerCode();

            if (tagNumberString.isEmpty() || birthdate.isEmpty()) {
                Snackbar.make(v, "Παρακαλώ συμπληρώστε όλα τα πεδία!", Snackbar.LENGTH_LONG).show();
                return;
            }

            int tagNumber = Integer.parseInt(tagNumberString);

            Gender gender = null;
            if(genderString.equals("ΑΡΣΕΝΙΚΟ")){
                gender = Gender.MALE;
            }
            else if(genderString.equals("ΘΗΛΥΚΟ")){
                gender = Gender.FEMALE;
            }
            else if(genderString.equals("ΑΛΛΟ")){
                gender = Gender.OTHER;
            }

            Farmer farmer = UserManager.getInstance(getContext()).getFarmer();
            if(animalType.equalsIgnoreCase("ΠΡΟΒΑΤΟ") &&
                    farmer.getAnimalsType().equalsIgnoreCase("ΓΙΔΙΑ")){
                Toast.makeText(requireContext(), "Δεν μπορείτε να προσθέσετε " + animalType + " ενώ έχετε " + farmer.getAnimalsType()+ "!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(animalType.equalsIgnoreCase("ΑΙΓΑ") &&
                    farmer.getAnimalsType().equalsIgnoreCase("ΠΡΟΒΑΤΑ")){
                Toast.makeText(requireContext(), "Δεν μπορείτε να προσθέσετε " + animalType + " ενώ έχετε " + farmer.getAnimalsType()+ "!", Toast.LENGTH_SHORT).show();
                return;
            }

            Animal animal;
            // Δημιουργία του αντικειμένου Animal
            if(animalType.equals("ΑΙΓΑ")){
                animal = new Goat(farmerCode, tagNumber, gender, birthdate);
            }
            else {
                animal = new Sheep(farmerCode, tagNumber, gender, birthdate);
            }

            // Εισαγωγή στη βάση δεδομένων
            new Thread(() -> {
                DatabaseHelper dbHelper = new DatabaseHelper(this.requireContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                long result = dbHelper.addAnimal(animal);
                animal.setId((int) result);
                Log.d("SETID", "ID: "+animal.getId());
                requireActivity().runOnUiThread(() ->
                        Snackbar.make(v, "Το ζώο αποθηκεύτηκε επιτυχώς!", Snackbar.LENGTH_LONG).show());
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
                    textBirthDate.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}