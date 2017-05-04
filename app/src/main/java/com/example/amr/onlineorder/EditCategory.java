package com.example.amr.onlineorder;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//zy el add bezabt
public class EditCategory extends AppCompatActivity {

    EditText Input_category_name;
    private SeekBar red;
    private SeekBar green;
    private SeekBar blue;
    Button Btn_update_category;
    String cat_id, name_cat, col_cat;
    private TextView cat_color;
    private static int color_red = 0;
    private static int color_green = 0;
    private static int color_blue = 0;
    private static String finColor = "";
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private static final int sizeOfIntInHalfBytes = 8;
    private static final int numberOfBitsInAHalfByte = 4;
    private static final int halfByte = 0x0F;
    private static final char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("categoriesAdmin");

        Input_category_name = (EditText) findViewById(R.id.input_category_name_edit);
        red = (SeekBar) findViewById(R.id.red_edit);
        green = (SeekBar) findViewById(R.id.green_edit);
        blue = (SeekBar) findViewById(R.id.blue_edit);
        cat_color = (TextView) findViewById(R.id.image_product_edit);
        Btn_update_category = (Button) findViewById(R.id.btn_edit_category);

        Bundle extras = getIntent().getExtras();
        cat_id = extras.getString("iD_cat");
        name_cat = extras.getString("na_cat");
        col_cat = extras.getString("col_cat");

        Input_category_name.setText(name_cat);
        cat_color.setBackgroundColor(Color.parseColor(col_cat));

        red.setProgress(color_red);
        green.setProgress(color_green);
        blue.setProgress(color_blue);

        Btn_update_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Input_category_name.getText().toString().isEmpty()) {
                    Input_category_name.setError("Please Enter Category Name");
                } else {
                    if (finColor.isEmpty()) {
                        updateCat(Input_category_name.getText().toString(), col_cat, cat_id);
                    } else {
                        updateCat(Input_category_name.getText().toString(), finColor, cat_id);
                    }
                    Toast.makeText(EditCategory.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                color_red = progress;
                cat_color.setBackgroundColor(Color.rgb(color_red, color_green, color_blue));
                finColor = reurn_color(color_red, color_green, color_blue);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                color_green = progress;
                cat_color.setBackgroundColor(Color.rgb(color_red, color_green, color_blue));
                finColor = reurn_color(color_red, color_green, color_blue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                color_blue = progress;
                cat_color.setBackgroundColor(Color.rgb(color_red, color_green, color_blue));
                finColor = reurn_color(color_red, color_green, color_blue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    public static String decToHex(int dec) {
        StringBuilder hexBuilder = new StringBuilder(sizeOfIntInHalfBytes);
        hexBuilder.setLength(sizeOfIntInHalfBytes);
        for (int i = sizeOfIntInHalfBytes - 1; i >= 0; --i) {
            int j = dec & halfByte;
            hexBuilder.setCharAt(i, hexDigits[j]);
            dec >>= numberOfBitsInAHalfByte;
        }
        return hexBuilder.toString().substring(6, 8);
    }


    public static String reurn_color(int r, int g, int b) {
        String color = "#";
        color = color + decToHex(r);
        color = color + decToHex(g);
        color = color + decToHex(b);

        return color;

    }

    // function el update fel firebase bmsek el root w b7ded el id w babda2 a update
    private void updateCat(String name, String color, String id) {

        mFirebaseDatabase.child(id).child("name").setValue(name);
        mFirebaseDatabase.child(id).child("color").setValue(color);

    }

}
