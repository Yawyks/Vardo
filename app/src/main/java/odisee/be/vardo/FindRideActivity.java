package odisee.be.vardo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import odisee.be.vardo.ridesFoundRecyclerView.RidesFoundAdapter;
import odisee.be.vardo.ridesFoundRecyclerView.RidesFoundObject;
import odisee.be.vardo.ridesOfferedRecyclerView.RidesOfferedObject;

public class FindRideActivity extends AppCompatActivity {

    // App
    private RecyclerView myRecycleViewRidesFound;
    private RecyclerView.Adapter myRecycleViewAdapterRidesFound;
    private RecyclerView.LayoutManager myRecyclerViewLayoutManagerRidesFound;
    private ArrayList resultFindRides = new ArrayList<RidesFoundObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);

        myRecycleViewRidesFound = findViewById(R.id.recyclerViewRidesFound);
        myRecycleViewRidesFound.setNestedScrollingEnabled(true);
        myRecycleViewRidesFound.setHasFixedSize(true);

        myRecyclerViewLayoutManagerRidesFound = new LinearLayoutManager(FindRideActivity.this);
        myRecycleViewRidesFound.setLayoutManager(myRecyclerViewLayoutManagerRidesFound);
        myRecycleViewAdapterRidesFound = new RidesFoundAdapter(getDataSetRidesFound(), FindRideActivity.this);
        myRecycleViewRidesFound.setAdapter(myRecycleViewAdapterRidesFound);

        getRideIds();

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
