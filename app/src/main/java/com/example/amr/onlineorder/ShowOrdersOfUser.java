package com.example.amr.onlineorder;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowOrdersOfUser extends AppCompatActivity {

    ListView viewAllOrders;
    String id_user = "";
    private ProgressDialog progressDialog;
    ArrayList<Product> products;
    DatabaseReference mDataRef;
    ArrayList<Order> orderlist;
    DatabaseReference mData;
    String username="";
    private DatabaseReference mF;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orders_of_user);

        orderlist = new ArrayList<>();
        viewAllOrders = (ListView) findViewById(R.id.listview_ordersofuser);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Bundle extras = getIntent().getExtras();
        id_user = extras.getString("user_id");

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
                        if (id_user.equals(oneOrder.userID)) {
                            orderlist.add(oneOrder);
                        }

                    }


                    CustomAdapter myAdapter = new CustomAdapter(orderlist);
                    viewAllOrders.setAdapter(myAdapter);
                } catch (Exception e) {
                    Toast.makeText(ShowOrdersOfUser.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        viewAllOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent GOTOorder = new Intent(ShowOrdersOfUser.this, show_products_of_order.class);
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater linflater = getLayoutInflater();

            View view1 = linflater.inflate(R.layout.row_order_of_admin, null);
            final Spinner state=(Spinner)view1.findViewById(R.id.spinnerstatus);

            if(orderlist.get(position).getState().equals("Pending"))
            {
                List<String> list=new ArrayList<String>();
                list.add("Pending");
                list.add("InProgress");
                list.add("Delivered");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowOrdersOfUser.this,android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                state.setAdapter(adapter);

            }
            else if(orderlist.get(position).getState().equals("InProgress"))
            {
                List<String> list=new ArrayList<String>();
                list.add("InProgress");
                list.add("Pending");
                list.add("Delivered");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowOrdersOfUser.this,android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                state.setAdapter(adapter);
            }
            else if(orderlist.get(position).getState().equals("Delivered"))
            {
                List<String> list=new ArrayList<String>();
                list.add("Delivered");
                list.add("Pending");
                list.add("InProgress");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowOrdersOfUser.this,android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                state.setAdapter(adapter);
            }


            TextView ordername = (TextView) view1.findViewById(R.id.productsname);
            for (Product s : orderlist.get(position).items) {
                ordername.append(s.getName() + " \n");
            }





            state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,int sposition, long id) {
                    String s = ((String) parent.getItemAtPosition(sposition));



                    mFirebaseInstance = FirebaseDatabase.getInstance();
                    mF = mFirebaseInstance.getReference("Order");
                    mF.child(orderArrayList.get(position).getId()).child("state").setValue(state.getSelectedItem().toString());


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });

            TextView orderprice = (TextView) view1.findViewById(R.id.totalprice);
            orderprice.setText("Total Price: " + orderArrayList.get(position).totalPrice.toString() + " LE");

            return view1;
        }
    }



}
