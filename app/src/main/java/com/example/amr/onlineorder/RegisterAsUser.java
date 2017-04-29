package com.example.amr.onlineorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterAsUser extends AppCompatActivity {

    EditText name, email, password, phone, address;
    FirebaseAuth firebaseAuth;
    String a = "";
    Button bTnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_user);

        bTnReg = (Button) findViewById(R.id.btn_reg_user);

        firebaseAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.input_brand_name_log_user);
        email = (EditText) findViewById(R.id.input_email_log_user);
        password = (EditText) findViewById(R.id.input_password_log_user);
        phone = (EditText) findViewById(R.id.input_phone_log_user);
        address = (EditText) findViewById(R.id.input_address_log_user);

        bTnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().isEmpty()) {
                    name.setError("Please Enter Name");
                }
                if (email.getText().toString().isEmpty()) {
                    email.setError("Please Enter Email");
                }
                if (password.getText().toString().isEmpty()) {
                    password.setError("Please Enter Password");
                }
                if (phone.getText().toString().isEmpty()) {
                    phone.setError("Please Enter Phone");
                }
                if (address.getText().toString().isEmpty()) {
                    address.setError("Please Enter Address");
                } else {
                    final ProgressDialog progressDialog = ProgressDialog.show(RegisterAsUser.this, "Please wait...", "Processing...", true);
                    (firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();

                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterAsUser.this, "Registration successful", Toast.LENGTH_LONG).show();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent i = new Intent(RegisterAsUser.this, LoginAsUser.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        a = "a";
                                        Log.e("ERROR", task.getException().toString());
                                        Toast.makeText(RegisterAsUser.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    if (a.isEmpty()) {
                        DatabaseReference mDatabase;

                        mDatabase = FirebaseDatabase.getInstance().getReference();

                        String s = mDatabase.push().getKey();

                        User user = new User(s,name.getText().toString(), email.getText().toString(), phone.getText().toString(), address.getText().toString());

                        mDatabase.child("users").child(s).setValue(user);

                    }
                }
            }
        });
    }

    public void GoLoginUser(View view) {
        Intent i = new Intent(RegisterAsUser.this, LoginAsUser.class);
        startActivity(i);
    }
}
