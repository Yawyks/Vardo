package odisee.be.vardo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class OfferRideActivity extends AppCompatActivity {

    private EditText myEditOfferDateTime;
    private Spinner mySpinnerFromLocation;
    private Spinner mySpinnerToLocation;

    private String mySpinnerNamesFromLocation[] = {"Select Location", "Campus Brussel", "Campus Aalst"};
    private String mySpinnerNamesToLocation[] = {"Select Destination", "Campus Brussel", "Campus Aalst"};

    private Button myButtonOfferRideNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);

        //myEditOfferDateTime = findViewById(R.id.editOfferDateTime);
        mySpinnerFromLocation = findViewById(R.id.spinnerFromLocation);
        mySpinnerToLocation = findViewById(R.id.spinnerToLocation);
        myButtonOfferRideNext = findViewById(R.id.buttonOfferRideNext);

        mySpinnerFromLocation.setSelection(0);
        mySpinnerToLocation.setSelection(0);

        myButtonOfferRideNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), OfferRideSuccessActivity.class);

                startActivity(i);
            }
        });
    }
}
