package gr.myaigoprov.ui.milk;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import gr.myaigoprov.R;
import gr.myaigoprov.database.DatabaseHelper;
import gr.myaigoprov.model.Farmer;
import gr.myaigoprov.model.Milk;
import gr.myaigoprov.manager.*;

public class MilkProductionFragment extends Fragment {

    private Spinner spinnerMilkType;
    private EditText editTextQuantity;
    private TextView textSelectedDate;
    private EditText editTextCountAnimals;
    private Button buttonSelectDate, buttonSave;
    private String selectedMilkType, selectedDate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View rootView = inflater.inflate(R.layout.fragment_milk_production, container, false);

        spinnerMilkType = rootView.findViewById(R.id.spinnerMilkType);
        editTextQuantity = rootView.findViewById(R.id.editTextQuantity);
        textSelectedDate = rootView.findViewById(R.id.textViewDate);
        editTextCountAnimals = rootView.findViewById(R.id.editTextAnimalCount);
        buttonSelectDate = rootView.findViewById(R.id.buttonSelectDate);
        buttonSave = rootView.findViewById(R.id.buttonSave);

        loadLastEntry();
        // Handle spinner selection
        spinnerMilkType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMilkType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMilkType = null;
            }
        });

        // Handle date selection
        buttonSelectDate.setOnClickListener(v -> showDatePicker());

        // Handle save button
        buttonSave.setOnClickListener(v -> saveMilkProduction());

        return rootView;
    }



    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year1, month1, dayOfMonth) -> {
                    selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    textSelectedDate.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void saveMilkProduction() {
        Farmer farmer = UserManager.getInstance(requireContext()).getFarmer();
        String quantityStr = editTextQuantity.getText().toString().trim();
        String count = editTextCountAnimals.getText().toString().trim();
        selectedDate = textSelectedDate.getText().toString().trim();

        if(quantityStr.isEmpty() || quantityStr.equals(" ")){
            editTextQuantity.setError("Εισάγετε την ποσότητα!");
            return;
        }
        if(quantityStr.equals("0")){
            editTextQuantity.setError("Ποσότητα μεγαλύτερη από 0!");
            return;
        }
        if(selectedDate == null){
            textSelectedDate.setError("Επιλέξτε μια ημερομηνία!");
            return;
        }
        if (count.isEmpty() || count.equals(" ")) {
            editTextCountAnimals.setError("Εισάγετε τον αριθμό των ζώων!");
            return;
        }
        if(count.equals("0")){
            editTextCountAnimals.setError("Αριθμός των ζώων πάνω από 0!");
        }

        if(farmer.getAnimalsType().equalsIgnoreCase("ΓΙΔΙΑ") &&
                selectedMilkType.equalsIgnoreCase("ΠΡΟΒΕΙΟ")){
            Toast.makeText(requireContext(), "Δεν μπορείτε να προσθέσετε " + selectedMilkType + " γαλα ενώ έχετε " + farmer.getAnimalsType()+ "!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(farmer.getAnimalsType().equalsIgnoreCase("ΠΡΟΒΑΤΑ") &&
                selectedMilkType.equalsIgnoreCase("ΚΑΤΣΙΚΙΣΙΟ")){
            Toast.makeText(requireContext(), "Δεν μπορείτε να προσθέσετε " + selectedMilkType + " γάλα ενώ έχετε " + farmer.getAnimalsType()+ "!", Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity = Double.parseDouble(quantityStr);
        String date = selectedDate;
        int countAnimals = Integer.parseInt(count);

        Milk milk= new Milk(quantity, date);
        Log.d("MILK", "Milk{" + milk + "}");

        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        if(selectedMilkType.equalsIgnoreCase("ΚΑΤΣΙΚΙΣΙΟ")){
            long id = dbHelper.addGoatMilk(milk, countAnimals);
            if (id != -1) {
                milk.setId((int) id);
                Log.d("Database", "Επιτυχής προσθήκη εγγραφής με ID: " + id + ", date: " + date + ", Quantity: " + quantity);
                Toast.makeText(requireContext(), "Η παραγωγή αποθηκεύτηκε!", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("Database", "Αποτυχία προσθήκης εγγραφής!");
                Toast.makeText(requireContext(), "Προέκυψε σφάλμα! Ελέγξτε τα δεδομένα σας και προσπαθήστε ξανά.", Toast.LENGTH_LONG).show();
            }
        }
        else{
            dbHelper.addSheepMilk(milk, countAnimals);
        }
        dbHelper.close();
    }

    private void loadLastEntry() {
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        Cursor cursor = dbHelper.getGoatMilkOrderedByLatest();
        if (cursor.moveToFirst()) {
            int animalCount = cursor.getInt(cursor.getColumnIndexOrThrow("count_animals"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

            textSelectedDate.setText(date);
            editTextCountAnimals.setText(String.valueOf(animalCount));
        }
        cursor.close();
    }

}
