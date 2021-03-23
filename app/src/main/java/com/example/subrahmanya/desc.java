package com.example.subrahmanya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class desc extends AppCompatActivity {

    TextView qty,total,mrp,name,livead;
    Button b1,b2;
    public Integer rate;
    ImageView i1;

    public  String fname,frate,fqty,famt;
    public  String a,b,c,d;
    myadapter adapter;
    public String url;
    public DatabaseReference reference;
    foodlist fd;
    address ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);
        b1=(Button)findViewById(R.id.buttoninc);
        b2=(Button)findViewById(R.id.buttondec);
        qty=(TextView)findViewById(R.id.qty);
        total=(TextView)findViewById(R.id.total);
        mrp=(TextView)findViewById(R.id.mrp);
        name=(TextView)findViewById(R.id.name);
        i1=(ImageView) findViewById(R.id.foodimg);
       // livead=(TextView)findViewById(R.id.address);
        reference=FirebaseDatabase.getInstance().getReference();
        qty.setText("1");
        //rate=Integer.parseInt(mrp.getText().subSequence(1,3).toString());

        Bundle extras = getIntent().getExtras();
        Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
        //i1.setImageBitmap(bitmap);
        name.setText(""+extras.get("name"));
        mrp.setText("₹"+extras.get("mrp"));
        a=name.getText().toString();
        total.setText("₹"+extras.get("mrp"));
        url= (String) extras.get("Imageurl");
        Picasso.with(getApplicationContext()).load(url).into(i1);
       // Message.message(getApplicationContext(),""+a);
        Integer lt=mrp.length();
        rate=Integer.parseInt(mrp.getText().subSequence(1,lt-2).toString());


        getSupportActionBar().setTitle("Food Details");
        ad=new address();



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer getqt=Integer.parseInt(qty.getText().toString());
                if(getqt==0)
                {

                }else
                {
                    getqt++;
                    qty.setText(""+getqt);
                    //Message.message(getApplicationContext(),""+rate);
                    Integer qt=Integer.parseInt(qty.getText().toString());
                    Integer amt=rate*qt;
                    total.setText("₹"+amt+"/-");
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer getqt=Integer.parseInt(qty.getText().toString());
                if(getqt==1)
                {

                }else
                {
                    getqt--;
                    qty.setText(""+getqt);
                    Integer qt=Integer.parseInt(qty.getText().toString());
                    Integer amt=rate*qt;
                    total.setText("₹"+amt+"/-");
                }
            }
        });


    }

    public Boolean getdata() {
        //fname=""+a;
        /*frate=mrp.getText().toString();
        fqty=qty.getText().toString();
        famt=total.getText().toString();*/
        return  true;
    }


    public void buy(View view)
    {
        if(isNetworkConnected()==false)
        {
            Message.message(getApplicationContext(),"No Internet Connection");
        }else{
            Intent intent=new Intent(desc.this,address.class);
            intent.putExtra("Name",name.getText().toString());
            intent.putExtra("Rate",mrp.getText().toString());
            intent.putExtra("Quantity",qty.getText().toString());
            intent.putExtra("Amount",total.getText().toString());
            intent.putExtra("Url",url);
            startActivity(intent);
            finish();
        }

    }
    public void feedback(View view)
    {
        if(isNetworkConnected()==false)
        {
            Message.message(getApplicationContext(),"No Internet Connection");
        }else{
            Intent intent=new Intent(desc.this,feedback.class);
            intent.putExtra("Name",name.getText().toString());
            intent.putExtra("Url",url);
            startActivity(intent);
            finish();
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
