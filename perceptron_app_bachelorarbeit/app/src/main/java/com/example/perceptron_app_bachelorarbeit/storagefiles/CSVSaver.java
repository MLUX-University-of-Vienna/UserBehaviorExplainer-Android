package com.example.perceptron_app_bachelorarbeit.storagefiles;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.example.perceptron_app_bachelorarbeit.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CSVSaver {

    private static String fileName = "csvSavedForUsage";

    /**
     * Used for writing the current CSV File
     * @param context context of Application
     * @param csvData Values that need to be stored
     */

    public static void writeCSV(Context context, HashMap<Integer, HashMap<Integer, String>> csvData) {
        FileOutputStream outputStream;
        try {
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(directory, fileName);
            outputStream = new FileOutputStream(file);
            for (Map.Entry<Integer, HashMap<Integer, String>> rowEntry : csvData.entrySet()) {
                HashMap<Integer, String> rowData = rowEntry.getValue();
                StringBuilder rowBuilder = new StringBuilder();
                for (Map.Entry<Integer, String> cellEntry : rowData.entrySet()) {
                    rowBuilder.append(cellEntry.getValue()).append(",");
                }
                rowBuilder.deleteCharAt(rowBuilder.length() - 1);
                rowBuilder.append("\n");
                outputStream.write(rowBuilder.toString().getBytes());
            }
            outputStream.flush();
            outputStream.close();

        } catch (IOException failWhileCreating) {
            failWhileCreating.printStackTrace();
        }
    }

    /**
     * Used to check if file is saved
     * @return true or false
     */
    public boolean checkFile(){
        File directoryOfFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File checkFile = new File(directoryOfFile, fileName);
        if(checkFile.exists()){
            System.out.println("Exists");
            return true;
        } else {
            System.out.println("Does not Exist");
            return false;
        }
    }

    /**
     * Gives back data from the CSV file
     * @return the Hashmap of datavalues
     * @throws IOException
     */
    public HashMap<Integer, HashMap<Integer, String>> getCSVData() throws IOException {
        HashMap<Integer, HashMap<Integer, String>> csvData = new HashMap<>();
        try {
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(directory, fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String readLine;
        int rowNumber = 0;
        while ((readLine = reader.readLine()) != null) {
            String[] cellsOfCSV = readLine.split(",");
            HashMap<Integer, String> rowDataCSV = new HashMap<>();
            for (int indexOfRow = 0; indexOfRow < cellsOfCSV.length; indexOfRow++) {
                rowDataCSV.put(indexOfRow, cellsOfCSV[indexOfRow]);
            }
            csvData.put(rowNumber, rowDataCSV);
            rowNumber++;
        }

        reader.close();

        } catch (IOException exceptionRead) {
         exceptionRead.printStackTrace();
         }
        return csvData;
    }
}