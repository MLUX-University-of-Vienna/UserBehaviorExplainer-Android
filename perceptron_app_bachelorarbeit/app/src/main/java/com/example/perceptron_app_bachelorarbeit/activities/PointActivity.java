package com.example.perceptron_app_bachelorarbeit.activities;

import android.content.Intent;
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

public class PointActivity extends AppCompatActivity {

    private DisplayNode runningNode;
    private DisplayNode previousNode;
    private DisplayNode nextNode;
    private TextView textViewPoint;
    private Button next;
    private Button previous;
    private Button goToTotal;
    private RecyclerView recyclerHighestPoint;
    private RecyclerView recyclerLowestPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        textViewPoint = findViewById(R.id.textViewPointTotal);
        runningNode = MainActivity.storageForData.getRunningNode();
        System.out.println(runningNode.getNodeName());
        previousNode = MainActivity.storageForData.getPreviousNode(runningNode.getNodeName());
        nextNode = MainActivity.storageForData.getNextNode(runningNode.getNodeName());
        textViewPoint.setText("Node - " + runningNode.getNodeName());
        next = findViewById(R.id.buttonRightPoint);
        previous = findViewById(R.id.buttonLeftPoint);
        next.setText(nextNode.getNodeName());
        previous.setText(previousNode.getNodeName());

        next.setOnClickListener(view -> loadNext());
        previous.setOnClickListener(view -> loadPrevious());
        recyclerHighestPoint = findViewById(R.id.recyclerHighestPoint);
        recyclerLowestPoint = findViewById(R.id.recyclerTotal);
        goToTotal = findViewById(R.id.showTotalPoint);
        goToTotal.setOnClickListener(view -> goToTotal());
        fillNode();

    }

    private void goToTotal(){
        Intent intent = new Intent(this, TotalPoint.class);
        startActivity(intent);
    }

    private void loadNext(){
        MainActivity.storageForData.setRunningNode(nextNode.getNodeName());
        Intent intent = new Intent(this, PointActivity.class);
        startActivity(intent);
    }

    private void loadPrevious(){
        MainActivity.storageForData.setRunningNode(previousNode.getNodeName());
        Intent intent = new Intent(this, PointActivity.class);
        startActivity(intent);
    }


    private void fillNode() {
        fillTop();
        fillBottom();
    }

    private void fillTop() {
        HashMap<Integer, String> topValue = new HashMap<>();
        int counter = 0;
        for(int index = 4; index >= 0; index--){
            Node insert = runningNode.getBestNodes().get(index);
            String value =  "Perceptron Value: " + insert.getValue() + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insert.getEvent());
            topValue.put(counter, value);
            counter++;
        }

        System.out.println(topValue);



        RecycleViewAdapterPoint adapterForRecycle = new RecycleViewAdapterPoint(this, topValue);
        recyclerHighestPoint.setAdapter(adapterForRecycle);
        recyclerHighestPoint.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fillBottom() {
        HashMap<Integer, String> bottomValue = new HashMap<>();
        for(int index = 5; index < 10; index++){
            Node insert = runningNode.getBestNodes().get(index);
            String value = "Perceptron Value: " + insert.getValue() + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insert.getEvent());
            bottomValue.put(index, value);
        }
        System.out.println(bottomValue);

        RecycleViewAdapterPoint adapterForRecycle = new RecycleViewAdapterPoint(this, bottomValue);
        recyclerLowestPoint.setAdapter(adapterForRecycle);
        recyclerLowestPoint.setLayoutManager(new LinearLayoutManager(this));
    }
}
