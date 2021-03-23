package com.example.subrahmanya.ui.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.subrahmanya.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    ImageView i1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        i1=(ImageView)root.findViewById(R.id.imageView2);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en_IN&gl=US");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en_IN&gl=US");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}