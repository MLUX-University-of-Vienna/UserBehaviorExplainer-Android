package com.example.perceptron_app_bachelorarbeit.adapter;

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
    public RecycleViewAdapterNode.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View displayView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout_point, parent, false);
        return new RecycleViewAdapterNode.ViewHolder(displayView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.println(dataFromNode);
        System.out.println(position);
        Integer keyValue = (Integer) dataFromNode.keySet().toArray()[position];
        String displayItem = dataFromNode.get(keyValue);
        holder.displayTextView.setText(displayItem);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View displayView) {
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