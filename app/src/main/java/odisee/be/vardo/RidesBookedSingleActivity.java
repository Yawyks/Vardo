package odisee.be.vardo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class RidesBookedSingleActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String rideId;
    private String ownerId;

    private TextView rideDeparture;
    private TextView rideDestination;
    private TextView rideDate;
    private TextView userName;
    private TextView userLastName;
    private TextView userPhone;
    private ImageView userImage;
    private TextView userEmail;
    private TextView userType;

    private GoogleMap myGoogleMap;
    private SupportMapFragment mySupportMapFragment;

    private DatabaseReference myDatabaseReferenceRideDetails;
    private DatabaseReference myDatabaseReferenceUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_booked_single);

        rideId = getIntent().getExtras().getString("rideId");

        //mySupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //mySupportMapFragment.getMapAsync(this);

        rideDeparture = findViewById(R.id.rideDeparture);
        rideDestination = findViewById(R.id.rideDestination);
        rideDate = findViewById(R.id.rideDate);
        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userImage = findViewById(R.id.userImage);
        userLastName = findViewById(R.id.userLastName);
        userEmail = findViewById(R.id.userEmail);
        userType = findViewById(R.id.userType);

        myDatabaseReferenceRideDetails = FirebaseDatabase.getInstance().getReference().child("RidesBooked").child(rideId);

        getRideDetails();
    }

    private void getRideDetails() {

        myDatabaseReferenceRideDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getKey().equals("Departure")) {
                            rideDeparture.setText(child.getValue().toString());
                        }
                        if (child.getKey().equals("Destination")) {
                            rideDestination.setText(child.getValue().toString());
                        }
                        if (child.getKey().equals("Date Departure")) {
                            rideDate.setText(child.getValue().toString());
                        }
                        if (child.getKey().equals("Owner")) {
                            ownerId = child.getValue().toString();
                            getUserInformation(ownerId);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getUserInformation(String ownerId) {

        myDatabaseReferenceUserDetails = FirebaseDatabase.getInstance().getReference().child("Users").child(ownerId);

        myDatabaseReferenceUserDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                    if (map.get("First Name") != null) {
                        userName.setText(map.get("First Name").toString());
                    }
                    if (map.get("Phone Number") != null) {
                        userPhone.setText(map.get("Phone Number").toString());
                    }
                    if (map.get("Last Name") != null) {
                        userLastName.setText(map.get("Last Name").toString());
                    }
                    if (map.get("Email") != null) {
                        userEmail.setText(map.get("Email").toString());
                    }
                    if (map.get("Account Type") != null) {
                        userType.setText(map.get("Account Type").toString());
                    }
                    if (map.get("Profile Avatar") != null) {
                        Glide.with(getApplication()).load(map.get("Profile Avatar").toString()).into(userImage);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


    }
}
