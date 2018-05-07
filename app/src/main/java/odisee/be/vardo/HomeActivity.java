package odisee.be.vardo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // App
    private Button myButtonFindRide;
    private Button myButtonOfferRide;
    private Button myButtonProfile;
    private Button myButtonLogout;

    // Navigation Drawer
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle myActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myButtonFindRide = findViewById(R.id.buttonFindRide);
        myButtonOfferRide = findViewById(R.id.buttonOfferRide);
        myButtonProfile = findViewById(R.id.buttonProfile);
        myButtonLogout = findViewById(R.id.buttonLogout);

        myDrawerLayout = findViewById(R.id.navigationDrawer);
        myActionBarDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.string.open, R.string.close);

        myDrawerLayout.addDrawerListener(myActionBarDrawerToggle);

        myActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView myNavigationView = findViewById(R.id.navigation_content);
        myNavigationView.setNavigationItemSelectedListener(this);


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
                Intent h = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(h);
                break;
            case R.id.navigationDrawerItemProfile:
                Intent p = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(p);
                break;
            case R.id.navigationDrawerItemLogout:
                FirebaseAuth.getInstance().signOut();
                Intent l = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(l);
                Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        return false;
    }
}