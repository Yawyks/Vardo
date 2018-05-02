package odisee.be.vardo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OfferRideActivity extends AppCompatActivity {

    private EditText editFromLocation;
    private EditText editToLocation;
    private EditText editOfferDateTime;

    Button btnOfferRideNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);

        editFromLocation = findViewById(R.id.editFromLocation);
        editToLocation = findViewById(R.id.editToLocation);
        editOfferDateTime = findViewById(R.id.editOfferDateTime);

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
