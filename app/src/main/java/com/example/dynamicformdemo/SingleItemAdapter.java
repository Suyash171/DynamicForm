package com.example.dynamicformdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * {
 *       "name": "How the hell are you ?",
 *       "type": "QUESTIONS",
 *       "defaultValue": true,
 *       "values": [
 *         {
 *           "name": "Male",
 *           "type": "STRING",
 *           "max": 103,
 *           "defaultValue": false
 *         },
 *         {
 *           "name": "Female",
 *           "type": "STRING",
 *           "max": 104,
 *           "defaultValue": false
 *         }
 *       ]
 *     }
 */
public class SingleItemAdapter extends RecyclerView.Adapter<SingleItemAdapter.MyViewHolder> {

    private ArrayList<Field> singleItemArrayList = new ArrayList<>();

    public void addAll(ArrayList<Field> fields) {
        singleItemArrayList.addAll(fields);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_item, parent, false);
        return new SingleItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Field field = singleItemArrayList.get(position);
        holder.tvAns.setText(field.getName());

        holder.tvAns.setSelected(field.isSelected());

        holder.llvContainer.setOnClickListener(view -> {
            if (field.isSelected()){
                field.setSelected(false);
                holder.tvAns.setSelected(field.isSelected());
            }else {
                singleSelect(field);
                holder.tvAns.setSelected(field.isSelected());
            }
            notifyDataSetChanged();
        });
    }

    /**
     *
     * @param selectedOption
     */
    private void singleSelect(Field selectedOption) {
        for (Field option : singleItemArrayList) {
            if (selectedOption.getMax() != option.getMax()) {
                option.setSelected(false);
            } else
                option.setSelected(true);
        }
    }

    @Override
    public int getItemCount() {
        return singleItemArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout llvContainer;
        public RecyclerView recylerView;
        private TextView tvAns;

        public MyViewHolder(View v) {
            super(v);
            //recylerView = itemView.findViewById(R.id.rv_child_list);
            llvContainer = itemView.findViewById(R.id.llvAnsparent);
            tvAns = itemView.findViewById(R.id.tvAns);
        }
    }

}
