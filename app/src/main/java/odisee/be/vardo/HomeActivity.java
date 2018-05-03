package odisee.be.vardo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    // Variables App
    private Button btnFindRide;
    private Button btnOfferRide;
    private Button myButtonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnFindRide = findViewById(R.id.btnFindRide);
        btnOfferRide = findViewById(R.id.btnOfferRide);
        myButtonLogout = findViewById(R.id.buttonLogout);

        btnFindRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnFindRideClick = new Intent(v.getContext(), FindRideActivity.class);
                startActivity(btnFindRideClick);
            }
        });

        btnOfferRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnOfferRideClick = new Intent(v.getContext(), OfferRideActivity.class);
                startActivity(btnOfferRideClick);
            }
        });

        myButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });
    }
}