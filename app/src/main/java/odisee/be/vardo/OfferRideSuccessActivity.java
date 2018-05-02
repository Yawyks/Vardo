package odisee.be.vardo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class OfferRideSuccessActivity extends AppCompatActivity {

    private EditText editFromLocation;
    private EditText editToLocation;
    private EditText editOfferDateTime;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride_success);

        editFromLocation = findViewById(R.id.editFromLocation);
        editToLocation = findViewById(R.id.editToLocation);
        editOfferDateTime = findViewById(R.id.editOfferDateTime);


    }
}