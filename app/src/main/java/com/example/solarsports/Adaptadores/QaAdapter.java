package com.example.solarsports.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.solarsports.databinding.ItemQuestionBinding;

import java.util.List;

public class QaAdapter extends RecyclerView.Adapter<QaAdapter.ViewHolder> {
    private List<String> question;
    private OnQuestionSelectListener select;

    public interface OnQuestionSelectListener {
        void onQuestionSelect(int position);
    }

    public QaAdapter(List<String> question, OnQuestionSelectListener select) {
        this.question = question;
        this.select = select;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemQuestionBinding binding;

        public ViewHolder(ItemQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.tvQuestionSuggestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (select != null) {
                        select.onQuestionSelect(getAdapterPosition());
                    }
                }
            });
        }

        public void bind(String question) {
            binding.tvQuestionSuggestion.setText(question);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemQuestionBinding binding = ItemQuestionBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(question.get(position));
    }

    @Override
    public int getItemCount() {
        return question.size();
    }
}