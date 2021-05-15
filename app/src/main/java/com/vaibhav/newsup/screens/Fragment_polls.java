package com.vaibhav.newsup.screens;

import android.content.SharedPreferences;
import android.nfc.TagLostException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vaibhav.newsup.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Fragment_polls extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tex;
    private List<Polls> list = new ArrayList<>();

    public Fragment_polls() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_polls,container,false);
        recyclerView = v.findViewById(R.id.polls_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar = v.findViewById(R.id.pppp);
        progressBar.setVisibility(View.VISIBLE);
        tex = v.findViewById(R.id.no_polls);
        tex.setVisibility(View.INVISIBLE);
        setPolls();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    private void setPolls(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("data_polls");
        final SharedPreferences preferences = getContext().getSharedPreferences("preff",0);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    Polls p = d.getValue(Polls.class);
                    if(!preferences.getString(p.getId()+"","no").equals("yes")){
                        list.add(p);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Polls_adapter polls_adapter = new Polls_adapter(list);
        recyclerView.setAdapter(polls_adapter);
        if(list.size()==0)
            tex.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
