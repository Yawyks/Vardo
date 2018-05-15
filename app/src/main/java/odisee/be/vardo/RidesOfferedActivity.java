package odisee.be.vardo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import odisee.be.vardo.ridesOfferedRecyclerView.RidesOfferedAdapter;
import odisee.be.vardo.ridesOfferedRecyclerView.RidesOfferedObject;
import odisee.be.vardo.ridesOfferedRecyclerView.RidesOfferedViewHolders;

public class RidesOfferedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // App
    private String myUserId;

    private RecyclerView myRecyclerViewRidesOffered;

    private RecyclerView.Adapter myAdapterRidesOffered;

    private RecyclerView.LayoutManager myLayoutManagerRidesOffered;

    private ArrayList ridesOfferedResult = new ArrayList<RidesOfferedObject>();

    // Navigation Drawer
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle myActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_offered);

        myRecyclerViewRidesOffered = findViewById(R.id.recyclerViewRidesOffered);
        myRecyclerViewRidesOffered.setNestedScrollingEnabled(false);
        myRecyclerViewRidesOffered.setHasFixedSize(true);

        // Navigation Drawer
        myDrawerLayout = findViewById(R.id.navigationDrawer);
        myActionBarDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.string.open, R.string.close);

        myDrawerLayout.addDrawerListener(myActionBarDrawerToggle);

        myActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView myNavigationView = findViewById(R.id.navigation_content);
        myNavigationView.setNavigationItemSelectedListener(this);

        myLayoutManagerRidesOffered = new LinearLayoutManager(RidesOfferedActivity.this);
        myRecyclerViewRidesOffered.setLayoutManager(myLayoutManagerRidesOffered);
        myAdapterRidesOffered = new RidesOfferedAdapter(getDataRidesOffered(), RidesOfferedActivity.this);
        myRecyclerViewRidesOffered.setAdapter(myAdapterRidesOffered);

        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        getUserRidesOfferedIds();
    }

    // Functions
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
                Intent h = new Intent(RidesOfferedActivity.this, HomeActivity.class);
                startActivity(h);
                break;
            case R.id.navigationDrawerItemRidesOffered:
                Intent r = new Intent(RidesOfferedActivity.this, RidesOfferedActivity.class);
                startActivity(r);
                break;
            case R.id.navigationDrawerItemRidesBooked:
                Intent b = new Intent(RidesOfferedActivity.this, RidesBookedActivity.class);
                startActivity(b);
                break;
            case R.id.navigationDrawerItemProfile:
                Intent p = new Intent(RidesOfferedActivity.this, ProfileActivity.class);
                startActivity(p);
                break;
            case R.id.navigationDrawerItemCampus:
                Intent s = new Intent(RidesOfferedActivity.this, CampussesActivity.class);
                startActivity(s);
                break;
            case R.id.navigationDrawerItemLogout:
                FirebaseAuth.getInstance().signOut();
                Intent l = new Intent(RidesOfferedActivity.this, LoginActivity.class);
                startActivity(l);
                finish();
                break;
        }

        return false;
    }

    private void getUserRidesOfferedIds() {

        DatabaseReference ridesOfferedDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(myUserId).child("Rides Offered");

        ridesOfferedDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        FetchRidesOfferedInformation(ds.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void FetchRidesOfferedInformation(final String rideKey) {

        DatabaseReference ridesOfferedDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Rides Offered").child(rideKey);

        ridesOfferedDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String rideId = dataSnapshot.getKey();

                    String departure = "";
                    String destination = "";
                    String date = "";

                    if (dataSnapshot.child("Departure").getValue() != null) {
                        departure = dataSnapshot.child("Departure").getValue().toString();
                    }

                    if (dataSnapshot.child("Destination").getValue() != null) {
                        destination = dataSnapshot.child("Destination").getValue().toString();
                    }

                    if (dataSnapshot.child("Date Departure").getValue() != null) {
                        date = dataSnapshot.child("Date Departure").getValue().toString();
                    }

                    RidesOfferedObject obj = new RidesOfferedObject(rideId, departure, destination, date);
                    ridesOfferedResult.add(obj);

                    myAdapterRidesOffered.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private ArrayList<RidesOfferedObject> getDataRidesOffered() {
        return ridesOfferedResult;
    }

    private String getDate(Long timestamp) {

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(timestamp * 1000);

        String date = DateFormat.format("dd MMMM yyyy hh:mm", cal).toString();

        return date;
    }
}
