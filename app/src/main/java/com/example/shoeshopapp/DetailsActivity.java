package com.example.shoeshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class DetailsActivity extends AppCompatActivity {

    private TextView tv,tv2;
    private TextView et;
    private ImageView iv;
    private Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tv=findViewById(R.id.textView);
        tv2=findViewById(R.id.textView2);
        iv=findViewById(R.id.imageView2);
        b1=findViewById(R.id.baddtocart);
        b2=findViewById(R.id.bqminus);
        b3=findViewById(R.id.bqadd);
        et=findViewById(R.id.etcqty);

        final int pid=getIntent().getIntExtra("pid",0);
        Product pro=ShoeAppUtils.findProduct(pid);
        tv.setText(pro.getArticle());
        tv2.setText("$."+pro.getPrice());
        Context context = getApplicationContext();
        int id = context.getResources().getIdentifier(pro.getImage().substring(0,pro.getImage().lastIndexOf(".")), "drawable", context.getPackageName());
        iv.setImageResource(id);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty=Integer.parseInt(et.getText().toString());
                et.setText(""+(qty+1));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty=Integer.parseInt(et.getText().toString());
                if(qty==1){
                    Snackbar.make(v,"Minimum Quantity 1", Snackbar.LENGTH_LONG).show();
                }
                else{
                    et.setText(""+(qty-1));
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                /*if(userid.equals("guest")){
                    Toast.makeText(getApplicationContext(), "Please login first", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }else {
                    int qty = Integer.parseInt(et.getText().toString());
                    CartItem ci=new CartItem(pid,userid,qty);
                    cart.add(ci);
                    Toast.makeText(DetailsActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                    finish();
                }*/
            }
        });
    }


}