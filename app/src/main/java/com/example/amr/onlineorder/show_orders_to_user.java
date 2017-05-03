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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class show_orders_to_user extends AppCompatActivity {

    ListView viewAllOrders;
    DatabaseReference mData;
    String id_admin = "";
    private ProgressDialog mprogressDialog;
    private ProgressDialog progressDialog;
    ArrayList<Product> products;
    DatabaseReference mDataRef;
    ArrayList<Order> orderlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orders_to_user);

        orderlist = new ArrayList<>();
        viewAllOrders = (ListView) findViewById(R.id.listview_Orders_user);
        FirebaseDatabase database = FirebaseDatabase.getInstance();





        /**
         * User ID

         **/

        mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setMessage("Please wait...");
        mprogressDialog.show();

        mData = database.getReference();
        mData.child("users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mprogressDialog.dismiss();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {

                    String uid = child.child("id").getValue().toString();
                    String email = child.child("email").getValue().toString();


                    if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(email)) {
                        id_admin=uid;

                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        /**
         * fill Orders
         *
         **/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDataRef=database.getReference();
        mDataRef.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();
                orderlist.clear();
                Order oneOrder;
                products = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                try
                {
                    for (DataSnapshot child : children)
                    {

                        oneOrder=child.getValue(Order.class);
                        if(id_admin.equals(oneOrder.userID)) {orderlist.add(oneOrder);}

                    }


                    CustomAdapter myAdapter = new CustomAdapter(orderlist);
                    viewAllOrders.setAdapter(myAdapter);
                }
                catch (Exception e)
                {
                    Toast.makeText(show_orders_to_user.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        viewAllOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                    Intent GOTOorder = new Intent(show_orders_to_user.this, show_products_of_order.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PRODUCTS", orderlist.get(position).items);
                    GOTOorder.putExtras(bundle);
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

           TextView ordername=(TextView)view1.findViewById(R.id.order_name_show_user);
           ordername.setText("Order "+(position+1));

            TextView orderstate=(TextView)view1.findViewById(R.id.order_state_user);
            orderstate.setText(orderArrayList.get(position).state);

            TextView orderprice=(TextView)view1.findViewById(R.id.order_price_user);
            orderprice.setText("Total Price: "+orderArrayList.get(position).totalPrice.toString()+" LE");

            return view1;
        }
    }


}
