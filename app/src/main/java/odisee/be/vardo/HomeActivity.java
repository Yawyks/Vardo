package odisee.be.vardo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    // Variables App
    private Button myButtonFindRide;
    private Button myButtonOfferRide;
    private Button myButtonProfile;
    private Button myButtonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myNavigationDrawerToolbar = findViewById(R.id.navigation_drawer_toolbar);
        setSupportActionBar(myNavigationDrawerToolbar);

        myButtonFindRide = findViewById(R.id.buttonFindRide);
        myButtonOfferRide = findViewById(R.id.buttonOfferRide);
        myButtonProfile = findViewById(R.id.buttonProfile);
        myButtonLogout = findViewById(R.id.buttonLogout);

        myButtonFindRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), FindRideActivity.class);
                startActivity(i);
                return;
            }
        });

        myButtonOfferRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), OfferRideActivity.class);
                startActivity(i);
                return;
            }
        });

        myButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ProfileActivity.class);
                startActivity(i);
                return;
            }
        });

        myButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                return;
            }
        });
    }
}