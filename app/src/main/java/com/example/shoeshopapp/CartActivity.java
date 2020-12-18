package com.example.shoeshopapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private ListView lv;
    private TextView tv,tv5,tv6;
    private Button b;
    private CustomAdapter adp;
    private List<Cart> list;
    public static Orders orders=new Orders();
    private int qty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        lv=findViewById(R.id.lvcart);
        tv=findViewById(R.id.textView9);
        tv5=findViewById(R.id.tvgsts);
        tv6=findViewById(R.id.tvnet);
        b=findViewById(R.id.fabcheckout);
        b.setVisibility(View.INVISIBLE);
        sp=getSharedPreferences("myapi",MODE_PRIVATE);
        final String userid=sp.getString("userid","guest");
        list=SplashScreen.cart;

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //list.clear();
                SimpleDateFormat sdf=new SimpleDateFormat("ddMMhhmm");
                int orderid=Integer.parseInt(sdf.format(new Date()));
                orders.setOrderid(orderid);
                SimpleDateFormat sdf2=new SimpleDateFormat("dd-MMM-yyyy");
                orders.setOrder_date(sdf2.format(new Date()));
                orders.setUserid(userid);
                orders.setClist(SplashScreen.cart);
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                db.getReference("Order").push().setValue(orders);
                startActivity(new Intent(getApplicationContext(),PaymentActivity.class));
                finish();
                SplashScreen.cart.clear();
            }
        });
        if(list.size()==0){
            startActivity(new Intent(getApplicationContext(),EmptycartActivity.class));
            finish();
        }else{
            b.setVisibility(View.VISIBLE);
            calc();
        }
        sp=getSharedPreferences("users",MODE_PRIVATE);
        adp=new CustomAdapter(list);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //final String pname=list.get(position).getProdid();
                qty=list.get(position).getQty();
                final String userid=sp.getString("userid","guest");
                AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
                View vv=getLayoutInflater().inflate(R.layout.cart_item,null,false);
                TextView tv1=vv.findViewById(R.id.tvcpname);
                final TextView et1=vv.findViewById(R.id.etcqty);
                Button b1=vv.findViewById(R.id.bqminus);
                Button b2=vv.findViewById(R.id.bqadd);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qty=Integer.parseInt(et1.getText().toString())+1;
                        et1.setText(""+(qty));
                    }
                });
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qty=Integer.parseInt(et1.getText().toString());
                        if(qty==1){
                            Snackbar.make(v,"Minimum Quantity 1", Snackbar.LENGTH_LONG).show();
                        }
                        else{
                            qty--;
                            et1.setText(""+(qty));
                        }
                    }
                });
                Product pp=ShoeAppUtils.findProduct(list.get(position).getPid());
                final String pname=pp.getArticle();
                final int prodid=pp.getId();
                tv1.setText(pname);
                et1.setText(""+qty);
                builder.setView(vv);
                builder.setTitle("Cart Item Update");
                builder.setNegativeButton("Update Quantity", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cart cc=list.get(position);
                        cc.setQty(qty);
                        //list.set(position,new Cart(prodid,userid,qty));
                        list.set(position,cc);
                        lv.setAdapter(new CustomAdapter(list));
                        calc();
                    }
                });
                builder.setPositiveButton("Delete Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Product Removed", Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        if(list.size()==0){
                            startActivity(new Intent(getApplicationContext(),EmptycartActivity.class));
                            finish();
                        }
                        lv.setAdapter(new CustomAdapter(list));
                        calc();
                    }
                });
                builder.show();
            }
        });
    }
    private void calc() {
        float amount=totalBill();
        tv.setText(String.format("$.%.2f",amount));
        float gst=amount*.10f;
        float net=amount+gst;
        tv5.setText(String.format("$.%.2f",gst));
        tv6.setText(String.format("$.%.2f",net));
    }

    public float totalBill() {
        //List<CartItem> list=new ArrayList<>();
        float total=0;
        for(Cart item : list)
        {
            Product p=ShoeAppUtils.findProduct(item.getPid());
            total+=(item.getQty()*p.getPrice());
        }
        return total;
    }

    private class CustomAdapter extends BaseAdapter
    {
        private List<Cart> list;
        private LayoutInflater inflater;

        public CustomAdapter(List<Cart> list) {
            this.list = list;
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vv=getLayoutInflater().inflate(R.layout.cart_item_layout,parent,false);
            TextView tv1=vv.findViewById(R.id.tvcpname);
            TextView tv2=vv.findViewById(R.id.tvcprice);
            TextView tv3=vv.findViewById(R.id.tvcqty);
            TextView tv4=vv.findViewById(R.id.tvcamt);
            Product p= ShoeAppUtils.findProduct(list.get(position).getPid());
            tv1.setText(p.getArticle());
            float price=p.getPrice();
            int qty=list.get(position).getQty();
            float amount=price*qty;
            tv2.setText(String.format("$.%.2f",price));
            tv3.setText(String.valueOf(qty));
            tv4.setText(String.format("$.%.2f",amount));

            /*if(position%2==0){
                vv.setBackgroundResource(R.color.fav1);
            }else{
                vv.setBackgroundResource(R.color.fav2);
            }*/
            return vv;
        }
    }

}