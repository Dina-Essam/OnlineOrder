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
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegisterAsAdmin extends AppCompatActivity {

    EditText name, email, password, phone, address;
    Button bTnReg;
    ImageView Image_brand;
    FirebaseAuth firebaseAuth;
    private static final int PICK_IMAGE = 100;
    //uri to store file
    private Uri filePath;
    String imageurl = "";
    //firebase objects
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_admin);

        firebaseAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        name = (EditText) findViewById(R.id.input_brand_name_log_admin);
        email = (EditText) findViewById(R.id.input_email_log_admin);
        password = (EditText) findViewById(R.id.input_password_log_admin);
        phone = (EditText) findViewById(R.id.input_phone_log_admin);
        address = (EditText) findViewById(R.id.input_address_log_admin);

        bTnReg = (Button) findViewById(R.id.btn_reg_admin);
        Image_brand = (ImageView) findViewById(R.id.image_brand);


        Image_brand.setOnClickListener(new View.OnClickListener() {
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
                    (firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        uploadFile();
                                    } else {
                                        Log.e("ERROR", task.getException().toString());
                                        Toast.makeText(RegisterAsAdmin.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });
    }


    public void GoLoginAdmin(View view) {
        Intent i = new Intent(RegisterAsAdmin.this, LoginAsAdmin.class);
        startActivity(i);
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
            StorageReference sRef = storageReference.child("admins/" + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //dismissing the progress dialog
                    progressDialog.dismiss();

                    //displaying success toast
                    Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                    imageurl = taskSnapshot.getDownloadUrl().toString();

                    DatabaseReference mDatabase;

                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    String s = mDatabase.push().getKey();

                    Admin a = new Admin(s,name.getText().toString(), email.getText().toString(), phone.getText().toString(), address.getText().toString(), imageurl);

                    mDatabase.child("admins").child(s).setValue(a);

                    Toast.makeText(RegisterAsAdmin.this, "Registration successful", Toast.LENGTH_LONG).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(RegisterAsAdmin.this, LoginAsAdmin.class);
                    startActivity(i);
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
            Toast.makeText(RegisterAsAdmin.this, "File Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Image_brand.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
