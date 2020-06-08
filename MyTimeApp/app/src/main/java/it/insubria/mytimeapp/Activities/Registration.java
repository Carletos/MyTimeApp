package it.insubria.mytimeapp.Activities;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;
import android.app.*;
import fr.ganfra.materialspinner.MaterialSpinner;
import it.insubria.mytimeapp.Database.DB;
import it.insubria.mytimeapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
    This class is used to provide all the necessary items for the activity Registration
 */

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private Button saveButton;
    private Button calendarButton;
    private Button timeFromButton;
    private Button timeToButton;

    private TextInputEditText name;
    private TextInputEditText date;
    private TextInputEditText time_from;
    private TextInputEditText time_to;

    private Toolbar toolbarRegistration;

    MaterialSpinner spinner;
    List<String> namesStuff = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        time_from = findViewById(R.id.timeFrom);
        time_to = findViewById(R.id.timeTo);

        calendarButton = findViewById(R.id.buttonCalendar);
        timeFromButton = findViewById(R.id.buttonTimeFrom);
        timeToButton = findViewById(R.id.buttonTimeTo);

        toolbarRegistration = findViewById(R.id.toolbarRegistration);
        setSupportActionBar(toolbarRegistration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        calendarButton.setOnClickListener(this);
        timeFromButton.setOnClickListener(this);
        timeToButton.setOnClickListener(this);

        spinner = findViewById(R.id.spinner);

        // preset stuff for the spinner
        namesStuff.add("Studio");
        namesStuff.add("Lavoro");
        namesStuff.add("Igiene Personale");
        namesStuff.add("Svago");
        namesStuff.add("Pranzo");
        namesStuff.add("Ceno");
        namesStuff.add("Faccio colazione");
        namesStuff.add("Palestra");
        namesStuff.add("Altro");

        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,namesStuff);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // this listener is necessary to display the correct selected activity
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // this button saves each field and controls that aren't empty. If there isn't any empty field, it creates the "INSERT INTO" query
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveIntent = new Intent();
                String query = "INSERT INTO Person(nome, stuff, data, ora_inizio, ora_fine) VALUES ('%s', '%s', '%s', '%s', '%s') ";
                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(spinner.getSelectedItem().toString()) || TextUtils.isEmpty(date.getText()) || TextUtils.isEmpty(time_from.getText()) || TextUtils.isEmpty(time_to.getText())){
                    Toast.makeText(Registration.this, "Attenzione! Non hai completato tutti i dati", Toast.LENGTH_SHORT).show();
                    return;
                }
                query = String.format(query, name.getText(), spinner.getSelectedItem().toString(), date.getText(), time_from.getText(), time_to.getText());
                DB Person = new DB(getApplicationContext());
                if (Person.executeQuery(query)){
                    setResult(RESULT_OK, saveIntent);
                    finish();
                }
            }
        });
    }

    // Toolbar method for the back button
    @Override
    public boolean onSupportNavigateUp() {
        Intent resultIntent = new Intent();
        setResult(RESULT_CANCELED, resultIntent);
        finish();
        return true;
    }

    // this method is used to implement correctly the date and time pickers
    @Override
    public void onClick(View v) {
        if(v == calendarButton) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date.setText(((dayOfMonth>=10)?dayOfMonth:"0"+dayOfMonth) + "/" + (month + 1) + "/" + year);
                }
            }, year, month, day);
            datePickerDialog.show();
        }

        if(v == timeFromButton) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                    String minuti = minute < 10 ? "0" + minute : "" + minute;

                    time_from.setText(hour + ":" + minuti);
                }
            }, hour, minute, false);
            timePickerDialog.show();
        }

        if (v == timeToButton) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                    String minuti = minute < 10 ? "0" + minute : "" + minute;

                    time_to.setText(hour + ":" + minuti);
                }
            }, hour, minute, false);
            timePickerDialog.show();
        }
    }
}
