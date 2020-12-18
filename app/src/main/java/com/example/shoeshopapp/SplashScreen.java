package com.example.shoeshopapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private int counter;
    private ProgressBar pbar;
    private Handler handler=new Handler();
    public final static List<Product> plist=new ArrayList<>();
    public final static List<Cart> cart=new ArrayList<>();
    public final static List<Orders> orders=new ArrayList<>();
    //public final static List<User> users=new ArrayList<>();
    private static final String TAG ="api";
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        plist.clear();
        new Task().execute();
        pbar=findViewById(R.id.progressBar);
        handler.postDelayed(run,1000);
        sp=getSharedPreferences("myapp",MODE_PRIVATE);
    }



    Runnable run=new Runnable() {
        @Override
        public void run() {
            counter++;
            pbar.setProgress(counter);
            if (counter == 100) {
                finish();
                String userid=sp.getString("userid","guest");
                if(userid.equals("guest")) {
                    startActivity(new Intent(SplashScreen.this, RegisterActivity.class));
                }
                else{
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
            }else{
                handler.postDelayed(this,50);
            }
        }
    };

    class Task extends AsyncTask<Void,Void,String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                final String API="https://e-commerce-bc5e9-default-rtdb.firebaseio.com/products.json";
                URL url=new URL(API);
                HttpURLConnection con= (HttpURLConnection) url.openConnection();
                if(con.getResponseCode()==200)
                {
                    StringBuilder sb=new StringBuilder();
                    BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    while(true)
                    {
                        final String line=br.readLine();
                        Log.d(TAG, "doInBackground: "+line);
                        if(line==null) break;
                        sb.append(line);
                    }

                    return sb.toString();
                }
            }
            catch(Exception ex){
                Log.d(TAG, "Error doInBackground: "+ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //Log.d(TAG, "onPostExecute: "+s);
            try {
                JSONObject obj=new JSONObject(s);
                //Log.d(TAG, "onPostExecute: "+obj.names());
                Iterator<String> keys = obj.keys();
                while(keys.hasNext()) {
                    Product p=new Product();
                    String key = keys.next();
                    p.setCategory(key);
                    Log.d(TAG, "onPostExecute: "+key+" => "+obj.get(key));
                    JSONObject ob= (JSONObject) obj.get(key);
                    p.setId(ob.getInt("id"));
                    p.setArticle(ob.getString("article"));
                    p.setDescription(ob.getString("description"));
                    p.setImage(ob.getString("image"));
                    p.setPrice(ob.getInt("price"));
                    plist.add(p);
                }
            }
            catch(Exception ex){
                Log.d(TAG, "onPostExecute: "+ex.getMessage());
            }
        }
    }
}