package com.example.amr.onlineorder;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class show_categories_to_user extends AppCompatActivity {

    private ProgressDialog progressDialog;
    ListView viewAllBrands;
    Admin Brandinit;
    ArrayList<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categories_to_user);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Brandinit = (Admin) bundle.getSerializable("BRAND");
        categoryList = new ArrayList<>();
        viewAllBrands = (ListView) findViewById(R.id.listview_categories);
        progressDialog = new ProgressDialog(this);


        progressDialog.setMessage("Please wait...");
        progressDialog.show();



        /**
         * Fill List from Brand
         */

        // hena bn3ml show lel categories ele mwgoda fel brand el folany ele enTa e5tarto w wla asaso ba5od el id bta3 el category lma tedos w tro7 t4try product mn henak fel activity ele ba3do

        categoryList=Brandinit.CategoriesofBrand(Brandinit.id,viewAllBrands,this);
        progressDialog.dismiss();




        viewAllBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                /** send it to product activity
                 * h8yar l userlogin 34an ywdeni 3l Products lma tt3ml
                 */
                Intent GOTOproduct = new Intent(show_categories_to_user.this, show_products_to_user.class);

                Bundle bundle = new Bundle();
                //bundle.putSerializable("USER", Theone);
                bundle.putSerializable("CATEGORY", categoryList.get(position));
                GOTOproduct.putExtras(bundle);
                startActivity(GOTOproduct);
            }
        });


    }


    /**
     * Adapter
     */
    class CustomAdapter extends BaseAdapter {
        ArrayList<Category> categoryArrayList = new ArrayList<>();

        CustomAdapter(ArrayList<Category> listofbrands) {
            this.categoryArrayList = listofbrands;
        }

        @Override
        public int getCount() {
            return categoryArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return categoryArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater linflater = getLayoutInflater();
            View view1 = linflater.inflate(R.layout.row_category, null);

            /**
             * blwen l category w bzher 2sm l Product
             */
            RelativeLayout layoutcat = (RelativeLayout) view1.findViewById(R.id.layoutcat);
            layoutcat.setBackgroundColor(Color.parseColor(categoryArrayList.get(position).getColor()));
            TextView bname = (TextView) view1.findViewById(R.id.category_name_show_user);
            bname.setText(categoryArrayList.get(position).getName());


            return view1;
        }
    }


}
