package com.example.amr.onlineorder;

import android.content.Context;
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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class show_products_of_order extends AppCompatActivity {

    ArrayList<Product> products=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products_of_order);




        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        products = (ArrayList<Product>) bundle.getSerializable("PRODUCTS");

        recyclerView = (RecyclerView) findViewById(R.id.product_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new ProductAdapter(show_products_of_order.this, products);

        //adding adapter to recyclerview
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(show_products_of_order.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);




    }



    /**
     * Adapter
     */

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
    {
        ArrayList<Product> productArrayList = new ArrayList<>();
        show_products_of_order activity;
        private Context context;

        public ProductAdapter(Context context, ArrayList<Product> productArrayList) {
            this.productArrayList = productArrayList;
            this.context = context;
            activity = (show_products_of_order) context;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
            ViewHolder holder = new ViewHolder(view, activity);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {



            final Product upload = productArrayList.get(position);

            /**
             * Color
             */

            //holder.RL.setBackgroundColor(Color.parseColor(cate.getColor()));
            holder.textViewName.setText(upload.getName());
            holder.price.setText(upload.getPrice().toString() + " LE");
            Glide.with(getApplicationContext()).load(upload.getUrl()).into(holder.imageView);
            holder.overflow.setVisibility(View.GONE);





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
            show_products_of_order activity;

            public ViewHolder(View itemView, show_products_of_order activity) {
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
