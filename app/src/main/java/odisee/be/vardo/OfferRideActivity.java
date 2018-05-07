package odisee.be.vardo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.format.DateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OfferRideActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    // App
    private Spinner mySpinnerFromLocation;
    private Spinner mySpinnerToLocation;

    private TextView myTextViewDateTime;

    private Button myButtonOfferRideNext;

    int myDay, myMonth, myYear, myHour, myMinute;
    int myFinalDay, myFinalMonth, myFinalYear, myFinalHour, myFinalMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);

        mySpinnerFromLocation = findViewById(R.id.spinnerFromLocation);
        mySpinnerToLocation = findViewById(R.id.spinnerToLocation);
        myTextViewDateTime = findViewById(R.id.editTextDateTime);
        myButtonOfferRideNext = findViewById(R.id.buttonOfferRideNext);

        addItemsOnFirstSpinner();
        addItemsOnSecondSpinner();

        myTextViewDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar myCalendar = Calendar.getInstance();

                myDay = myCalendar.get(Calendar.DAY_OF_MONTH);
                myMonth = myCalendar.get(Calendar.MONTH);
                myYear = myCalendar.get(Calendar.YEAR);

                DatePickerDialog myDatePickerDialog = new DatePickerDialog(OfferRideActivity.this, OfferRideActivity.this,
                        myDay, myMonth, myYear);

                myDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                myDatePickerDialog.show();
            }
        });

        myButtonOfferRideNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), OfferRideSuccessActivity.class);

                startActivity(i);
            }
        });
    }

    public void addItemsOnFirstSpinner() {

        mySpinnerFromLocation = (Spinner) findViewById(R.id.spinnerFromLocation);
        List<String> list = new ArrayList<String>();
        list.add("Select Campus");
        list.add("Campus Antwerpen");
        list.add("Campus Brugge");
        list.add("Campus Brussel");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinnerFromLocation.setAdapter(dataAdapter);
    }

    public void addItemsOnSecondSpinner() {

        mySpinnerToLocation = (Spinner) findViewById(R.id.spinnerToLocation);
        List<String> list = new ArrayList<String>();
        list.add("Select Campus");
        list.add("Campus Antwerpen");
        list.add("Campus Brugge");
        list.add("Campus Brussel");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinnerToLocation.setAdapter(dataAdapter);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myFinalYear = year;
        myFinalMonth = month + 1;
        myFinalDay = dayOfMonth;

        Calendar myCalendar = Calendar.getInstance();

        myHour = myCalendar.get(Calendar.HOUR_OF_DAY);
        myMinute = myCalendar.get(Calendar.MINUTE);

        TimePickerDialog myTimePickerDialog = new TimePickerDialog(OfferRideActivity.this, OfferRideActivity.this,
                myHour, myMinute, DateFormat.is24HourFormat(this));

        myTimePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myFinalHour = hourOfDay;
        myFinalMinute = minute;

        myTextViewDateTime.setText(myFinalDay + "/" + myFinalMonth + "/" + myFinalYear + " " + myFinalHour + ":" + myFinalMinute);
    }
}
