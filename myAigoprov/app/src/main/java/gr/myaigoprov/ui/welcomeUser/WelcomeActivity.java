package gr.myaigoprov.ui.welcomeUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import gr.myaigoprov.ui.createUser.CreateUserActivity;
import gr.myaigoprov.R;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Αρχικοποίηση κουμπιού
        btnRegister = findViewById(R.id.btnRegister);

        // Προσθήκη click listener στο κουμπί
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Όταν πατηθεί το κουμπί, ανοίγει το Activity για εγγραφή (CreateUserActivity)
                Intent intent = new Intent(WelcomeActivity.this, CreateUserActivity.class);
                startActivity(intent);
            }
        });
    }
}
