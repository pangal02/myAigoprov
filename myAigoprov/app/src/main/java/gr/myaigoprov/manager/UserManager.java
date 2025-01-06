package gr.myaigoprov.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import gr.myaigoprov.model.Farmer;

public class UserManager {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_FARMER = "farmer";

    private static UserManager instance;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    // Ιδιωτικός constructor για Singleton pattern
    private UserManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Δημιουργία ή επιστροφή της υπάρχουσας instance
    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context.getApplicationContext());
        }
        return instance;
    }

    // Αποθήκευση χρήστη
    public void saveFarmer(Farmer farmer) {
        String farmerJson = gson.toJson(farmer);
        sharedPreferences.edit().putString(KEY_FARMER, farmerJson).apply();
    }

    // Ανάκτηση χρήστη
    public Farmer getFarmer() {
        String farmerJson = sharedPreferences.getString(KEY_FARMER, null);
        if (farmerJson == null) {
            return null; // Αν δεν υπάρχει χρήστης, επιστρέφει null
        }
        return gson.fromJson(farmerJson, Farmer.class);
    }

    // Διαγραφή χρήστη
    public void deleteFarmer() {
        sharedPreferences.edit().remove(KEY_FARMER).apply();
    }
}
