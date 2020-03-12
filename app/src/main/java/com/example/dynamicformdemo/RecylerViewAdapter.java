package com.example.dynamicformdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gipl.imagepicker.ImageResult;

import java.util.ArrayList;
import java.util.List;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.MyViewHolder> {

    private ArrayList<Field> mDataset;
    private Context mContext;
    private ModelForm modelForm = new ModelForm();
    private ArrayList<Field> childListItems = new ArrayList<>();
    private  ChildAdapter adapter;

    private iOnClickListener onClickListener;
    private ImageAdapter imageAdapter;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecylerViewAdapter(ArrayList<Field> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Field field = mDataset.get(position);

        /*holder.recylerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ChildAdapter(mDataset,mContext);
        holder.recylerView.setAdapter(adapter);*/

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        holder.llvContainer.removeAllViews();

        if (field.getType().equalsIgnoreCase("STRING")) {
            View view = inflater.inflate(R.layout.layout_tv_label, null);
            TextView tvName = view.findViewById(R.id.tv_name);
            tvName.setText(field.getName());
            holder.llvContainer.addView(view, 0);
        } else if (field.getType().equalsIgnoreCase("TEXT")) {
            View view = inflater.inflate(R.layout.layout_edit, null);
            EditText editText = view.findViewById(R.id.et);
            editText.setText("");
            editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            holder.llvContainer.addView(view, 0);
        } else if (field.getType().equalsIgnoreCase("RADIO")) {
            View view = inflater.inflate(R.layout.layout_radiogroup, null);
            RadioGroup radioGroup = view.findViewById(R.id.radioGrp);
           /*    RadioButton[] rb = new RadioButton[5];
            radioGroup.setOrientation(RadioGroup.HORIZONTAL); //or RadioGroup.VERTICAL
            for (int i = 0; i < 3; i++) {
                rb[i] = new RadioButton(mContext);
                rb[i].setText("Position" + i);
                rb[i].setId(i + 100);
                radioGroup.addView(rb[i]);
            }
            holder.llvContainer.addView(view, 0);*/

            RadioButton rb;
            radioGroup.setOrientation(RadioGroup.VERTICAL); //or RadioGroup.VERTICAL

            if (field.getValues() != null && field.getValues().size() != 0){
                for (Field values: field.getValues()) {
                    rb= new RadioButton(mContext);
                    rb.setText("Radio " + values.getName());
                    rb.setId(values.getMax());
                    radioGroup.addView(rb);
                }
                holder.llvContainer.addView(view, 0);
            }


        }else if (field.getType().equalsIgnoreCase("CHECKBOX")){
            View view = inflater.inflate(R.layout.layout_checkbox, null);
            LinearLayout linearLayout = view.findViewById(R.id.checkbox);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            if (field.getValues() != null && field.getValues().size() != 0){
                List<Field> fields = field.getValues();
                for (Field values: fields) {
                    final CheckBox ch = new CheckBox(mContext);
                    ch.setId(0);
                    ch.setText("Cheers !! " + values.getName());
                    ch.setOnCheckedChangeListener((compoundButton, b) -> {

                    });
                    linearLayout.addView(ch);
                }
            }

            holder.llvContainer.addView(linearLayout, 0);

           /* for (int i = 0; i < 5; i++) {
                final CheckBox ch = new CheckBox(mContext);
                ch.setId(i);
                ch.setText("Cheers !! "+i);
                ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    }
                });
                linearLayout.addView(ch);
            }
            holder.llvContainer.addView(linearLayout, 0);*/
        }else if (field.getType().equalsIgnoreCase("UPLOAD_IMAGE")){
            View view = inflater.inflate(R.layout.layout_upload_image, null);
            LinearLayout linearLayout = view.findViewById(R.id.llvImage);
            TextView button = linearLayout.findViewById(R.id.btn_upload);
            RecyclerView recyclerView = linearLayout.findViewById(R.id.rv_images);

            imageAdapter = new ImageAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(imageAdapter);

            button.setOnClickListener(view1 -> {
                // Open custom dialog with icons.
                onClickListener.onImageUpload(position);
            });

            holder.llvContainer.addView(view, 0);
        }
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ConstraintLayout llvContainer;
        public RecyclerView recylerView;
        public MyViewHolder(View v) {
            super(v);
            //recylerView = itemView.findViewById(R.id.rv_child_list);
            llvContainer = itemView.findViewById(R.id.insert_point);
        }
    }


    public void setSelectedImage(ArrayList<ImageResult> imageResults, int pos) {
        imageAdapter.addAll(imageResults);
    }


    public iOnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(iOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    interface iOnClickListener {
        void onImageUpload(int position);
    }


    private void prepareRecylerView(){

    }
}

