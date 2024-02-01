package com.example.perceptron_app_bachelorarbeit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
    private RecyclerView recyclerPoints;
    private Boolean allDataSet = true;

    Button goToDescription;

    Button allValuesOfNode;

    Button topDataPoint;

    Button weatherData;

    Button timeData;

    Button temperatureData;

    Button allData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        TextView textViewPoint = findViewById(R.id.textViewPointTotal);
        runningNode = MainActivity.storageForData.getRunningNode();
        System.out.println(runningNode.getNodeName());
        textViewPoint.setText("Node - " + runningNode.getNodeName());
        recyclerPoints = findViewById(R.id.recyclerHighestPoint);
        //Buttons and Listeners

        goToDescription = findViewById(R.id.Icondescription);
        goToDescription.setOnClickListener(view -> goToDescription());

        allValuesOfNode = findViewById(R.id.allDataPoint);
        allValuesOfNode.setOnClickListener(view -> allValuesOfNodeActive());

        topDataPoint = findViewById(R.id.topDataPoint);
        topDataPoint.setOnClickListener(view -> topDataPointActive());

        weatherData = findViewById(R.id.weatherData);
        weatherData.setOnClickListener(view -> fillWeatherNodeValues());

        timeData = findViewById(R.id.timeData);
        timeData.setOnClickListener(view -> fillTimeNodeValues());

        temperatureData = findViewById(R.id.temperatureData);
        temperatureData.setOnClickListener(view -> fillTemperatureNodeValues());

        allData = findViewById(R.id.allData);
        allData.setOnClickListener(view -> allDataFill());

        topDataPointActive();
    }

    /**
     * goToToal opens the TotalNode Activity for a Node
     */
    private void goToDescription() {
        Intent intent = new Intent(this, DescriptionActivity.class);
        startActivity(intent);
    }

    private void resetButtonColors(){
        temperatureData.setBackgroundResource(R.drawable.standard_button);
        timeData.setBackgroundResource(R.drawable.standard_button);
        weatherData.setBackgroundResource(R.drawable.standard_button);
        topDataPoint.setBackgroundResource(R.drawable.standard_button);
        allValuesOfNode.setBackgroundResource(R.drawable.standard_button);
        allData.setBackgroundResource(R.drawable.standard_button);
    }

    private void allDataFill() {
        allData.setBackgroundResource(R.drawable.standard_button_alternative_2);
        if(allDataSet) {
            fillAllNodeValues();
        } else {
            fillTopNodeValues();
        }
    }

    /**
     * allValuesOfNodeActive represents the selection of All data
     */
    private void allValuesOfNodeActive() {
        allDataSet = true;
        fillAllNodeValues();
    }
    /**
     * allValuesOfNodeActive represents the selection of highest and lowest data
     */
    private void topDataPointActive() {
        allDataSet = false;
        fillTopNodeValues();
    }

    /**
     * Fills the Recycler with the complete data of the Node
     */
    private void fillAllNodeValues() {
        resetButtonColors();
        allValuesOfNode.setBackgroundResource(R.drawable.standard_button_alternative_2);
        allData.setBackgroundResource(R.drawable.standard_button_alternative_2);
        HashMap<Integer, String> valuesOfNode = new HashMap<>();
        for (int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++) {
            Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
            String valueOfNode;
            if (insertNode.getValue().length() <= 4) {
                valueOfNode = insertNode.getValue();
            } else {
                valueOfNode = insertNode.getValue().substring(0, 5);
            }

            String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
            valuesOfNode.put(indexOfNodes, value);
        }

        RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
        recyclerPoints.setAdapter(adapterForRecycle);
        recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
    }
    /**
     * Fills the Recycler with the highest and lowest data of the node
     */
    private void fillTopNodeValues() {
        resetButtonColors();
        topDataPoint.setBackgroundResource(R.drawable.standard_button_alternative_2);
        allData.setBackgroundResource(R.drawable.standard_button_alternative_2);
        HashMap<Integer, String> valuesOfNode = new HashMap<>();
        for (int indexOfNodes = 0; indexOfNodes < runningNode.getBestNodes().size(); indexOfNodes++) {
            Node insertNode = runningNode.getBestNodes().get(indexOfNodes);
            String valueOfNode;
            if (insertNode.getValue().length() <= 4) {
                valueOfNode = insertNode.getValue();
            } else {
                valueOfNode = insertNode.getValue().substring(0, 5);
            }
            String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
            valuesOfNode.put(indexOfNodes, value);
        }

        RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
        recyclerPoints.setAdapter(adapterForRecycle);
        recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Fills the Recycler with the values for weather in the data
     */
    private void fillWeatherNodeValues() {
        resetButtonColors();
        //if allDataSet is true we get the complete data of the node with weather data. Else we only get the highest and lowest
        if (allDataSet) {
            allValuesOfNode.setBackgroundResource(R.drawable.standard_button_alternative_2);
            weatherData.setBackgroundResource(R.drawable.standard_button_alternative_2);
            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for (int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++) {
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
                if (insertNode.getEvent().contains("p_n")) {
                    String valueOfNode;
                    if (insertNode.getValue().length() <= 4) {
                        valueOfNode = insertNode.getValue();
                    } else {
                        valueOfNode = insertNode.getValue().substring(0, 5);
                    }

                    String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                    valuesOfNode.put(indexOfNodes, value);
                }
            }

            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));

        } else {
            topDataPoint.setBackgroundResource(R.drawable.standard_button_alternative_2);
            weatherData.setBackgroundResource(R.drawable.standard_button_alternative_2);
            HashMap<Integer, Node> insertWheater = new HashMap<>();
            for (int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++) {
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
                if (insertNode.getEvent().contains("p_n")) {
                    insertWheater.put(indexOfNodes, insertNode);
                }
            }

            DisplayNode topAndWorstWeather = new DisplayNode(insertWheater);
            topAndWorstWeather = MainActivity.storageForData.getBestAndWorst(topAndWorstWeather);

            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for (int indexOfNodes = 0; indexOfNodes < topAndWorstWeather.getBestNodes().size(); indexOfNodes++) {
                Node insertNode = topAndWorstWeather.getBestNodes().get(indexOfNodes);
                String valueOfNode;
                if (insertNode.getValue().length() <= 4) {
                    valueOfNode = insertNode.getValue();
                } else {
                    valueOfNode = insertNode.getValue().substring(0, 5);
                }
                String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                valuesOfNode.put(indexOfNodes, value);
            }
            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
        }
    }
    /**
     * Fills the Recycler with the values for time in the data
     */
    private void fillTimeNodeValues() {
        resetButtonColors();
        //if allDataSet is true we get the complete data of the node with time data. Else we only get the highest and lowest
        if (allDataSet) {
            allValuesOfNode.setBackgroundResource(R.drawable.standard_button_alternative_2);
            timeData.setBackgroundResource(R.drawable.standard_button_alternative_2);
            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for (int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++) {
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);

                if (insertNode.getEvent().contains("tq_n")) {

                    String valueOfNode;
                    if (insertNode.getValue().length() <= 4) {
                        valueOfNode = insertNode.getValue();
                    } else {
                        valueOfNode = insertNode.getValue().substring(0, 5);
                    }
                    String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                    valuesOfNode.put(indexOfNodes, value);
                }
            }

            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
        } else {
            topDataPoint.setBackgroundResource(R.drawable.standard_button_alternative_2);
            timeData.setBackgroundResource(R.drawable.standard_button_alternative_2);
            HashMap<Integer, Node> insertTime = new HashMap<>();
            for (int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++) {
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
                if (insertNode.getEvent().contains("tq_n")) {
                    insertTime.put(indexOfNodes, insertNode);
                }
            }

            DisplayNode topAndWorstTime = new DisplayNode(insertTime);
            topAndWorstTime = MainActivity.storageForData.getBestAndWorst(topAndWorstTime);

            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for (int indexOfNodes = 0; indexOfNodes < topAndWorstTime.getBestNodes().size(); indexOfNodes++) {
                Node insertNode = topAndWorstTime.getBestNodes().get(indexOfNodes);
                String valueOfNode;
                if (insertNode.getValue().length() <= 4) {
                    valueOfNode = insertNode.getValue();
                } else {
                    valueOfNode = insertNode.getValue().substring(0, 5);
                }
                String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                valuesOfNode.put(indexOfNodes, value);
            }

            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
        }
    }
    /**
     * Fills the Recycler with the values for temperature in the data
     */
    private void fillTemperatureNodeValues() {
        resetButtonColors();
        //if allDataSet is true we get the complete data of the node with temperature data. Else we only get the highest and lowest
        if (allDataSet) {
            allValuesOfNode.setBackgroundResource(R.drawable.standard_button_alternative_2);
            temperatureData.setBackgroundResource(R.drawable.standard_button_alternative_2);
            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for (int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++) {
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);

                if (insertNode.getEvent().contains("t_n")) {
                    String valueOfNode;
                    if (insertNode.getValue().length() <= 4) {
                        valueOfNode = insertNode.getValue();
                    } else {
                        valueOfNode = insertNode.getValue().substring(0, 5);
                    }

                    String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                    valuesOfNode.put(indexOfNodes, value);
                }
            }

            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
        } else {
            topDataPoint.setBackgroundResource(R.drawable.standard_button_alternative_2);
            temperatureData.setBackgroundResource(R.drawable.standard_button_alternative_2);
            HashMap<Integer, Node> insertTemperature = new HashMap<>();
            for (int indexOfNodes = 0; indexOfNodes < runningNode.getNodesComplete().size(); indexOfNodes++) {
                Node insertNode = runningNode.getNodesComplete().get(indexOfNodes);
                if (insertNode.getEvent().contains("t_n")) {
                    insertTemperature.put(indexOfNodes, insertNode);
                }
            }

            DisplayNode topAndWorstTemperature = new DisplayNode(insertTemperature);
            topAndWorstTemperature = MainActivity.storageForData.getBestAndWorst(topAndWorstTemperature);

            HashMap<Integer, String> valuesOfNode = new HashMap<>();
            for (int indexOfNodes = 0; indexOfNodes < topAndWorstTemperature.getBestNodes().size(); indexOfNodes++) {
                Node insertNode = topAndWorstTemperature.getBestNodes().get(indexOfNodes);
                String valueOfNode;
                if (insertNode.getValue().length() <= 4) {
                    valueOfNode = insertNode.getValue();
                } else {
                    valueOfNode = insertNode.getValue().substring(0, 5);
                }
                String value = "Perceptron Value: " + valueOfNode + "\n" + MainActivity.storageForData.convertEventPrefixForNode(insertNode.getEvent());
                valuesOfNode.put(indexOfNodes, value);
            }

            RecycleViewAdapterNode adapterForRecycle = new RecycleViewAdapterNode(this, valuesOfNode);
            recyclerPoints.setAdapter(adapterForRecycle);
            recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
