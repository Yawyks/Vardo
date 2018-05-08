package odisee.be.vardo;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import odisee.be.vardo.ridesOfferedRecyclerView.RidesOfferedAdapter;
import odisee.be.vardo.ridesOfferedRecyclerView.RidesOfferedObject;

public class RidesOfferedActivity extends AppCompatActivity {

    // App
    private RecyclerView myRecyclerViewRidesOffered;
    private RecyclerView.Adapter myAdapterRidesOffered;
    private RecyclerView.LayoutManager myLayoutManagerRidesOffered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_offered);

        myRecyclerViewRidesOffered = findViewById(R.id.recyclerViewRidesOffered);
        myRecyclerViewRidesOffered.setNestedScrollingEnabled(false);
        myRecyclerViewRidesOffered.setHasFixedSize(true);
        myLayoutManagerRidesOffered = new LinearLayoutManager(RidesOfferedActivity.this);
        myRecyclerViewRidesOffered.setLayoutManager(myLayoutManagerRidesOffered);
        myAdapterRidesOffered = new RidesOfferedAdapter(getDataRidesOffered(), RidesOfferedActivity.this);
        myRecyclerViewRidesOffered.setAdapter(myAdapterRidesOffered);

        RidesOfferedObject rob = new RidesOfferedObject("91");
        ridesOfferedResult.add(rob);

        myAdapterRidesOffered.notifyDataSetChanged();
    }

    private ArrayList ridesOfferedResult = new ArrayList<RidesOfferedObject>();

    private ArrayList<RidesOfferedObject> getDataRidesOffered() {
        return ridesOfferedResult;
    }
}
