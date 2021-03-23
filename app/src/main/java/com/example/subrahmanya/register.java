package com.example.subrahmanya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5,e6;
    Button b1;
    final AwesomeValidation valid=new AwesomeValidation(ValidationStyle.BASIC);
    private FirebaseAuth mAuth;
    String updateUI;
    ProgressBar pbar;
    public String Phone;
    public  profile p;
    global g;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        e1=(EditText)findViewById(R.id.Fname);
        e2=(EditText)findViewById(R.id.Lname);
        e3=(EditText)findViewById(R.id.Email);
        e4=(EditText)findViewById(R.id.Pass1);
        e5=(EditText)findViewById(R.id.pass2);
        e6=(EditText)findViewById(R.id.Phno);
        b1=(Button)findViewById(R.id.register);
        pbar=(ProgressBar)findViewById(R.id.progressBar1);

        mAuth = FirebaseAuth.getInstance();
        pbar.setVisibility(View.INVISIBLE);

        g=global.getInstance();
        p=new profile();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void register(View view) {
        try {
            if(isNetworkConnected()==false)
            {
                Message.message(getApplicationContext(),"No Internet Connection");
            }
            String pattern2 = "[a-zA-Z]";
            final String fname = e1.getText().toString();
            String lname = e2.getText().toString();
            final String email = e3.getText().toString();
            String pass1 = e4.getText().toString();
            String pass2 = e5.getText().toString();
            Phone=e6.getText().toString();
            valid.addValidation(this, R.id.Fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.invalidname);
            valid.addValidation(this, R.id.Lname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.invalidname);
            valid.addValidation(this, R.id.Email, Patterns.EMAIL_ADDRESS, R.string.invalidemail);
            valid.addValidation(this,R.id.Phno,Patterns.PHONE,R.string.invalidno);
            if (valid.validate()) {
            }
            if (fname.isEmpty()) {
                e1.setError("Must Enter FirstName");
            } else if (lname.isEmpty()) {
                e2.setError("Must Enter LastName");
            } else if (email.isEmpty()) {
                e3.setError("Must Enter Email");

            } else if (email.isEmpty() && e2.length() < 6) {
                e3.setError("Password is too short");

            }
            else  if(Phone.isEmpty()){
                e6.setError("Must Enter Phone Number");
            }
            else if (pass1.isEmpty()) {
                e4.setError("Password is too short");
            } else if (pass1.matches(pass2) == false) {
                e4.setError("Password does not match");
                e5.setError("Password does not match");
            } else {
                String pattern = "[a-zA-Z0-9._-]+@[gmail]+\\.+[com]+";
                if (email.matches(pattern)) {
                    //global g = global.getInstance();
                    //g.setData(Phone);
                    pbar.setVisibility(view.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email,pass1).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Message.message(getApplicationContext(),"Register Success");
                                p.setFname(fname);
                                p.setEmail(email);
                                p.setPhone(Phone);
                                mAuth = FirebaseAuth.getInstance();
                                String id=mAuth.getUid();
                                FirebaseUser ml=mAuth.getCurrentUser();
                                reference= FirebaseDatabase.getInstance().getReference().child("Profile").child(""+id);
                                reference.push().setValue(p);
                                Intent intent = new Intent(register.this,login.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Message.message(getApplicationContext(),"Register failed");
                                pbar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }

        } catch (Exception e) {
        }
    }
}
