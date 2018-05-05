package odisee.be.vardo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    // Variables App
    private ImageView myImageViewProfileAvatar;

    private EditText myEditTextProfileFirstName;
    private EditText myEditTextProfileLastName;
    private EditText myEditTextProfileEmail;
    private EditText myEditTextProfilePhoneNumber;

    private Button myButtonProfileUpdate;

    // Variables Firebase
    private FirebaseAuth myFireBaseAuth;
    private DatabaseReference myUserDatabase;

    private String myUserId;
    private String myUserProfileAvatar;
    private String myUserProfileFirstName;
    private String myUserProfileLastName;
    private String myUserProfileEmail;
    private String myUserProfilePhoneNumber;

    private Uri myUriAvatarResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Edit Text
        myImageViewProfileAvatar = findViewById(R.id.imageViewProfileAvatar);
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

        myImageViewProfileAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK);

                i.setType("image/*");

                startActivityForResult(i, 1);
            }
        });

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
                    if (myMap.get("Profile Avatar") != null) {
                        myUserProfileAvatar = myMap.get("Profile Avatar").toString();
                        Glide.with(getApplication()).load(myUserProfileAvatar).into(myImageViewProfileAvatar);
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

        FirebaseUser myUser = FirebaseAuth.getInstance().getCurrentUser();
        myUser.updateEmail(myUserProfileEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Email updated successfully", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        if (myUriAvatarResult != null) {

            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_avatars").child(myUserId);

            Bitmap myBitmap = null;

            try {
                myBitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), myUriAvatarResult);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream b = new ByteArrayOutputStream();

            myBitmap.compress(Bitmap.CompressFormat.JPEG, 20, b);

            byte[] data = b.toByteArray();

            UploadTask myUploadTask = filePath.putBytes(data);

            myUploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                    return;
                }
            });

            myUploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Map myMapAvatar = new HashMap();

                    myMapAvatar.put("Profile Avatar", downloadUrl.toString());

                    myUserDatabase.updateChildren(myMapAvatar);

                    finish();

                    Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_LONG).show();

                    return;
                }
            });
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            final Uri u = data.getData();

            myUriAvatarResult = u;

            myImageViewProfileAvatar.setImageURI(myUriAvatarResult);
        }
    }
}
