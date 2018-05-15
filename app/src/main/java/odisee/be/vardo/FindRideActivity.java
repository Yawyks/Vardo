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
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import odisee.be.vardo.ridesFoundRecyclerView.RidesFoundAdapter;
import odisee.be.vardo.ridesFoundRecyclerView.RidesFoundObject;

public class FindRideActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // App
    private RecyclerView myRecycleViewRidesFound;
    private RecyclerView.Adapter myRecycleViewAdapterRidesFound;
    private RecyclerView.LayoutManager myRecyclerViewLayoutManagerRidesFound;
    private ArrayList resultFindRides = new ArrayList<RidesFoundObject>();

    // Navigation Drawer
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle myActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);

        // Navigation Drawer
        myDrawerLayout = findViewById(R.id.navigationDrawer);
        myActionBarDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.string.open, R.string.close);

        myDrawerLayout.addDrawerListener(myActionBarDrawerToggle);

        myActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView myNavigationView = findViewById(R.id.navigation_content);
        myNavigationView.setNavigationItemSelectedListener(this);

        myRecycleViewRidesFound = findViewById(R.id.recyclerViewRidesFound);
        myRecycleViewRidesFound.setNestedScrollingEnabled(true);
        myRecycleViewRidesFound.setHasFixedSize(true);

        myRecyclerViewLayoutManagerRidesFound = new LinearLayoutManager(FindRideActivity.this);
        myRecycleViewRidesFound.setLayoutManager(myRecyclerViewLayoutManagerRidesFound);
        myRecycleViewAdapterRidesFound = new RidesFoundAdapter(getDataSetRidesFound(), FindRideActivity.this);
        myRecycleViewRidesFound.setAdapter(myRecycleViewAdapterRidesFound);

        getRideIds();

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
                Intent h = new Intent(FindRideActivity.this, HomeActivity.class);
                startActivity(h);
                break;
            case R.id.navigationDrawerItemRidesOffered:
                Intent r = new Intent(FindRideActivity.this, RidesOfferedActivity.class);
                startActivity(r);
                break;
            case R.id.navigationDrawerItemRidesBooked:
                Intent b = new Intent(FindRideActivity.this, RidesBookedActivity.class);
                startActivity(b);
                break;
            case R.id.navigationDrawerItemProfile:
                Intent p = new Intent(FindRideActivity.this, ProfileActivity.class);
                startActivity(p);
                break;
            case R.id.navigationDrawerItemCampus:
                Intent s = new Intent(FindRideActivity.this, CampussesActivity.class);
                startActivity(s);
                break;
            case R.id.navigationDrawerItemLogout:
                FirebaseAuth.getInstance().signOut();
                Intent l = new Intent(FindRideActivity.this, LoginActivity.class);
                startActivity(l);
                finish();
                break;
        }

        return false;
    }

    private void getRideIds() {

        DatabaseReference ridesOfferedDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Rides Offered");

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

                    RidesFoundObject obj = new RidesFoundObject(rideId, departure, destination, date);
                    resultFindRides.add(obj);

                    myRecycleViewAdapterRidesFound.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private ArrayList<RidesFoundObject> getDataSetRidesFound() {
        return resultFindRides;
    }

}
