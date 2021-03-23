package com.example.subrahmanya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class address extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5,e6,e7;
    FusedLocationProviderClient fusedLocationProviderClient;

    public String name,rate,qty,amt;
    public String cname,cnum,cnum2,nation,state,local,post;
    public String orderad,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Bundle extras = getIntent().getExtras();
        name= (String) extras.get("Name");
        rate=(String)extras.get("Rate");
        qty=(String)extras.get("Quantity");
        amt=(String)extras.get("Amount");
        url=(String)extras.get("Url");


        e1=(EditText)findViewById(R.id.txt1);
        e2=(EditText)findViewById(R.id.txt2);
        e3=(EditText)findViewById(R.id.txt3);
        e4=(EditText)findViewById(R.id.txt4);
        e5=(EditText)findViewById(R.id.txt5);
        e6=(EditText)findViewById(R.id.txt6);
        e7=(EditText)findViewById(R.id.txt7);

        e5.setFocusable(true);

       /* e7.setEnabled(true);
        e7.setFocusable(true);
        e7.setClickable(true);
        e7.setCursorVisible(true);
        e1.setFocusable(false);
        e2.setFocusable(false);
        e3.setFocusable(false);
        e4.setFocusable(false);*/
        e1.setEnabled(false);
        e2.setEnabled(false);
        e3.setEnabled(false);
        e4.setEnabled(false);
        e5.setText("");
        //e5.setFocusable(true);
        getSupportActionBar().setTitle("Address Details");

        cname=e5.getText().toString();
        cnum=e7.getText().toString();
        cnum2=e6.getText().toString();
        nation=e1.getText().toString();
        state=e2.getText().toString();
        local=e3.getText().toString();
        post=e4.getText().toString();

       /* e1.setKeyListener(null);
        e2.setKeyListener(null);
        e3.setKeyListener(null);
        e4.setKeyListener(null);*/

       /* e1.setFocusable(false);
        e2.setFocusable(false);
        e3.setFocusable(false);
        e4.setFocusable(false);
        e1.setLongClickable(false);
        e2.setLongClickable(false);
        e3.setLongClickable(false);
        e4.setLongClickable(false);*/


        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(address.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            getlocation();
        }
        else
        {
            ActivityCompat.requestPermissions(address.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }


    }
    public void getlocation(){

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location=task.getResult();

                if(location!=null)
                {
                    try {

                        Geocoder geocoder=new Geocoder(address.this, Locale.getDefault());

                        List<Address> addresses =geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);


                       // e1.setText(""+addresses.get(0).getLatitude());
                        //e2.setText(""+addresses.get(0).getLongitude());
                        e1.setText(""+addresses.get(0).getCountryName());
                        e2.setText(""+addresses.get(0).getAdminArea());
                        e4.setText(""+addresses.get(0).getAddressLine(0));
                        e3.setText(""+addresses.get(0).getFeatureName());

                        orderad=addresses.get(0).getAddressLine(0);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void edit(View view){
        /*e1.setFocusable(true);
        e2.setFocusable(true);
        e3.setFocusable(true);
        e4.setFocusable(true);
        e1.setLongClickable(true);
        e2.setLongClickable(true);
        e3.setLongClickable(true);
        e4.setLongClickable(true);*/
       e1.setEnabled(true);
       e2.setEnabled(true);
       e3.setEnabled(true);
       e4.setEnabled(true);
       e5.setText("");


    }
    public void payment(View view)
    {
        if(isNetworkConnected()==false)
        {
            Message.message(getApplicationContext(),"No Internet Connection");
        }else{
        cname=e5.getText().toString();
        cnum=e7.getText().toString();
        cnum2=e6.getText().toString();
        nation=e1.getText().toString();
        state=e2.getText().toString();
        local=e3.getText().toString();
        post=e4.getText().toString();
        String pattern = "^[A-Za-z ]+";

        if(cname.isEmpty()||cnum.isEmpty()||cnum2.isEmpty()||nation.isEmpty()||state.isEmpty()||local.isEmpty()||post.isEmpty())
        {
            new SweetAlertDialog(address.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error...")
                    .setContentText("Enter Mandatory Fields")
                    .setConfirmText("OK")
                    .show();
        }
        else if(cnum.equals(cnum2))
        {
            e6.setError("Enter Different Phone Number");
        }
        else if(!cname.matches(pattern)||cname.length()<3)
        {
            e5.setError("Enter Valid Name");
        }
        else if(cnum.length()<=6)
        {
            e7.setError("Enter Valid Number");
        }
        else if(cnum2.length()<=6)
        {
            e6.setError("Enter Valid Number");
        }
        else {

            Intent intent = new Intent(address.this, payment.class);

            intent.putExtra("Name", name);
            intent.putExtra("Rate", rate);
            intent.putExtra("Quantity", qty);
            intent.putExtra("Amount", amt);
            intent.putExtra("Cname", cname);

            intent.putExtra("Cname", e5.getText().toString());
            intent.putExtra("Cnum", e7.getText().toString());
            intent.putExtra("Cnum2", e6.getText().toString());
            intent.putExtra("Nation", e1.getText().toString());
            intent.putExtra("State", e2.getText().toString());
            intent.putExtra("Local", e3.getText().toString());
            intent.putExtra("Address", e4.getText().toString());
            intent.putExtra("Url", url);

            //Message.message(getApplicationContext(),""+name);
            startActivity(intent);
            finish();
            }

        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
