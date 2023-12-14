package com.example.newsapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adaptor extends RecyclerView.Adapter<Adaptor.MyViewHolder> {

    private List<String> titles;
    private List<String> authors;
    private List<String> urls;
    private List<String> imgs;


    public Adaptor(List<String> titles, List<String> author, List<String> urls, List<String> imgs) {

        this.imgs = imgs;
        this.urls = urls;
        this.titles = titles;
        this.authors = author;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String title = titles.get(position);
        holder.tv.setText(title);
        String auth = authors.get(position);
        holder.tv1.setText(auth);
        String img = imgs.get(position);
        Picasso.get().load(img).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ur = urls.get(position);
                openUrlInBrowser(view.getContext(), ur);

            }
        });


    }

    private void openUrlInBrowser(Context context, String ur) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ur));
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private TextView tv1;
        public CardView cardView;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textview);
            tv1 = itemView.findViewById(R.id.textview1);
            cardView = itemView.findViewById(R.id.cardview);
            imageView = itemView.findViewById(R.id.imageview);

        }
    }
}
