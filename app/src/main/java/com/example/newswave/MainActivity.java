package com.example.newswave;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.newswave.Models.NewsApiResponse;
import com.example.newswave.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener , View.OnClickListener {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog ;
    SearchView searchView;

    Button b2,b3,b4,b5,b6,b7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news articles..");
        dialog.show();

        b2= findViewById(R.id.btn_2);
        b2.setOnClickListener(this);
        b3= findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4= findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5= findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6= findViewById(R.id.btn_6);
        b6.setOnClickListener(this);
        b7= findViewById(R.id.btn_7);
        b7.setOnClickListener(this);
        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching news articles of "+ query);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener,"general",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(MainActivity.this, "An error Occured!!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });





        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,"general",null);
    }
    public final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(MainActivity.this, "News not found", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                return;
            }
          showNews(list);

          dialog.dismiss();
        }



        @Override
        public void onError(String message) {

        }
    };
    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recyclerView );
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this ,1));
        adapter = new CustomAdapter(this,list,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClicked(NewsHeadlines newsHeadlines) {

        startActivity(new Intent(MainActivity.this,DetailsActivity.class)
                .putExtra("data",newsHeadlines));
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        dialog.setTitle("Fetching news articles of "+ category);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,category,null);
    }
}