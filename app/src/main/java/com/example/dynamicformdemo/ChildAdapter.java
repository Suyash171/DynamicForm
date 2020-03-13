package com.example.dynamicformdemo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder> {

    private Context mContext;
    private iOnClickListener iOnClickListener;
    // private MyViewHolder myViewHolder;
    private ImageAdapter imageAdapter;
    private FieldsModel fieldsModel = new FieldsModel();

    public ChildAdapter(Context context) {
        mContext = context;
    }


    public void addAll(ArrayList<Field> fields) {
        fieldsModel.setFields(fields);
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
        Field field = fieldsModel.getFields().get(position);

        switch (field.getType()) {
            case "STRING":
                holder.tvName.setText(field.getName());
                holder.tvName.setVisibility(View.VISIBLE);

                //disable other views
                holder.etName.setVisibility(View.GONE);
                holder.radioGroup.setVisibility(View.GONE);
                holder.linearLayout.setVisibility(View.GONE);
                holder.llvImageUpload.setVisibility(View.GONE);

                break;
            case "TEXT":
                holder.etName.setVisibility(View.VISIBLE);

                //update text entered
                holder.etName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        fieldsModel.getFields().get(position).setEnteredValue(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                //disable other views
                holder.tvName.setVisibility(View.GONE);
                holder.radioGroup.setVisibility(View.GONE);
                holder.linearLayout.setVisibility(View.GONE);
                holder.llvImageUpload.setVisibility(View.GONE);

                break;
            case "RADIO":
                holder.radioGroup.setVisibility(View.VISIBLE);
                holder.radioGroup.removeAllViews();

                //disable other views
                holder.etName.setVisibility(View.GONE);
                holder.tvName.setVisibility(View.GONE);
                holder.linearLayout.setVisibility(View.GONE);
                holder.llvImageUpload.setVisibility(View.GONE);

                RadioGroup radioGroup = holder.itemView.findViewById(R.id.radioGrp);
                RadioButton rb;
                radioGroup.setOrientation(RadioGroup.VERTICAL); //or RadioGroup.VERTICAL

                if (field.getValues() != null && field.getValues().size() != 0){
                    for (int i = 0; i < field.getValues().size(); i++) {
                        rb = new RadioButton(mContext);
                        rb.setText("Radio " + field.getValues().get(i).getName());
                        rb.setId(field.getValues().get(i).getMax());
                        RadioButton finalRb = rb;
                        int finalI = i;
                        rb.setOnCheckedChangeListener((compoundButton, b) -> {
                            if (b){
                                fieldsModel.getFields().get(position).setSetSelectedRadioButton(field.getValues().get(finalI).getMax());
                            }
                        });
                        radioGroup.addView(rb);
                    }
                }

                break;
            case "CHECKBOX":
                holder.linearLayout.setVisibility(View.VISIBLE);
                holder.linearLayout.setOrientation(LinearLayout.VERTICAL);
                holder.linearLayout.removeAllViews();

                //disable other views
                holder.etName.setVisibility(View.GONE);
                holder.tvName.setVisibility(View.GONE);
                holder.radioGroup.setVisibility(View.GONE);
                holder.llvImageUpload.setVisibility(View.GONE);


                if (field.getValues() != null && field.getValues().size() != 0){
                    List<Field> fields = field.getValues();
                    for (Field values: fields) {
                        final CheckBox ch = new CheckBox(mContext);
                        ch.setId(0);
                        ch.setText("Cheers !! " + values.getName());
                        ch.setOnCheckedChangeListener((compoundButton, b) -> {

                        });
                        holder.linearLayout.addView(ch);
                    }
                }

                break;
            case "UPLOAD_IMAGE":
                holder.llvImageUpload.setVisibility(View.VISIBLE);

                //disable other views
                holder.etName.setVisibility(View.GONE);
                holder.tvName.setVisibility(View.GONE);
                holder.radioGroup.setVisibility(View.GONE);
                holder.linearLayout.setVisibility(View.GONE);

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
        return fieldsModel.getFields().size();
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
