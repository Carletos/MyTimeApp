package it.insubria.mytimeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import it.insubria.mytimeapp.Activities.GraphActivity;
import it.insubria.mytimeapp.Activities.Registration;
import it.insubria.mytimeapp.Activities.StatisticsActivity;
import it.insubria.mytimeapp.Database.Person;
import it.insubria.mytimeapp.Database.PersonListAdapter;
import it.insubria.mytimeapp.Database.PersonViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int REGISTRATION_REQUEST_CODE = 1;
    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private PersonViewModel mPersonViewModel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);
        final PersonListAdapter adapter = new PersonListAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPersonViewModel = new ViewModelProvider(this).get(PersonViewModel.class);

        mPersonViewModel.getAllPeople().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable final List<Person> people) {
                adapter.setPeople(people);
            }
        });

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivityForResult(intent, REGISTRATION_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.stat_item:
                //Toast.makeText(getApplicationContext(),"Hai cliccato su activity_statistics", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
                break;
            case R.id.graph_item:
               // Toast.makeText(getApplicationContext(),"Hai cliccato su grafici", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REGISTRATION_REQUEST_CODE && resultCode == RESULT_OK){
            Person person = new Person(data.getStringExtra(Registration.EXTRA_REPLY));
            mPersonViewModel.insert(person);
        } else{
            Toast.makeText(getApplicationContext(), "Non è stata salvata l'attività perchè è vuota", Toast.LENGTH_LONG).show();
        }
    }
}
