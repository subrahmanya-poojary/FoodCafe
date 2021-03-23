package com.example.subrahmanya;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.subrahmanya.ui.home.HomeFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<foodlist,myadapter.myviewholder>
{
    public Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;


    public myadapter(@NonNull FirebaseRecyclerOptions<foodlist> options,Context context) {
        super(options);
        this.context=context;
    }


    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, int position, @NonNull final foodlist model)
    {
        holder.name.setText(model.getName());
        holder.mrp.setText("₹"+model.getMrp());
        //holder.rating.setText(model.getRating());
        Glide.with(holder.purl.getContext()).load(model.getPurl()).into(holder.purl);
        holder.rating1.setRating(model.getRating());
        Glide.with(holder.cart.getContext()).load(R.drawable.menulist).into(holder.cart);


        //holder.opn.setImageResource(R.drawable.option);

      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,address.class);
                ctx.startActivity(intent);
            }
        });*/
        holder.c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkConnected()==false)
                {
                    Message.message(context,"No Internet Connection");
                }
                else
                {
                    Intent intent=new Intent(context,desc.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    holder.purl.buildDrawingCache();
                    Bitmap bitmap = holder.purl.getDrawingCache();
                    intent.putExtra("BitmapImage", bitmap);
                    intent.putExtra("name",model.getName());
                    intent.putExtra("mrp",model.getMrp());
                    intent.putExtra("rate",model.getRating());

                    String url =model.getPurl();
                    intent.putExtra("Imageurl",model.getPurl());
                    // Message.message(context,""+url);
                    context.startActivity(intent);
                }


            }
        });

        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_main, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){

                            case R.id.popCart:
                                Cart cart=new Cart();
                                cart.setName(model.getName());
                                cart.setMrp(model.getMrp());
                                cart.setRating(model.getRating());
                                cart.setPurl(model.getPurl());
                                mAuth = FirebaseAuth.getInstance();
                                String id=mAuth.getUid();
                                FirebaseUser ml=mAuth.getCurrentUser();
                                reference= FirebaseDatabase.getInstance().getReference().child("Cart").child(""+id);
                                reference.push().setValue(cart);
                                Message.message(context,"Item Added To Cart");



                        }
                        return true;
                    }
                });

            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.foodcard,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView purl,i1;
        ImageView foodimg;
        TextView name,mrp,rating;
        RatingBar rating1;
        ImageView cart;
        CardView c1;
        ProgressBar p;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            purl=(ImageView)itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            rating1=(RatingBar) itemView.findViewById(R.id.coursetext);
            mrp=(TextView)itemView.findViewById(R.id.emailtext);
            cart=(ImageView)itemView.findViewById(R.id.opn);
            c1=(CardView)itemView.findViewById(R.id.card);
            foodimg=(ImageView)itemView.findViewById(R.id.foodimg);

            //rating=(TextView)itemView.findViewById(R.id.emailtext);
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
