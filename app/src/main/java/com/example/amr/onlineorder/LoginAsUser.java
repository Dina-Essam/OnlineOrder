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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAsUser extends AppCompatActivity {

    private EditText txtEmailLogin;
    private EditText txtPwd;
    private FirebaseAuth firebaseAuth;
    Button btn_login;
    DatabaseReference databaseReference;
    String id_user = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {

            progressDialog = new ProgressDialog(this);
            //displaying progress dialog while fetching images
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            // hena ana b3ml check eza kan el email ele kan 3amel login 2bl ma y destroy lel app hwa hwa ele mwgod fel table bta3 el users wla la2
            // l2en el auth bta3et firebase msh btfara2 ben user w admin de ele bt5lihom yfr2o :D

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference();
            databaseReference.child("users").addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    for (DataSnapshot child : children) {
                        String uid = child.getKey();
                        String email = child.child("email").getValue().toString();

                        if (firebaseAuth.getCurrentUser().getEmail().equals(email)) {
                            id_user = uid;
                        }
                    }
                    if (!id_user.isEmpty()) {
                        startActivity(new Intent(LoginAsUser.this, show_brands_to_user.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        setContentView(R.layout.activity_login_as_user);

        txtEmailLogin = (EditText) findViewById(R.id.input_email_log_user);
        txtPwd = (EditText) findViewById(R.id.input_password_log_user);
        firebaseAuth = FirebaseAuth.getInstance();

        btn_login = (Button) findViewById(R.id.btn_login_user);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtEmailLogin.getText().toString().isEmpty()) {
                    txtEmailLogin.setError("Please Enter Email");
                }
                if (txtPwd.getText().toString().isEmpty()) {
                    txtPwd.setError("Please Enter Password");
                } else {
                    final ProgressDialog progressDialog = ProgressDialog.show(LoginAsUser.this, "Please wait...", "Proccessing...", true);

                    (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(), txtPwd.getText().toString()))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();

                                    // check of auth firebase el email dh mwgod wla la2
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginAsUser.this, "Login successful", Toast.LENGTH_LONG).show();
                                        GoMainPage();

                                    } else {
                                        Log.e("ERROR", task.getException().toString());
                                        Toast.makeText(LoginAsUser.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }


    public void GoRegisterUser(View view) {
        Intent i = new Intent(LoginAsUser.this, RegisterAsUser.class);
        startActivity(i);
    }

    public void ForgetUser(View view) {
        Intent i = new Intent(LoginAsUser.this, ForgetPassword.class);
        startActivity(i);
    }

    private void GoMainPage() {
        Intent userLog = new Intent(LoginAsUser.this, show_brands_to_user.class);
        startActivity(userLog);
        finish();
    }
}
