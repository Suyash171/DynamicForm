package com.example.dynamicformdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gipl.imagepicker.ImageResult;

import java.util.ArrayList;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder> {

    private ArrayList<Field> mDataset;
    private Context mContext;
    private iOnClickListener iOnClickListener;
    // private MyViewHolder myViewHolder;
    private ImageAdapter imageAdapter;

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
        // myViewHolder = holder;
        Field field = mDataset.get(position);
        switch (field.getType()) {
            case "STRING":
                holder.tvName.setText(field.getName());
                holder.tvName.setVisibility(View.VISIBLE);
                break;
            case "TEXT":
                holder.etName.setVisibility(View.VISIBLE);
                break;
            case "RADIO":
                holder.radioGroup.setVisibility(View.VISIBLE);
                RadioGroup radioGroup = holder.itemView.findViewById(R.id.radioGrp);
                RadioButton[] rb = new RadioButton[5];
                radioGroup.removeAllViews();
                radioGroup.setOrientation(RadioGroup.VERTICAL); //or RadioGroup.VERTICAL
                rb[0] = new RadioButton(mContext);
                rb[0].setText("Position" + 0);
                rb[0].setId(0 + 100);
                radioGroup.addView(rb[0]);
                break;
            case "CHECKBOX":
                holder.linearLayout.setVisibility(View.VISIBLE);
                holder.linearLayout.setOrientation(LinearLayout.VERTICAL);
                holder.linearLayout.removeAllViews();

                final CheckBox ch = new CheckBox(mContext);
                ch.setId(0);
                ch.setText("Cheers !! " + 0);
                ch.setOnCheckedChangeListener((compoundButton, b) -> {

                });
                holder.linearLayout.addView(ch);
                break;
            case "UPLOAD_IMAGE":
                holder.llvImageUpload.setVisibility(View.VISIBLE);
                holder.btnUploadImage.setOnClickListener(view -> {
                    // Open custom dialog with icons.
                    holder.rvImages.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                    imageAdapter = new ImageAdapter();
                    holder.rvImages.setAdapter(imageAdapter);

                    iOnClickListener.onImageUpload(position);
                });

                break;
            default:
                break;
        }
    }

    /**
     *
     */
    public void setSelectedImage(ArrayList<ImageResult> imageResults, int pos) {
        imageAdapter.addAll(imageResults);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvName, btnUploadImage;
        public EditText etName;
        public RadioGroup radioGroup;
        public LinearLayout linearLayout;
        public LinearLayout llvImageUpload;
        public RecyclerView rvImages;
        public MyViewHolder myViewHolder;

        public MyViewHolder(View v) {
            super(v);
            tvName = itemView.findViewById(R.id.tvName);
            etName = itemView.findViewById(R.id.et);
            radioGroup = itemView.findViewById(R.id.radioGrp);
            linearLayout = itemView.findViewById(R.id.checkbox);
            llvImageUpload = itemView.findViewById(R.id.llvImage);
            btnUploadImage = itemView.findViewById(R.id.btn_upload);
            rvImages = itemView.findViewById(R.id.rv_images);

            // llvContainer = itemView.findViewById(R.id.insert_point);
        }
    }


    public ChildAdapter.iOnClickListener getiOnClickListener() {
        return iOnClickListener;
    }

    public void setiOnClickListener(ChildAdapter.iOnClickListener iOnClickListener) {
        this.iOnClickListener = iOnClickListener;
    }


    interface iOnClickListener {
        void onImageUpload(int position);
    }

}
