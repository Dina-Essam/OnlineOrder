package com.example.amr.onlineorder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoriesAdapter extends BaseAdapter {
    public Context context;
    // el array list de 4ail feha el name w el color le kol category
    public ArrayList<Category> texts;
    public LayoutInflater layoutInflater;

    public CategoriesAdapter(Context context, ArrayList<Category> texts) {
        this.texts = texts;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    public class Holder {
        public TextView textView;
        public RelativeLayout rl;
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
            convertView = layoutInflater.inflate(R.layout.list_item_cat, parent, false);
            holder.textView = (TextView) convertView.findViewById(R.id.name_cat);
            holder.rl = (RelativeLayout) convertView.findViewById(R.id.relativecategory);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setText(texts.get(position).getName());
        holder.rl.setBackgroundColor(Color.parseColor(texts.get(position).getColor()));
        return convertView;
    }

}