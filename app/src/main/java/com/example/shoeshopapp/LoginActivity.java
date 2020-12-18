package com.example.shoeshopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText et1,et2;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et1=findViewById(R.id.etnuserid);
        et2=findViewById(R.id.etnpwd);
        sp=getSharedPreferences("myapi",MODE_PRIVATE);
    }

    public void gotoregister(View v) {
        startActivity(new Intent(this,RegisterActivity.class));
        finish();
    }

    public void login(View v)
    {
        boolean valid=true;
        final String userid=et1.getText().toString().trim();
        final String pwd=et2.getText().toString().trim();
        if(userid.isEmpty()){
            et1.setError("User Id must be given");
            valid=false;
        }
        else if(pwd.isEmpty()){
            et2.setError("Password must be given");
            valid=false;
        }
        if(valid) {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            db.getReference()
                    .child("users")
                    .orderByChild("userid")
                    .equalTo(userid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean valid = false;
                            User user = null;
                            //Toast.makeText(LoginActivity.this, "total "+snapshot.getChildren(), Toast.LENGTH_SHORT).show();
                            if (snapshot.exists()) {
                                for(DataSnapshot uu : snapshot.getChildren()){
                                //DataSnapshot uu = snapshot.getChildren().iterator().next();
                                user = uu.getValue(User.class);
                                if (user == null) {
                                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                } else if (user.getPwd().equals(pwd) && user.getUserid().equals(userid)) {
                                    valid = true;
                                    Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    edit = sp.edit();
                                    edit.putString("userid", userid);
                                    edit.putString("uname", user.getFname());
                                    edit.commit();
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    break;
                                } else {
                                    Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }
}