package com.example.perceptron_app_bachelorarbeit.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perceptron_app_bachelorarbeit.R;
import com.example.perceptron_app_bachelorarbeit.adapter.RecycleViewAdapterElementDescription;

import java.util.HashMap;

/**
 * Description Activity shows which elements represent which logic behind it for the Node descriptions
 */

public class DescriptionActivity extends AppCompatActivity {

    private RecyclerView recyclerDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        recyclerDescription = findViewById(R.id.recyclerViewDescription);
        Button backToNode = findViewById(R.id.backToNode);
        backToNode.setOnClickListener(view -> backToNodeActivity());
        fillDescription();
    }

    private void backToNodeActivity() {
        finish();
    }

    private void fillDescription() {
        HashMap<Integer, String> description = new HashMap<>();
        description.put(1, "⬅️ ... previous event activity");
        description.put(2, "⬅️⬅️ ... before previous event activity");
        description.put(3, "➡️ ... next activity");
        description.put(4, "➡️➡️ ... next next activity");
        description.put(5, "↕️ ... current activity");
        description.put(6, "☀ ... precipitation good (sunny) for activity");
        description.put(7, "☔ ... precipitation bad (rainy) for activity");
        description.put(8, new StringBuilder().appendCodePoint(0x1F55B) + " 00:00-06:00 " + " ... different time quarters");
        description.put(9, "❔ ... value not given or not valid");

        RecycleViewAdapterElementDescription adapterForRecycle = new RecycleViewAdapterElementDescription(this, description);
        recyclerDescription.setAdapter(adapterForRecycle);
        recyclerDescription.setLayoutManager(new LinearLayoutManager(this));
    }
}
