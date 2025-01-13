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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import gr.myaigoprov.R;
import gr.myaigoprov.database.DatabaseHelper;
import gr.myaigoprov.model.Milk;

public class MilkFragment extends Fragment {
    private ListView listView;
    private MilkAdapter milkAdapter;
    private ArrayList<String> milkArrayList;
    private static Map<String, ArrayList<Milk>> milkMap;
    private DatabaseHelper dbHelper;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_milk, container, false);

        listView = view.findViewById(R.id.listView);
        loadGoatMilk();
        milkAdapter = new MilkAdapter(requireContext(), milkArrayList);

        listView.setAdapter(milkAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(requireContext(), DetailedActivity.class);
                intent.putExtra("date", milkAdapter.getItem(position).toString());
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadGoatMilk(){
        dbHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM goat_milk", null);
        milkArrayList = new ArrayList<>();
        milkMap = new TreeMap<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Πάρε τις τιμές από τον πίνακα
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

                Milk milk = new Milk(quantity, date);
                if(!milkArrayList.contains(milk.getDate())){
                    milkArrayList.add(milk.getDate());
                }
                //MAP
                if(milkMap.containsKey(date)){
                    milkMap.get(date).add(milk);
                }
                else{
                    milkMap.put(date, new ArrayList<>());
                    milkMap.get(date).add(milk);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    public static Map<String, ArrayList<Milk>> getMilkMap(){
        return milkMap;
    }

}
