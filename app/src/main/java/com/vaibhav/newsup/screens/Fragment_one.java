package com.vaibhav.newsup.screens;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.vaibhav.newsup.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.SEARCH_SERVICE;

public class Fragment_one extends Fragment implements View.OnClickListener {

    private GridView gridView;
    private AppInterface appInterface;
    private GridAdapter adapter;
    private ProgressBar progressBar;
    private SearchView searchEdit;
    private RelativeLayout relativeLayout;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private Button backButton;
    List<TopHeadlines.ArticlesBean> list = new ArrayList<>();
    List<String> dateInNews = new ArrayList<>();
    List<TopHeadlines.ArticlesBean> filter = new ArrayList<>();


    public Fragment_one() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one,container,false);
        gridView = view.findViewById(R.id.categories);
        progressBar = view.findViewById(R.id.progressBar);
        relativeLayout = getActivity().findViewById(R.id.RelativeLayout);
        toolbar = getActivity().findViewById(R.id.Toolbar);
        searchEdit = getActivity().findViewById(R.id.searchEditText);
        appBarLayout = getActivity().findViewById(R.id.appBarLayout);
        appInterface = AppUtils.getApiService();
        backButton = getActivity().findViewById(R.id.backButton);
        setCategories(getContext());
        gridView.setNumColumns(1);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

    }

    public void setCategories(final Context context){

        appInterface.getGeneralNews().enqueue(new Callback<TopHeadlines>() {
            @Override
            public void onResponse(Call<TopHeadlines> call, Response<TopHeadlines> response) {
                if(response.isSuccessful()) {
                    //Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    filter = response.body().getArticles();
                    list.addAll(filter);

                    for(int i=0;i<list.size();i++){
                        SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                        TopHeadlines.ArticlesBean t = list.get(i);
                        //Date d = new SimpleDateFormat("dd-MMM-yy").parse(t.getPublishedAt());
                        try {
                            Date d = new Date();
                            if(t.getPublishedAt()!=null)
                                d = formatter.parse(t.getPublishedAt().replaceAll("Z$", "+0000"));
                            else
                                continue;
                            SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
                            String s = format1.format(d);
                            if(!dateInNews.contains(s)){
                                TopHeadlines.ArticlesBean h = new TopHeadlines.ArticlesBean();
                                h.setTitle("Vaibhav NewsUp");
                                h.setContent(s);
                                list.add(i,h);
                                dateInNews.add(s);
                                i++;
                            }
                        } catch (ParseException e) {
                            Log.e("DD",t.getPublishedAt());
                        }
                    }

                    filter.clear();
                    filter.addAll(list);
                    adapter = new GridAdapter(filter,context);
                    gridView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                    gridView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<TopHeadlines> call, Throwable t) {

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        searchEdit.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                adapter.getFilter().filter(s);

                filter.clear();
                if(!s.equals("")) {
                    for (int i = 0; i < list.size(); i++) {
                        TopHeadlines.ArticlesBean t = list. get(i);
                        if(t.getContent()!=null  &&  t.getTitle()!=null) {
                            if (t.getContent().toLowerCase().contains(s)  ||  t.getTitle().toLowerCase().contains(s))
                                filter.add(t);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    filter.addAll(list);
                    adapter.notifyDataSetChanged();
                }setHasOptionsMenu(true);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                filter.clear();
                if(!s.equals("")) {
                    for (int i = 0; i < list.size(); i++) {
                        TopHeadlines.ArticlesBean t = list. get(i);
                        if(t.getContent()!=null  &&  t.getTitle()!=null) {
                            if (t.getContent().toLowerCase().contains(s)  ||  t.getTitle().toLowerCase().contains(s))
                                filter.add(t);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    filter.addAll(list);
                    adapter.notifyDataSetChanged();
                }
                //adapter.notifyDataSetChanged();
                return false;
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.search){
            if(relativeLayout.getVisibility()==View.GONE)
            {
                relativeLayout.setVisibility(View.VISIBLE);
                appBarLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.backButton)
        {
            if(relativeLayout.getVisibility()== View.VISIBLE)
            {
                searchEdit.setIconified(true);
                relativeLayout.setVisibility(View.GONE);
                searchEdit.setVisibility(View.GONE);
                backButton.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
            }
            filter.clear();
            filter.addAll(list);
            adapter.notifyDataSetChanged();
        }

    }
}
