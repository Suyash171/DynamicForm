package com.example.dynamicformdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gipl.imagepicker.ImagePickerDialog;
import com.gipl.imagepicker.ImageResult;
import com.gipl.imagepicker.PickerConfiguration;
import com.gipl.imagepicker.PickerListener;
import com.gipl.imagepicker.PickerResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChildAdapter.iOnClickListener {

    private TextView name;
    private RecyclerView recyclerView;
    private ChildAdapter adapter;
    private ArrayList<Field> dataset = new ArrayList<>();
    private PickerConfiguration pickerConfiguration;
    private ImagePickerDialog imagePickerDialog;
    private int selectedImageViewPosition;

    String jsonString = "{\"name\":\"Mahesh\", \"type\":1}";
    //String dummyForm = "[{\"name\":\"fieldA\",\"type\":\"STRING\",\"minCharacters\":10,\"maxCharacters\":100},{\"name\":\"fieldB\",\"type\":\"INTEGER\",\"min\":10,\"max\":100},{\"name\":\"fieldC\",\"type\":\"BOOLEAN_CHECKBOX\",\"defaultValue\":true}]";
    //String dummyForm = "[{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldA\",\"type\":\"RADIO\",\"minCharacters\":10,\"maxCharacters\":100},{\"name\":\"fieldB\",\"type\":\"TEXT\",\"min\":10,\"max\":100},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true}]";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldA\",\"type\":\"RADIO\",\"minCharacters\":10,\"maxCharacters\":100},{\"name\":\"fieldB\",\"type\":\"TEXT\",\"min\":10,\"max\":100},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";


    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";
    String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":101,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":102,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":1,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":2,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":7,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":8,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChildAdapter(dataset,this);
        adapter.setiOnClickListener(this);
        recyclerView.setAdapter(adapter);

        createPickerConfiguration();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        //LinearLayout lv = findViewById(R.id.lv);

        Gson gson = builder.create();
       // Model model = gson.fromJson(dummyForm, Model.class);
        //List<FieldsModel> objects = toList(dummyForm, FieldsModel.class);
        FieldsModel model = gson.fromJson(dummyForm,FieldsModel.class);

       // ArrayList<FieldsModel> playersList= (ArrayList<FieldsModel>) fromJson(dummyForm, new TypeToken<ArrayList<Field>>() {}.getType());

        if (model != null){
            dataset.addAll(model.getFields());
        }
    }



    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (imagePickerDialog != null)
            imagePickerDialog.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePickerDialog.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (null == json) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<T>(){}.getType());
    }

    @Override
    public void onImageUpload(int position) {
        selectedImageViewPosition = position;
        imagePickerDialog = ImagePickerDialog.display(getSupportFragmentManager(), pickerConfiguration);
    }

    private void createPickerConfiguration(){
        pickerConfiguration = PickerConfiguration.build()
                .setTextColor(Color.parseColor("#000000"))
                .setIconColor(Color.parseColor("#000000"))
                .setBackGroundColor(Color.parseColor("#ffffff"))
                .setIsDialogCancelable(false)
                .enableMultiSelect(true)
                .setMultiSelectImageCount(6)
                .setPickerDialogListener(new PickerListener() {
                    @Override
                    public void onCancelClick() {
                        super.onCancelClick();
                    }
                })
                .setImagePickerResult(new PickerResult() {
                    @Override
                    public void onImageGet(ImageResult imageResult) {
                        super.onImageGet(imageResult);
                    }

                    @Override
                    public void onReceiveImageList(ArrayList<ImageResult> sFilePath) {
                        super.onReceiveImageList(sFilePath);
                        adapter.setSelectedImage(sFilePath,selectedImageViewPosition);
                    }
                })
                .setSetCustomDialog(true);

    }

}
