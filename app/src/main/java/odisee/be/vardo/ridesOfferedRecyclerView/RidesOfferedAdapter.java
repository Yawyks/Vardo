package odisee.be.vardo.ridesOfferedRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import odisee.be.vardo.R;

public class RidesOfferedAdapter extends RecyclerView.Adapter<RidesOfferedViewHolders> {

    private List<RidesOfferedObject> myItemList;
    private Context myContext;

    public RidesOfferedAdapter(List<RidesOfferedObject> myItemList, Context myContext){
        this.myItemList = myItemList;
        this.myContext = myContext;
    }

    @Override
    public RidesOfferedViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rides_offered, null, false);

        RecyclerView.LayoutParams myLayoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        myLayoutView.setLayoutParams(myLayoutParams);

        RidesOfferedViewHolders myRidesOfferedViewHolders = new RidesOfferedViewHolders(myLayoutView);

        return myRidesOfferedViewHolders;
    }

    @Override
    public void onBindViewHolder(@NonNull RidesOfferedViewHolders holder, int position) {

        holder.rideId.setText(myItemList.get(position).getRideId());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
