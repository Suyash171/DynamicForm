package com.example.dynamicformdemo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.MyViewHolder> {

    private ArrayList<Field> mDataset;
    private Context mContext;
    private ModelForm modelForm = new ModelForm();
    private ArrayList<Field> childListItems = new ArrayList<>();
    private ChildAdapter adapter;

    private iOnClickListener onClickListener;
    private ImageAdapter imageAdapter;
    private FieldsModel fieldsModel = new FieldsModel();

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecylerViewAdapter(Context context) {
        //mDataset = myDataset;
        mContext = context;
    }

    public void addAll(ArrayList<Field> fields) {
        fieldsModel.setFields(fields);
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
        Field field = fieldsModel.getFields().get(position);

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

            if (field.getEnteredValue() != null){
                editText.setText(field.getEnteredValue());
                editText.setError(null);
            }else {
                editText.setText("");
                editText.setError(null);
            }

            if (field.getErrorPosition() != -1){
                editText.setError(field.getSetError());
            }else {
                editText.setError(null);
            }

            //update text entered
            editText.addTextChangedListener(new TextWatcher() {
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

            editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            holder.llvContainer.addView(view, 0);
        } else if (field.getType().equalsIgnoreCase("RADIO")) {
            View view = inflater.inflate(R.layout.layout_radiogroup, null);
            RadioGroup radioGroup = view.findViewById(R.id.radioGrp);

            RadioButton rb;
            radioGroup.setOrientation(RadioGroup.VERTICAL); //or RadioGroup.VERTICAL

            if (field.getValues() != null && field.getValues().size() != 0) {
                for (int i = 0; i < field.getValues().size(); i++) {
                    rb = new RadioButton(mContext);
                    rb.setText("Radio " + field.getValues().get(i).getName());
                    rb.setId(field.getValues().get(i).getMax());
                    int finalI = i;
                    rb.setOnCheckedChangeListener((compoundButton, b) -> {
                        if (b) {
                            fieldsModel.getFields().get(position).setSetSelectedRadioButton(field.getValues().get(finalI).getMax());
                        }
                    });
                    radioGroup.addView(rb);
                }
                holder.llvContainer.addView(view, 0);
            }
        } else if (field.getType().equalsIgnoreCase("CHECKBOX")) {
            View view = inflater.inflate(R.layout.layout_checkbox, null);
            LinearLayout linearLayout = view.findViewById(R.id.checkbox);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            if (field.getValues() != null && field.getValues().size() != 0) {
                List<Field> fields = field.getValues();
                for (Field values : fields) {
                    final CheckBox ch = new CheckBox(mContext);
                    ch.setId(0);
                    ch.setText("Cheers !! " + values.getName());
                    ch.setOnCheckedChangeListener((compoundButton, b) -> {

                    });
                    linearLayout.addView(ch);
                }
            }

            holder.llvContainer.addView(linearLayout, 0);

        } else if (field.getType().equalsIgnoreCase("UPLOAD_IMAGE")) {
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
        }else if (field.getType().equalsIgnoreCase("QUESTIONS")){
            View questions = inflater.inflate(R.layout.layout_multichoice,null);
            ConstraintLayout layout =  questions.findViewById(R.id.quesRoot);
            RecyclerView rvQuestion = questions.findViewById(R.id.rvList);
            TextView tvQuestion = questions.findViewById(R.id.tvQuestion);

            tvQuestion.setText(field.getName());

            SingleItemAdapter singleItemAdapter = new SingleItemAdapter();
            rvQuestion.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            singleItemAdapter.addAll((ArrayList<Field>) field.getValues());
            rvQuestion.setAdapter(singleItemAdapter);

            holder.llvContainer.addView(questions, 0);
        }
    }


    @Override
    public int getItemCount() {
        return fieldsModel.getFields().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout llvContainer;
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


    private void prepareRecylerView() {

    }
}

