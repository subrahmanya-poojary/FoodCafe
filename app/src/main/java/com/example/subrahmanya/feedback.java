package com.example.subrahmanya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class feedback extends AppCompatActivity {

    public  String name,url,id;

    TextView t1;
    ImageView i1;
    EditText e1;
    RatingBar r1;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        t1=(TextView)findViewById(R.id.fname);
        e1=(EditText)findViewById(R.id.feeds);
        i1=(ImageView)findViewById(R.id.fimg);
        r1=(RatingBar)findViewById(R.id.fbar);

        Bundle extras = getIntent().getExtras();
        name= (String) extras.get("Name");
        url=(String)extras.get("Url");

        getSupportActionBar().hide();

        t1.setText(name);
        Picasso.with(getApplicationContext()).load(url).into(i1);

        mAuth = FirebaseAuth.getInstance();
        id=mAuth.getUid();
    }
    public  void send(View view)
    {

        String rating,feedback,food;
        rating=Float.toString(r1.getRating());
        feedback=e1.getText().toString();
        food=t1.getText().toString();

        if(rating.isEmpty()||feedback.isEmpty()||food.isEmpty()||rating.equals(0.0))
        {
            Message.message(getApplicationContext(),"Enter Feedback & Ratings");
        }
        else
        {
            userfeed uf=new userfeed();
            uf.setFname(food);
            uf.setFrating(rating);
            uf.setFeedback(feedback);
            reference= FirebaseDatabase.getInstance().getReference().child("Feedback").child(""+id);
            reference.push().setValue(uf);
            Message.message(getApplicationContext(),"Thank You For Your Feedback");

            Intent i=new Intent(this,Home.class);
            startActivity(i);
            finish();
        }

    }
}
