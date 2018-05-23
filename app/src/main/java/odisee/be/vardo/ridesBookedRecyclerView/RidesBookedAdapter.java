package odisee.be.vardo.ridesBookedRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import odisee.be.vardo.R;

public class RidesBookedAdapter extends RecyclerView.Adapter<RidesBookedViewHolders> {

    private List<RidesBookedObject> myItemList;
    private Context myContext;

    public RidesBookedAdapter(List<RidesBookedObject> myItemList, Context myContext) {
        this.myItemList = myItemList;
        this.myContext = myContext;
    }

    @Override
    public RidesBookedViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rides_booked, null, false);
        RecyclerView.LayoutParams myLayoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myLayoutView.setLayoutParams(myLayoutParams);
        RidesBookedViewHolders myRidesBookedViewHolders = new RidesBookedViewHolders(myLayoutView);

        return myRidesBookedViewHolders;
    }

    @Override
    public void onBindViewHolder(@NonNull RidesBookedViewHolders holder, int position) {

        holder.rideId.setText(myItemList.get(position).getRideId());
        holder.rideDeparture.setText(myItemList.get(position).getDeparture());
        holder.rideDestination.setText(myItemList.get(position).getRideDestination());
        holder.rideDateTime.setText(myItemList.get(position).getRideDateTime());
    }

    @Override
    public int getItemCount() {
        return this.myItemList.size();
    }
}
