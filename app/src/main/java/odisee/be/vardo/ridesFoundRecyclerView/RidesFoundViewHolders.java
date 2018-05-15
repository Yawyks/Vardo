package odisee.be.vardo.ridesFoundRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import odisee.be.vardo.R;

public class RidesFoundViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView rideDeparture;
    public TextView rideDestination;
    public TextView rideDateTime;

    public RidesFoundViewHolders(View itemView) {
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
