package odisee.be.vardo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    // Variables App
    private EditText myEditTextProfileFirstName;
    private EditText myEditTextProfileLastName;
    private EditText myEditTextProfileEmail;
    private EditText myEditTextProfilePhoneNumber;

    private Button myButtonProfileUpdate;

    // Variables Firebase
    private FirebaseAuth myFireBaseAuth;
    private DatabaseReference myUserDatabase;

    private String myUserId;
    private String myUserProfileFirstName;
    private String myUserProfileLastName;
    private String myUserProfileEmail;
    private String myUserProfilePhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Edit Text
        myEditTextProfileFirstName = findViewById(R.id.editTextProfileFirstName);
        myEditTextProfileLastName = findViewById(R.id.editTextProfileLastName);
        myEditTextProfileEmail = findViewById(R.id.editTextProfileEmail);
        myEditTextProfilePhoneNumber = findViewById(R.id.editTextProfilePhoneNumber);

        // Buttons
        myButtonProfileUpdate = findViewById(R.id.buttonProfileUpdate);

        myFireBaseAuth = FirebaseAuth.getInstance();
        myUserId = myFireBaseAuth.getCurrentUser().getUid();
        myUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(myUserId);

        getUserProfileInformation();

        myButtonProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInformation();
            }
        });
    }

    private void getUserProfileInformation() {
        myUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> myMap = (Map<String, Object>) dataSnapshot.getValue();
                    if (myMap.get("First Name") != null) {
                        myUserProfileFirstName = myMap.get("First Name").toString();
                        myEditTextProfileFirstName.setText(myUserProfileFirstName);
                    }

                    if (myMap.get("Last Name") != null) {
                        myUserProfileLastName = myMap.get("Last Name").toString();
                        myEditTextProfileLastName.setText(myUserProfileLastName);
                    }
                    if (myMap.get("Email") != null) {
                        myUserProfileEmail = myMap.get("Email").toString();
                        myEditTextProfileEmail.setText(myUserProfileEmail);
                    }
                    if (myMap.get("Phone Number") != null) {
                        myUserProfilePhoneNumber = myMap.get("Phone Number").toString();
                        myEditTextProfilePhoneNumber.setText(myUserProfilePhoneNumber);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUserInformation() {

        myUserProfileFirstName = myEditTextProfileFirstName.getText().toString();
        myUserProfileLastName = myEditTextProfileLastName.getText().toString();
        myUserProfileEmail = myEditTextProfileEmail.getText().toString();
        myUserProfilePhoneNumber = myEditTextProfilePhoneNumber.getText().toString();

        Map myMapUserInformation = new HashMap();

        myMapUserInformation.put("First Name", myUserProfileFirstName);
        myMapUserInformation.put("Last Name", myUserProfileLastName);
        myMapUserInformation.put("Email", myUserProfileEmail);
        myMapUserInformation.put("Phone Number", myUserProfilePhoneNumber);
        myUserDatabase.updateChildren(myMapUserInformation);

        finish();

    }
}
