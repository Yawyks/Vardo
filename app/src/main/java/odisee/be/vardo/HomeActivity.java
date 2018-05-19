package odisee.be.vardo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import odisee.be.vardo.Model.MyResponse;
import odisee.be.vardo.Model.Notification;
import odisee.be.vardo.Model.Sender;
import odisee.be.vardo.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Notification
    APIService mService;
    // App - Buttons
    private EditText myEditTextTitle;
    private EditText myEditTextBody;
    private Button myButtonSendNotification;
    private Button myButtonFindRide;
    private Button myButtonOfferRide;
    // Navigation Drawer
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle myActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Notification
        // Token
        /*
        Common.currentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("My token", Common.currentToken);
        */

        // Topic
        mService = Common.getFCMClient();
        FirebaseMessaging.getInstance().subscribeToTopic("RidesOffered");

        myEditTextTitle = findViewById(R.id.editTextTitle);
        myEditTextBody = findViewById(R.id.editTextBody);
        myButtonSendNotification = findViewById(R.id.buttonSendNotification);

        // Token

        myButtonSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Token
                /*
                Notification notification = new Notification(myEditTextTitle.getText().toString(), myEditTextBody.getText().toString());
                Sender sender = new Sender(Common.currentToken,notification);
                mService.sendNotification(sender)
                        .enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                                if(response.body().success == 1){
                                    Toast.makeText(HomeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(HomeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {
                                Log.e("ERROR", t.getMessage());
                            }
                        });
                */

                // Topic
                Notification notification = new Notification(myEditTextTitle.getText().toString(), myEditTextBody.getText().toString());
                Sender sender = new Sender("/topics/RidesOffered", notification);

                mService.sendNotification(sender)
                        .enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                                if (response.body().success == 1) {
                                    Toast.makeText(HomeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(HomeActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {
                                Log.e("ERROR", t.getMessage());
                            }
                        });
            }
        });

        // Buttons
        myButtonFindRide = findViewById(R.id.buttonFindRide);
        myButtonOfferRide = findViewById(R.id.buttonOfferRide);

        // Navigation Drawer
        myDrawerLayout = findViewById(R.id.navigationDrawer);
        myActionBarDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.string.open, R.string.close);

        myDrawerLayout.addDrawerListener(myActionBarDrawerToggle);

        myActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView myNavigationView = findViewById(R.id.navigation_content);
        myNavigationView.setNavigationItemSelectedListener(this);

        // Listeners
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
                Intent h = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(h);
                break;
            case R.id.navigationDrawerItemRidesOffered:
                Intent r = new Intent(HomeActivity.this, RidesOfferedActivity.class);
                startActivity(r);
                break;
            case R.id.navigationDrawerItemRidesBooked:
                Intent b = new Intent(HomeActivity.this, RidesBookedActivity.class);
                startActivity(b);
                break;
            case R.id.navigationDrawerItemProfile:
                Intent p = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(p);
                break;
            case R.id.navigationDrawerItemCampus:
                Intent s = new Intent(HomeActivity.this, CampussesActivity.class);
                startActivity(s);
                break;
            case R.id.navigationDrawerItemLogout:
                FirebaseAuth.getInstance().signOut();
                Intent l = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(l);
                finish();
                break;
        }

        return false;
    }
}