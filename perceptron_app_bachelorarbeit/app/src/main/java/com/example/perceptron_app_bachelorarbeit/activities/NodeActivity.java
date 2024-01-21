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
import com.example.perceptron_app_bachelorarbeit.javafiles.Storage;

import java.util.HashMap;

/**
 * Node Activity is used to display a Node which was selected in Main and give it context
 */

public class NodeActivity extends AppCompatActivity {

    private DisplayNode runningNode;
    private RecyclerView recyclerPoints;
    private Boolean allDataSet = true;

    private TextView pointInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        TextView textViewPoint = findViewById(R.id.textViewPointTotal);
        runningNode = MainActivity.storageForData.getRunningNode();
        System.out.println(runningNode.getNodeName());
        textViewPoint.setText("Node - " + runningNode.getNodeName());
        recyclerPoints = findViewById(R.id.recyclerHighestPoint);
        pointInformation = findViewById(R.id.pointInformation);
        pointInformation.setText("No Category selected - Please select one");
        //Buttons and Listeners

        Button goToDescription = findViewById(R.id.Icondescription);
        goToDescription.setOnClickListener(view -> goToDescription());

        Button allValuesOfNode = findViewById(R.id.allDataPoint);
        allValuesOfNode.setOnClickListener(view -> allValuesOfNodeActive());

        Button topDataPoint = findViewById(R.id.topDataPoint);
        topDataPoint.setOnClickListener(view -> topDataPointActive());

        Button weatherData = findViewById(R.id.weatherData);
        weatherData.setOnClickListener(view -> fillWeatherNodeValues());

        Button timeData = findViewById(R.id.timeData);
        timeData.setOnClickListener(view -> fillTimeNodeValues());

        Button temperatureData = findViewById(R.id.temperatureData);
        temperatureData.setOnClickListener(view -> fillTemperatureNodeValues());
    }

    /**
     * goToToal opens the TotalNode Activity for a Node
     */
    private void goToDescription(){
        Intent intent = new Intent(this, DescriptionActivity.class);
        startActivity(intent);
    }

    private void allValuesOfNodeActive(){
        allDataSet = true;
        pointInformation.setText("All Values of Node ");
        fillAllNodeValues();
    }

    private void topDataPointActive() {
        allDataSet = false;
        pointInformation.setText("Highest and Lowest Node Values");
        fillTopNodeValues();
    }

    private void fillAllNodeValues() {
        HashMap<Integer, String> valuesOfNode = new HashMap<>();
        for(int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++){
            Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
            String valueOfNode = "";
            if(insertNode.getValue().length() <=4) {
                valueOfNode = insertNode.getValue();
            } else {
                valueOfNode = insertNode.getValue().substring(0,5);
            }

            String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
            valuesOfNode.put(indexOfNodes, value);
        }

        RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
        recyclerPoints.setAdapter(adapterForRecycle);
        recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fillTopNodeValues() {
        HashMap<Integer, String> valuesOfNode = new HashMap<>();
        for(int indexOfNodes = 0; indexOfNodes < runningNode.getBestNodes().size(); indexOfNodes++){
            Node insertNode = runningNode.getBestNodes().get(indexOfNodes);
            String valueOfNode = "";
            if(insertNode.getValue().length() <=4) {
                valueOfNode = insertNode.getValue();
            } else {
                valueOfNode = insertNode.getValue().substring(0,5);
            }
            String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
            valuesOfNode.put(indexOfNodes, value);
        }

        RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
        recyclerPoints.setAdapter(adapterForRecycle);
        recyclerPoints.setLayoutManager(new LinearLayoutManager(this));

        pointInformation.setText("Highest and Lowest of Node");
    }

    private void fillWeatherNodeValues() {
        if(allDataSet){
            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for(int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++){
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
                if(insertNode.getEvent().contains("p_n")){
                    String valueOfNode = "";
                    if(insertNode.getValue().length() <=4) {
                        valueOfNode = insertNode.getValue();
                    } else {
                        valueOfNode = insertNode.getValue().substring(0,5);
                    }

                    String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                    valuesOfNode.put(indexOfNodes, value);
                }
            }

            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
            pointInformation.setText("All Values of Node for Weather");

        } else {
            HashMap<Integer, Node> insertWheater = new HashMap<>();
            for(int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++) {
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
                if (insertNode.getEvent().contains("p_n")) {
                    insertWheater.put(indexOfNodes, insertNode);
                }
            }

            DisplayNode topAndWorstWeather = new DisplayNode(insertWheater);
            topAndWorstWeather = MainActivity.storageForData.getBestAndWorst(topAndWorstWeather);

            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for(int indexOfNodes = 0; indexOfNodes < topAndWorstWeather.getBestNodes().size(); indexOfNodes++){
                Node insertNode = topAndWorstWeather.getBestNodes().get(indexOfNodes);
                String valueOfNode = "";
                if(insertNode.getValue().length() <=4) {
                    valueOfNode = insertNode.getValue();
                } else {
                    valueOfNode = insertNode.getValue().substring(0,5);
                }
                String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                valuesOfNode.put(indexOfNodes, value);
            }
            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));

            pointInformation.setText("Highest and Lowest Values for Weather");
        }
    }

    private void fillTimeNodeValues() {
        if(allDataSet){
            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for(int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++){
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);

                if(insertNode.getEvent().contains("tq_n")){

                    String valueOfNode = "";
                    if(insertNode.getValue().length() <=4) {
                        valueOfNode = insertNode.getValue();
                    } else {
                        valueOfNode = insertNode.getValue().substring(0,5);
                    }
                    String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                    valuesOfNode.put(indexOfNodes, value);
                }
            }

            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
            pointInformation.setText("All Values of Node for Time");
        } else {
            HashMap<Integer, Node> insertTime = new HashMap<>();
            for(int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++) {
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
                if (insertNode.getEvent().contains("tq_n")) {
                    insertTime.put(indexOfNodes, insertNode);
                }
            }

            DisplayNode topAndWorstTime = new DisplayNode(insertTime);
            topAndWorstTime = MainActivity.storageForData.getBestAndWorst(topAndWorstTime);

            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for(int indexOfNodes = 0; indexOfNodes < topAndWorstTime.getBestNodes().size(); indexOfNodes++){
                Node insertNode = topAndWorstTime.getBestNodes().get(indexOfNodes);
                String valueOfNode = "";
                if(insertNode.getValue().length() <=4) {
                    valueOfNode = insertNode.getValue();
                } else {
                    valueOfNode = insertNode.getValue().substring(0,5);
                }
                String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                valuesOfNode.put(indexOfNodes, value);
            }

            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
            pointInformation.setText("Highest and Lowest Values for Time");
        }
    }

    private void fillTemperatureNodeValues() {
        if(allDataSet){
            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for(int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++){
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);

                if(insertNode.getEvent().contains("t_n")){
                    String valueOfNode = "";
                    if(insertNode.getValue().length() <=4) {
                        valueOfNode = insertNode.getValue();
                    } else {
                        valueOfNode = insertNode.getValue().substring(0,5);
                    }

                    String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                    valuesOfNode.put(indexOfNodes, value);
                }
            }

            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
            pointInformation.setText("All Values of Node for Temperature");
        } else {
            HashMap<Integer, Node> insertTemperature = new HashMap<>();
            for(int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++) {
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
                if (insertNode.getEvent().contains("t_n")) {
                    insertTemperature.put(indexOfNodes, insertNode);
                }
            }

            DisplayNode topAndWorstTemperature = new DisplayNode(insertTemperature);
            topAndWorstTemperature = MainActivity.storageForData.getBestAndWorst(topAndWorstTemperature);

            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for(int indexOfNodes = 0; indexOfNodes < topAndWorstTemperature.getBestNodes().size(); indexOfNodes++){
                Node insertNode = topAndWorstTemperature.getBestNodes().get(indexOfNodes);
                String valueOfNode = "";
                if(insertNode.getValue().length() <=4) {
                    valueOfNode = insertNode.getValue();
                } else {
                    valueOfNode = insertNode.getValue().substring(0,5);
                }
                String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                valuesOfNode.put(indexOfNodes, value);
            }

            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));

            pointInformation.setText("Highest and Lowest Values for Temperature");
        }
    }
}
