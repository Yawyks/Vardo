package odisee.be.vardo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class RidesOfferedActivity extends AppCompatActivity {

    private RecyclerView myRecyclerViewRidesOffered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_offered);

        myRecyclerViewRidesOffered = findViewById(R.id.recyclerViewRidesOffered);

        myRecyclerViewRidesOffered.setNestedScrollingEnabled(true);
        myRecyclerViewRidesOffered.setHasFixedSize(true);

    }
}
