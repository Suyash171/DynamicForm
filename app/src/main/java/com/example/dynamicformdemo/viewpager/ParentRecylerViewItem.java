package com.example.dynamicformdemo.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dynamicformdemo.Field;
import com.example.dynamicformdemo.FieldsModel;
import com.example.dynamicformdemo.R;
import com.example.dynamicformdemo.RecylerViewAdapter;

import java.util.ArrayList;

public class ParentRecylerViewItem extends RecyclerView.Adapter<ParentRecylerViewItem.MyViewHolder> {

    private ViewGroup mParent;
    private Context mContext;
    private NewFormModel fieldsModel = new NewFormModel();
    private RecylerViewAdapter adapter;

    public ParentRecylerViewItem(Context context) {
        //mDataset = myDataset;
        mContext = context;
    }

    public void addAll(ArrayList<Field> fields) {
        fieldsModel.setParentList(fields);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_parent_list_item, parent, false);
        mParent = parent;
        return new ParentRecylerViewItem.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ArrayList<Field> fields = (ArrayList<Field>) fieldsModel.getParentList();

        holder.recylerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new RecylerViewAdapter(mContext);
        adapter.addAll(fields);
        holder.recylerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return fieldsModel.getParentList().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RecyclerView recylerView;
        public MyViewHolder(View v) {
            super(v);
            recylerView = itemView.findViewById(R.id.rvParentList);
        }
    }

}
