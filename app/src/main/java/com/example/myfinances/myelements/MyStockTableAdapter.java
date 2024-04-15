package com.example.myfinances.myelements;

import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinances.R;

import java.util.List;

import lombok.Getter;

public class MyStockTableAdapter extends RecyclerView.Adapter<MyStockTableAdapter.MyViewHolder> {

    private List<String> data;

    public MyStockTableAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyStockTableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_stock_table_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.getTextView().setText(Html.fromHtml(data.get(position), Html.FROM_HTML_MODE_COMPACT));
        }
        holder.getTextView().setTextSize(10);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView stockTableRow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stockTableRow = itemView.findViewById(R.id.stock_table_row);
        }

        public TextView getTextView() {
            return stockTableRow;
        }
    }
}
