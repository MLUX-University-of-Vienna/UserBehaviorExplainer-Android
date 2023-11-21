package com.example.perceptron_app_bachelorarbeit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perceptron_app_bachelorarbeit.R;

import java.util.HashMap;

/**
 * Recycle View Adapter is used to display Textviews of the values of a given Node
 */

public class RecycleViewAdapterNode extends RecyclerView.Adapter<RecycleViewAdapterNode.ViewHolder> {

    private HashMap<Integer, String> dataFromNode;
    private Context nodeContext;

    public RecycleViewAdapterNode(Context nodeContext, HashMap<Integer, String> dataFromNode) {
        this.dataFromNode = dataFromNode;
        this.nodeContext = nodeContext;
    }


    @NonNull
    @Override
    public RecycleViewAdapterNode.ViewHolder onCreateViewHolder(@NonNull ViewGroup parentView, int viewTypeParent) {
        View displayView = LayoutInflater.from(parentView
            .getContext()).inflate(R.layout.list_item_layout_point, parentView
            , false);
        return new RecycleViewAdapterNode.ViewHolder(displayView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder csvElementHolder, int positionOfCSVElement) {
        Integer keyValueOfItem = (Integer) dataFromNode.keySet().toArray()[positionOfCSVElement];
        String displayItem = dataFromNode.get(keyValueOfItem);
        csvElementHolder.displayTextView.setText(displayItem);

        if(positionOfCSVElement %2 == 0){
            csvElementHolder.displayTextView.setBackgroundColor(Color.parseColor("#0864A4"));
        } else {
            csvElementHolder.displayTextView.setBackgroundColor(Color.parseColor("#d3d3d3"));
            csvElementHolder.displayTextView.setTextColor(Color.parseColor("#000000"));
        }


        csvElementHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View displayView) {
                System.out.println("Clicked but not needed");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataFromNode.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView displayTextView;

        public ViewHolder(@NonNull View displayView) {
            super(displayView);
            displayTextView = displayView.findViewById(R.id.TextViewListPoint);
        }
    }
}