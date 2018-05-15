package odisee.be.vardo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // App - Image Views
    private ImageView myImageViewProfileAvatar;

    // App - Edit Texts
    private EditText myEditTextProfileFirstName;
    private EditText myEditTextProfileLastName;
    private EditText myEditTextProfileEmail;
    private EditText myEditTextProfilePhoneNumber;
    private EditText myEditTextProfileType;

    // App - Buttons
    private Button myButtonProfileUpdate;

    // App - Variables
    private String myUserId;
    private String myUserProfileAvatar;
    private String myUserProfileFirstName;
    private String myUserProfileLastName;
    private String myUserProfileEmail;
    private String myUserProfilePhoneNumber;
    private String myUserProfileType;
    private int status = 0;

    // Navigation Drawer
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle myActionBarDrawerToggle;

    // Firebase
    private FirebaseAuth myFireBaseAuth;
    private DatabaseReference myUserDatabase;
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
        myEditTextProfileType = findViewById(R.id.editTextProfileType);

        // Buttons
        myButtonProfileUpdate = findViewById(R.id.buttonProfileUpdate);

        // Navigation Drawer
        myDrawerLayout = findViewById(R.id.navigationDrawer);
        myActionBarDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.string.open, R.string.close);

        myDrawerLayout.addDrawerListener(myActionBarDrawerToggle);

        myActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView myNavigationView = findViewById(R.id.navigation_content);
        myNavigationView.setNavigationItemSelectedListener(this);

        myFireBaseAuth = FirebaseAuth.getInstance();
        myUserId = myFireBaseAuth.getCurrentUser().getUid();
        myUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(myUserId);

        getUserProfileInformation();

        // Listeners
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

                if (myEditTextProfileFirstName.getText().toString().equals("")) {
                    Toast.makeText(ProfileActivity.this, "Please, fill in your first name", Toast.LENGTH_SHORT).show();
                    status = 1;
                } else if (myEditTextProfileLastName.getText().toString().equals("")) {
                    Toast.makeText(ProfileActivity.this, "Please, fill in your last name", Toast.LENGTH_SHORT).show();
                    status = 1;
                } else if (myEditTextProfilePhoneNumber.getText().toString().equals("")) {
                    Toast.makeText(ProfileActivity.this, "Please, fill in your phone number", Toast.LENGTH_SHORT).show();
                    status = 1;
                } else if (status == 1) {
                    updateUserInformation();
                    Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_LONG).show();
                    finish();
                    status = 0;
                } else if (status == 0) {
                    updateUserInformation();
                    finish();
                }
            }
        });
    }

    // Functions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myActionBarDrawerToggle.onOptionsItemSelected(item)) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.navigationDrawerItemHome:
                Intent h = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(h);
                break;
            case R.id.navigationDrawerItemRidesOffered:
                Intent r = new Intent(ProfileActivity.this, RidesOfferedActivity.class);
                startActivity(r);
                break;
            case R.id.navigationDrawerItemRidesBooked:
                Intent b = new Intent(ProfileActivity.this, RidesBookedActivity.class);
                startActivity(b);
                break;
            case R.id.navigationDrawerItemProfile:
                Intent p = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(p);
                break;
            case R.id.navigationDrawerItemCampus:
                Intent s = new Intent(ProfileActivity.this, CampussesActivity.class);
                startActivity(s);
                break;
            case R.id.navigationDrawerItemLogout:
                FirebaseAuth.getInstance().signOut();
                Intent l = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(l);
                finish();
                break;
        }

        return false;
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
                    if (myMap.get("Account Type") != null) {
                        myUserProfileType = myMap.get("Account Type").toString();
                        myEditTextProfileType.setText(myUserProfileType);
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
        myUserProfilePhoneNumber = myEditTextProfilePhoneNumber.getText().toString();

        Map myMapUserInformation = new HashMap();

        myMapUserInformation.put("First Name", myUserProfileFirstName);
        myMapUserInformation.put("Last Name", myUserProfileLastName);
        myMapUserInformation.put("Phone Number", myUserProfilePhoneNumber);

        myUserDatabase.updateChildren(myMapUserInformation);

        /*

        // Email update
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
        */

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

                    Toast.makeText(ProfileActivity.this, "Avatar updated successfully", Toast.LENGTH_LONG).show();

                    finish();

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
