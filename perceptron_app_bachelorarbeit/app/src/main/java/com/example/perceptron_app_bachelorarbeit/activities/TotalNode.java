package com.example.perceptron_app_bachelorarbeit.activities;

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
 * TotalNode is a Activity which displays all Values to a given Node which was selected in the previous activity
 */

public class TotalNode extends AppCompatActivity {

    private RecyclerView totalPointRecycler;
    private DisplayNode runningNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_node);
        runningNode = MainActivity.storageForData.getRunningNode();
        Button backToPointOverview = findViewById(R.id.backToPointOverview);
        TextView totalPoint = findViewById(R.id.textViewPointTotal);
        totalPointRecycler = findViewById(R.id.recyclerTotal);
        totalPoint.setText("Node - " + runningNode.getNodeName());

       backToPointOverview.setOnClickListener(view -> goBack());
       fillRecyclerTotal();
    }

    /**
     * goBack goes back to the previous node which was displayed
     */
    private void goBack() {
        finish();
    }

    /**
     * Fills the Recycler with all values and then shows it to the user
     */

    private void fillRecyclerTotal(){
        HashMap<Integer, String> valuesOfNode = new HashMap<>();
        for(int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++){
            Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
            String value = "Perceptron Value: " + insertNode.getValue() + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
            valuesOfNode.put(indexOfNodes, value);
        }

        RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
        totalPointRecycler.setAdapter(adapterForRecycle);
        totalPointRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

}
