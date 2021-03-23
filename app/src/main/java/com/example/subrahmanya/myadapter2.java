package com.example.subrahmanya;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter2 extends FirebaseRecyclerAdapter<orderlist,myadapter2.myviewholder>
{
    Context context;

    public myadapter2(@NonNull FirebaseRecyclerOptions<orderlist> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, int position, @NonNull final orderlist model)
    {

        if(model.getFoodname()==null||model.getFoodrate()==null||model.getFoodqty()==null||model.getFoodtotal()==null||model.getUrl()==null||model.getOrderdate()==null)
        {

        }else
        {
            holder.name.setText(model.getFoodname());
            holder.frate.setText(model.getFoodrate());
            holder.mrp.setText("QTY :"+model.getFoodqty());
            holder.ftotal.setText("Total :"+model.getFoodtotal());
            Glide.with(holder.purl.getContext()).load(model.getUrl()).into(holder.purl);
            holder.dt.setText(model.getOrderdate());
        }


       // holder.rating1.setRating(model.getRating());
        //Glide.with(holder.opn.getContext()).load(R.drawable.menulist).into(holder.opn);

        //holder.opn.setImageResource(R.drawable.option);

      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,address.class);
                ctx.startActivity(intent);
            }
        });*/
       /* holder.c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });*/

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ordercard,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView purl;
        ImageView foodimg;
        TextView name,mrp,rating;
        RatingBar rating1;
        ImageView opn;
        CardView c1;
        TextView frate,ftotal,dt;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            purl=(ImageView)itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            rating1=(RatingBar) itemView.findViewById(R.id.coursetext);
            mrp=(TextView)itemView.findViewById(R.id.emailtext);
            opn=(ImageView)itemView.findViewById(R.id.opn);
            c1=(CardView)itemView.findViewById(R.id.card);
            foodimg=(ImageView)itemView.findViewById(R.id.foodimg);
            frate=(TextView)itemView.findViewById(R.id.rate);
            ftotal=(TextView)itemView.findViewById(R.id.total);
            dt=(TextView)itemView.findViewById(R.id.dates);
            //rating=(TextView)itemView.findViewById(R.id.emailtext);
        }
    }
}
