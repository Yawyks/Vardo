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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    // App
    private EditText myEditTextEmailForgot;
    private Button myButtonForgotPassword;

    // Firebase
    private FirebaseAuth myFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Edit Texts
        myEditTextEmailForgot = findViewById(R.id.editTextEmailForgot);

        // Buttons
        myButtonForgotPassword = findViewById(R.id.buttonForgotPassword);

        // Firebase
        myFirebaseAuth = FirebaseAuth.getInstance();

        // Listeners
        myButtonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = myEditTextEmailForgot.getText().toString().trim();

                if (userEmail.matches("")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid e-mail address", Toast.LENGTH_SHORT).show();
                } else {
                    myFirebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                finish();
                                startActivity(i);
                                Toast.makeText(ForgotPasswordActivity.this, "Reset password link sent successfully", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Error sending password link", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
