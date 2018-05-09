package odisee.be.vardo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //  App - Edit Texts
    private EditText myEditTextRegisterEmail;
    private EditText myEditTextRegisterPassword;

    // App - Buttons
    private Button myButtonRegister;

    // App - Variables
    private String myUserRegisterEmail;

    // Firebase
    private FirebaseAuth myFirebaseAuth;
    private FirebaseAuth.AuthStateListener myFireBaseAuthStateListener;
    private DatabaseReference myUserDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Edit Texts
        myEditTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        myEditTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);

        // Buttons
        myButtonRegister = findViewById(R.id.buttonRegister);

        myFirebaseAuth = FirebaseAuth.getInstance();

        // Listeners
        myFireBaseAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser myFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                if (myFirebaseUser != null) {
                    Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
            }
        };

        myButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String myEmail = myEditTextRegisterEmail.getText().toString();
                final String myPassword = myEditTextRegisterPassword.getText().toString();

                if (myEmail.matches("")) {
                    Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (myPassword.matches("")) {
                    Toast.makeText(RegisterActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                } else {

                    myFirebaseAuth.createUserWithEmailAndPassword(myEmail, myPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Sign-up failed", Toast.LENGTH_SHORT).show();
                            } else {
                                String myUserId = myFirebaseAuth.getCurrentUser().getUid();

                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(myUserId);
                                current_user_db.setValue(true);

                                myUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(myUserId);
                                updateUserInformation();

                                Toast.makeText(RegisterActivity.this, "Registered completed successfully", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                                return;
                            }
                        }
                    });
                }
            }
        });
    }

    // Functions
    @Override
    protected void onStart() {
        super.onStart();
        myFirebaseAuth.addAuthStateListener(myFireBaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myFirebaseAuth.removeAuthStateListener(myFireBaseAuthStateListener);
    }

    private void updateUserInformation() {

        myUserRegisterEmail = myEditTextRegisterEmail.getText().toString();

        Map myMapUserInformation = new HashMap();

        myMapUserInformation.put("Email", myUserRegisterEmail);

        myUserDatabase.updateChildren(myMapUserInformation);

        finish();
    }
}
