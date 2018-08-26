package com.channel.example.newsapp1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, @NonNull ArrayList<News> NewsList){
        super(context,0,NewsList);
    }
    @Override
    public View getView (int postition, View ConvertView, @NonNull ViewGroup parent){
        if (ConvertView == null){
            LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        TextView articleTitle = ConvertView.findViewById(R.id.article_title);
        TextView articleAuthor = ConvertView.findViewById(R.id.article_Author);
        TextView articleDate = ConvertView.findViewById(R.id.date);
        News currentNews = getItem(postition);
        articleTitle.setText(currentNews.getTitle());
        articleAuthor.setText(currentNews.getAuthor());
        articleDate.setText(currentNews.getDate());
        return ConvertView;
    }

}
