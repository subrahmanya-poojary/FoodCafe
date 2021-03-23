package com.example.subrahmanya.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.subrahmanya.MainActivity;
import com.example.subrahmanya.Message;
import com.example.subrahmanya.R;
import com.example.subrahmanya.address;
import com.example.subrahmanya.foodlist;
import com.example.subrahmanya.login;
import com.example.subrahmanya.myadapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView recview;
    myadapter adapter;
    ImageView opn;
    ImageView i1;
    ProgressBar p;
    Handler handler;
    FusedLocationProviderClient fusedLocationProviderClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        View root1 = inflater.inflate(R.layout.foodcard, container, false);
        i1=(ImageView)root.findViewById(R.id.imageView2);
        p=(ProgressBar)root.findViewById(R.id.pbar);
        p.setVisibility(View.VISIBLE);
        //Glide.with(getContext()).load(R.drawable.foodscreen).into(i1);

        opn=(ImageView)root1.findViewById(R.id.opn);
        recview = (RecyclerView) root.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getContext());
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

        }
        else
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               p.setVisibility(View.GONE);
            }
        },3000);
        //p.setVisibility(View.GONE);
        if(isNetworkConnected()==false)
        {
            Message.message(getContext(),"No Internet Connection");
        }


        FirebaseRecyclerOptions<foodlist> options =
                new FirebaseRecyclerOptions.Builder<foodlist>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foodlist"), foodlist.class)
                        .build();

        adapter = new myadapter(options,getContext());

        recview.setAdapter(adapter);



        try{
            opn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),address.class);
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