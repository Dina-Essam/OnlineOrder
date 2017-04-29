package com.example.amr.onlineorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    Button resetPassword;
    EditText forgot_emailEditText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        firebaseAuth = FirebaseAuth.getInstance();

        forgot_emailEditText = (EditText) findViewById(R.id.emailtoreset);
        resetPassword = (Button) findViewById(R.id.reset_btn);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = forgot_emailEditText.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(ForgetPassword.this, "Please, Enter your registered Email", Toast.LENGTH_LONG).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(ForgetPassword.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(ForgetPassword.this, "Failed to send reset Email!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(ForgetPassword.this, "We have sent you instructions to reset your password on mail!", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }

}
