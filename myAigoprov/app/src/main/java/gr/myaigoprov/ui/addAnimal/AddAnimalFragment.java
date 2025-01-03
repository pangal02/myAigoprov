package gr.myaigoprov.ui.addAnimal;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import gr.myaigoprov.MainActivity;
import gr.myaigoprov.R;

import gr.myaigoprov.database.*;
import gr.myaigoprov.model.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAnimalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAnimalFragment extends Fragment {

    private Spinner spinnerAnimalType;
    private Spinner spinnerAnimalGender;
    private EditText editTextTagNumber;
    private EditText editTextBirthDate;
    private Button buttonSelectDate;
    private Button buttonSaveAnimal;
    private String date;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddAnimalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAnimalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAnimalFragment newInstance(String param1, String param2) {
        AddAnimalFragment fragment = new AddAnimalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_animal, container, false);

        // Σύνδεση UI στοιχείων
        spinnerAnimalType = root.findViewById(R.id.spinnerAnimalType);
        spinnerAnimalGender = root.findViewById(R.id.spinnerAnimalGender);
        editTextTagNumber = root.findViewById(R.id.editTextTagNumber);
        editTextBirthDate = root.findViewById(R.id.editTextBirthDate);
        buttonSelectDate = root.findViewById(R.id.buttonSelectDate);
        buttonSaveAnimal = root.findViewById(R.id.buttonSaveAnimal);

        // Ρύθμιση επιλογών για το Spinner
        ArrayAdapter<CharSequence> typeAnimalAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.animal_types, android.R.layout.simple_spinner_item);
        typeAnimalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimalType.setAdapter(typeAnimalAdapter);

        ArrayAdapter<CharSequence> genderAnimalAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.animal_genders, android.R.layout.simple_spinner_item);
        genderAnimalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimalGender.setAdapter(genderAnimalAdapter);

        buttonSelectDate.setOnClickListener(v -> openDatePicker());

        // Ρύθμιση επιλογής ημερομηνίας
        buttonSaveAnimal.setOnClickListener(v -> {
            String animalType = spinnerAnimalType.getSelectedItem().toString();
            String genderString = spinnerAnimalGender.getSelectedItem().toString();
            String tagNumberString = editTextTagNumber.getText().toString();
            String birthdate = editTextBirthDate.getText().toString();
            String farmerCode = MainActivity.farmer.getFarmerCode();

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

            Animal animal;
            // Δημιουργία του αντικειμένου Animal
            if(animalType.equals("ΓΙΔΙ")){
                animal = new Goat(farmerCode, tagNumber, gender, birthdate);
            }
            else {
                animal = new Sheep(farmerCode, tagNumber, gender, birthdate);
            }

            // Εισαγωγή στη βάση δεδομένων
            new Thread(() -> {
                DatabaseHelper dbHelper = new DatabaseHelper(this.requireContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                dbHelper.addAnimal(animal);

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
                    editTextBirthDate.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}