package it.insubria.mytimeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import it.insubria.mytimeapp.R;

public class GraphActivity extends AppCompatActivity {

    private Toolbar toolbarGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        toolbarGraph = findViewById(R.id.toolbarGraph);
        setSupportActionBar(toolbarGraph);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
        return true;
    }
}
