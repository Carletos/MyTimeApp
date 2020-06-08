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

/*
    This is the most important class of the project, where the user can see all the activities that he stored
 */

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // setting the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new PersonListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPersonViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        adapter.setPeople(mPersonViewModel.getAllPeople());

        // this button is used to switch to "Registration" to record new activities
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivityForResult(intent, REGISTRATION_REQUEST_CODE);
            }
        });

        // this instance is for "swipe to delete"
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        db = new DB(getApplicationContext());
        timeRefresh.postDelayed(timerTick, 500);
    }

    // the two following methods are used to create the menu items on the toolbar
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
                Intent intent = new Intent(MainActivity.this, FilterStatistics.class);
                startActivity(intent);
                break;
            case R.id.graph_item:
                Intent intent2 = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        dateChanged = (resultCode == RESULT_OK);
    }

    // this method is used to set the correct delay for "swipe to delete"
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
            snackbar.setDuration(2000);
            snackbar.show();
        }

        // this method is used to display the selected icon and background color for the swipe
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
