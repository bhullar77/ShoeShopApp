package com.example.shoeshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        lv=findViewById(R.id.lvdetails);
        getSupportActionBar().setTitle("Order Details");

        int position=getIntent().getIntExtra("position",-1);
        Orders order=HistoryActivity.orders.get(position);

        lv.setAdapter(new CustomAdapter(order.getClist()));

    }

    class CustomAdapter extends BaseAdapter {

        private List<Cart> list;

        public CustomAdapter(List<Cart> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View myview=getLayoutInflater().inflate(R.layout.details_item_layout,viewGroup,false);
            TextView tv1=myview.findViewById(R.id.tvopname);
            TextView tv2=myview.findViewById(R.id.tvoprice);
            TextView tv3=myview.findViewById(R.id.tvoqty);
            ImageView ivpic=myview.findViewById(R.id.imageView4);
            tv1.setText(list.get(i).getArticle());
            tv2.setText("$. "+list.get(i).getPrice());
            tv3.setText("Qty. "+list.get(i).getQty());
            Context context = getApplicationContext();
            int id = context.getResources().getIdentifier(list.get(i).getImage().substring(0,list.get(i).getImage().lastIndexOf(".")), "drawable", context.getPackageName());
            ivpic.setImageResource(id);
            return myview;
        }
    }
}