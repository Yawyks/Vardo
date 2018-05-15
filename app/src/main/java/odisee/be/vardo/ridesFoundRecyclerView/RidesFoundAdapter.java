package odisee.be.vardo.ridesFoundRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import odisee.be.vardo.R;
import odisee.be.vardo.ridesOfferedRecyclerView.RidesOfferedViewHolders;

public class RidesFoundAdapter extends RecyclerView.Adapter<RidesFoundViewHolders> {

    private List<RidesFoundObject> myItemList;
    private Context myContext;

    public RidesFoundAdapter(List<RidesFoundObject> myItemList, Context myContext) {
        this.myItemList = myItemList;
        this.myContext = myContext;
    }

    @Override
    public RidesFoundViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rides_found, null, false);
        RecyclerView.LayoutParams myLayoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myLayoutView.setLayoutParams(myLayoutParams);

        RidesFoundViewHolders myRidesFoundViewHolders = new RidesFoundViewHolders(myLayoutView);

        return myRidesFoundViewHolders;
    }

    @Override
    public void onBindViewHolder(@NonNull RidesFoundViewHolders holder, int position) {

        holder.rideDeparture.setText(myItemList.get(position).getDeparture());
        holder.rideDestination.setText(myItemList.get(position).getRideDestination());
        holder.rideDateTime.setText(myItemList.get(position).getRideDateTime());
    }

    @Override
    public int getItemCount() {
        return this.myItemList.size();
    }
}
