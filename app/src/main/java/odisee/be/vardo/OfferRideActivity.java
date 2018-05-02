package odisee.be.vardo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OfferRideActivity extends AppCompatActivity {

    Button btnOfferRideNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);

        btnOfferRideNext = findViewById(R.id.btnOfferRideNext);

        btnOfferRideNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnOfferRideNextClick = new Intent(v.getContext(), OfferRideSuccessActivity.class);
                startActivity(btnOfferRideNextClick);
            }
        });
    }
}
