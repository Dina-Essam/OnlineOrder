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
    DatabaseReference databaseReference;
    User theone;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_as_user);

        txtEmailLogin = (EditText) findViewById(R.id.input_email_log_user);
        txtPwd = (EditText) findViewById(R.id.input_password_log_user);
        firebaseAuth = FirebaseAuth.getInstance();
        theone=new User();
        btn_login = (Button) findViewById(R.id.btn_login_user);


        if(firebaseAuth.getCurrentUser() != null)
        {
            /**
             * h3ml function bt3ml save ll 7aga
             */

            Toast.makeText(this, "you are log in already",Toast.LENGTH_SHORT).show();
            GoMainPage();

        }


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

                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginAsUser.this, "Login successful", Toast.LENGTH_LONG).show();
                                        GoMainPage();


                                    }
                                    else {
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

    private void GoMainPage()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();


        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children)
                {
                    String uid = child.child("id").getValue().toString();
                    String name = child.child("name").getValue().toString();
                    String address = child.child("address").getValue().toString();
                    String email = child.child("email").getValue().toString();
                    String phone = child.child("phone").getValue().toString();
                    User c= new User(uid,name,email,phone,address);
                    if (firebaseAuth.getCurrentUser().getEmail()==c.getEmail())
                    {
                        theone=c;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        Intent userLog=new Intent(LoginAsUser.this,show_brands_to_user.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("User",theone);
        userLog.putExtras(bundle);
        startActivity(userLog);
    }
}
