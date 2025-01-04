package gr.myaigoprov.user;

import android.content.SharedPreferences;
import android.content.Context;

import gr.myaigoprov.model.Farmer;

public class UserPreferences  {
    private static final String PREFS_FULLNAME = "UserPrefs";
    private static final String KEY_FARMER_CODE = "UserCode";
    private SharedPreferences sharedPreferences;

    public UserPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_FULLNAME, Context.MODE_PRIVATE);
    }

    // Αποθήκευση δεδομένων χρήστη
    public void saveUser(String username, String farmerCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FARMER_CODE, farmerCode);
        editor.apply();
    }

    // Ανάκτηση δεδομένων χρήστη
    public Farmer getUser() {
        String farmerCode = sharedPreferences.getString(KEY_FARMER_CODE, "EL12345");
        return new Farmer(PREFS_FULLNAME, farmerCode);
    }

    // Διαγραφή δεδομένων χρήστη
    public void clearUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
