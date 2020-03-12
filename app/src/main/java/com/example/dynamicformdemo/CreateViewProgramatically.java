package com.example.dynamicformdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class CreateViewProgramatically extends AppCompatActivity {

    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";
    String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":101,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":102,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":1,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":2,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":7,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":8,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";

    private LinearLayout linearLayout;
    private LayoutInflater layoutInflater;
    private ImageAdapter imageAdapter;
    private onSetImagesListener onSetImagesListener;

    public static void start(Context context) {
        Intent intent = new Intent(context, CreateViewProgramatically.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_programatically);

        linearLayout = findViewById(R.id.lllvRoot);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        FieldsModel model = gson.fromJson(dummyForm, FieldsModel.class);
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setRecylerData((ArrayList<Field>) model.getFields());

    }

    private void setRecylerData(ArrayList<Field> model) {
        for (Field field : model) {

            switch (field.getType()) {
                case "STRING":
                    View view = layoutInflater.inflate(R.layout.layout_tv_label, null);
                    TextView tvName = view.findViewById(R.id.tv_name);
                    tvName.setText(field.getName());
                    linearLayout.addView(view, 0);
                    break;
                case "TEXT":

                    View edit = layoutInflater.inflate(R.layout.layout_edit, null);
                    EditText editText = edit.findViewById(R.id.et);
                    editText.setText("");
                    editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    linearLayout.addView(edit, 0);
                    break;
                case "RADIO":
                    View radio = layoutInflater.inflate(R.layout.layout_radiogroup, null);
                    RadioGroup radioGroup = radio.findViewById(R.id.radioGrp);
                    RadioButton rb;
                    radioGroup.setOrientation(RadioGroup.VERTICAL); //or RadioGroup.VERTICAL

                    if (field.getValues() != null && field.getValues().size() != 0){
                        for (Field values: field.getValues()) {
                            rb= new RadioButton(this);
                            rb.setText("Radio " + values.getName());
                            rb.setId(values.getMax());
                            radioGroup.addView(rb);
                        }
                        linearLayout.addView(radio, 0);
                    }
                    break;
                case "CHECKBOX":

                    View check = layoutInflater.inflate(R.layout.layout_checkbox, null);
                    LinearLayout llvCheck = check.findViewById(R.id.checkbox);
                    llvCheck.setOrientation(LinearLayout.VERTICAL);

                    if (field.getValues() != null && field.getValues().size() != 0){
                        List<Field> fields = field.getValues();
                        for (Field values: fields) {
                            final CheckBox ch = new CheckBox(this);
                            ch.setId(0);
                            ch.setText("Cheers !! " + values.getName());
                            ch.setOnCheckedChangeListener((compoundButton, b) -> {

                            });
                            linearLayout.addView(ch);
                        }
                    }

                    break;
                case "UPLOAD_IMAGE":
                    View image = layoutInflater.inflate(R.layout.layout_upload_image, null);
                    LinearLayout llvImage = image.findViewById(R.id.llvImage);
                    TextView button = llvImage.findViewById(R.id.btn_upload);
                    RecyclerView recyclerView = llvImage.findViewById(R.id.rv_images);

                    imageAdapter = new ImageAdapter();
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(imageAdapter);

                    button.setOnClickListener(view1 -> {
                        // Open custom dialog with icons.

                    });

                    linearLayout.addView(image, 0);

                    break;
                default:
                    break;
            }
        }
    }


    interface onSetImagesListener {
        void onResetImages(int position);
    }


}
