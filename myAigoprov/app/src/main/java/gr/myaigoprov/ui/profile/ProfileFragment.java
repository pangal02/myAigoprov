package gr.myaigoprov.ui.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import gr.myaigoprov.MainActivity;
import gr.myaigoprov.R;
import gr.myaigoprov.database.DatabaseHelper;
import gr.myaigoprov.manager.UserManager;
import gr.myaigoprov.model.Farmer;

public class ProfileFragment extends Fragment {

    private TextView tvFullName;
    private TextView tvFarmerCode;
    private TextView tvAnimalType;
    private Button btnEditProfile;
    private Button btnDeleteUser;
    private Button btnDeleteDatabase;

    private static final String PREFS_NAME = "UserPrefs";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initializing views
        tvFullName = rootView.findViewById(R.id.tvFullName);
        tvFarmerCode = rootView.findViewById(R.id.tvFarmerCode);
        tvAnimalType = rootView.findViewById(R.id.tvAnimalType);
        btnEditProfile = rootView.findViewById(R.id.btnEditUser);
        btnDeleteUser = rootView.findViewById(R.id.btnDeleteUser);
        btnDeleteDatabase = rootView.findViewById(R.id.btnDeleteDatabase);

        // Display user information
        Farmer farmer = UserManager.getInstance(requireContext()).getFarmer();
        setUser(farmer);

        btnEditProfile.setOnClickListener(v ->  {
                // Ξεκινάει το activity επεξεργασίας χρήστη
                Intent intent = new Intent(requireContext(), EditProfileActivity.class);
                intent.putExtra("name", farmer.getName());
                intent.putExtra("farmerCode",farmer.getFarmerCode());
                intent.putExtra("animalsType", farmer.getAnimalsType());
                startActivity(intent);
        });
        // Delete user button
        btnDeleteUser.setOnClickListener(v -> confirmDeleteUser());

        // Delete database button
        btnDeleteDatabase.setOnClickListener(v -> confirmDeleteDatabase());

        return rootView;
    }

    private void setUser(Farmer farmer){
        tvFullName.setText("Κτηνοτρόφος: " + farmer.getName());
        tvFarmerCode.setText("Κωδικός: " + farmer.getFarmerCode());
        tvAnimalType.setText("Είδος Ζώων: " + farmer.getAnimalsType());
    }

    private void deleteUser() {
        // Διαγραφή της βάσης
        deleteDatabase();

        // Delete user from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();  // Clear all saved data
        editor.apply();

        // Show a toast message to inform the user
        Toast.makeText(getActivity(), "Ο χρήστης διαγράφηκε επιτυχώς!", Toast.LENGTH_SHORT).show();

        // Navigate back to MainActivity or Login screen
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish(); // Close the current fragment/activity
    }
    private void confirmDeleteUser() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Διαγραφή Χρήστη!")
                .setMessage("Είστε σίγουροι ότι θέλετε να διαγράψετε τον λογαριασμό; Αν επιλέξετε ΝΑΙ, θα διαγραφούν όλα τα δεδομένα σας. Αυτή η ενέργεια δεν μπορεί να αναιρεθεί.")
                .setPositiveButton("Ναι", (dialog, which) -> deleteUser())
                .setNegativeButton("Όχι", null)
                .show();
    }

    private void confirmDeleteDatabase() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Διαγραφή Βάσης Δεδομένων!")
                .setMessage("Είστε σίγουροι ότι θέλετε να διαγράψετε τη βάση δεδομένων; Αυτή η ενέργεια δεν μπορεί να αναιρεθεί.")
                .setPositiveButton("ΝΑΙ", (dialog, which) -> deleteDatabase())
                .setNegativeButton("ΟΧΙ", null)
                .show();
    }


    private void deleteDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        // Attempt to delete the database
        if (dbHelper.deleteDatabase(getContext())) {
            Toast.makeText(getActivity(), "Η βάση δεδομένων διαγράφηκε επιτυχώς!", Toast.LENGTH_SHORT).show();

            // Create a new database to replace the old one
            dbHelper.getWritableDatabase();
            Toast.makeText(getActivity(), "Η νέα βάση δεδομένων δημιουργήθηκε!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        } else {
            Toast.makeText(getActivity(), "Η διαγραφή της βάσης δεδομένων απέτυχε.", Toast.LENGTH_SHORT).show();
        }
    }

}
