package com.example.amr.onlineorder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products_to_user);


        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /**
         ** Bundle
         **/

        Bundle bundle = new Bundle();
        bundle=getIntent().getExtras();
        cate=(Category) bundle.getSerializable("CATEGORY");


        progressDialog = new ProgressDialog(this);

        products=new ArrayList<>();



        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        databaseReference =FirebaseDatabase.getInstance().getReference("productsC");

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
                    Product c = new Product(id,name, price, url, category_id);

                    if (cate.id.equals(c.category_id))
                    {
                        products.add(c);
                    }
                }

                adapter = new ProductsAdapter(getApplicationContext(),products);

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













    }





    /**
     * Adapter
     */

    public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

        private Context context;
        ArrayList<Product> productArrayList;

        public ProductsAdapter(Context context, ArrayList<Product> productArrayList) {
            this.productArrayList = productArrayList;
            this.context = context;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_card, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            final Product upload = productArrayList.get(position);

            /**
             * Color
             */
            holder.RL.setBackgroundColor(Color.parseColor(cate.getColor()));
            holder.textViewName.setText(upload.getName());
            holder.price.setText(upload.getPrice().toString()+" LE");
            Glide.with(getApplicationContext()).load(upload.getUrl()).into(holder.imageView);




        }

        @Override
        public int getItemCount() {
            return productArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public TextView textViewName, price;
            public ImageView imageView;
            public CheckBox overflow;

            RelativeLayout RL;

            public ViewHolder(View itemView) {
                super(itemView);

                textViewName = (TextView) itemView.findViewById(R.id.product_name);
                price = (TextView) itemView.findViewById(R.id.product_price);
                imageView = (ImageView) itemView.findViewById(R.id.image_product);
                overflow = (CheckBox) itemView.findViewById(R.id.overflow);
                RL = (RelativeLayout) itemView.findViewById(R.id.layoutproduct);
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
