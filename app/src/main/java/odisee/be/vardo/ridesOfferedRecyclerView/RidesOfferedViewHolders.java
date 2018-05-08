package odisee.be.vardo.ridesOfferedRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import odisee.be.vardo.R;

public class RidesOfferedViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView rideId;

    public RidesOfferedViewHolders(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        rideId = itemView.findViewById(R.id.rideId);
    }

    @Override
    public void onClick(View v) {

    }
}
