package gr.myaigoprov.ui.milk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import gr.myaigoprov.database.DatabaseHelper;
import gr.myaigoprov.model.Milk;

public class DetailedActivity extends AppCompatActivity {

    private static class DetailedMilkAdapter extends ArrayAdapter<Milk> {
        DetailedMilkAdapter(@NonNull Context context, ArrayList<Milk> dataArrayList) {
            super(context, R.layout.list_detailed_milk, dataArrayList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
            Milk milk = getItem(position);
            Log.d("Milk", "Milk{" + milk + "}");

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
    private ArrayList<Milk> dailyMilkList;

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

            detailDate.setText(date);
            loadGoatMilk(date);
                // Εδώ φτιάχνουμε τον adapter με τη λίστα
                milkAdapter = new DetailedMilkAdapter(getApplicationContext(), dailyMilkList);

                // Ρυθμίζουμε το adapter στο ListView
                listView.setAdapter(milkAdapter);

    }

    private void loadGoatMilk(String date){
        try {

            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String quantityQuery = "SELECT quantity FROM goat_milk WHERE date = ?";
            Cursor cursor = db.rawQuery(quantityQuery, new String[]{date});

            Log.d("Database Daily MILK", "Query: " + quantityQuery);
            dailyMilkList = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    double quantity = cursor.getDouble(0);
                    Log.i("Database Daily MILK", "Date: " + date + ", Quantity: " + quantity);
                    dailyMilkList.add(new Milk(quantity, date));
                } while (cursor.moveToNext());

            } else {
                Log.e("Database Daily MILK", "No data found for query: " + quantityQuery);
                Toast.makeText(this, "0 αποτελεσματα!", Toast.LENGTH_SHORT).show();
            }
            if (dailyMilkList.isEmpty()) {
                Toast.makeText(this, "Δεν βρέθηκεαν για: " + date, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}