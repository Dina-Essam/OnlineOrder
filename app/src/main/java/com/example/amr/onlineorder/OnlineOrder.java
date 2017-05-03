package com.example.amr.onlineorder;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnlineOrder extends AppCompatActivity {
    ListView viewAllOrders;
    private ProgressDialog progressDialog;
    ArrayList<Product> products;
    DatabaseReference mDataRef;
    ArrayList<Order> orderlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_order);

        orderlist = new ArrayList<>();
        viewAllOrders = (ListView) findViewById(R.id.listViewOrders);
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        /**
         * fill Orders
         *
         **/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDataRef = database.getReference();
        mDataRef.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();
                orderlist.clear();
                Order oneOrder;
                products = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                try {
                    for (DataSnapshot child : children) {

                        oneOrder = child.getValue(Order.class);

                        orderlist.add(oneOrder);

                    }

                    CustomAdapter myAdapter = new CustomAdapter(orderlist);
                    viewAllOrders.setAdapter(myAdapter);
                } catch (Exception e) {
                    Toast.makeText(OnlineOrder.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    /**
     * Adapter
     */
    class CustomAdapter extends BaseAdapter {
        ArrayList<Order> orderArrayList = new ArrayList<>();

        CustomAdapter(ArrayList<Order> orderArrayList) {
            this.orderArrayList = orderArrayList;
        }

        @Override
        public int getCount() {
            return orderArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return orderArrayList.get(position);
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
            View view1 = linflater.inflate(R.layout.row_order_of_admin, null);

            TextView ordername = (TextView) view1.findViewById(R.id.productsname);
            for (Product s : orderlist.get(position).items) {
                ordername.append(s.getName() + " \n");
            }
            // ordername.setText("Order " + (position + 1));

            TextView userIDD = (TextView) view1.findViewById(R.id.customer);
            userIDD.setText(orderArrayList.get(position).userID);

            TextView orderprice = (TextView) view1.findViewById(R.id.totalprice);
            orderprice.setText("Total Price: " + orderArrayList.get(position).totalPrice.toString() + " LE");

            return view1;
        }
    }


}