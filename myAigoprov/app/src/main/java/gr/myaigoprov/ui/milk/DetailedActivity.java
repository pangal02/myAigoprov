package gr.myaigoprov.ui.milk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import gr.myaigoprov.MainActivity;
import gr.myaigoprov.R;
import gr.myaigoprov.model.Milk;

public class DetailedActivity extends AppCompatActivity {

    private Map<String, ArrayList<Milk>> milkMap = new TreeMap<>(MilkFragment.getMilkMap());
    private class DetailedMilkAdapter extends ArrayAdapter<Milk> {
        DetailedMilkAdapter(@NonNull Context context, ArrayList<Milk> dataArrayList) {
            super(context, R.layout.list_detailed_milk, dataArrayList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
            Milk milk = getItem(position);

            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.list_detailed_milk, parent, false);
            }

            // Εδώ αναγνωρίζεις τα views για κάθε στοιχείο
            TextView textViewDate = view.findViewById(R.id.textViewDate);
            TextView textViewQuantity = view.findViewById(R.id.textViewQuantity);

            // Ρυθμίζεις τα δεδομένα στα views
            textViewDate.setText(milk.getDate());
            textViewQuantity.setText(String.format("%.2f kg", milk.getQuantity()));

            return view;
        }
    }

    private TextView detailDate;
    private ImageView imageView;
    private ListView listView;
    private DetailedMilkAdapter milkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed);

        imageView = findViewById(R.id.detailImage);
        detailDate = findViewById(R.id.detailDate);
        listView = findViewById(R.id.listView);

        imageView.setImageResource(R.drawable.goat_milk);

        // Παίρνουμε την ημερομηνία από το Intent
        String date = getIntent().getStringExtra("date");
        if (date != null) {
            detailDate.setText(date);

            // Αν υπάρχει η ημερομηνία στο milkMap, φτιάχνουμε τη λίστα
            if(milkMap.containsKey(date)) {
                ArrayList<Milk> milks = new ArrayList<>(milkMap.get(date));
                // Εδώ φτιάχνουμε τον adapter με τη λίστα
                milkAdapter = new DetailedMilkAdapter(getApplicationContext(), milks);

                // Ρυθμίζουμε το adapter στο ListView
                listView.setAdapter(milkAdapter);
            }
        }
        else {
            // Αν δεν έχει βρεθεί η ημερομηνία, μπορείς να εμφανίσεις κάποιο μήνυμα ή να επιστρέψεις
            Toast.makeText(this, "Δεν βρέθηκαν δεδομένα για αυτήν την ημερομηνία", Toast.LENGTH_SHORT).show();
        }
    }
}