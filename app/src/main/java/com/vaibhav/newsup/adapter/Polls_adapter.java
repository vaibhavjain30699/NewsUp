package com.vaibhav.newsup.adapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vaibhav.newsup.R;
import com.vaibhav.newsup.models.Polls;

import java.util.List;

public class Polls_adapter extends RecyclerView.Adapter<Polls_adapter.MyviewHolder> {

    private List<Polls> list;
    private List<Long> list11;
    private FirebaseAuth mauth;
    private FirebaseUser user3;
    SharedPreferences preferences;

    public Polls_adapter(List<Polls> list) {
//        mauth = FirebaseAuth.getInstance();
//        user3 = mauth.getCurrentUser();
//        list11 = new ArrayList<>();
//        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
//        DatabaseReference reference1 = database1.getReference(user3.getUid());
//        reference1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list11.clear();
//                for(DataSnapshot h:dataSnapshot.getChildren()){
//                    Long kk = h.getValue(Long.class);
//                    list11.add(kk);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        this.list = list;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_polls,viewGroup,false);
        preferences = v.getContext().getSharedPreferences("preff",0);
        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, final int i) {

        final long oo = list.get(i).getId();
//        for(int kkk=0;kkk<list11.size();kkk++){
//            if(list11.get(kkk)==list.get(i).getId())
//                flag = 1;
//        }

        if(true){
            myviewHolder.title.setText(list.get(i).getTitle());
            myviewHolder.content.setText(list.get(i).getContent());
            myviewHolder.yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(oo+"","yes");
                    editor.apply();
                    list.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i,list.size());
                }
            });

            myviewHolder.no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(oo+"","yes");
                    editor.apply();
                    list.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i,list.size());
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{

        public TextView title,content;
        public Button yes,no;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            yes = itemView.findViewById(R.id.yes);
            no = itemView.findViewById(R.id.no);
        }
    }

}
