package gr.myaigoprov.ui.milk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import gr.myaigoprov.MainActivity;
import gr.myaigoprov.R;
import gr.myaigoprov.model.Milk;

public class MilkAdapter extends ArrayAdapter<String> {
    private Map<String, ArrayList<Milk>> milkMap = new TreeMap<>(MilkFragment.getMilkMap());
    private ArrayList<Milk> dayMilk = new ArrayList<>();
    public MilkAdapter(@NonNull Context context, ArrayList<String> dataAarrayList) {
        super(context, R.layout.list_milk, dataAarrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent){
        String date = getItem(position);
        double count = 0;

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_milk, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.listImage);
        TextView textViewDate = view.findViewById(R.id.listDate);
        TextView textViewSumQuantity = view.findViewById(R.id.listQuantity);

        imageView.setImageResource(R.drawable.goat);
        textViewDate.setText(date);
        dayMilk = milkMap.get(date);
        for(Milk m : dayMilk){
            count += m.getQuantity();
        }
        textViewSumQuantity.setText(String.format("%.2f kg", count));

        return view;
    }

}
