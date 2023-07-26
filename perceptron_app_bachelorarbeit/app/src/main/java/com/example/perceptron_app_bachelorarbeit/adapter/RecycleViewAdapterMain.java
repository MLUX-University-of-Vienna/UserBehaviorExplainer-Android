package com.example.perceptron_app_bachelorarbeit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perceptron_app_bachelorarbeit.R;
import com.example.perceptron_app_bachelorarbeit.activities.MainActivity;
import com.example.perceptron_app_bachelorarbeit.activities.PointActivity;

import java.util.HashMap;
import java.util.List;

public class RecycleViewAdapterMain extends RecyclerView.Adapter<RecycleViewAdapterMain.ViewHolder> {

    private HashMap<Integer, String> dataFromMain;
    private Context mainContext;

    public RecycleViewAdapterMain(Context mainContext, HashMap<Integer, String> dataFromMain) {
        this.dataFromMain = dataFromMain;
        this.mainContext = mainContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View displayView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(displayView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterMain.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        System.out.println(dataFromMain);
        System.out.println(position);
        Integer keyValue = (Integer) dataFromMain.keySet().toArray()[position];
        String displayItem = dataFromMain.get(keyValue);
        holder.display.setText(displayItem);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View displayView) {
                MainActivity.storageForData.setRunningNode(dataFromMain.get(position));
                Intent goToPoint = new Intent(mainContext, PointActivity.class);
                mainContext.startActivity(goToPoint);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataFromMain.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView display;

        public ViewHolder(@NonNull View displayView) {
            super(displayView);
            display = displayView.findViewById(R.id.TextViewListMain);
        }
    }
}
