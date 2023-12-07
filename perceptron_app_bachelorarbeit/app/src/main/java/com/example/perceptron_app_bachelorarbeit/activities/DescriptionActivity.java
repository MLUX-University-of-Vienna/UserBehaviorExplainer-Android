package com.example.perceptron_app_bachelorarbeit.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perceptron_app_bachelorarbeit.R;
import com.example.perceptron_app_bachelorarbeit.adapter.RecycleViewAdapterElementDescription;
import com.example.perceptron_app_bachelorarbeit.adapter.RecycleViewAdapterNode;
import com.example.perceptron_app_bachelorarbeit.javafiles.Node;

import java.util.HashMap;

public class DescriptionActivity extends AppCompatActivity {

    private RecyclerView recyclerDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        recyclerDescription = findViewById(R.id.recyclerViewDescription);

        fillDescription();
    }

    private void fillDescription() {
        HashMap<Integer, String> description = new HashMap<>();
        description.put(1,"< --> previous event activity");
        description.put(2,"<< --> before previous event activity");
        description.put(3,"> --> next activity");
        description.put(4,">> --> next next activity");
        description.put(5,"| --> current activity");
        description.put(6,"☀ --> precipitation good (sunny) for activity");
        description.put(7,"☔ --> precipitation bad (rainy) for activity");
        description.put(8,"° --> values of temperature for activity");
        description.put(9,new StringBuilder().appendCodePoint(0x1F55B).toString() + " --> different time quarters");
        description.put(10, "? --> value not given or not valid");

        RecycleViewAdapterElementDescription adapterForRecycle = new RecycleViewAdapterElementDescription(this, description);
        recyclerDescription.setAdapter(adapterForRecycle);
        recyclerDescription.setLayoutManager(new LinearLayoutManager(this));
    }
}
