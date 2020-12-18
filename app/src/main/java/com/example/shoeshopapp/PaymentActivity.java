package com.example.shoeshopapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentActivity extends AppCompatActivity {

    private Button bpay;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        bpay=findViewById(R.id.bpay);
        sp=getSharedPreferences("users",MODE_PRIVATE);

        bpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid=sp.getString("userid","guest");
                showSuccess();
            }
        });
    }
    private void showSuccess(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View vv=getLayoutInflater().inflate(R.layout.success_layout,null,false);
        builder.setView(vv);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        builder.show();
    }
}