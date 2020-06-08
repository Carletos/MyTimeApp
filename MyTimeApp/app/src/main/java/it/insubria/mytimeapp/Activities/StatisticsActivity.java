package it.insubria.mytimeapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.insubria.mytimeapp.Database.DB;
import it.insubria.mytimeapp.Database.Person;
import it.insubria.mytimeapp.Database.PersonListAdapter;
import it.insubria.mytimeapp.Database.PersonViewModel;
import it.insubria.mytimeapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
    This class displays all the statistics chosen by the filter
 */

public class StatisticsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewStatistics;

    private PersonViewModel mPersonViewModel;

    private PersonListAdapter adapter;

    private String dataInizio;
    private String dataFine;
    private String oraInizio;
    private String oraFine;

    private DB db;

    private Toolbar toolbarStat;

    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        toolbarStat = findViewById(R.id.toolbarStat);
        setSupportActionBar(toolbarStat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerViewStatistics = findViewById(R.id.recyclerViewStatistica);
        adapter = new PersonListAdapter(this);
        recyclerViewStatistics.setAdapter(adapter);
        recyclerViewStatistics.setLayoutManager(new LinearLayoutManager(this));

        mPersonViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        adapter.setPeople(mPersonViewModel.getAllPeople());

        dataInizio = getIntent().getExtras().getString("startDate");
        dataFine = getIntent().getExtras().getString("endDate");
        oraInizio = getIntent().getExtras().getString("oraInizio");
        oraFine = getIntent().getExtras().getString("oraFine");

        db = new DB(getApplicationContext());

        ArrayList<Person> people = db.getAllPeopleFilter(dataInizio, dataFine, oraInizio, oraFine);
        adapter.setPeople(people);

        // with this button and method the user can share his statistics with social media
        shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String txt = "";

                for (Person people: db.getAllPeople()){
                    txt = txt.concat("%s --> %s \nIn data: %s , dalle ore %s alle ore %s\n");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
                    txt = String.format(txt, people.getName(),people.getStuff(),sdf.format(people.getDate()),people.getTimeFrom(),people.getTimeTo());
                }
                intent.putExtra(Intent.EXTRA_TEXT, txt);
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        });
    }

    // Toolbar method for the back button
    @Override
    public boolean onSupportNavigateUp() {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
        return true;
    }
}
