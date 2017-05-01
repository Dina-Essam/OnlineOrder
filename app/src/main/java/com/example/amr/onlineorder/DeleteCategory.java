package com.example.amr.onlineorder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeleteCategory extends AppCompatActivity {

    ArrayList<String> listnames, listids;
    String s, catch_id;
    Button delete;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("categoriesAdmin");

        delete = (Button) findViewById(R.id.deleteCat);

        Intent i = getIntent();
        listids = i.getStringArrayListExtra("idsCatlist");
        listnames = i.getStringArrayListExtra("namesCatlist");

        Spinner staticSpinner = (Spinner) findViewById(R.id.spinnerCatname);

        ArrayAdapter<String> staticAdapter = new ArrayAdapter<String>(DeleteCategory.this,
                android.R.layout.simple_spinner_item, listnames);

        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);

        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                s = ((String) parent.getItemAtPosition(position));

                if (s.equals(listnames.get(position))) {
                    catch_id = listids.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteCategory.this);
                builder.setMessage("Do you want to delete " + s + " ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                mFirebaseDatabase.child(catch_id).removeValue();
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
