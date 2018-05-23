package odisee.be.vardo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import odisee.be.vardo.ridesBookedRecyclerView.RidesBookedAdapter;
import odisee.be.vardo.ridesBookedRecyclerView.RidesBookedObject;

public class RidesBookedActivity extends AppCompatActivity {

    private String myUserId;

    // Recycler View
    private RecyclerView myRidesBookedRecyclerView;
    private RecyclerView.Adapter myRidesBookedRecyclerViewAdapter;
    private RecyclerView.LayoutManager myRidesBookedLayoutManager;

    private ArrayList resultRidesBooked = new ArrayList<RidesBookedObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_booked);

        myRidesBookedRecyclerView = findViewById(R.id.recyclerViewRidesBooked);
        myRidesBookedRecyclerView.setNestedScrollingEnabled(false);
        myRidesBookedRecyclerView.setHasFixedSize(true);

        myRidesBookedLayoutManager = new LinearLayoutManager(RidesBookedActivity.this);

        myRidesBookedRecyclerView.setLayoutManager(myRidesBookedLayoutManager);

        myRidesBookedRecyclerViewAdapter = new RidesBookedAdapter(getRidesBooked(), RidesBookedActivity.this);
        myRidesBookedRecyclerView.setAdapter(myRidesBookedRecyclerViewAdapter);

        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        getUserRidesBooked();
    }

    private void getUserRidesBooked() {

        DatabaseReference ridesOfferedDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(myUserId).child("RidesBooked");

        ridesOfferedDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        FetchRidesBookedInformation(ds.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void FetchRidesBookedInformation(final String rideKey) {

        DatabaseReference ridesOfferedDatabaseReference = FirebaseDatabase.getInstance().getReference().child("RidesBooked").child(rideKey);

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

                    RidesBookedObject obj = new RidesBookedObject(rideId, departure, destination, date);
                    resultRidesBooked.add(obj);

                    myRidesBookedRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private ArrayList<RidesBookedObject> getRidesBooked() {
        return resultRidesBooked;
    }
}
