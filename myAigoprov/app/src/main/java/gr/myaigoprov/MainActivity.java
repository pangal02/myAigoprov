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
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_add_animal, R.id.nav_add_born_animals, R.id.nav_add_milk, R.id.nav_view_animals, R.id.recycler_view_milk, R.id.nav_profile)
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




////////////////////////////////////////////////////////////
//package gr.myaigoprov;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.View;
//import android.widget.TextView;
//
//import com.google.android.material.snackbar.Snackbar;
//import com.google.android.material.navigation.NavigationView;
//
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.navigation.ui.NavigationUI;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.appcompat.app.AppCompatActivity;
//
//import gr.myaigoprov.databinding.ActivityMainBinding;
//import gr.myaigoprov.model.Farmer;
//import gr.myaigoprov.ui.welcomeUser.WelcomeUserFragment;
//
//public class MainActivity extends AppCompatActivity {
//    public MainActivity(){}
//    private AppBarConfiguration mAppBarConfiguration;
//    private ActivityMainBinding binding;
//    private static final String PREFS_NAME = "UserPrefs";
//    private static final String KEY_FARMER_FULLNAME = "UserFarmerName";
//    private static final String KEY_FARMER_CODE = "UserFarmerCode";
//    private static final String KEY_FARMER_ANIMALS_TYPE = "UserFarmerAnimalType";
//    private static final String KEY_USER_CREATED = "userCreated";
//    private static Farmer farmer = new Farmer();
//    private TextView fullnameTextView;
//    private TextView farmerCodeTextView;
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////
////        binding = ActivityMainBinding.inflate(getLayoutInflater());
////        setContentView(binding.getRoot());
////
////        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
////        boolean isUserCreated = sharedPreferences.getBoolean(KEY_USER_CREATED, false);
////
////        if (!isUserCreated) {
////            // Μετάβαση στο Fragment δημιουργίας χρήστη αν δεν υπάρχει χρήστης
////            getSupportFragmentManager().beginTransaction()
////                    .replace(R.id.nav_host_fragment_content_main, new WelcomeUserFragment())
////                    .commit();
////        }
////        else {
////            // Αν υπάρχει ήδη χρήστης, φόρτωσε τα δεδομένα του χρήστη
////            String name = sharedPreferences.getString(KEY_FARMER_FULLNAME, "");
////            String farmerCode = sharedPreferences.getString(KEY_FARMER_CODE, "");
////            String animalType = sharedPreferences.getString(KEY_FARMER_ANIMALS_TYPE, "");
////            farmer = new Farmer(name, farmerCode, animalType);
////
////            // Ρύθμιση του Toolbar και του Navigation Drawer
////            setSupportActionBar(binding.appBarMain.toolbar);
////
////            binding.appBarMain.toolbar.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                            .setAction("Action", null)
////                            .setAnchorView(R.id.toolbar).show();
////                }
////            });
////
////
////            DrawerLayout drawer = binding.drawerLayout;
////            NavigationView navigationView = binding.navView;
////
////            // Διαμόρφωση του AppBarConfiguration για τη σωστή πλοήγηση
////            mAppBarConfiguration = new AppBarConfiguration.Builder(
////                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
////                    R.id.nav_add_animal, R.id.nav_add_all_animals, R.id.nav_view, R.id.nav_profile)
////                    .setOpenableLayout(drawer)
////                    .build();
////
////            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
////            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
////            NavigationUI.setupWithNavController(navigationView, navController);
////
////        }
////    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Εμφάνιση του μενού στην action bar
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//
//        fullnameTextView = findViewById(R.id.textViewFullname);
//        farmerCodeTextView = findViewById(R.id.textViewFarmerCode);
//
//        // Αν υπάρχουν δεδομένα χρήστη, εμφάνισέ τα
////        if(farmer.getName() != null && farmer.getFarmerCode() != null){
//            fullnameTextView.setText(farmer.getName());
//            farmerCodeTextView.setText(farmer.getFarmerCode());
////        }
////        else{
////            fullnameTextView.setText("[Όνομα Κτηνοτρόφου]");
////            farmerCodeTextView.setText("[Κωδικός Κτηνοτρόφου]");
////        }
//
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
//
//    public static Farmer getFarmer() {
//        return farmer;
//    }
//
//    public static void setFarmer(Farmer newFarmer){
//        if(newFarmer == null){
//            throw new IllegalArgumentException("Δεν μπορείτε να αλλάξετε χρήστη! Επειδή ο νέος χρήστης είναι κενός.");
//        }
//        else {
//            farmer = newFarmer;
//        }
//    }
//
//}
