package com.example.ilove.rental;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    // view for rental records


    Context context;
    ArrayList<Record>records;
    OnRecordListener onRecordListener;


    public MyAdapter(Context c, ArrayList<Record> r, OnRecordListener onRecordListener){
        context = c;
        records = r;
        this.onRecordListener = onRecordListener;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.record_view_layout,parent,false),onRecordListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewHouseNumber.setText(records.get(position).getHouseNumber());
        holder.textViewAmountPaid.setText("$"+ records.get(position).getAmountPaid());
        holder.textViewRoomNumber.setText("Rm "+records.get(position).getRoomNumber());
        holder.textViewMethodOfPayment.setText(records.get(position).getMethodOfPayment());

    }


    @Override
    public int getItemCount() {
        return records.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView textViewAmountPaid, textViewHouseNumber, textViewRoomNumber,textViewMethodOfPayment, imageView;
        OnRecordListener onRecordListener;
        public MyViewHolder(@NonNull View itemView, OnRecordListener onRecordListener) {
            super(itemView);
            imageView = (TextView)itemView.findViewById(R.id.roomNumberLargeCardView);
            textViewAmountPaid = (TextView)itemView.findViewById(R.id.amountPaidCardView);
            textViewHouseNumber = (TextView)itemView.findViewById(R.id.houseNumberCardView);
            textViewRoomNumber = (TextView)itemView.findViewById(R.id.roomNumberCardView);
            textViewMethodOfPayment = (TextView) itemView.findViewById(R.id.methodOfPaymentCardView);
            this.onRecordListener = onRecordListener;
            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            onRecordListener.onRecordClick(getAdapterPosition());
        }
    }

    public interface OnRecordListener{
        void onRecordClick(int pos);
    }



}
