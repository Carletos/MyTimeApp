package it.insubria.mytimeapp.Activities;

import android.graphics.Canvas;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.snackbar.Snackbar;
import it.insubria.mytimeapp.Database.DB;
import it.insubria.mytimeapp.Database.Person;
import it.insubria.mytimeapp.Database.PersonListAdapter;
import it.insubria.mytimeapp.Database.PersonViewModel;
import it.insubria.mytimeapp.R;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class MainActivity extends AppCompatActivity {

    private static final int REGISTRATION_REQUEST_CODE = 1;
    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private PersonViewModel mPersonViewModel;
    private Toolbar toolbar;
    private PersonListAdapter adapter;
    private DB db;
    private boolean dateChanged = false;
    private Handler timeRefresh = new Handler();

    //DB dbPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new PersonListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPersonViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        adapter.setPeople(mPersonViewModel.getAllPeople());


        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivityForResult(intent, REGISTRATION_REQUEST_CODE);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        db = new DB(getApplicationContext());
        timeRefresh.postDelayed(timerTick, 500);
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
                Intent intent = new Intent(MainActivity.this, FilterStatistics.class);
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

        /*super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REGISTRATION_REQUEST_CODE && resultCode == RESULT_OK){
            Person person = new Person(data.getStringExtra(Registration.EXTRA_REPLY));
            mPersonViewModel.insert(person);
        } else{
            Toast.makeText(getApplicationContext(), "Non è stata salvata l'attività perchè è vuota", Toast.LENGTH_LONG).show();
        }*/
        dateChanged = (resultCode == RESULT_OK);

        //recyclerViewStatistica.setAdapter(adapter);

    }
    private Runnable timerTick = new Runnable() {
        @Override
        public void run() {
            if (dateChanged){
                dateChanged = !dateChanged;
                adapter.setPeople(mPersonViewModel.getAllPeople());
            }
            timeRefresh.postDelayed(timerTick,500);
        }
    };


    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            int position = viewHolder.getAdapterPosition();
            Person p = adapter.getPerson(position);
            int i = p.getPid();
            adapter.deletePerson(i);
            dateChanged = true;
            Person last = p;
            Snackbar snackbar = Snackbar.make(viewHolder.itemView, "Attività eliminata ", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.addPerson(last);
                    dateChanged = true;
                }
            });
            snackbar.setDuration(30000);
            snackbar.show();

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(MainActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorRed))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


}
