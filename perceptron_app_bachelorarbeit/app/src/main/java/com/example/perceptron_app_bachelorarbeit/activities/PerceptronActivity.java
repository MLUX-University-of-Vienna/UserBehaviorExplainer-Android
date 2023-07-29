package com.example.perceptron_app_bachelorarbeit.activities;


import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.perceptron_app_bachelorarbeit.R;

/**
 * Perceptron Activity was ment to show the complete Perceptron but no framework was found to display it correctly
 */

public class PerceptronActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perceptron);

        Button buttonPerceptronBackToMain = findViewById(R.id.buttonPerceptronBackToMain);
        buttonPerceptronBackToMain.setOnClickListener(view -> finish());
    }
}
