package com.example.dynamicformdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder> {

    private ArrayList<Field> mDataset;
    private Context mContext;

    public ChildAdapter(ArrayList<Field> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_child_list_item, parent, false);
        return new ChildAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Field field = mDataset.get(position);
        holder.tvName.setText(field.getType());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvName;
        public MyViewHolder(View v) {
            super(v);
            tvName = itemView.findViewById(R.id.tvName);
            // llvContainer = itemView.findViewById(R.id.insert_point);
        }
    }

}
