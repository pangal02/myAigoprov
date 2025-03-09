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

import gr.myaigoprov.R;
import gr.myaigoprov.model.Milk;

public class MilkProductionAdapter extends ArrayAdapter<Milk> {
    private double totalQuantity;

    public MilkProductionAdapter(@NonNull Context context, @NonNull ArrayList<Milk> dailyMilkProductions) {
        super(context, R.layout.list_milk, dailyMilkProductions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent){
        Milk currentProduction = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_milk, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.listImage);
        TextView textViewDate = view.findViewById(R.id.listDate);
        TextView textViewSumQuantity = view.findViewById(R.id.listQuantity);

        imageView.setImageResource(R.drawable.goat);
        textViewDate.setText(currentProduction.getDate());
        totalQuantity = currentProduction.getQuantity();
        textViewSumQuantity.setText(String.format("%.2f kg", totalQuantity));

        return view;
    }

}
