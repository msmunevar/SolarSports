package com.example.solarsports.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.solarsports.databinding.ItemDatasetBinding;

import java.util.List;

public class DatasetAdapter extends RecyclerView.Adapter<DatasetAdapter.ViewHolder> {

    private List<String> dataSetTitle;
    private OnItemSelectedListener onSelected;

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    public DatasetAdapter(List<String> dataSetTitle, OnItemSelectedListener onSelected) {
        this.dataSetTitle = dataSetTitle;
        this.onSelected = onSelected;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDatasetBinding binding;

        public ViewHolder(ItemDatasetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.tvDataTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSelected.onItemSelected(getAdapterPosition());
                }
            });
        }

        public void bind(String title) {
            binding.tvDataTitle.setText(title);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDatasetBinding binding = ItemDatasetBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(dataSetTitle.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSetTitle.size();
    }
}
