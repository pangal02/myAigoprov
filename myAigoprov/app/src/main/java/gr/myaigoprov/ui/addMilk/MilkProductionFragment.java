package gr.myaigoprov.ui.addMilk;

import android.app.DatePickerDialog;
import android.os.Bundle;
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
    private TextView textViewSelectedDate;
    private Button buttonSelectDate, buttonSave;
    private String selectedMilkType;
    private String selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View rootView = inflater.inflate(R.layout.fragment_milk_production, container, false);

        spinnerMilkType = rootView.findViewById(R.id.spinnerMilkType);
        editTextQuantity = rootView.findViewById(R.id.editTextQuantity);
        textViewSelectedDate = rootView.findViewById(R.id.textViewSelectedDate);
        buttonSelectDate = rootView.findViewById(R.id.buttonSelectDate);
        buttonSave = rootView.findViewById(R.id.buttonSave);

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
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year1, month1, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            textViewSelectedDate.setText(selectedDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void saveMilkProduction() {
        Farmer farmer = UserManager.getInstance(requireContext()).getFarmer();
        String quantityStr = editTextQuantity.getText().toString();

        if (selectedMilkType == null || quantityStr.isEmpty() || selectedDate == null) {
            Toast.makeText(requireContext(), "Παρακαλώ συμπληρώστε όλα τα πεδία!", Toast.LENGTH_SHORT).show();
            return;
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

        Milk milk = new Milk(quantity, selectedDate);
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        if(selectedMilkType.equalsIgnoreCase("ΚΑΤΣΙΚΙΣΙΟ")){
            dbHelper.addGoatMilk(milk);
        }
        else{
            dbHelper.addSheepMilk(milk);
        }
        // Save milk production (logic to be implemented with database)
        Toast.makeText(requireContext(), "Η παραγωγή αποθηκεύτηκε!", Toast.LENGTH_SHORT).show();
    }
}
