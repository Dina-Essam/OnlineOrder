package com.example.amr.onlineorder;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainAdmin extends AppCompatActivity {

    Button CMS, OnlineOrder, ShowUser, Logout;
    DatabaseReference databaseReference;
    String id_admin = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        CMS = (Button) findViewById(R.id.cms);
        OnlineOrder = (Button) findViewById(R.id.onlineorder);
        ShowUser = (Button) findViewById(R.id.showuser);
        Logout = (Button) findViewById(R.id.logout);

        progressDialog = new ProgressDialog(this);
        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        // hena b3ml display lel id bta3 el admin ele 3ml login b7es ene ab3to lel activity ele ba3deh 34an ageb el categories
        // 3la asaso w b7es mt5od4 w2t henak ene a3ml el 7agten fa 2semthom 3la el two activities a7sn
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.child("admins").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    String uid = child.getKey();
                    String email = child.child("email").getValue().toString();

                    if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(email)) {
                        id_admin = uid;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        CMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // el bundle dh 34an ywadeh 3la el show categories w ykon m3ah el id of admin w hwa ray7 34an a3red 3la asaso el categories bt3to b2a
                Bundle dataBundle = new Bundle();
                dataBundle.putString("admin_id", id_admin);
                Intent i = new Intent(MainAdmin.this, ShowCategories.class);
                i.putExtras(dataBundle);
                startActivity(i);

            }
        });
        OnlineOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // el bundle dh 34an ywadeh 3la el show categories w ykon m3ah el id of admin w hwa ray7 34an a3red 3la asaso el categories bt3to b2a
                Bundle dataBundle = new Bundle();
                dataBundle.putString("ad_id", id_admin);
                Intent i = new Intent(MainAdmin.this, OnlineOrder.class);
                i.putExtras(dataBundle);
                startActivity(i);

            }
        });
        ShowUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle dataBundle = new Bundle();
                dataBundle.putString("admi_id", id_admin);
                Intent i = new Intent(MainAdmin.this, ShowUsersOfBrand.class);
                i.putExtras(dataBundle);
                startActivity(i);

            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainAdmin.this);
                builder.setMessage("Do you want to logout ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Nothing
                    }
                });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

            }
        });
    }

}
