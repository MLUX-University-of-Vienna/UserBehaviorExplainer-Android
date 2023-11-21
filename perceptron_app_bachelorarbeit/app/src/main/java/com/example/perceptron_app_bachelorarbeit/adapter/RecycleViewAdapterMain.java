package com.example.perceptron_app_bachelorarbeit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perceptron_app_bachelorarbeit.R;
import com.example.perceptron_app_bachelorarbeit.activities.MainActivity;
import com.example.perceptron_app_bachelorarbeit.activities.NodeActivity;

import java.util.HashMap;

/**
 * Recycle View Adapter is used to display Textviews of the Nodes on the Mainpage
 */

public class RecycleViewAdapterMain extends RecyclerView.Adapter<RecycleViewAdapterMain.ViewHolder> {

    private HashMap<Integer, String> dataFromMain;
    private Context mainContext;

    public RecycleViewAdapterMain(Context mainContext, HashMap<Integer, String> dataFromMain) {
        this.dataFromMain = dataFromMain;
        this.mainContext = mainContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parentView, int viewTypeParent) {
        View displayView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.list_item_layout, parentView, false);
        return new ViewHolder(displayView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterMain.ViewHolder csvHolder, @SuppressLint("RecyclerView") int positionOfElement) {
        Integer keyValueOfElement = (Integer) dataFromMain.keySet().toArray()[positionOfElement];
        String displayItem = dataFromMain.get(keyValueOfElement);
        csvHolder.displayTextView.setText(displayItem);

        if(positionOfElement %2 == 0){
            csvHolder.displayTextView.setBackgroundColor(Color.parseColor("#0864A4"));
        } else {
            csvHolder.displayTextView.setBackgroundColor(Color.parseColor("#d3d3d3"));
            csvHolder.displayTextView.setTextColor(Color.parseColor("#000000"));
        }

        csvHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View displayView) {
                MainActivity.storageForData.setRunningNode(dataFromMain.get(positionOfElement));
                Intent goToNode = new Intent(mainContext, NodeActivity.class);
                mainContext.startActivity(goToNode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataFromMain.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView displayTextView;

        public ViewHolder(@NonNull View displayView) {
            super(displayView);
            displayTextView = displayView.findViewById(R.id.TextViewListMain);
        }
    }
}
