package odisee.be.vardo.ridesOfferedRecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(myContext);
                alert.setTitle("Remove offered ride");
                alert.setMessage("Delete offered ride?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(infoData);
                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;
            }
        });
    }

    private void removeItem(RidesOfferedObject infoData) {

        final int currPosition = myListItem.indexOf(infoData);
        final String rideId = myListItem.get(currPosition).getRideId();
        final String myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference myDatabaseReferenceRidesOffered = FirebaseDatabase.getInstance().getReference().child("Rides Offered");
        myDatabaseReferenceRidesOffered.child(rideId).removeValue();

        DatabaseReference myDatabaseReferenceRidesOfferedUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(myUserId).child("Rides Offered");
        myDatabaseReferenceRidesOfferedUsers.child(rideId).removeValue();

        myListItem.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    @Override
    public int getItemCount() {
        return this.myListItem.size();
    }
}
