package com.example.subrahmanya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class payment extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5,e6;
    Button b1;
    final int UPI_PAYMENT = 0;

    RadioGroup rg;

    public String name,rate,qty;
    String foodname,foodrate,foodqty,foodtotal,cname,cnum,cnum2,cnation,cstate,clocal,caddress,payment;
    EditText amountEt,noteEt,nameEt,upiIdEt;
    FirebaseDatabase fd;
    DatabaseReference reference;
    public Userdata userdata;
    private RadioButton paybutton;
    public String phn,purl,orderdate;
    private FirebaseAuth mAuth;

    private  RadioButton r1,r2,r3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        global g=global.getInstance();
        getSupportActionBar().setTitle("Payment Method");


        e1=(EditText)findViewById(R.id.nameEt);
        e2=(EditText)findViewById(R.id.noteEt);
        e3=(EditText)findViewById(R.id.txt2);
        e4=(EditText)findViewById(R.id.txt3);
        e5=(EditText)findViewById(R.id.amountEt);
        e6=(EditText)findViewById(R.id.upiIdEt);
        b1=(Button)findViewById(R.id.order);
        rg=(RadioGroup)findViewById(R.id.rgroup);

        r1=(RadioButton)findViewById(R.id.radioButton);
        r2=(RadioButton)findViewById(R.id.radioButton2);
        r3=(RadioButton)findViewById(R.id.radioButton3);

        e1.setEnabled(false);
        e2.setEnabled(false);
        e3.setEnabled(false);
        e4.setEnabled(false);
        e5.setEnabled(false);
        e1.setFocusable(false);
        e2.setFocusable(false);
        e3.setFocusable(false);
        e4.setFocusable(false);
        e5.setFocusable(false);


        phn=g.getData();

        e6.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        String id=mAuth.getUid();
        FirebaseUser ml=mAuth.getCurrentUser();
        String em=ml.getEmail();
        //Message.message(getApplicationContext(),""+em);

        reference= FirebaseDatabase.getInstance().getReference().child(""+id);
        userdata=new Userdata();

        /*desc ds=new desc();
        Boolean b=ds.getdata();
        if(b==true)
        {
            e1.setText(""+ds.a);
        }*/


        final Bundle extras = getIntent().getExtras();
       // Message.message(getApplicationContext(),""+extras.get("Name"));
        e1.setText(""+extras.get("Cname"));
        e2.setText(""+extras.get("Name"));
        e3.setText(""+extras.get("Rate"));
        e4.setText(""+extras.get("Quantity"));
        e5.setText(""+extras.get("Amount"));
       // e1.setText(""+extras.get("Name"));

        foodname=e2.getText().toString();
        foodrate=e3.getText().toString();
        foodqty=e4.getText().toString();
        foodtotal=e5.getText().toString();
        cname= (String) extras.get("Cname");
        cnum=(String)extras.get("Cnum");
        cnum2=(String)extras.get("Cnum2");
        cnation=(String)extras.get("Nation");
        cstate=(String)extras.get("State");
        clocal=(String)extras.get("Local");
        caddress=(String)extras.get("Address");
        purl=(String)extras.get("Url");
        orderdate= DateFormat.getDateTimeInstance().format(new Date());

       /* name= (String) extras.get("Name");
        rate=(String)extras.get("Rate");
        qty=(String)extras.get("Quantity");

        e1.setText(""+name);
        /*e1.setText(""+extras.get("Name"));
        e2.setText(""+extras.get("Rate"));
        e3.setText(""+extras.get("Quantity"));*/




       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(isNetworkConnected()==false)
               {
                   Message.message(getApplicationContext(),"No Internet Connection");
               }else
               {
                   int selectedId=rg.getCheckedRadioButtonId();
                   paybutton=(RadioButton)findViewById(selectedId);
                   payment=paybutton.getText().toString();
                   //Message.message(getApplicationContext(),""+foodname+" "+foodrate+" "+foodqty+" "+foodtotal+" "+cname+" "+cnum+" "+cnum2+" "+cnation+" "+cstate+" "+clocal+" "+caddress+" "+payment);

                   if(payment.equals("Cash On Delivary"))
                   {
                       String amt=e5.getText().subSequence(1,2).toString();
                       String nt=e2.getText().toString();
                       String nm=e1.getText().toString();
                       String uid=e6.getText().toString();
                       userdata.setFoodname(foodname);
                       userdata.setFoodrate(foodrate);
                       userdata.setFoodqty(foodqty);
                       userdata.setFoodtotal(foodtotal);
                       userdata.setCname(cname);
                       userdata.setCnum(cnum);
                       userdata.setCnum2(cnum2);
                       userdata.setCnation(cnation);
                       userdata.setCstate(cstate);
                       userdata.setClocal(clocal);
                       userdata.setCaddress(caddress);
                       userdata.setPayment(payment);
                       userdata.setUrl(purl);
                       userdata.setOrderdate(orderdate);

                       reference.push().setValue(userdata);

                       SweetAlertDialog pDialog2 = new SweetAlertDialog(payment.this, SweetAlertDialog.SUCCESS_TYPE);
                       pDialog2.setTitleText("Order Placed");
                       pDialog2.setConfirmText("OK");
                       pDialog2.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                           @Override
                           public void onClick(SweetAlertDialog sweetAlertDialog) {
                               Intent i1=new Intent(payment.this,Home.class);
                               startActivity(i1);
                               finish();
                           }
                       });
                       pDialog2.setCancelable(false);
                       pDialog2.show();
                       //Message.message(getApplicationContext(),"Order Placed");
                   }
                   else if(payment.equals("UPI Payments"))
                   {
                       Integer lt=e5.length();
                       String amount = e5.getText().subSequence(1,lt-2).toString();
                       //Message.message(getApplicationContext(),""+amount);
                       String note = e2.getText().toString();
                       String name = e1.getText().toString();
                       String upiId = e6.getText().toString();
                       payUsingUpi(amount, upiId, name, note);
                   }else {

                       Message.message(getApplicationContext(),"This Service is Not Available");

                   }
                   //Message.message(getApplicationContext(),""+amt+""+nt+""+nm+""+uid);

                   //Message.message(getApplicationContext(),""+payment);
               }
           }

       });
    }
    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(payment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(payment.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(payment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);

                userdata.setFoodname(foodname);
                userdata.setFoodrate(foodrate);
                userdata.setFoodqty(foodqty);
                userdata.setFoodtotal(foodtotal);
                userdata.setCname(cname);
                userdata.setCnum(cnum);
                userdata.setCnum2(cnum2);
                userdata.setCnation(cnation);
                userdata.setCstate(cstate);
                userdata.setClocal(clocal);
                userdata.setCaddress(caddress);
                userdata.setPayment(payment);
                userdata.setUrl(purl);

                reference.push().setValue(userdata);
                SweetAlertDialog pDialog2 = new SweetAlertDialog(payment.this, SweetAlertDialog.SUCCESS_TYPE);
                pDialog2.setTitleText("Order Placed");
                pDialog2.setConfirmText("OK");
                pDialog2.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent i1=new Intent(payment.this,Home.class);
                        startActivity(i1);
                        finish();
                    }
                });
                pDialog2.setCancelable(false);
                pDialog2.show();
                //Message.message(getApplicationContext(),"Order Placed");



            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(payment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(payment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(payment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
