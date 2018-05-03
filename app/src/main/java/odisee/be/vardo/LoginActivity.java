package odisee.be.vardo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // Variables App
    private EditText myEditTextLoginEmail;
    private EditText myEditTextLoginPassword;
    private Button myButtonLogin;
    private TextView myTextViewRegister;

    // Variables Firebase
    private FirebaseAuth myFirebaseAuth;
    private FirebaseAuth.AuthStateListener myFireBaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myTextViewRegister = findViewById(R.id.textViewRegister);
        myEditTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        myEditTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        myButtonLogin = findViewById(R.id.buttonLogin);

        myTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        myButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String myEmail = myEditTextLoginEmail.getText().toString();
                final String myPassword = myEditTextLoginPassword.getText().toString();

                myFirebaseAuth.signInWithEmailAndPassword(myEmail, myPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
