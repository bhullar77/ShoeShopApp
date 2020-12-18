package com.example.shoeshopapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="api";
    private RecyclerView rv;
    private SearchView.OnQueryTextListener queryTextListener;
    private SearchView searchView=null;
    RVAdapter adp=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayUseLogoEnabled(true);*/
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        rv=findViewById(R.id.recyclerView);

        rv.setLayoutManager(new GridLayoutManager(this,1));
        rv.setHasFixedSize(true);
        Log.d(TAG, "onCreate: "+SplashScreen.plist.size());
        rv.setAdapter(new RVAdapter(SplashScreen.plist));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchitem=menu.findItem(R.id.app_bar_search);
        if(searchitem!=null){
            Log.d("anand", "onCreateOptionsMenu: Hello i am here");
            searchView= (SearchView) searchitem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d("anand", "onQueryTextSubmit: "+s);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d("anand", "onQueryTextSubmit: "+s);
                    adp.getFilter().filter(s);
                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mlogout:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            case R.id.mvcart:
                startActivity(new Intent(this,CartActivity.class));
                break;
            case R.id.mhistory:
                startActivity(new Intent(this,HistoryActivity.class));
                break;
        }
        return true;
    }

    class RVViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivpic;
        public TextView tvpname;
        public TextView tvprice;
        public Button badd,bbuy;
        public RVViewHolder(@NonNull View itemView) {
            super(itemView);
            tvpname=itemView.findViewById(R.id.tvpname);
            tvprice=itemView.findViewById(R.id.tvpprice);
            ivpic=itemView.findViewById(R.id.ivprod);
            badd=itemView.findViewById(R.id.badd);
            bbuy=itemView.findViewById(R.id.bbuy);
        }

        public void  setData(Product p)
        {
            tvpname.setText(p.getArticle());
            tvprice.setText("$."+p.getPrice());
            Context context = getApplicationContext();
            int id = context.getResources().getIdentifier(p.getImage().substring(0,p.getImage().lastIndexOf(".")), "drawable", context.getPackageName());
            ivpic.setImageResource(id);
        }

    }

    class RVAdapter extends RecyclerView.Adapter<RVViewHolder> implements Filterable {
        private List<Product> list;
        private List<Product> templist = new ArrayList<>();

        public RVAdapter(List<Product> list) {
            this.list = list;
            templist.addAll(list);
        }

        @NonNull
        @Override
        public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View vv = LayoutInflater.from(MainActivity.this).inflate(R.layout.gv_product_item, parent, false);
            RVViewHolder rvViewHolder = new RVViewHolder(vv);
            return rvViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RVViewHolder holder, final int position) {
            holder.setData(list.get(position));
            holder.badd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product p=list.get(position);
                    Cart c=new Cart(p.getId(),p.getArticle(),p.getDescription(),1,p.getPrice(),p.getPrice());
                    c.setImage(p.getImage());
                    /*FirebaseDatabase db=FirebaseDatabase.getInstance();
                    db.getReference("cart").push().setValue(c);*/
                    SplashScreen.cart.add(c);
                    Toast.makeText(MainActivity.this, "Product added to cart", Toast.LENGTH_SHORT).show();
                }
            });
            holder.bbuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product p=list.get(position);
                    Cart c=new Cart(p.getId(),p.getArticle(),p.getDescription(),1,p.getPrice(),p.getPrice());
                    c.setImage(p.getImage());
                    SplashScreen.cart.add(c);
                    startActivity(new Intent(getApplicationContext(),CartActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    Log.d("anand", "performFiltering: "+charSequence);
                    List<Product> filteredList=new ArrayList<>();
                    if(charSequence==null || charSequence.length()==0){
                        filteredList.addAll(templist);
                    }
                    else{
                        String pattern=charSequence.toString().trim().toLowerCase();
                        for(Product p : templist){
                            if(p.getArticle().toLowerCase().trim().contains(pattern)){
                                filteredList.add(p);
                            }
                        }
                    }
                    FilterResults results=new FilterResults();
                    results.values=filteredList;
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    list.clear();
                    list.addAll((List<Product>)filterResults.values);
                    notifyDataSetChanged();
                }
            };
        }
    }


}