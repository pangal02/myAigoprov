package gr.myaigoprov;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.widget.TextView;

import gr.myaigoprov.databinding.ActivityMainBinding;
import gr.myaigoprov.manager.UserManager;
import gr.myaigoprov.model.Farmer;
import gr.myaigoprov.ui.createUser.CreateUserActivity;
import gr.myaigoprov.ui.welcomeUser.WelcomeActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView fullnameTextView;
    private TextView farmerCodeTextView;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_FARMER = "farmer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Farmer farmer = UserManager.getInstance(this).getFarmer();

        if (farmer == null) {
            // Μετάβαση στο CreateUserActivity αν δεν υπάρχει χρήστης
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish(); // Τερματίζουμε το MainActivity για να μην επιστρέψει εδώ
        }
            // Ρύθμιση πλοήγησης
            setupNavigation();
    }


    public void setupNavigation() {
        // Ρύθμιση του Toolbar και του Navigation Drawer
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Διαμόρφωση του AppBarConfiguration για σωστή πλοήγηση
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_add_animal, R.id.nav_add_born_animals, R.id.exit_animals,
                R.id.nav_add_milk,
                R.id.nav_view_animals, R.id.view_milk,
                R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Εμφάνιση του μενού στην Action Bar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        fullnameTextView = findViewById(R.id.textViewFullname);
        farmerCodeTextView = findViewById(R.id.textViewFarmerCode);
        Farmer farmer = UserManager.getInstance(this).getFarmer();

        if(farmer.getName() != null && farmer.getFarmerCode() != null){
            fullnameTextView.setText(farmer.getName());
            farmerCodeTextView.setText(farmer.getFarmerCode());
        }
        else{
            fullnameTextView.setText("[Όνομα Κτηνοτρόφου]");
            farmerCodeTextView.setText("[Κωδικός Κτηνοτρόφου]");
        }
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }


}