package odisee.be.vardo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OfferRideSuccessActivity extends AppCompatActivity {

    String editFromLocation;
    String editToLocation;
    String editOfferDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride_success);

        TextView editFromLocation = findViewById(R.id.editFromLocation);
        TextView editToLocation = findViewById(R.id.editToLocation);
        TextView editOfferDateTime = findViewById(R.id.editOfferDateTime);

    }
}
