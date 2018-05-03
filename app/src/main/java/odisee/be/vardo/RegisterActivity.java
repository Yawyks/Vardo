package odisee.be.vardo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {

    // Variables App
    private EditText myEditTextRegisterEmail;
    private EditText myEditTextRegisterPassword;
    private Button myButtonRegister;

    // Variables Firebase
    private FirebaseAuth myFirebaseAuth;
    private FirebaseAuth.AuthStateListener myFireBaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myEditTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        myEditTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        myButtonRegister = findViewById(R.id.buttonRegister);

        myFirebaseAuth = FirebaseAuth.getInstance();

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
                    Toast.makeText(RegisterActivity.this, "Invalid e-mail address", Toast.LENGTH_SHORT).show();
                } else if (myPassword.matches("")) {
                    Toast.makeText(RegisterActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                } else {

                    myFirebaseAuth.createUserWithEmailAndPassword(myEmail, myPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Sign-up error.", Toast.LENGTH_SHORT).show();
                            } else {
                                String myUserId = myFirebaseAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(myUserId);
                                current_user_db.setValue(true);

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
}
