package com.example.subrahmanya.ui.send;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.subrahmanya.R;
import com.example.subrahmanya.address;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    Button b1;
    TextView t1;
    String TO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        t1=(TextView)root.findViewById(R.id.textView3);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"works4parttime@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Any FoodCafe Queries");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getActivity().getPackageManager())!=null)
                    startActivity(intent);
                else
                    Toast.makeText(getContext(),"Gmail App is not installed",Toast.LENGTH_SHORT).show();
            }
        });

        return root;
        }
}