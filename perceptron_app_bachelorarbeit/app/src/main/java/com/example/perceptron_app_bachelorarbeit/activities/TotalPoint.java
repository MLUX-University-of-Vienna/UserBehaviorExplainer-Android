package com.example.perceptron_app_bachelorarbeit.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perceptron_app_bachelorarbeit.R;
import com.example.perceptron_app_bachelorarbeit.adapter.RecycleViewAdapterPoint;
import com.example.perceptron_app_bachelorarbeit.javafiles.DisplayNode;
import com.example.perceptron_app_bachelorarbeit.javafiles.Node;

import java.util.HashMap;

public class TotalPoint extends AppCompatActivity {

    private Button backToPointOverview;
    private TextView totalPoint;
    private RecyclerView totalPointRecycler;
    private DisplayNode runningNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_point);
        runningNode = MainActivity.storageForData.getRunningNode();
        backToPointOverview = findViewById(R.id.backToPointOverview);
        totalPoint = findViewById(R.id.textViewPointTotal);
        totalPointRecycler = findViewById(R.id.recyclerTotal);
        totalPoint.setText("Node - " + runningNode.getNodeName());

       backToPointOverview.setOnClickListener(view -> goBack());
       fillRecyclerTotal();
    }

    private void goBack() {
        finish();
    }

    private void fillRecyclerTotal(){
        HashMap<Integer, String> valuesOfNode = new HashMap<>();
        for(int index = 0; index < runningNode.getNodesComplete().size(); index++){
            Node insert = runningNode.getNodesComplete().get(index);
            String value = "Perceptron Value: " + insert.getValue() + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insert.getEvent());
            valuesOfNode.put(index, value);
        }

        RecycleViewAdapterPoint adapterForRecycle = new RecycleViewAdapterPoint(this, valuesOfNode);
        totalPointRecycler.setAdapter(adapterForRecycle);
        totalPointRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

}
