package com.example.perceptron_app_bachelorarbeit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perceptron_app_bachelorarbeit.R;
import com.example.perceptron_app_bachelorarbeit.adapter.RecycleViewAdapterNode;
import com.example.perceptron_app_bachelorarbeit.javafiles.DisplayNode;
import com.example.perceptron_app_bachelorarbeit.javafiles.Node;

import java.util.HashMap;

/**
 * Node Activity is used to display a Node which was selected in Main and give it context
 */

public class NodeActivity extends AppCompatActivity {

    private DisplayNode runningNode;
    private DisplayNode previousNode;
    private DisplayNode nextNode;
    private RecyclerView recyclerHighestPoint;
    private RecyclerView recyclerLowestPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        TextView textViewPoint = findViewById(R.id.textViewPointTotal);
        runningNode = MainActivity.storageForData.getRunningNode();
        System.out.println(runningNode.getNodeName());
        previousNode = MainActivity.storageForData.getPreviousNode(runningNode.getNodeName());
        nextNode = MainActivity.storageForData.getNextNode(runningNode.getNodeName());
        textViewPoint.setText("Node - " + runningNode.getNodeName());

        //Button next = findViewById(R.id.buttonRightPoint);
        //Button previous = findViewById(R.id.buttonLeftPoint);
        //next.setText(nextNode.getNodeName());
        //previous.setText(previousNode.getNodeName());

        //next.setOnClickListener(view -> loadNext());
        //previous.setOnClickListener(view -> loadPrevious());
        recyclerHighestPoint = findViewById(R.id.recyclerHighestPoint);
        //recyclerLowestPoint = findViewById(R.id.recyclerTotal);
        Button goToDescription = findViewById(R.id.Icondescription);
        goToDescription.setOnClickListener(view -> goToDescription());
        fillNode();
    }

    /**
     * goToToal opens the TotalNode Activity for a Node
     */

    private void goToDescription(){
        Intent intent = new Intent(this, DescriptionActivity.class);
        startActivity(intent);
    }

    /**
     * loadNext opens the next Node from the List


    private void loadNext(){
        MainActivity.storageForData.setRunningNode(nextNode.getNodeName());
        Intent intent = new Intent(this, NodeActivity.class);
        startActivity(intent);
    }
     */

    /**
     * loadPrevious opens the previous Node from the List


    private void loadPrevious(){
        MainActivity.storageForData.setRunningNode(previousNode.getNodeName());
        Intent intent = new Intent(this, NodeActivity.class);
        startActivity(intent);
    }
    */

    /**
     * fillNode opens the functions to fill the Recycler Views for the Node
     */

    private void fillNode() {
        fillTop();
        //fillBottom();
    }

    /**
     * Fill Top fills the recycler View for the Top elements of the Node
     */

    private void fillTop() {
        HashMap<Integer, String> valuesOfNode = new HashMap<>();
        for(int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++){
            Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
            String valueOfNode = "";
            if(insertNode.getValue().length() <=4) {
                valueOfNode = insertNode.getValue();
            } else {
                valueOfNode = insertNode.getValue().substring(0,3);
            }

            String value = "PV: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
            valuesOfNode.put(indexOfNodes, value);
        }

        RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
        recyclerHighestPoint.setAdapter(adapterForRecycle);
        recyclerHighestPoint.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Fill Bottom fills the recycler View for the bottom elements of the Node
     */

    private void fillBottom() {
        HashMap<Integer, String> bottomValue = new HashMap<>();
        for(int indexForBottomValues = 5; indexForBottomValues < 10; indexForBottomValues++){
            Node insertNode = runningNode.getBestNodes().get(indexForBottomValues);
            String valueOfNode = "";
            if(insertNode.getValue().length() <=6) {
                valueOfNode = insertNode.getValue();
            } else {
                valueOfNode = insertNode.getValue().substring(0,5);
            }
            String value = "PV: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
            bottomValue.put(indexForBottomValues, value);
        }

        RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, bottomValue);
        recyclerLowestPoint.setAdapter(adapterForRecycle);
        recyclerLowestPoint.setLayoutManager(new LinearLayoutManager(this));
    }
}
