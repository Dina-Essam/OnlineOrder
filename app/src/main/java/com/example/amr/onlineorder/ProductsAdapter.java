package com.example.amr.onlineorder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<Product> uploads;
    private String color;

    public ProductsAdapter(Context context, List<Product> uploads, String color) {
        this.uploads = uploads;
        this.context = context;
        this.color = color;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Product upload = uploads.get(position);

        holder.RL.setBackgroundColor(Color.parseColor(color));

        holder.textViewName.setText(upload.getName());
        holder.price.setText(upload.getPrice() + " LE");

        Glide.with(context).load(upload.getUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName, price;
        public ImageView imageView;
        RelativeLayout RL;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.name_product);
            price = (TextView) itemView.findViewById(R.id.price_product);
            imageView = (ImageView) itemView.findViewById(R.id.image_product);
            RL = (RelativeLayout) itemView.findViewById(R.id.mainRelativeLayout);
        }
    }

}
