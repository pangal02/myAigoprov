package gr.myaigoprov.ui.viewAnimals;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import gr.myaigoprov.R;
import gr.myaigoprov.database.DatabaseHelper;
import gr.myaigoprov.manager.UserManager;
import gr.myaigoprov.model.Farmer;

public class ViewAnimals extends Fragment {
    private DatabaseHelper dbHelper;
    private RadioGroup radioGroup;
    private RadioButton rbGoats;
    private RadioButton rbsheeps;
    private TextView textViewData;
    private ListView listViewAnimals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_animals, container, false);

        dbHelper = new DatabaseHelper(requireContext());
//        textViewData = view.findViewById(R.id.textViewData);

        // RadioGroup για επιλογή Goats ή Sheeps
        radioGroup = view.findViewById(R.id.radioGroup);
        rbGoats = view.findViewById(R.id.radioGoats);
        rbsheeps = view.findViewById(R.id.radioSheeps);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioGoats) {
                displayAnimals("goats");

            } else if (checkedId == R.id.radioSheeps) {
                displayAnimals("sheeps");

            }
        });

        return view;
    }

    private void displayAnimals(String tableName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        // Βρίσκουμε το TableLayout
        TableLayout tableLayout = getView().findViewById(R.id.tableLayoutAnimals);
        tableLayout.removeAllViews();  // Καθαρίζουμε τον πίνακα πριν προσθέσουμε νέες γραμμές

        // Δημιουργία επικεφαλίδων για τον πίνακα
        TableRow headerRow = new TableRow(requireContext());
        String[] headers = {"ID", "Ενώτιο", "Γένος", "Ημ/μνία Εισαγ.", "Έχει γεννήσει κάτω του έτους"};

        // Προσθήκη επικεφαλίδων με ίδιο padding και layout_weight
        for (String header : headers) {
            TextView textView = new TextView(requireContext());
            textView.setText(header);
            textView.setPadding(8, 8, 8, 8);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(android.R.color.white));
            textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1);
            textView.setLayoutParams(params);
            headerRow.addView(textView);
        }

        // Προσθήκη της γραμμής επικεφαλίδας στον πίνακα
        tableLayout.addView(headerRow);

        // Διαβάζουμε τα δεδομένα από τη βάση και τα προσθέτουμε στον πίνακα
        while (cursor.moveToNext()) {
            TableRow dataRow = new TableRow(requireContext());

            // Δεδομένα για κάθε γραμμή
            String[] data = {
                    String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("id"))),
                    String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("tag_number"))),
                    String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("gender"))),
                    String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("birthdate"))),
                    String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("young_mom")) == 1 ? "" : "ΟΧΙ")
            };

            // Προσθήκη των δεδομένων στις στήλες του πίνακα
            for (String item : data) {
                TextView textView = new TextView(requireContext());
                textView.setText(item);
                textView.setPadding(16, 16, 16, 16);
                textView.setGravity(Gravity.CENTER);

                TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
                textView.setLayoutParams(params);
                dataRow.addView(textView);
            }

            // Προσθήκη της γραμμής δεδομένων στον πίνακα
            tableLayout.addView(dataRow);
        }

        // Κλείνουμε τον cursor
        cursor.close();
    }



}