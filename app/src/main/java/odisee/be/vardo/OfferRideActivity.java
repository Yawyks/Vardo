package odisee.be.vardo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OfferRideActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    int myDay, myMonth, myYear, myHour, myMinute;
    int myFinalDay, myFinalMonth, myFinalYear, myFinalHour, myFinalMinute;

    // App
    private Spinner mySpinnerFromLocation;
    private Spinner mySpinnerToLocation;
    private EditText myEditTextDateTime;
    private Button myButtonOfferRideNext;

    // Firebase
    private DatabaseReference myUserDatabase;
    private DatabaseReference myHistoryDatabase;
    private String myUserFromLocation;
    private String myUserToLocation;
    private String myUserDateTime;

    // Navigation Drawer
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle myActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);

        myDrawerLayout = findViewById(R.id.navigationDrawer);
        myActionBarDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.string.open, R.string.close);

        myDrawerLayout.addDrawerListener(myActionBarDrawerToggle);

        myActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView myNavigationView = findViewById(R.id.navigation_content);
        myNavigationView.setNavigationItemSelectedListener((this));

        mySpinnerFromLocation = findViewById(R.id.spinnerFromLocation);
        mySpinnerToLocation = findViewById(R.id.spinnerToLocation);
        myEditTextDateTime = findViewById(R.id.editTextDateTime);
        myButtonOfferRideNext = findViewById(R.id.buttonOfferRideNext);

        addItemsOnFirstSpinner();
        addItemsOnSecondSpinner();

        myEditTextDateTime.setFocusableInTouchMode(false);
        myEditTextDateTime.setFocusable(false);

        myEditTextDateTime.setOnClickListener(new View.OnClickListener() {
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

                final String myUserCheckFromLocation = mySpinnerFromLocation.getSelectedItem().toString();
                final String myUserCheckToLocation = mySpinnerToLocation.getSelectedItem().toString();
                final String myUserCheckDateTime = myEditTextDateTime.getText().toString();

                if (myUserCheckFromLocation.matches("- Select departure -")) {
                    Toast.makeText(OfferRideActivity.this, "Select Departure", Toast.LENGTH_LONG).show();
                } else if (myUserCheckToLocation.matches("- Select destination -")) {
                    Toast.makeText(OfferRideActivity.this, "Select Destination", Toast.LENGTH_LONG).show();
                } else if (myUserCheckDateTime.matches("")) {
                    Toast.makeText(OfferRideActivity.this, "Select Date/Time", Toast.LENGTH_LONG).show();
                } else {

                    addOffer();

                    Intent l = new Intent(OfferRideActivity.this, HomeActivity.class);
                    startActivity(l);

                    finish();

                    Toast.makeText(OfferRideActivity.this, "Your offer has been posted successfully.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myActionBarDrawerToggle.onOptionsItemSelected(item)) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.navigationDrawerItemHome:
                Intent h = new Intent(OfferRideActivity.this, HomeActivity.class);
                startActivity(h);
                break;
            case R.id.navigationDrawerItemProfile:
                Intent p = new Intent(OfferRideActivity.this, ProfileActivity.class);
                startActivity(p);
                break;
            case R.id.navigationDrawerItemLogout:
                FirebaseAuth.getInstance().signOut();
                Intent l = new Intent(OfferRideActivity.this, LoginActivity.class);
                startActivity(l);
                Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        return false;
    }

    public void addItemsOnFirstSpinner() {

        mySpinnerFromLocation = (Spinner) findViewById(R.id.spinnerFromLocation);
        List<String> list = new ArrayList<String>();
        list.add("- Select departure -");
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
        list.add("- Select destination -");
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

        myEditTextDateTime.setText(myFinalDay + "-" + myFinalMonth + "-" + myFinalYear + " " + myFinalHour + ":" + myFinalMinute);
    }

    private void addOffer(){

        String myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myUserFromLocation = mySpinnerFromLocation.getSelectedItem().toString();
        myUserToLocation = mySpinnerToLocation.getSelectedItem().toString();
        myUserDateTime = myEditTextDateTime.getText().toString();

        myUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(myUserId).child("Rides Offered");
        myHistoryDatabase = FirebaseDatabase.getInstance().getReference().child("Rides Offered");

        String myRidesOfferedId = myHistoryDatabase.push().getKey();

        myUserDatabase.child(myRidesOfferedId).setValue(true);

        HashMap myHashMap = new HashMap();

        myHashMap.put("Date Departure", myUserDateTime);
        myHashMap.put("Departure", myUserFromLocation);
        myHashMap.put("Destination", myUserToLocation);
        myHashMap.put("Owner", myUserId);

        myHistoryDatabase.child(myRidesOfferedId).updateChildren(myHashMap);

        finish();
    }

    private Long getCurrentTimestamp() {
        Long timestamp = System.currentTimeMillis()/1000;
        return timestamp;
    }
}
