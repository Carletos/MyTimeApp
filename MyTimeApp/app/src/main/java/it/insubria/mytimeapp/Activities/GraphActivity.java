package it.insubria.mytimeapp.Activities;

import android.graphics.Color;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.data.View;
import it.insubria.mytimeapp.Database.DB;
import it.insubria.mytimeapp.Database.Person;
import it.insubria.mytimeapp.R;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private Toolbar toolbarGraph;
    private DB db;
    private String stuff;

    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        toolbarGraph = findViewById(R.id.toolbarGraph);
        setSupportActionBar(toolbarGraph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        /*
        db = new DB(getApplicationContext());

        Pie pie = AnyChart.pie();
        anyChartView = findViewById(R.id.Chart);
        anyChartView.setChart(pie);

        ArrayList<Person> people = db.getAllStuff(stuff);
         */
        anyChartView = findViewById(R.id.Chart);

        setupPieChart();


    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
        return true;
    }

    public void setupPieChart(){


        Pie pie = AnyChart.pie();
        int[] values = {0,0,0,0,0,0,0,0,0};
        String[] activities = {"Studio","Lavoro","Igiene Personale","Svago","Pranzo","Ceno","Faccio colazione","Palestra","Altro"};
        db = new DB(getApplicationContext());

        List<DataEntry> dataEntries = new ArrayList<>();
        for(Person people: db.getAllPeople()){
            switch(people.getStuff()) {
                case "Studio":
                    values[0]++;
                    break;
                case "Lavoro":
                    values[1]++;
                    break;
                case "Igiene Personale":
                    values[2]++;
                    break;
                case "Svago":
                    values[3]++;
                    break;
                case "Pranzo":
                    values[4]++;
                    break;
                case "Ceno":
                    values[5]++;
                    break;
                case "Faccio colazione":
                    values[6]++;
                    break;
                case "Palestra":
                    values[7]++;
                    break;
                case "Altro":
                    values[8]++;
                    break;
            }
        }
        for(int i = 0; i < activities.length; i++){
            dataEntries.add(new ValueDataEntry(activities[i], values[i]));
        }
        pie.data(dataEntries);
        pie.title("All Stuff");
        anyChartView.setBackgroundColor(Color.TRANSPARENT);
        anyChartView.setChart(pie);

    }
}
