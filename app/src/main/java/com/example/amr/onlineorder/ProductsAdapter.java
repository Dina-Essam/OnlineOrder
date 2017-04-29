package com.example.amr.onlineorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Belal on 2/23/2017.
 */

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
        holder.price.setText(upload.getPrice());

        Glide.with(context).load(upload.getUrl()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                dataBundle.putString("id_pro", upload.getId());
                dataBundle.putString("name_pro", upload.getName());
                dataBundle.putString("price_pro", upload.getPrice());
                dataBundle.putString("image_pro", upload.getUrl());
                Intent i = new Intent(context, SelectHowtoDoProduct.class);
                i.putExtras(dataBundle);
                context.startActivity(i);
            }
        });

//        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//
//
//                return true;
//            }
//        });
//
//        holder.textViewName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, upload.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(context, upload.getPrice(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName, price;
        public ImageView imageView, overflow;
        RelativeLayout RL;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.name_product);
            price = (TextView) itemView.findViewById(R.id.price_product);
            imageView = (ImageView) itemView.findViewById(R.id.image_product);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);
            RL = (RelativeLayout) itemView.findViewById(R.id.mainRelativeLayout);
        }
    }

}
