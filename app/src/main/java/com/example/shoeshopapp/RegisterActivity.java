package com.example.shoeshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText et1,et2,et3;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et1=findViewById(R.id.etuname);
        et2=findViewById(R.id.etemail);
        et3=findViewById(R.id.etcpwd);
        sp=getSharedPreferences("myapp",MODE_PRIVATE);
        edit=sp.edit();
    }

    public void register(View v) {
        String uname=et1.getText().toString().trim();
        String pwd=et3.getText().toString().trim();
        String userid=et2.getText().toString().trim();
        boolean valid=true;
        if(uname.isEmpty()){
            et1.setError("Name must be given");
            valid=false;
        }
        else if(userid.isEmpty()){
            et2.setError("Email must be given");
            valid=false;
        }
        else if(pwd.isEmpty()){
            et3.setError("Password must be given");
            valid=false;
        }
        if(valid) {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            User u = new User(uname, userid, pwd);
            db.getReference("users").push().setValue(u);
            Toast.makeText(this, "User Registered successfully", Toast.LENGTH_SHORT).show();
            /*edit.putString("userid", userid);
            edit.commit();*/
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void gotologin(View v) {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}