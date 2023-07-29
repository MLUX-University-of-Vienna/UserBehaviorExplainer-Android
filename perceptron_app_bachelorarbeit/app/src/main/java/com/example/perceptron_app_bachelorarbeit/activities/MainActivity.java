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
import com.example.perceptron_app_bachelorarbeit.javafiles.Event;
import com.example.perceptron_app_bachelorarbeit.R;
import com.example.perceptron_app_bachelorarbeit.javafiles.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Mainactivity for the whole System
 */

public class MainActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    private HashMap<Integer, HashMap<Integer,String>> csvData = new HashMap<>();
    public static final Storage storageForData = new Storage();
    private RecyclerView recyclerViewMainActivity;
    private HashMap<Integer, Event> eventDataFromCSV = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button to get input of CSV
        Button inputCSV = findViewById(R.id.import_csv_main);
        inputCSV.setOnClickListener(view -> filePicker());
        Button viewPerceptron = findViewById(R.id.view_perceptron);
        viewPerceptron.setOnClickListener(view -> loadPerceptron());
        TextView startingPage = findViewById(R.id.mainPageInformation);
        recyclerViewMainActivity = findViewById(R.id.recyclerView);

        if(csvData.size() >= 1){
            startingPage.setText("CSV Selected");

        } else {
            startingPage.setText("To start the application select a csv for import");
        }
    }

    /**
     * loadPerceptron should open the PerceptronActivity for Display of the Perceptron
     */

    private void loadPerceptron() {
        Intent intent = new Intent(this, PerceptronActivity.class);
        startActivity(intent);
    }

    private void filePicker() {
        Intent csvImport = new Intent(Intent.ACTION_GET_CONTENT);
        csvImport.setType("*/*"); // Set the MIME type to all files
        startActivityForResult(csvImport, REQUESTCODE);
    }

    /**
     * Class for CSV Import from files
     */
    @Override
    protected void onActivityResult(int requestCodeForCSVData, int resultCodeForCsvData, Intent dataForCSVRequest) {
        super.onActivityResult(requestCodeForCSVData, resultCodeForCsvData, dataForCSVRequest);

        if (requestCodeForCSVData == REQUESTCODE && resultCodeForCsvData == RESULT_OK) {
            if (dataForCSVRequest.getData() != null && dataForCSVRequest != null) {
                Uri uriOfCSVFile = dataForCSVRequest.getData();
                try {
                    InputStream csvIn = super.getContentResolver().openInputStream(uriOfCSVFile);
                    BufferedReader readerCsvIn = new BufferedReader(
                            new InputStreamReader(csvIn, StandardCharsets.UTF_8)
                    );

                    String stringValueOfCSV;
                    Integer indexForHashMapCSVData = 0;

                    while ((stringValueOfCSV = readerCsvIn.readLine()) != null) {
                        String[] fieldsOfCSVRow = stringValueOfCSV.split(",");
                        HashMap<Integer, String> insert = new HashMap<>();
                        for (int counterFields = 0; counterFields < fieldsOfCSVRow.length; counterFields++) {
                            insert.put(counterFields, fieldsOfCSVRow[counterFields]);
                        }

                        csvData.put(indexForHashMapCSVData, insert);
                        indexForHashMapCSVData++;
                    }

                    csvIn.close();
                    readerCsvIn.close();
                } catch (IOException streamError) {
                    streamError.printStackTrace();
                }

                /**
                 * Gets the definitions of Events for the Activity to translate later
                 */

                try {
                    InputStream csvIn = getResources().openRawResource(R.raw.mapping);
                    BufferedReader readerCsvIn = new BufferedReader(
                            new InputStreamReader(csvIn, StandardCharsets.UTF_8)
                    );

                    String stringValueOfCSV;
                    Integer indexForHashMapEvent = 0;

                    while ((stringValueOfCSV = readerCsvIn.readLine()) != null) {
                        String[] fieldsOfCSVRow = stringValueOfCSV.split(";");

                        Event insert = new Event(fieldsOfCSVRow[0], fieldsOfCSVRow[1]);
                        eventDataFromCSV.put(indexForHashMapEvent, insert);
                        indexForHashMapEvent++;
                    }
                    csvIn.close();
                    readerCsvIn.close();
                } catch (IOException streamError) {
                    streamError.printStackTrace();
                }

                storageForData.setCsvData(csvData);
                storageForData.setEvents(eventDataFromCSV);

                TextView startingPage = findViewById(R.id.mainPageInformation);
                startingPage.setText("CSV Selected");

                storageForData.setNames();
                storageForData.setEventsAndValues();
                HashMap<Integer, String> firstRow = storageForData.getNames();
                Recycle();
            }
        }
    }

    /**
     * Recycler View for the MainActivity after CSV import and show the different Nodes
     */

    private void Recycle() {
        RecycleViewAdapterMain adapterForRecycle = new RecycleViewAdapterMain(this, storageForData.getNames());
        recyclerViewMainActivity.setAdapter(adapterForRecycle);
        recyclerViewMainActivity.setLayoutManager(new LinearLayoutManager(this));
    }
}