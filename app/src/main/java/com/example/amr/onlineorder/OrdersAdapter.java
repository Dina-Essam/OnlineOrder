package com.example.amr.onlineorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class OrdersAdapter extends BaseAdapter {
    public Context context;
    public ArrayList<Order> texts;
    public LayoutInflater layoutInflater;

    public OrdersAdapter(Context context, ArrayList<Order> texts ) {
        this.texts = texts;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public class Holder {
        public TextView textproducts, textcustomer, textprice;
        public Spinner s1;
    }

    @Override
    public int getCount() {
        return texts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_order_of_admin, parent, false);
            holder.textproducts = (TextView) convertView.findViewById(R.id.textproducts);
            holder.textcustomer = (TextView) convertView.findViewById(R.id.customer);
            holder.textprice = (TextView) convertView.findViewById(R.id.totalprice);
            holder.s1 = (Spinner)convertView.findViewById(R.id.spinnerstatus);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.textcustomer.setText(texts.get(position).userID);
        holder.textprice.setText(texts.get(position).totalPrice.toString());

        return convertView;
    }

}