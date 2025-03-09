package gr.myaigoprov.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import gr.myaigoprov.MainActivity;
import gr.myaigoprov.R;
import gr.myaigoprov.manager.UserManager;
import gr.myaigoprov.model.Farmer;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editTextName, editTextFarmerCode;
    private Spinner spinAnimalType;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        // Αρχικοποιήσεις
        editTextName = findViewById(R.id.editTextName);
        editTextFarmerCode = findViewById(R.id.editTextFarmerCode);
        spinAnimalType = findViewById(R.id.spinAnimalType);
        buttonSave = findViewById(R.id.buttonSave);

        // Εδώ μπορείς να προσθέσεις δεδομένα στον Spinner για τα ζώα (π.χ. goats, sheeps)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.farmer_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinAnimalType.setAdapter(adapter);
        editTextName.setText(getIntent().getStringExtra("name"));
        editTextFarmerCode.setText(getIntent().getStringExtra("farmerCode"));


        // Όταν πατηθεί το κουμπί "Αποθήκευση"
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Παίρνουμε τα δεδομένα από τα πεδία
                String name = editTextName.getText().toString();
                String farmerCode = editTextFarmerCode.getText().toString();
                String animalType = spinAnimalType.getSelectedItem().toString();

                if (name.isEmpty()) {
                    editTextName.setError("Το όνομα είναι υποχρεωτικό");
                    return;
                }
                if (farmerCode.isEmpty()) {
                    editTextFarmerCode.setError("Ο κωδικός είναι υποχρεωτικός");
                    return;
                }

                Farmer farmer = new Farmer(name, farmerCode, animalType);
                // Εδώ μπορείς να αποθηκεύσεις τα δεδομένα (π.χ. στην βάση δεδομένων)
                UserManager.getInstance(getApplicationContext()).saveFarmer(farmer);
                // Κλείσιμο του activity και επιστροφή στο προηγούμενο

                // Επιστροφή στο MainActivity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
