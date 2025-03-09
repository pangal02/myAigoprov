package gr.myaigoprov.ui.milk;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import gr.myaigoprov.R;
import gr.myaigoprov.database.DatabaseHelper;
import gr.myaigoprov.model.Milk;

public class MilkFragment extends Fragment {
    private ListView listView;
    private MilkProductionAdapter milkProdAdapter;
    private ArrayList<Milk> dailyList;
    private DatabaseHelper dbHelper;
    private double totalQuantity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_milk, container, false);

        listView = view.findViewById(R.id.listView);
        loadGoatMilk();
        milkProdAdapter = new MilkProductionAdapter(requireContext(), dailyList);

        listView.setAdapter(milkProdAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(requireContext(), DetailedActivity.class);
                intent.putExtra("date", milkProdAdapter.getItem(position).getDate());
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadGoatMilk(){
        dbHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String countQuery = "SELECT date, SUM(quantity) FROM goat_milk GROUP BY date";
        Cursor cursor = db.rawQuery(countQuery, null);
        dailyList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0); // Πρώτη στήλη: date
                totalQuantity = cursor.getDouble(1); // Δεύτερη στήλη: SUM(quantity)
                Log.i("Database SUM MILK", "Date: " + date + ", Total Quantity: " + totalQuantity);

                dailyList.add(new Milk(totalQuantity, date));

            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        //ταξινομεί την λίστα με βάση την ημερομηνία σε φθίνουσα σειρά
        dailyList.sort((milk1, milk2) -> milk2.getDate().compareTo(milk1.getDate()));
    }

}
