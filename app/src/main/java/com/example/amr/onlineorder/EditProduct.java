package com.example.amr.onlineorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditProduct extends AppCompatActivity {

    EditText namee, price;
    Button bTnReg;
    ImageView Image_product;
    String id_pro, name_pro, price_pro, image_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Bundle extras = getIntent().getExtras();
        id_pro = extras.getString("iD_pro");
        name_pro = extras.getString("na_pro");
        price_pro = extras.getString("pri_pro");
        image_pro = extras.getString("img_pro");

        namee = (EditText) findViewById(R.id.input_product_name_edit);
        price = (EditText) findViewById(R.id.input_product_price_edit);

        bTnReg = (Button) findViewById(R.id.btn_edit_product);
        Image_product = (ImageView) findViewById(R.id.image_product_edit);

        Toast.makeText(EditProduct.this, id_pro, Toast.LENGTH_SHORT).show();

    }
}
