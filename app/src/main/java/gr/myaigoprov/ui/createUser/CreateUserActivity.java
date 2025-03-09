package gr.myaigoprov.ui.createUser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import gr.myaigoprov.MainActivity;
import gr.myaigoprov.R;
import gr.myaigoprov.manager.UserManager;
import gr.myaigoprov.model.Farmer;

public class CreateUserActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText farmerCodeEditText;
    private Spinner spinnerAnimalType;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        // Σύνδεση UI στοιχείων
        nameEditText = findViewById(R.id.editTextName);
        farmerCodeEditText = findViewById(R.id.editTextFarmerCode);
        spinnerAnimalType = findViewById(R.id.spinAnimalType);
        saveButton = findViewById(R.id.buttonSave);

        // Δημιουργία Adapter για τον Spinner
        String[] animalTypes = getResources().getStringArray(R.array.farmer_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                animalTypes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimalType.setAdapter(adapter);


        saveButton.setOnClickListener(view -> {
            String fullname = nameEditText.getText().toString().trim();
            String farmerCode = farmerCodeEditText.getText().toString().trim();
            String animalType = spinnerAnimalType.getSelectedItem().toString();

            if (!fullname.isEmpty() && !farmerCode.isEmpty()) {
                // Αποθήκευση χρήστη
                Farmer farmer = new Farmer(fullname, farmerCode, animalType);
                UserManager.getInstance(this).saveFarmer(farmer);

                // Επιστροφή στο MainActivity
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            else {
                // Ενημέρωση χρήστη για κενά πεδία
                if (fullname.isEmpty()) {
                    nameEditText.setError("Το όνομα είναι υποχρεωτικό");
                }
                if (farmerCode.isEmpty()) {
                    farmerCodeEditText.setError("Ο κωδικός είναι υποχρεωτικός");
                }
            }
        });
    }
}