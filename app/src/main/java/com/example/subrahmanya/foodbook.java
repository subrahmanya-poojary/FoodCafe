package com.example.subrahmanya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class foodbook extends AppCompatActivity {
    Button b1;
    private FirebaseAuth mAuth;
    public FirebaseUser user;

    public int Items;
    TableRow tbrow0,tbrow;
    TableLayout stk;
    DatabaseReference reference;
    public String fdate,ftime,fitem,id,orderdate;
    Button b2;
    neworder no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodbook);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        id=mAuth.getUid();
        stk = (TableLayout)findViewById(R.id.table_main);
        b2=(Button)findViewById(R.id.button3);

        Bundle extras = getIntent().getExtras();
        fitem=(String)extras.get("Fitem");
        fdate=(String)extras.get("Fdate");
        ftime=(String)extras.get("Ftime");
        Items=Integer.parseInt(fitem);

        getSupportActionBar().setTitle("Order Details");

        no=new neworder();
        orderdate= DateFormat.getDateTimeInstance().format(new Date());


        tbrow0 = new TableRow(getApplicationContext());
        EditText iname = new EditText(getApplicationContext());
        iname.setText(" Item Name ");
        iname.setTextColor(Color.WHITE);
        tbrow0.addView(iname);
        EditText iqty = new EditText(getApplicationContext());
        iqty.setText(" QTY ");
        iqty.setTextColor(Color.WHITE);
        tbrow0.addView(iqty);
        iname.setEnabled(false);
        iqty.setEnabled(false);
        for (int i = 0; i < Items; i++)
        {
            tbrow = new TableRow(getApplicationContext());
            EditText i3 = new EditText(getApplicationContext());
            tbrow.addView(i3);
            EditText i4 = new EditText(getApplicationContext());
            tbrow.addView(i4);
            stk.addView(tbrow);
        }
                    /*TextView t1v = new TextView(getContext());
                    t1v.setText("" + i);
                    t1v.setTextColor(Color.WHITE);
                    t1v.setGravity(Gravity.CENTER);
                    tbrow.addView(t1v);
                    TextView t2v = new TextView(getContext());
                    t2v.setText("Product " + i);
                    t2v.setTextColor(Color.WHITE);
                    t2v.setGravity(Gravity.CENTER);
                    tbrow.addView(t2v);
                    TextView t3v = new TextView(getContext());
                    t3v.setText("Rs." + i);
                    t3v.setTextColor(Color.WHITE);
                    t3v.setGravity(Gravity.CENTER);
                    tbrow.addView(t3v);
                    TextView t4v = new TextView(getContext());
                    t4v.setText("" + i * 15 / 32 * 10);
                    t4v.setTextColor(Color.WHITE);
                    t4v.setGravity(Gravity.CENTER);
                    tbrow.addView(t4v);*/


            //Intent in=new Intent(getActivity(), foodbook.class);
            // startActivity(in);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()==false)
                {
                    Message.message(getApplicationContext(),"No Internet Connection");
                }
                /*int part=tbrow.getChildCount();
                Items=Integer.parseInt(e1.getText().toString());
                for(int i=1;i<part;i++){
                    EditText e1=(EditText)tbrow.getChildAt(2);
                    String nm=e1.getText().toString();
                    Message.message(getContext(),""+nm);*/
                else
                {
                    TableLayout stk = ((TableLayout)findViewById(R.id.table_main));
                    int childParts = stk.getChildCount();
                    if (stk != null) {
                        for (int i = 0; i < childParts; i++) {
                            View viewChild = stk.getChildAt(i);
                            if (viewChild instanceof TableRow) {
                                int rowChildParts = ((TableRow) viewChild).getChildCount();
                                for (int j = 0; j < rowChildParts; j++) {
                                    View viewChild2 = ((TableRow) viewChild).getChildAt(j);
                                    if (viewChild2 instanceof EditText) {
                                        // get text from edit text
                                        String txtEdit = ((EditText) viewChild2).getText()
                                                .toString();

                                        if(txtEdit==null)
                                        {
                                            Message.message(getApplicationContext(),"Enter Food Items");
                                        }
                                        else
                                        {
                                            reference= FirebaseDatabase.getInstance().getReference().child("New Order").child(""+id);
                                            reference.push().setValue(txtEdit);
                                        }
                                        //Message.message(getContext(),""+txtEdit);


                                    } else if (viewChild2 instanceof TextView) {
                                        // get text from text view
                                        String txttext = ((TextView) viewChild2).getText().toString();
                                        //Message.message(getApplicationContext(),""+txttext);
                                        //TODO: add rest of the logic
                                    }
                                }
                            }
                        }

                    }
                    no.setFdate(fdate);
                    no.setFtime(ftime);
                    no.setOrderdate(orderdate);
                    reference.push().setValue(no);
                }
                SweetAlertDialog pDialog2 = new SweetAlertDialog(foodbook.this, SweetAlertDialog.SUCCESS_TYPE);
                pDialog2.setTitleText("Order Placed");
                pDialog2.setConfirmText("OK");
                pDialog2.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent i1=new Intent(foodbook.this,Home.class);
                        startActivity(i1);
                        finish();
                    }
                });
                pDialog2.setCancelable(false);
                pDialog2.show();
            }
        });
    }
    public void back(View view)
    {
        mAuth.signOut();
        Intent i=new Intent(foodbook.this,login.class);
        startActivity(i);
        finish();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
