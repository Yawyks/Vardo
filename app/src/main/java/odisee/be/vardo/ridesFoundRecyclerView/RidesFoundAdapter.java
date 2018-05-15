package odisee.be.vardo.ridesFoundRecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import odisee.be.vardo.HomeActivity;
import odisee.be.vardo.R;

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
    public void onBindViewHolder(@NonNull RidesFoundViewHolders holder, final int position) {

        holder.rideDeparture.setText(myItemList.get(position).getDeparture());
        holder.rideDestination.setText(myItemList.get(position).getRideDestination());
        holder.rideDateTime.setText(myItemList.get(position).getRideDateTime());

        final RidesFoundObject infoData = myItemList.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(myContext);
                alert.setTitle("Join Ride");
                alert.setMessage("Join this ride?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Firebase
                        DatabaseReference myUserDatabase;
                        DatabaseReference myHistoryDatabase;

                        String myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        myUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(myUserId).child("Rides Booked");
                        myHistoryDatabase = FirebaseDatabase.getInstance().getReference().child("Rides Booked");

                        String myRidesBookedId = myHistoryDatabase.push().getKey();

                        myUserDatabase.child(myRidesBookedId).setValue(true);

                        HashMap myHashMap = new HashMap();

                        myHashMap.put("Date Departure", myItemList.get(position).getRideDateTime());
                        myHashMap.put("Departure", myItemList.get(position).getDeparture());
                        myHashMap.put("Destination", myItemList.get(position).getRideDestination());

                        myHistoryDatabase.child(myRidesBookedId).updateChildren(myHashMap);

                        dialog.dismiss();

                        Intent i = new Intent(myContext, HomeActivity.class);
                        myContext.startActivity(i);
                        Toast.makeText(myContext, "You joined the ride successfully", Toast.LENGTH_SHORT).show();

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

    @Override
    public int getItemCount() {
        return this.myItemList.size();
    }
}
