package com.example.amr.onlineorder;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {

    EditText name, price;
    Button bTnReg;
    ImageView Image_product;
    private static final int PICK_IMAGE = 100;
    ArrayList<String> name_cat, id_cat;
    //uri to store file
    private Uri filePath;
    String catch_id;
    DatabaseReference databaseReference;
    //firebase objects
    String imageurl = "";
    String adminnnn_id;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        name_cat = new ArrayList<>();
        id_cat = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        adminnnn_id = extras.getString("adminnnn_id");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.child("categoriesAdmin").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                name_cat.clear();
                id_cat.clear();
                for (DataSnapshot child : children) {
                    String uid = child.getKey();
                    String name = child.child("name").getValue().toString();
                    String admin_id = child.child("admin_id").getValue().toString();

                    if (adminnnn_id.equals(admin_id)) {
                        name_cat.add(name);
                        id_cat.add(uid);
                    }
                }
                Spinner staticSpinner = (Spinner) findViewById(R.id.static_spinner);

                ArrayAdapter<String> staticAdapter = new ArrayAdapter<String>(AddProduct.this,
                        android.R.layout.simple_spinner_item, name_cat);

                staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                staticSpinner.setAdapter(staticAdapter);

                staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        String s = ((String) parent.getItemAtPosition(position));

                        if (s.equals(name_cat.get(position))) {
                            catch_id = id_cat.get(position);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();

        name = (EditText) findViewById(R.id.input_product_name);
        price = (EditText) findViewById(R.id.input_product_price);

        bTnReg = (Button) findViewById(R.id.btn_add_product);
        Image_product = (ImageView) findViewById(R.id.image_product);


        Image_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        bTnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().isEmpty()) {
                    name.setError("Please Enter Name");
                }
                if (price.getText().toString().isEmpty()) {
                    price.setError("Please Enter Price");
                } else {
                    uploadFile();
                }
            }
        });
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child("productsC/" + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //dismissing the progress dialog
                    progressDialog.dismiss();

                    //displaying success toast
                    Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                    imageurl = taskSnapshot.getDownloadUrl().toString();

                    //  Toast.makeText(AddProduct.this, catch_id+imageurl, Toast.LENGTH_SHORT).show();
                    DatabaseReference mDatabase;

                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    String s = mDatabase.push().getKey();

                    Product a = new Product(s, name.getText().toString().trim(), price.getText().toString().trim(), imageurl, catch_id);

                    mDatabase.child("productsC").child(s).setValue(a);
                    Toast.makeText(AddProduct.this, "Done", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //displaying the upload progress
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                }
            });
        } else {
            Toast.makeText(AddProduct.this, "File Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Image_product.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
