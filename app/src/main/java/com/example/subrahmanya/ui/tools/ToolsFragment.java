package com.example.subrahmanya.ui.tools;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subrahmanya.Cart;
import com.example.subrahmanya.Message;
import com.example.subrahmanya.R;
import com.example.subrahmanya.address;
import com.example.subrahmanya.foodlist;
import com.example.subrahmanya.global;
import com.example.subrahmanya.myadapter;
import com.example.subrahmanya.myadapter3;
import com.example.subrahmanya.register;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    register r;
    TextView t1;
    global g;
    RecyclerView recview;
    myadapter3 adapter;
    ImageView opn;
    Handler handler;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    ProgressBar p;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);


        View root1 = inflater.inflate(R.layout.wishlist, container, false);

        opn=(ImageView)root1.findViewById(R.id.opn);
        recview = (RecyclerView) root.findViewById(R.id.recview);
        p=(ProgressBar)root.findViewById(R.id.pbar);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        String id=mAuth.getUid();
        FirebaseUser ml=mAuth.getCurrentUser();
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

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child(""+id), Cart.class)
                        .build();

        adapter = new myadapter3(options,getContext());
        recview.setAdapter(adapter);

        try{
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
        }



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
        adapter.stopListening();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}