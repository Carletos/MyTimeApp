package it.insubria.mytimeapp.Activities;

import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;
import android.app.*;
import it.insubria.mytimeapp.R;

import java.util.Calendar;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_REPLY = "it.insubria.personlistsql.REPLY";
    private Button saveButton;
    private Button calendarButton;
    private Button timeFromButton;
    private Button timeToButton;

    private TextInputEditText name;
    private TextInputEditText stuff;
    private TextInputEditText date;
    private TextInputEditText time_from;
    private TextInputEditText time_to;

    private Toolbar toolbarRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.name);
        stuff = findViewById(R.id.stuff);
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

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveIntent = new Intent();
                if(TextUtils.isEmpty(name.getText())){
                    setResult(RESULT_CANCELED, saveIntent);
                } else{
                    String name1 = name.getText().toString();
                    saveIntent.putExtra(EXTRA_REPLY, name1);
                    setResult(RESULT_OK, saveIntent);
                }

                if(TextUtils.isEmpty(stuff.getText())){
                    setResult(RESULT_CANCELED, saveIntent);
                } else{
                    String name2 = stuff.getText().toString();
                    saveIntent.putExtra(EXTRA_REPLY, name2);
                    setResult(RESULT_OK, saveIntent);
                }

                if(TextUtils.isEmpty(date.getText())){
                    setResult(RESULT_CANCELED, saveIntent);
                } else{
                    String name3 = date.getText().toString();
                    saveIntent.putExtra(EXTRA_REPLY, name3);
                    setResult(RESULT_OK, saveIntent);
                }

                if(TextUtils.isEmpty(time_from.getText())){
                    setResult(RESULT_CANCELED, saveIntent);
                } else{
                    String name4 = time_from.getText().toString();
                    saveIntent.putExtra(EXTRA_REPLY, name4);
                    setResult(RESULT_OK, saveIntent);
                }

                if(TextUtils.isEmpty(time_to.getText())){
                    setResult(RESULT_CANCELED, saveIntent);
                } else{
                    String name5 = time_to.getText().toString();
                    saveIntent.putExtra(EXTRA_REPLY, name5);
                    setResult(RESULT_OK, saveIntent);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent resultIntent = new Intent();
        setResult(RESULT_CANCELED, resultIntent);
        finish();
        return true;
    }

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
                    date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
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
                    time_from.setText(hourOfDay + ":" + minute);
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
                    time_to.setText(hourOfDay + ":" + minute);
                }
            }, hour, minute, false);
            timePickerDialog.show();
        }
    }
}
