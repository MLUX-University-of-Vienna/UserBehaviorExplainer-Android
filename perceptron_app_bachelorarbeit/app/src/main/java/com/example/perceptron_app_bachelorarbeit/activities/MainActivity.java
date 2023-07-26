package com.example.perceptron_app_bachelorarbeit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.perceptron_app_bachelorarbeit.adapter.RecycleViewAdapterMain;
import com.example.perceptron_app_bachelorarbeit.javafiles.DisplayNode;
import com.example.perceptron_app_bachelorarbeit.javafiles.Event;
import com.example.perceptron_app_bachelorarbeit.javafiles.PerceptronData;
import com.example.perceptron_app_bachelorarbeit.R;
import com.example.perceptron_app_bachelorarbeit.javafiles.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Button to get input of CSV
    private Button inputCSV;
    private Button view_perceptron;
    private HashMap<Integer, HashMap<Integer,String>> csvData = new HashMap<>();
    public static final Storage storageForData = new Storage();
    private RecyclerView recyclerView;
    private HashMap<Integer, Event> eventData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputCSV = findViewById(R.id.import_csv_main);
        inputCSV.setOnClickListener(view -> startCSVImport());
        view_perceptron = findViewById(R.id.view_perceptron);
        view_perceptron.setOnClickListener(view -> loadPerceptron());
        TextView startingPage = findViewById(R.id.mainPageInformation);
        recyclerView = findViewById(R.id.recyclerView);

        if(csvData.size() >= 1){
            startingPage.setText("CSV Selected");

        } else {
            startingPage.setText("To start the application select a csv for import");
        }
    }

    private void loadPerceptron() {
        Intent intent = new Intent(this, PerceptronActivity.class);
        startActivity(intent);
    }

    //Class for CSV Import from files
    private void startCSVImport() {
        try {
            InputStream csvIn = getResources().openRawResource(R.raw.df_perceptron);
            BufferedReader readerCsvIn = new BufferedReader(
                    new InputStreamReader(csvIn, StandardCharsets.UTF_8)
            );

            String readAbleString;
            Integer index = 0;

            while ((readAbleString = readerCsvIn.readLine()) != null) {
                String[] fields = readAbleString.split(",");
                HashMap<Integer, String> insert = new HashMap<>();
                for(int counterFields = 0; counterFields < fields.length; counterFields++){
                    insert.put(counterFields,fields[counterFields]);
                }

                csvData.put(index, insert);
                index++;
            }

            csvIn.close();
            readerCsvIn.close();
        } catch (IOException streamError) {
            streamError.printStackTrace();
        }

        try {
            InputStream csvIn = getResources().openRawResource(R.raw.mapping);
            BufferedReader readerCsvIn = new BufferedReader(
                    new InputStreamReader(csvIn, StandardCharsets.UTF_8)
            );

            String readAbleString;
            Integer index = 0;

            while ((readAbleString = readerCsvIn.readLine()) != null) {
                String[] fields = readAbleString.split(";");

                Event insert = new Event(fields[0],fields[1]);
                eventData.put(index,insert);
                index++;
            }
            csvIn.close();
            readerCsvIn.close();
        } catch (IOException streamError) {
            streamError.printStackTrace();
        }

        storageForData.setCsvData(csvData);
        storageForData.setEvents(eventData);

        TextView startingPage = findViewById(R.id.mainPageInformation);
        startingPage.setText("CSV Selected");

        storageForData.setNames();
        storageForData.setEventsAndValues();
        HashMap<Integer, String> firstRow = storageForData.getNames();
        Recycle();
    }

    private void Recycle() {
        RecycleViewAdapterMain adapterForRecycle = new RecycleViewAdapterMain(this, storageForData.getNames());
        recyclerView.setAdapter(adapterForRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}