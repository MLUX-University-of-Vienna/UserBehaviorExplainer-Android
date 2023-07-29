package com.example.perceptron_app_bachelorarbeit.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.perceptron_app_bachelorarbeit.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class CSVPicker extends AppCompatActivity {

    private static final int REQUESTCODE = 1;
    private HashMap<Integer, HashMap<Integer,String>> csvData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv);
        Button csv = findViewById(R.id.csvPicker);
        csv.setOnClickListener(view -> filePicker());
    }

    
    private void filePicker() {
        Intent csvImport = new Intent(Intent.ACTION_GET_CONTENT);
        csvImport.setType("*/*"); // Set the MIME type to all files
        startActivityForResult(csvImport, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCodeForCSVData, int resultCodeForCsvData, Intent dataForCSVRequest) {
        super.onActivityResult(requestCodeForCSVData, resultCodeForCsvData, dataForCSVRequest);

        if (requestCodeForCSVData == REQUESTCODE && resultCodeForCsvData == RESULT_OK) {
            if (dataForCSVRequest != null && dataForCSVRequest.getData() != null) {
                Uri uriOfCSVFile = dataForCSVRequest.getData();
                System.out.println(uriOfCSVFile.getPath());

                try {
                    File csvFile = new File(uriOfCSVFile.getPath());
                    FileInputStream inputStream = new FileInputStream(csvFile);
                    InputStream csvIn = inputStream;
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

                    inputStream.close();
                    csvIn.close();
                    readerCsvIn.close();
                } catch (IOException streamError) {
                    streamError.printStackTrace();
                }

                System.out.println(csvData.size());


                // Here, you can work with the selected file's Uri (e.g., read the file)
                // Example usage: String filePath = uriOfCSVFile.getPath();
            }
        }
    }


}
