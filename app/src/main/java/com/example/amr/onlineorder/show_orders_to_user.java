package com.example.amr.onlineorder;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class show_orders_to_user extends AppCompatActivity {

    ListView viewAllOrders;
    DatabaseReference mdatabaseReference;
    String id_admin = "";
    private ProgressDialog mprogressDialog;
    DatabaseReference mDataRef;
    ArrayList<Order> orderlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orders_to_user);

        orderlist = new ArrayList<>();
        viewAllOrders = (ListView) findViewById(R.id.listview_Orders_user);



        mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setMessage("Please wait...");
        mprogressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mdatabaseReference = database.getReference();
        mdatabaseReference.child("users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
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



        mDataRef=database.getReference();
        mDataRef.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                mprogressDialog.dismiss();
                for(DataSnapshot child : children)
                {
                    Order order=child.getValue(Order.class);
                    if(order.userID==id_admin)
                    {
                        orderlist.add(order);
                    }

                }

                CustomAdapter myAdapter = new CustomAdapter(orderlist);
                viewAllOrders.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        viewAllOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

/**
 * hb3tha ll order products
 */

                Intent GOTOorder = new Intent(show_orders_to_user.this, show_brands_to_user.class);
                startActivity(GOTOorder);

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
            View view1 = linflater.inflate(R.layout.row_order, null);

            /**
             * blwen l category w bzher 2sm l Product
             */
            TextView bname = (TextView) view1.findViewById(R.id.order_name_show_user);
            bname.setText("Order"+position);

            TextView state =(TextView)view1.findViewById(R.id.order_state_user);
            state.setText(orderArrayList.get(position).getState());

            TextView price =(TextView)view1.findViewById(R.id.order_price_user);
            state.setText(orderArrayList.get(position).getTotalPrice().toString());


            return view1;
        }
    }


}
