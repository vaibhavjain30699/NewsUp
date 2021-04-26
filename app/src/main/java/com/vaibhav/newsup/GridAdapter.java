package com.vaibhav.newsup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter implements Filterable {

    private List<TopHeadlines.ArticlesBean> list,filter;
    private TextView title,date;
    private ImageView image;
    LayoutInflater inflater;
    private CardView cardView;
    private Context context;

    public GridAdapter(List<TopHeadlines.ArticlesBean> list, Context context) {
        this.list = list;
        this.filter = list;
        inflater = LayoutInflater.from(context.getApplicationContext());
        this.context = context;
    }

    @Override
    public int getCount() {
        return filter==null?0:filter.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(!list.get(position).getTitle().equals("Vaibhav NewsUp")) {
            convertView = inflater.inflate(R.layout.item_gridview, null);
            cardView = convertView.findViewById(R.id.cardview);
            title = convertView.findViewById(R.id.title);
            image = convertView.findViewById(R.id.image);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsInfo.class);
                    intent.putExtra("Title", list.get(position).getTitle());
                    intent.putExtra("Description", (CharSequence) list.get(position).getDescription());
                    intent.putExtra("Content", list.get(position).getContent());
                    intent.putExtra("Image", list.get(position).getUrlToImage());
                    intent.putExtra("URL", list.get(position).getUrl());
                    intent.putExtra("position",position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) list);
                    intent.putExtra("BUNDLE",bundle);
                    context.startActivity(intent);

                }
            });

            title.setText(filter.get(position).getTitle());
            //description.setText((CharSequence) list.get(position).getDescription());
            Picasso.with(convertView.getContext()).load(filter.get(position).getUrlToImage()).into(image);
        }
        else{
            convertView = inflater.inflate(R.layout.date_item,null);
            date = convertView.findViewById(R.id.date);
            date.setText(list.get(position).getContent());
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filtering = constraint.toString();
                List<TopHeadlines.ArticlesBean> filteredList = new ArrayList<>();
                if(filtering.isEmpty()){
                    filteredList = list;
                }

                else {

                    for(TopHeadlines.ArticlesBean row : list){
                        if(row.getTitle().toLowerCase().contains(filtering)  ||  row.getContent().toLowerCase().contains(filtering))
                            filteredList.add(row);
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if(results.values==null)
                    Log.e("result","null hai");
                filter = (List<TopHeadlines.ArticlesBean>) results.values;

            }
        };
    }
}
