      package com.vaibhav.newsup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Target;
import com.vaibhav.newsup.models.TopHeadlines;
import com.vaibhav.newsup.network.AppInterface;
import com.vaibhav.newsup.screens.Fragment_five;
import com.vaibhav.newsup.screens.Fragment_four;
import com.vaibhav.newsup.screens.Fragment_one;
import com.vaibhav.newsup.screens.Fragment_polls;
import com.vaibhav.newsup.screens.Fragment_three;
import com.vaibhav.newsup.screens.Fragment_two;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

      public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchView searchEdit;
    private RelativeLayout relativeLayout;
    private Button backButton;
    private AppInterface appInterface;
    Bitmap bitmap_;
    private List<TopHeadlines.ArticlesBean> list = new ArrayList<>();
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    Intent intent;
    Target target;

    private TextView email_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEdit = findViewById(R.id.searchEditText);
        backButton = findViewById(R.id.backButton);
        relativeLayout = findViewById(R.id.RelativeLayout);
        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        viewPager = findViewById(R.id.viewPager);
        setViewPager();

//        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
//        intent = new Intent(MainActivity.this,NotifierClass.class);
//        appInterface = AppUtils.getApiService();
//        appInterface.getGeneralNews().enqueue(new Callback<TopHeadlines>() {
//            @Override
//            public void onResponse(Call<TopHeadlines> call, Response<TopHeadlines> response) {
//                list.addAll(response.body().getArticles());
//                try {
//                    for(int i=0;i<list.size();i++) {
//                        if(list.get(i).getUrlToImage()!=null && !list.get(i).getTitle().equals("Vaibhav NewsUp")){
//                            intent.putExtra("title",list.get(i).getTitle());
//                            Picasso.with(MainActivity.this).load(list.get(i).getUrlToImage()).into(target);
//                            break;
//                        }
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<TopHeadlines> call, Throwable t) {
//
//            }
//        });
//
//        target = new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                bitmap_ = bitmap;
//                bitmap_.compress(Bitmap.CompressFormat.PNG,100,b);
//                intent.putExtra("image",b.toByteArray());
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*60*60*24,pendingIntent);
//            }
//
//            @Overridereturn false;
//            public void onBitmapFailed(Drawable errorDrawable) {
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//            }
//        };

        DrawerLayout drawer = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.navigation);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        email_user = navigationView.getHeaderView(0).findViewById(R.id.emailid_user);
        email_user.setText(getIntent().getStringExtra("id"));

        tabLayout = findViewById(R.id.TabLayout);
        tabLayout.setupWithViewPager(viewPager);

        backButton.setVisibility(View.GONE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(relativeLayout.getVisibility()== View.VISIBLE)
                {
//                    searchEdit.setIconified(true);
//                    relativeLayout.setVisibility(View.GONE);
//                    searchEdit.setVisibility(View.GONE);
//                    backButton.setVisibility(View.GONE);
//                    toolbar.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    toolbar.setVisibility(View.VISIBLE);
                }

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                searchEdit.setIconified(true);
                relativeLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
            }
        });

    }

    public void setViewPager(){

        ViewPage_Adapter viewPage_adapter = new ViewPage_Adapter(getSupportFragmentManager());
        viewPage_adapter.addFragment(new Fragment_one(),"General");
        viewPage_adapter.addFragment(new Fragment_two(),"Entertainment");
        viewPage_adapter.addFragment(new Fragment_three(),"Technology");
        viewPage_adapter.addFragment(new Fragment_four(),"Science");
        viewPage_adapter.addFragment(new Fragment_five(),"Health");
        viewPage_adapter.addFragment(new Fragment_polls(),"Polls");
        viewPager.setAdapter(viewPage_adapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    class ViewPage_Adapter extends FragmentPagerAdapter{

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> stringList = new ArrayList<>();

        public ViewPage_Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
        }

        public void addFragment(Fragment fragment, String title){

            fragmentList.add(fragment);
            stringList.add(title);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.search){
            if(relativeLayout.getVisibility()==View.GONE)
            {
                relativeLayout.setVisibility(View.VISIBLE);
                searchEdit.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.GONE);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(relativeLayout.getVisibility()== View.VISIBLE)
        {
            relativeLayout.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
        }
        else
            super.onBackPressed();
    }

//    private class notify extends AsyncTask<>{
//
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            return null;
//        }
//    }

}