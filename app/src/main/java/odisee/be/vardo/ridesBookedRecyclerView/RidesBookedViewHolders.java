package odisee.be.vardo.ridesBookedRecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import odisee.be.vardo.R;
import odisee.be.vardo.RidesBookedSingleActivity;

public class RidesBookedViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView rideId;
    public TextView rideDeparture;
    public TextView rideDestination;
    public TextView rideDateTime;

    public RidesBookedViewHolders(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        rideId = itemView.findViewById(R.id.rideId);
        rideDeparture = itemView.findViewById(R.id.rideDeparture);
        rideDestination = itemView.findViewById(R.id.rideDestination);
        rideDateTime = itemView.findViewById(R.id.rideDateTime);
    }

    @Override
    public void onClick(View v) {

        Intent i = new Intent(v.getContext(), RidesBookedSingleActivity.class);

        Bundle b = new Bundle();

        b.putString("rideId", rideId.getText().toString());
        i.putExtras(b);

        v.getContext().startActivity(i);
    }
}
