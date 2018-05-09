package odisee.be.vardo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.widget.Button;

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

public class RidesOfferedActivity extends AppCompatActivity {

    // App
    private String myUserId;

    private RecyclerView myRecyclerViewRidesOffered;

    private RecyclerView.Adapter myAdapterRidesOffered;

    private RecyclerView.LayoutManager myLayoutManagerRidesOffered;

    private ArrayList ridesOfferedResult = new ArrayList<RidesOfferedObject>();

    private Button myButtonRemoveRidesOffered;

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

        myButtonRemoveRidesOffered = findViewById(R.id.buttonRemoveRideOffered);

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

                    RidesOfferedObject obj = new RidesOfferedObject(departure, destination, date);
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

    private String getDate(Long timestamp){

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(timestamp*1000);

        String date = DateFormat.format("dd-MM-yyyy hh:mm", cal).toString();

        return date;
    }
}
