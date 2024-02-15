package com.example.perceptron_app_bachelorarbeit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perceptron_app_bachelorarbeit.adapter.RecycleViewAdapterMain;
import com.example.perceptron_app_bachelorarbeit.storagefiles.Event;
import com.example.perceptron_app_bachelorarbeit.R;
import com.example.perceptron_app_bachelorarbeit.storagefiles.Storage;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Mainactivity for the whole System
 */

public class MainActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    private HashMap<Integer, HashMap<Integer, String>> csvData = new HashMap<>();
    public static Storage storageForData = new Storage();
    private RecyclerView recyclerViewMainActivity;
    private HashMap<Integer, Event> eventDataFromCSV = new HashMap<>();
    private EditText searchValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button to get input of CSV
        Button inputCSV = findViewById(R.id.import_csv_main);
        inputCSV.setOnClickListener(view -> filePicker());
        TextView startingPage = findViewById(R.id.mainPageInformation);
        recyclerViewMainActivity = findViewById(R.id.recyclerViewDescription);
        searchValue = findViewById(R.id.editTextSearch);

         //Needed for the search function --> if element gets inserted it updates the recycler

        searchValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This method is called to notify you that characters within `charSequence` are about to be replaced with new text.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                onClickSearch();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called to notify you that the characters within `editable` have changed.
                // It is called after the text has been changed.
            }
        });

        if (csvData.size() >= 1) {
            startingPage.setText("CSV Selected");
        } else {
            startingPage.setText("To start the application select a csv for import");
        }
    }

    /**
     * Needed for the search function --> if element gets inserted it updates the recycler --> update according to inserted value
     */
    private void onClickSearch() {
        HashMap<Integer, String> firstRow = new HashMap<>();
        if (!searchValue.getText().toString().isEmpty()) {
            for (int i = 0; i < storageForData.getNames().size(); i++) {
                String elementOfStorage = storageForData.getNames().get(i);
                if (elementOfStorage.contains(searchValue.getText().toString())) {
                    firstRow.put(i, elementOfStorage);
                }
            }
            RecycleViewAdapterMain adapterForRecycle = new RecycleViewAdapterMain(this, firstRow);
            recyclerViewMainActivity.setAdapter(adapterForRecycle);
            recyclerViewMainActivity.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Recycle();
        }
    }

    /**
     * loadPerceptron should open the PerceptronActivity for Display of the Perceptron
     */
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
            storageForData = new Storage();
            csvData = new HashMap<>();
            eventDataFromCSV = new HashMap<>();

            if (dataForCSVRequest.getData() != null) {
                try {
                    InputStream csvIn = super.getContentResolver().openInputStream(dataForCSVRequest.getData());
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
                } catch (Exception streamError) {
                    Toast exceptionToast = Toast.makeText(getApplicationContext(),"Wrong CSV Format",Toast.LENGTH_SHORT);
                    exceptionToast.show();
                }

                 //Gets the definitions of Events for the Activity to translate later

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
                } catch (Exception streamError) {
                    Toast exceptionToast = Toast.makeText(getApplicationContext(),"Wrong CSV Format",Toast.LENGTH_SHORT);
                    exceptionToast.show();
                }

                try {
                    storageForData.setCsvData(csvData);
                    storageForData.setEvents(eventDataFromCSV);

                    TextView startingPage = findViewById(R.id.mainPageInformation);
                    startingPage.setText("CSV Selected");

                    storageForData.setNames();
                    storageForData.setEventsAndValues();

                } catch (Exception streamError) {
                    Toast exceptionToast = Toast.makeText(getApplicationContext(),"Wrong CSV Format",Toast.LENGTH_SHORT);
                    exceptionToast.show();
                }

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