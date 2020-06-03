package it.insubria.mytimeapp.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;
import it.insubria.mytimeapp.Database.DB;
import it.insubria.mytimeapp.R;
import it.insubria.mytimeapp.TypeFilter;

import java.util.Calendar;

public class FilterStatistics extends AppCompatActivity {

    private TextInputEditText dataInizio;
    private TextInputEditText dataFine;
    private TextInputEditText ora_inizio;
    private TextInputEditText ora_fine;

    private Button buttonCalendarInizio;
    private Button buttonCalendarFine;
    private Button buttonOraInizio;
    private Button buttonOraFine;
    private Button buttonFilter;

    private DB db;

    private Toolbar filterToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_statistics);

        filterToolbar = findViewById(R.id.toolbarFilter);
        setSupportActionBar(filterToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dataInizio = findViewById(R.id.dateStart);
        dataFine = findViewById(R.id.dateEnd);
        ora_inizio = findViewById(R.id.oraInizio);
        ora_fine = findViewById(R.id.oraFine);

        buttonCalendarInizio = findViewById(R.id.buttonCalendarStart);
        buttonCalendarFine = findViewById(R.id.buttonCalendarFinish);
        buttonOraInizio = findViewById(R.id.buttonTimeFrom);
        buttonOraFine = findViewById(R.id.buttonTimeTo);

        buttonFilter = findViewById(R.id.filterButton);

        buttonCalendarInizio.setOnClickListener(this::onClick);
        buttonCalendarFine.setOnClickListener(this::onClick);
        buttonOraInizio.setOnClickListener(this::onClick);
        buttonOraFine.setOnClickListener(this::onClick);

        buttonFilter.setOnClickListener(this::onClick);

        db = new DB(getApplicationContext());
    }

    public void onClick(View v) {

        if(v == buttonCalendarInizio) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dataInizio.setText(((dayOfMonth>=10)?dayOfMonth:"0"+dayOfMonth) + "/" + (month + 1) + "/" + year);
                }
            }, year, month, day);
            datePickerDialog.show();
        }

        if(v == buttonCalendarFine) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dataFine.setText(((dayOfMonth>=10)?dayOfMonth:"0"+dayOfMonth) + "/" + (month + 1) + "/" + year);
                }
            }, year, month, day);
            datePickerDialog.show();
        }

        if(v == buttonOraInizio) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                    String minuti = minute < 10 ? "0" + minute : "" + minute;

                    ora_inizio.setText(hour + ":" + minuti);
                }
            }, hour, minute, false);
            timePickerDialog.show();
        }

        if (v == buttonOraFine) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                    String minuti = minute < 10 ? "0" + minute : "" + minute;

                    ora_fine.setText(hour + ":" + minuti);
                }
            }, hour, minute, false);
            timePickerDialog.show();
        }

        if(v == buttonFilter) {
            TypeFilter f = TypeFilter.DataInizio;
            if (TextUtils.isEmpty(dataInizio.getText()) || TextUtils.isEmpty(dataFine.getText()) || TextUtils.isEmpty(ora_inizio.getText()) || TextUtils.isEmpty(ora_fine.getText())){
                Toast.makeText(this, "Attenzione! Compila tutti i campi", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(FilterStatistics.this, StatisticsActivity.class);
            intent.putExtra("startDate", dataInizio.getText().toString());
            intent.putExtra("endDate", dataFine.getText().toString());
            intent.putExtra("oraInizio", ora_inizio.getText().toString());
            intent.putExtra("oraFine", ora_fine.getText().toString());
            startActivity(intent);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent resultIntent = new Intent();
        setResult(RESULT_CANCELED, resultIntent);
        finish();
        return true;
    }

}
