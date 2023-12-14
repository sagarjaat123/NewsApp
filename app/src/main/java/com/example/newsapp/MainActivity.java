package com.example.newsapp;

import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String url;
    String country = "In";
    String category = "business";
    String Key = "eafb7be1cdb34d149f1ea65470e9e15b";

    RecyclerView recyclerView;
    private TextView textView, textView2, textView3, textView4, textView5, textView6;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleview);
        assignId(textView, R.id.txt11);
        assignId(textView2, R.id.txt12);
        assignId(textView3, R.id.txt13);
        assignId(textView4, R.id.txt21);
        assignId(textView5, R.id.txt22);
        assignId(textView6, R.id.txt23);
        changeCategoryAndMAkeApiCall(category);


    }

    public void assignId(TextView tv, int id) {
        tv = findViewById(id);
        tv.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view) {
        TextView tv = (TextView) view;
        String data = tv.getText().toString();

        switch (data) {
            case "Sports":
                changeCategoryAndMAkeApiCall("sports");
                Toast.makeText(this, "wait a second", Toast.LENGTH_SHORT).show();
                break;
            case "Business":
                changeCategoryAndMAkeApiCall("business");
                Toast.makeText(this, "wait a second", Toast.LENGTH_SHORT).show();
                break;
            case "Politics":
                changeCategoryAndMAkeApiCall("politics");
                Toast.makeText(this, "wait a second", Toast.LENGTH_SHORT).show();
                break;
            case "Technology":
                changeCategoryAndMAkeApiCall("technology");
                Toast.makeText(this, "wait a second", Toast.LENGTH_SHORT).show();
                break;

            case "Health":
                changeCategoryAndMAkeApiCall("health");
                Toast.makeText(this, "wait a second", Toast.LENGTH_SHORT).show();
                break;
            case "Fun":
                changeCategoryAndMAkeApiCall("entertainment");
                Toast.makeText(this, "wait a second", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    private void changeCategoryAndMAkeApiCall(String newCategory) {
        changeCategory(newCategory);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        Call<Data> data = api.getDetail(country, category, Key);
        data.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 404) {
                    Toast.makeText(MainActivity.this, "invalid", Toast.LENGTH_SHORT).show();
                }
                Data mydata = response.body();
                List<Article> articles = mydata.getArticles();
                List<String> titles = new ArrayList<>();
                List<String> authors = new ArrayList<>();
                List<String> urls = new ArrayList<>();
                List<String> imgs = new ArrayList<>();

                for (Article article : articles) {
                    String title = article.getTitle();
                    String author = article.getAuthor();
                    String url = article.getUrl();
                    String img = article.getUrlToImage();
                    if (article.getTitle().contains("Removed")) {
                        titles.remove(title);
                    } else {

                        titles.add(title);
                        authors.add(author);
                        urls.add(url);
                        imgs.add(img);
                    }
                }


                Adaptor adaptor = new Adaptor(titles, authors, urls, imgs);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adaptor);
            }


            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void changeCategory(String newCategory) {
        this.category = newCategory;

    }


}


//