package odisee.be.vardo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import odisee.be.vardo.ridesOfferedRecyclerView.RidesOfferedAdapter;
import odisee.be.vardo.ridesOfferedRecyclerView.RidesOfferedObject;

public class RidesOfferedActivity extends AppCompatActivity {

    // App
    private String myUserId;

    private RecyclerView myRecyclerViewRidesOffered;

    private RecyclerView.Adapter myAdapterRidesOffered;

    private RecyclerView.LayoutManager myLayoutManagerRidesOffered;

    private ArrayList ridesOfferedResult = new ArrayList<RidesOfferedObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_offered);

        myRecyclerViewRidesOffered = findViewById(R.id.recyclerViewRidesOffered);
        myRecyclerViewRidesOffered.setNestedScrollingEnabled(false);
        myRecyclerViewRidesOffered.setHasFixedSize(true);

        myLayoutManagerRidesOffered = new LinearLayoutManager(RidesOfferedActivity.this);
        myRecyclerViewRidesOffered.setLayoutManager(myLayoutManagerRidesOffered);
        myAdapterRidesOffered = new RidesOfferedAdapter(getDataRidesOffered(), RidesOfferedActivity.this);
        myRecyclerViewRidesOffered.setAdapter(myAdapterRidesOffered);

        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getUserRidesOfferedIds();
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

    private void FetchRidesOfferedInformation(String rideKey) {

        DatabaseReference ridesOfferedDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Rides Offered").child(rideKey);

        ridesOfferedDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String rideId = dataSnapshot.getKey();
                    String departure = "";
                    String destination = "";

                    if (dataSnapshot.child("Departure").getValue() != null) {
                        departure = dataSnapshot.child("Departure").getValue().toString();
                    }

                    if (dataSnapshot.child("Destination").getValue() != null) {
                        destination = dataSnapshot.child("Destination").getValue().toString();
                    }

                    RidesOfferedObject obj = new RidesOfferedObject(rideId, departure, destination);
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
}
