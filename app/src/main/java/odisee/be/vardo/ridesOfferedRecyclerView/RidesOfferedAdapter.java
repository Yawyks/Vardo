package odisee.be.vardo.ridesOfferedRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import odisee.be.vardo.R;

public class RidesOfferedAdapter extends RecyclerView.Adapter<RidesOfferedViewHolders> {

    private List<RidesOfferedObject> myListItem;
    private Context myContext;

    public RidesOfferedAdapter(List<RidesOfferedObject> myListItem, Context myContext) {
        this.myListItem = myListItem;
        this.myContext = myContext;
    }

    @Override
    public RidesOfferedViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rides_offered, null, false);

        RecyclerView.LayoutParams myLayoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        myLayoutView.setLayoutParams(myLayoutParams);

        RidesOfferedViewHolders ridesOfferedViewHolders = new RidesOfferedViewHolders(myLayoutView);

        return ridesOfferedViewHolders;
    }

    @Override
    public void onBindViewHolder(@NonNull RidesOfferedViewHolders holder, final int position) {

        holder.rideDeparture.setText(myListItem.get(position).getDeparture());
        holder.rideDestination.setText(myListItem.get(position).getRideDestination());
        holder.rideDateTime.setText(myListItem.get(position).getRideDateTime());

        final RidesOfferedObject infoData = myListItem.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myContext, "Onclick called at position " + position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                removeItem(infoData);

                return true;
            }
        });
    }

    private void removeItem(RidesOfferedObject infoData) {
        int currPosition = myListItem.indexOf(infoData);
        myListItem.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    @Override
    public int getItemCount() {
        return this.myListItem.size();
    }
}
