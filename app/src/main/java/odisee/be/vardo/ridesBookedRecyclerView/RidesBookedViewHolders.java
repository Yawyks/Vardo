package odisee.be.vardo.ridesBookedRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import odisee.be.vardo.R;

public class RidesBookedViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView rideDeparture;
    public TextView rideDestination;
    public TextView rideDateTime;

    public RidesBookedViewHolders(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        rideDeparture = itemView.findViewById(R.id.rideDeparture);
        rideDestination = itemView.findViewById(R.id.rideDestination);
        rideDateTime = itemView.findViewById(R.id.rideDateTime);
    }

    @Override
    public void onClick(View v) {

    }
}
