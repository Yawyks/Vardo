package odisee.be.vardo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OfferRideActivity extends AppCompatActivity {

    private Spinner mySpinnerFromLocation;
    private Spinner mySpinnerToLocation;

    private TextView myTextViewDateTime;
    private Button myButtonOfferRideNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);

        mySpinnerFromLocation = findViewById(R.id.spinnerFromLocation);
        mySpinnerToLocation = findViewById(R.id.spinnerToLocation);
        myTextViewDateTime = findViewById(R.id.editTextDateTime);
        myButtonOfferRideNext = findViewById(R.id.buttonOfferRideNext);

        mySpinnerFromLocation.setSelection(0);
        mySpinnerToLocation.setSelection(0);

        myTextViewDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OfferRideActivity.this, "Click OK", Toast.LENGTH_SHORT).show();
            }
        });

        myButtonOfferRideNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), OfferRideSuccessActivity.class);

                startActivity(i);
            }
        });
    }
}
