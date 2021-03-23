package com.example.subrahmanya.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subrahmanya.Message;
import com.example.subrahmanya.R;
import com.example.subrahmanya.address;
import com.example.subrahmanya.foodlist;
import com.example.subrahmanya.global;
import com.example.subrahmanya.login;
import com.example.subrahmanya.myadapter;
import com.example.subrahmanya.myadapter2;
import com.example.subrahmanya.orderlist;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    RecyclerView recview;
    myadapter2 adapter;
    public  String em;
    private FirebaseAuth mAuth;
    Handler handler;
    ProgressBar p;
   // login l;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        View root1 = inflater.inflate(R.layout.ordercard, container, false);


        recview = (RecyclerView) root.findViewById(R.id.recview);
        p=(ProgressBar)root.findViewById(R.id.pbar);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        p.setVisibility(View.VISIBLE);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p.setVisibility(View.GONE);
            }
        },3000);

        if(isNetworkConnected()==false)
        {
            Message.message(getContext(),"No Internet Connection");
        }
        mAuth = FirebaseAuth.getInstance();
        String id=mAuth.getUid();
        FirebaseRecyclerOptions<orderlist> options =
                new FirebaseRecyclerOptions.Builder<orderlist>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(""+id), orderlist.class)
                        .build();

        adapter = new myadapter2(options,getContext());
        recview.setAdapter(adapter);

        global g=global.getInstance();
        em=g.getData();

        //Message.message(getContext(),""+em);
        /*try{
            opn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), address.class);
                    getContext().startActivity(intent);
                }
            });

        }catch (Exception e)
        {
            Message.message(getContext(),""+e);
        }*/

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}