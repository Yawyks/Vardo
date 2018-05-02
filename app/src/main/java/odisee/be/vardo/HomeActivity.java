package odisee.be.vardo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button btnFindRide;
    private Button btnOfferRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnFindRide = findViewById(R.id.btnFindRide);
        btnOfferRide = findViewById(R.id.btnOfferRide);

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
    }
}