package com.proj.newswave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.proj.newswave.Models.NewsHeadlines;
import com.proj.newswave.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {


    NewsHeadlines newsHeadlines;
    TextView txt_title , txt_author , txt_time , txt_detail ,txt_content;
    ImageView img_news;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        txt_title = findViewById(R.id.text_detail_title);
        txt_author = findViewById(R.id.text_detail_author);
        txt_time= findViewById(R.id.text_detail_time);
        txt_detail = findViewById(R.id.text_detail_detail);
        txt_content = findViewById(R.id.text_detail_content);
        img_news = findViewById(R.id.img_detail_news);
        button=findViewById(R.id.readMore);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailsActivity.this,WebViewActivity.class);
                intent.putExtra("url",newsHeadlines.getUrl());
                startActivity(intent);
            }
        });


        newsHeadlines = (NewsHeadlines) getIntent().getSerializableExtra("data");

        txt_title.setText(newsHeadlines.getTitle());
        txt_author.setText(newsHeadlines.getAuthor());
        txt_time.setText(newsHeadlines.getPublishedAt());
        txt_detail.setText(newsHeadlines.getDescription());
        //txt_content.setText(newsHeadlines.getContent());
        Picasso.get().load(newsHeadlines.getUrlToImage()).into(img_news);

    }
}