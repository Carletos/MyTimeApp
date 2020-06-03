package it.insubria.mytimeapp.Activities;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;


public class StatisticsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewStatistica;
    private PersonViewModel mPersonViewModel;

    private PersonListAdapter adapter;

    private String dataInizio;
    private String dataFine;
    private String oraInizio;
    private String oraFine;

    private DB db;

    private Toolbar toolbarStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        toolbarStat = findViewById(R.id.toolbarStat);
        setSupportActionBar(toolbarStat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerViewStatistica = findViewById(R.id.recyclerViewStatistica);
        adapter = new PersonListAdapter(this);
        recyclerViewStatistica.setAdapter(adapter);
        recyclerViewStatistica.setLayoutManager(new LinearLayoutManager(this));

        mPersonViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        adapter.setPeople(mPersonViewModel.getAllPeople());

        dataInizio = getIntent().getExtras().getString("startDate");
        dataFine = getIntent().getExtras().getString("endDate");
        oraInizio = getIntent().getExtras().getString("oraInizio");
        oraFine = getIntent().getExtras().getString("oraFine");

        db = new DB(getApplicationContext());

        ArrayList<Person> people = db.getAllPeopleFilter(dataInizio, dataFine, oraInizio, oraFine);
        adapter.setPeople(people);

    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
        return true;
    }

}
