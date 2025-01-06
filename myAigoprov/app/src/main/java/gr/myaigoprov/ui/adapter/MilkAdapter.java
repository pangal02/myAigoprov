package gr.myaigoprov.ui.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.myaigoprov.R;
import gr.myaigoprov.model.Milk;

public class MilkAdapter extends RecyclerView.Adapter<MilkAdapter.MilkViewHolder> {

    private final List<Milk> milkList;

    public MilkAdapter(List<Milk> milkList) {
        this.milkList = milkList;
    }

    @NonNull
    @Override
    public MilkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_view_milk, parent, false);
        return new MilkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkViewHolder holder, int position) {
        Milk milk = milkList.get(position);
        holder.tvDate.setText(milk.getDate());
        holder.tvQuantity.setText(String.valueOf(milk.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return milkList.size();
    }

    static class MilkViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvQuantity;

        public MilkViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
}
