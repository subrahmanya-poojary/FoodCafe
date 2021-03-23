package com.example.subrahmanya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    EditText e1,e2;
    private FirebaseAuth mAuth;
    TextView t1,t2;
    ProgressBar pbar;
    Button b1;
    public static String email;
    //public   FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        e1=(EditText)findViewById(R.id.email);
        e2=(EditText)findViewById(R.id.password);
        t1=(TextView)findViewById(R.id.forgotpw);
        t2=(TextView)findViewById(R.id.textView1);
        pbar=(ProgressBar)findViewById(R.id.progressBar1);
        b1=(Button)findViewById(R.id.button1);
        pbar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent i=new Intent(login.this,Home.class);
            startActivity(i);
            finish();
        }
        else
        {

        }


    }
    public void reg(View view)
    {
        Intent intent = new Intent(this,register.class);
        startActivity(intent);
        finish();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void login(View view)
    {
        if(isNetworkConnected()==false)
        {
            Message.message(getApplicationContext(),"No Internet Connection");
        }
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        email=e1.getText().toString();
        String password=e2.getText().toString();
        if (email.isEmpty()) {
            e1.setError("Must Enter Email");
        }
        else if(password.isEmpty())
        {
            e2.setError("Must Enter a Password");
        }
        else
        {
            b1.setVisibility(View.INVISIBLE);
            t2.setVisibility(View.INVISIBLE);
            pbar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                // Log.d(TAG, "signInWithEmail:success");
                                // user = mAuth.getCurrentUser();
                                //updateUI(user);
                                Toast.makeText(login.this, "Authentication success.",
                                        Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(login.this,Home.class);
                                i.putExtra("Email",email);
                                startActivity(i);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                // Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                                pbar.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                b1.setVisibility(View.VISIBLE);
                                t2.setVisibility(View.VISIBLE);
                                e1.setText("");
                                e2.setText("");
                            }

                            // ...
                        }

                    });
        }

    }
    public void reset(View view)
    {
        final EditText resetMail = new EditText(view.getContext());
        final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Reset Password ?");
        passwordResetDialog.setMessage("Enter Registered Email to Recive Password Reset Link");
        passwordResetDialog.setView(resetMail);

        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // extract the email and send reset link
                String mail = resetMail.getText().toString();
                mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(login.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // close the dialog
            }
        });

        passwordResetDialog.create().show();

    }
}
