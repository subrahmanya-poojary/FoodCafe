package com.example.subrahmanya.ui.gallery;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.subrahmanya.Message;
import com.example.subrahmanya.R;
import com.example.subrahmanya.Userdata;
import com.example.subrahmanya.address;
import com.example.subrahmanya.foodbook;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    Button b1,b2;
    EditText e1,e2,e3;
    public int Items;
    TableRow tbrow0,tbrow;
    TableLayout stk;
    DatabaseReference reference;
    public String fdate,ftime,fitem,orderdate;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public DatePickerDialog datePickerDialog;
    Userdata userdata;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        b1 = (Button) root.findViewById(R.id.button2);
        e2=(EditText)root.findViewById(R.id.Items2);
        e3=(EditText)root.findViewById(R.id.Items3);
        e1=(EditText)root.findViewById(R.id.Items1);
        stk = (TableLayout) root.findViewById(R.id.table_main);
        e1.setKeyListener(null);
        e2.setKeyListener(null);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fdate=e1.getText().toString();
                ftime=e2.getText().toString();
                fitem=e3.getText().toString();

                if(fdate.isEmpty()||ftime.isEmpty()||fitem.isEmpty())
                {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText("Enter Mandatory Fields")
                            .setConfirmText("OK")
                            .show();
                }else
                {
                    Intent in=new Intent(getActivity(),foodbook.class);
                    in.putExtra("Fdate",fdate);
                    in.putExtra("Ftime",ftime);
                    in.putExtra("Fitem",fitem);
                    startActivity(in);
                }




                /*stk.setVisibility(View.VISIBLE);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.VISIBLE);
                e1.setVisibility(View.INVISIBLE);
                e2.setVisibility(View.INVISIBLE);
                e3.setVisibility(View.INVISIBLE);


                Items=Integer.parseInt(e1.getText().toString());

                tbrow0 = new TableRow(getContext());
                EditText iname = new EditText(getContext());
                iname.setText(" Item Name ");
                iname.setTextColor(Color.WHITE);
                tbrow0.addView(iname);
                EditText iqty = new EditText(getContext());
                iqty.setText(" QTY ");
                iqty.setTextColor(Color.WHITE);
                tbrow0.addView(iqty);
                for (int i = 0; i < Items; i++) {
                    tbrow = new TableRow(getContext());
                    EditText i3 =new EditText(getContext());
                    tbrow.addView(i3);
                    EditText i4=new EditText(getContext());
                    tbrow.addView(i4);
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
                   /* stk.addView(tbrow);

                    //Intent in=new Intent(getActivity(), foodbook.class);
                   // startActivity(in);
                }*/
            }
        });

        /*b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int part=tbrow.getChildCount();
                Items=Integer.parseInt(e1.getText().toString());
                for(int i=1;i<part;i++){
                    EditText e1=(EditText)tbrow.getChildAt(2);
                    String nm=e1.getText().toString();
                    Message.message(getContext(),""+nm);*/


                   /* TableLayout stk = ((TableLayout)root.findViewById(R.id.table_main));
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
                                        //Message.message(getContext(),""+txtEdit);
                                        reference= FirebaseDatabase.getInstance().getReference().child("New order");
                                        reference.push().setValue(txtEdit);

                                    } else if (viewChild2 instanceof TextView) {
                                        // get text from text view
                                        String txttext = ((TextView) viewChild2).getText().toString();
                                        Message.message(getContext(),""+txttext);
                                        //TODO: add rest of the logic
                                    }
                                }
                            }
                        }

                    }
            }
        });*/
                   e1.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if (v == e1) {

                               Calendar c = Calendar.getInstance();
                               mYear = c.get(Calendar.YEAR);
                               mMonth = c.get(Calendar.MONTH);
                               mDay = c.get(Calendar.DAY_OF_MONTH);
                               datePickerDialog = new DatePickerDialog(getContext(),
                                       new DatePickerDialog.OnDateSetListener() {

                                           @Override
                                           public void onDateSet(DatePicker view, int year,
                                                                 int monthOfYear, int dayOfMonth) {
                                               String mDay2, mMonth2;
                                               mDay2 = String.valueOf(dayOfMonth);
                                               mMonth2 = String.valueOf(monthOfYear + 1);
                                               if ((mDay2.length() == 1) && (mMonth2.length() == 1)) {
                                                   e1.setText("0" + dayOfMonth + "-" + "0" + (monthOfYear + 1) + "-" + year);
                                               } else if (mDay2.length() == 1) {
                                                   e1.setText("0" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                               } else if (mMonth2.length() == 1) {
                                                   e1.setText(dayOfMonth + "-" + "0" + (monthOfYear + 1) + "-" + year);
                                               }
                                               //Message.message(getApplicationContext(),""+mMonth2.length());
                                           }
                                       }, mYear, mMonth, mDay);
                               datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                               datePickerDialog.show();
                           }
                       }
                   });

                   e2.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(v==e2) {

                               final Calendar c = Calendar.getInstance();
                               mHour = c.get(Calendar.HOUR_OF_DAY);
                               mMinute = c.get(Calendar.MINUTE);

                               TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                       new TimePickerDialog.OnTimeSetListener() {

                                           @Override
                                           public void onTimeSet(TimePicker view, int hourOfDay,
                                                                 int minute) {

                                               e2.setText(hourOfDay + ":" + minute);
                                           }
                                       }, mHour, mMinute, false);
                               timePickerDialog.show();
                           }
                       }
                   });
        return root;
    }

}