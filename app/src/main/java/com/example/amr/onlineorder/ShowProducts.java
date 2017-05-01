package com.example.amr.onlineorder;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowProducts extends AppCompatActivity {

    //recyclerview object
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    //database reference
    private DatabaseReference mDatabase;

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<Product> uploads;

    String cat_id, cat_color, adminnn_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        cat_id = extras.getString("cat_id");
        cat_color = extras.getString("cat_color");
        adminnn_id = extras.getString("adminnn_id");

        progressDialog = new ProgressDialog(this);

        uploads = new ArrayList<>();

        //Toast.makeText(ShowProducts.this, cat_id, Toast.LENGTH_SHORT).show();
        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("productsC");

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();
                uploads.clear();
                //iterating through all the values in database
                for (DataSnapshot child : snapshot.getChildren()) {
                    String uid = child.getKey();
                    String name = child.child("name").getValue().toString();
                    String price = child.child("price").getValue().toString();
                    String url = child.child("url").getValue().toString();
                    String category_id = child.child("category_id").getValue().toString();
                    Product c = new Product(uid, name, price, url, category_id);

                    if (cat_id.equals(category_id)) {
                        uploads.add(c);
                    }
                }
                //creating adapter
                adapter = new ProductsAdapter(getApplicationContext(), uploads, cat_color);

                //adding adapter to recyclerview
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ShowProducts.this, 2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ShowProducts.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

                        final String idProd = uploads.get(position).getId();
                        final String nameProd = uploads.get(position).getName();
                        final String priceProd = uploads.get(position).getPrice();
                        final String imageProd = uploads.get(position).getUrl();

                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowProducts.this);
                        builder.setMessage("Do you want to update " + uploads.get(position).getName() + " ?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Bundle dataBundle = new Bundle();
                                        dataBundle.putString("iD_pro", idProd);
                                        dataBundle.putString("na_pro", nameProd);
                                        dataBundle.putString("pri_pro", priceProd);
                                        dataBundle.putString("img_pro", imageProd);
                                        Intent i = new Intent(ShowProducts.this, EditProduct.class);
                                        i.putExtras(dataBundle);
                                        startActivity(i);
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
                })
        );
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                dataBundle.putString("adminnnn_id", adminnn_id);
                Intent intent = new Intent(getApplicationContext(), AddProduct.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
