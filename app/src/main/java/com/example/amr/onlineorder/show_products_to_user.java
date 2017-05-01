package com.example.amr.onlineorder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class show_products_to_user extends AppCompatActivity {


    private ProgressDialog progressDialog;
    Category cate;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    DatabaseReference databaseReference;
    ArrayList<Product> products;
    ArrayList<Product> selected_items;
    int Counter = 0;
    DatabaseReference mData;
    String id_admin = "";
    private ProgressDialog mprogressDialog;


    Boolean is_in_action = false;
    TextView countertxtview;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products_to_user);


        toolbar = (Toolbar) findViewById(R.id.toolbar_make_order);
        setSupportActionBar(toolbar);

        countertxtview = (TextView) findViewById(R.id.countertxt);


        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /**
         ** Bundle
         **/

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        cate = (Category) bundle.getSerializable("CATEGORY");


        progressDialog = new ProgressDialog(this);

        selected_items = new ArrayList<>();
        products = new ArrayList<>();


        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference("productsC");

        /**
         * Fill List from Category
         */

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();
                //iterating through all the values in database
                products.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    String id = child.child("id").getValue().toString();
                    String name = child.child("name").getValue().toString();
                    String price = child.child("price").getValue().toString();
                    String url = child.child("url").getValue().toString();
                    String category_id = child.child("category_id").getValue().toString();
                    Product c = new Product(id, name, price, url, category_id);

                    if (cate.id.equals(c.category_id)) {
                        products.add(c);
                    }
                }

                adapter = new ProductsAdapter(show_products_to_user.this, products);

                //adding adapter to recyclerview
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(show_products_to_user.this, 2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /**
         * User ID
         */


        mprogressDialog = new ProgressDialog(this);
        //displaying progress dialog while fetching images
        mprogressDialog.setMessage("Please wait...");
        mprogressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mData = database.getReference();
        mData.child("users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mprogressDialog.dismiss();
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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_to_check,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.orderbtn) {
            /**
             * h3ml save ll order ll database
             */

            DatabaseReference mDatabase;

            mDatabase = FirebaseDatabase.getInstance().getReference();

            String id = mDatabase.push().getKey();

            Order send_order = new Order(id_admin, id, cate.Admin_id, "Pending", selected_items);

            mDatabase.child("Order").child(id).setValue(send_order);

            Toast.makeText(show_products_to_user.this, "Order Send Successfully", Toast.LENGTH_SHORT).show();


            is_in_action = false;
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.menu_to_check);
            adapter.notifyDataSetChanged();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            countertxtview.setText("Products");
            Counter = 0;
            selected_items.clear();

        }
        else if(item.getItemId()==R.id.checked_order)
        {
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.create_order);
            countertxtview.setText("0 Item Selected");
            is_in_action = true;
            adapter.notifyDataSetChanged();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Adapter
     */


    public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

        ArrayList<Product> productArrayList = new ArrayList<>();
        show_products_to_user activity;
        private Context context;

        public ProductsAdapter(Context context, ArrayList<Product> productArrayList) {
            this.productArrayList = productArrayList;
            this.context = context;
            activity = (show_products_to_user) context;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
            ViewHolder holder = new ViewHolder(view, activity);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            final Product upload = productArrayList.get(position);

            /**
             * Color
             */
            holder.RL.setBackgroundColor(Color.parseColor(cate.getColor()));
            holder.textViewName.setText(upload.getName());
            holder.price.setText(upload.getPrice().toString() + " LE");
            Glide.with(getApplicationContext()).load(upload.getUrl()).into(holder.imageView);
            if (!is_in_action) {
                holder.overflow.setVisibility(View.GONE);

            } else {
                holder.overflow.setVisibility(View.VISIBLE);
                holder.overflow.setChecked(false);
            }

            holder.overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /**
                     * lma b3ml select 3la 7aga
                     */

                    if (holder.overflow.isChecked()) {

                        selected_items.add(upload);
                        Counter = Counter + 1;
                        countertxtview.setText(Counter + " Item Selected");
                    }
                    /**
                     * lma y4el l checked b2a
                     */

                    else {
                        selected_items.remove(upload);
                        Counter = Counter - 1;
                        countertxtview.setText(Counter + " Item Selected");

                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return productArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView textViewName, price;
            public ImageView imageView;
            public CheckBox overflow;
            CardView cardView;
            RelativeLayout RL;
            show_products_to_user activity;

            public ViewHolder(View itemView, show_products_to_user activity) {
                super(itemView);

                textViewName = (TextView) itemView.findViewById(R.id.product_name);
                price = (TextView) itemView.findViewById(R.id.product_price);
                imageView = (ImageView) itemView.findViewById(R.id.image_product);
                overflow = (CheckBox) itemView.findViewById(R.id.overflow);
                RL = (RelativeLayout) itemView.findViewById(R.id.layoutproduct);
                this.activity = activity;
                cardView = (CardView) itemView.findViewById(R.id.card_view_product_to_user);
            }


        }


    }


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
