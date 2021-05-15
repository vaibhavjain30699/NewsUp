package com.vaibhav.newsup.screens;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vaibhav.newsup.R;
import com.vaibhav.newsup.models.TopHeadlines;

import java.util.ArrayList;
import java.util.List;

public class NewsInfo extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private TextView title,url,content;
    private ImageView image;
    private GestureDetector gestureDetector;
    private List<TopHeadlines.ArticlesBean> l = new ArrayList<>();
    int position;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);

        gestureDetector = new GestureDetector(NewsInfo.this,NewsInfo.this);

        Bundle b = getIntent().getBundleExtra("BUNDLE");
        l = (List<TopHeadlines.ArticlesBean>) b.getSerializable("list");
//        Toast.makeText(this, l.size()+"", Toast.LENGTH_SHORT).show();

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        url = findViewById(R.id.url);
        image = findViewById(R.id.image);

        title.setText(getIntent().getExtras().getString("Title"));
        content.setText(getIntent().getExtras().getString("Content"));
        Picasso.with(NewsInfo.this).load(getIntent().getExtras().getString("Image")).into(image);
        position = getIntent().getIntExtra("position",0);

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent internetIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(getIntent().getExtras().getString("URL")));
                //internetIntent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
                internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(internetIntent);

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (e1.getY() - e2.getY() > 50) {
            if(position>0  &&  !l.get(position-1).getTitle().equals("Vaibhav NewsUp")){
                position--;
                title.setText(l.get(position).getTitle());
                content.setText(l.get(position).getContent());
                Picasso.with(NewsInfo.this).load(l.get(position).getUrlToImage()).into(image);
            }
            return true;
        }

        if (e2.getY() - e1.getY() > 50) {
            if(position<l.size()-1  &&  !l.get(position+1).getTitle().equals("Vaibhav NewsUp")){
                position++;
                title.setText(l.get(position).getTitle());
                content.setText(l.get(position).getContent());
                Picasso.with(NewsInfo.this).load(l.get(position).getUrlToImage()).into(image);
            }
            //Toast.makeText(NewsInfo.this, "You Swiped Down!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (e1.getX() - e2.getX() > 50) {
            if(position<l.size()-1  &&  !l.get(position+1).getTitle().equals("Vaibhav NewsUp")){
                position++;
                title.setText(l.get(position).getTitle());
                content.setText(l.get(position).getContent());
                Picasso.with(NewsInfo.this).load(l.get(position).getUrlToImage()).into(image);
            }
            //Toast.makeText(NewsInfo.this, "You Swiped Left!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (e2.getX() - e1.getX() > 50) {
            if(position>0  &&  !l.get(position-1).getTitle().equals("Vaibhav NewsUp")){
                position--;
                title.setText(l.get(position).getTitle());
                content.setText(l.get(position).getContent());
                Picasso.with(NewsInfo.this).load(l.get(position).getUrlToImage()).into(image);
            }
            //Toast.makeText(NewsInfo.this, "You Swiped Right!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            return true;
        }

    }
}
