package com.example.motivup.hometargets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.motivup.R;

import java.util.ArrayList;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.MyViewHolder> {

    ArrayList<Target> mProductList;
    LayoutInflater inflater;

    public TargetAdapter(Context context, ArrayList<Target> products) {
        inflater = LayoutInflater.from(context);
        this.mProductList = products;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.target_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Target selectedProduct = mProductList.get(position);
        holder.setData(selectedProduct, position);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productDescription, hide_id, lastDate;
        ImageView productImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productDescription = (TextView) itemView.findViewById(R.id.productDescription);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
            hide_id = (TextView) itemView.findViewById(R.id.hide_id);
            lastDate = (TextView) itemView.findViewById(R.id.lastDate);
        }

        public void setData(Target selectedProduct, int position) {
            this.hide_id.setText(selectedProduct.getTarget_id());
            this.productName.setText(selectedProduct.getProductName());
            this.productDescription.setText(selectedProduct.getProductDescription());
            this.productImage.setImageResource(selectedProduct.getImageID());
            this.lastDate.setText(selectedProduct.getLastDate());
        }

    }
}
