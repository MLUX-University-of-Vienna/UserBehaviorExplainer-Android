package com.example.perceptron_app_bachelorarbeit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perceptron_app_bachelorarbeit.R;

import java.util.HashMap;

/**
 * Recycle View Adapter is used to display Textviews of the Nodes on the Mainpage
 */

public class RecycleViewAdapterElementDescription extends RecyclerView.Adapter<RecycleViewAdapterElementDescription.ViewHolder> {

    private HashMap<Integer, String> dataFromDescription;
    private Context descriptionContext;

    public RecycleViewAdapterElementDescription(Context mainContext, HashMap<Integer, String> dataFromMain) {
        this.dataFromDescription = dataFromMain;
        this.descriptionContext = mainContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parentView, int viewTypeParent) {
        View displayView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.list_item_layout_point, parentView, false);
        return new ViewHolder(displayView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterElementDescription.ViewHolder csvHolder, @SuppressLint("RecyclerView") int positionOfElement) {
        Integer keyValueOfElement = (Integer) dataFromDescription.keySet().toArray()[positionOfElement];
        String displayItem = dataFromDescription.get(keyValueOfElement);
        csvHolder.displayTextView.setText(displayItem);
        csvHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View displayView) {
                System.out.println("Clicked but not needed");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataFromDescription.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView displayTextView;

        public ViewHolder(@NonNull View displayView) {
            super(displayView);
            displayTextView = displayView.findViewById(R.id.TextViewListPoint);
        }
    }
}
