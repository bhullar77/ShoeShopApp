package com.example.shoeshopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView lv;
    public static final List<Orders> orders=new ArrayList<>();
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        lv=findViewById(R.id.lvhistory);
        sp=getSharedPreferences("myapi",MODE_PRIVATE);
        final String userid=sp.getString("userid","guest");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        db.getReference()
            .child("Order")
            .orderByChild("userid")
            .equalTo(userid)
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot uu : snapshot.getChildren()){
                            Orders order= uu.getValue(Orders.class);
                            Log.d("api", "onDataChange: "+order.getOrderid());
                            orders.add(order);
                        }
                        lv.setAdapter(new CustomAdapter(orders));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent details=new Intent(getApplicationContext(),OrderDetailsActivity.class);
                details.putExtra("position",i);
                startActivity(details);
            }
        });
    }

    class CustomAdapter extends BaseAdapter{

        private List<Orders> orders;

        public CustomAdapter(List<Orders> orders) {
            this.orders = orders;
        }

        @Override
        public int getCount() {
            return this.orders.size();
        }

        @Override
        public Object getItem(int i) {
            return this.orders.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View myview=getLayoutInflater().inflate(android.R.layout.simple_list_item_2,viewGroup,false);
            TextView tv=myview.findViewById(android.R.id.text1);
            TextView tv2=myview.findViewById(android.R.id.text2);
            Orders order=this.orders.get(i);
            tv.setText("Order No. "+order.getOrderid());
            tv2.setText("Order Date "+order.getOrder_date());
            return myview;
        }
    }
}