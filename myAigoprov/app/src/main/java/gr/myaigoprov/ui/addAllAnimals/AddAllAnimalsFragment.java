package gr.myaigoprov.ui.addAllAnimals;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import gr.myaigoprov.R;
import gr.myaigoprov.database.DatabaseHelper;
import gr.myaigoprov.model.Animal;
import gr.myaigoprov.model.Gender;
import gr.myaigoprov.model.Goat;
import gr.myaigoprov.model.Sheep;


public class AddAllAnimalsFragment extends Fragment {

    private Spinner spinnerAnimalType;
    private EditText strNumOfAnimals;
    private Button selectDateButton;
    private EditText editTextBirthDate;
    private Button saveButton;


    public AddAllAnimalsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_all_animals, container, false);
        // Σύνδεση UI στοιχείων
        spinnerAnimalType = root.findViewById(R.id.spinnerAnimalType);
        strNumOfAnimals = root.findViewById(R.id.etAnimalCount);
        selectDateButton = root.findViewById(R.id.btnSelectDate);
        editTextBirthDate = root.findViewById(R.id.editTextBirthDate);
        saveButton = root.findViewById(R.id.btnSave);

        // Ρύθμιση επιλογών για το Spinner
        ArrayAdapter<CharSequence> typeAnimalAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.animal_types, android.R.layout.simple_spinner_item);
        typeAnimalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimalType.setAdapter(typeAnimalAdapter);

        selectDateButton.setOnClickListener(v -> openDatePicker());

        saveButton.setOnClickListener(v -> {
            String animalType = spinnerAnimalType.getSelectedItem().toString();
            int numOfAnimals = Integer.parseInt(strNumOfAnimals.getText().toString());
            String birthdate = editTextBirthDate.getText().toString();

            ArrayList<Animal> bornAnimals = new ArrayList<>();

            if(animalType.equalsIgnoreCase("ΠΡΟΒΑΤΟ")){
                for(int i = 0; i < numOfAnimals; i++) {
                    bornAnimals.add(new Sheep("EL12345", -1, Gender.OTHER, birthdate));
                }
            }
            else{
                for(int i = 0; i < numOfAnimals; i++) {
                    bornAnimals.add(new Goat("EL12345", -1, Gender.OTHER, birthdate));
                }
            }


            new Thread(()->{
                DatabaseHelper dbHelper = new DatabaseHelper(this.requireContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                for(int i = 0; i < bornAnimals.size(); i++){
                    dbHelper.addAnimal(bornAnimals.get(i));
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
                    editTextBirthDate.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}