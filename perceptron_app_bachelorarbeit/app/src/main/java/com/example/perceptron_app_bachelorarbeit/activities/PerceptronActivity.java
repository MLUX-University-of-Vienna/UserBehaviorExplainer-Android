package com.example.perceptron_app_bachelorarbeit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perceptron_app_bachelorarbeit.R;

public class PerceptronActivity extends AppCompatActivity {

    private Button buttonPerceptronBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perceptron);

        buttonPerceptronBackToMain = findViewById(R.id.buttonPerceptronBackToMain);
        buttonPerceptronBackToMain.setOnClickListener(view -> finish());
    }
}
