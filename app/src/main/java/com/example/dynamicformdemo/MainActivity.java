package com.example.dynamicformdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements ChildAdapter.iOnClickListener, RecylerViewAdapter.iOnClickListener {

    private TextView name;
    private RecyclerView recyclerView;
    private RecylerViewAdapter adapter;
    private ChildAdapter childAdapter;
    private ArrayList<Field> dataset = new ArrayList<>();
    private PickerConfiguration pickerConfiguration;
    private ImagePickerDialog imagePickerDialog;
    private int selectedImageViewPosition;
    private boolean isInflator = false;
    private Button btnSubmit;
    private ModelForm form = new ModelForm();
    String jsonString = "{\"name\":\"Mahesh\", \"type\":1}";
    //String dummyForm = "[{\"name\":\"fieldA\",\"type\":\"STRING\",\"minCharacters\":10,\"maxCharacters\":100},{\"name\":\"fieldB\",\"type\":\"INTEGER\",\"min\":10,\"max\":100},{\"name\":\"fieldC\",\"type\":\"BOOLEAN_CHECKBOX\",\"defaultValue\":true}]";
    //String dummyForm = "[{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldA\",\"type\":\"RADIO\",\"minCharacters\":10,\"maxCharacters\":100},{\"name\":\"fieldB\",\"type\":\"TEXT\",\"min\":10,\"max\":100},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true}]";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldA\",\"type\":\"RADIO\",\"minCharacters\":10,\"maxCharacters\":100},{\"name\":\"fieldB\",\"type\":\"TEXT\",\"min\":10,\"max\":100},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";


    // String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";
    // String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":101,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":102,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":1,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":2,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":7,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":8,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true}]}";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":101,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":102,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"radio 1\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"radio 2\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true}]}";
    String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":false,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":101,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":102,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":false},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":false,\"values\":[{\"name\":\"radio 1\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"radio 2\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]},{\"name\":\"Name\",\"type\":\"TEXT\",\"defaultValue\":true,\"componentType\":\"NAME\",\"emailvalidation\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":false},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":false},{\"name\":\"Email\",\"type\":\"TEXT\",\"defaultValue\":true,\"componentType\":\"EMAIL\",\"emailvalidation\":true}]}";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":101,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":102,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":1,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":2,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":7,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":8,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";

    public static void start(Context context, boolean isInflator) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("KEY_TAG", isInflator);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnSubmit = findViewById(R.id.btn_submit);

        isInflator = getIntent().getBooleanExtra("KEY_TAG", false);

        if (isInflator) {
            adapter = new RecylerViewAdapter(form.getFielsModel(), this);
            adapter.setOnClickListener(this);
            recyclerView.setAdapter(adapter);
        } else {
            childAdapter = new ChildAdapter(this);
            childAdapter.setiOnClickListener(this);
            recyclerView.setAdapter(childAdapter);
        }

        createPickerConfiguration();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        //LinearLayout lv = findViewById(R.id.lv);

        Gson gson = builder.create();
        //Model model = gson.fromJson(dummyForm, Model.class);
        //List<FieldsModel> objects = toList(dummyForm, FieldsModel.class);
        FieldsModel model = gson.fromJson(dummyForm, FieldsModel.class);

        // ArrayList<FieldsModel> playersList= (ArrayList<FieldsModel>) fromJson(dummyForm, new TypeToken<ArrayList<Field>>() {}.getType());

        if (model != null) {
         /*   int count = 0;
            for (Field field : model.getFields()) {
                if (count <= 10){
                    dataset.add(field);
                    count++;
                }else {
                    count = 0;
                    return;
                }
            }*/
            //FieldsSingleTon.getInstance().setFieldArrayList((ArrayList<Field>) model.getFields());
            form.setFielsModel((ArrayList<Field>) model.getFields());
            childAdapter.addAll(form.getFielsModel());
        }

        btnSubmit.setOnClickListener(view -> {
            checkValidations(form.getFielsModel());
        });
    }


    /**
     * @param fields
     */
    private void checkValidations(ArrayList<Field> fields) {
        for (Field field : fields) {
            if (field.getDefaultValue()) {
                //check if edit text
                if (field.getType().equalsIgnoreCase("TEXT")) {
                    //check component type
                    if (field.getComponentType().equalsIgnoreCase("NAME")) {
                        if (field.getEnteredValue() == null || field.getEnteredValue().isEmpty()) {
                            Toast.makeText(this, "Please enter " + field.getName(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else if (field.getComponentType().equalsIgnoreCase("EMAIL")) {
                        if (field.getEmailvalidation()) {
                            boolean isValid = isValidEmail(field.getEnteredValue());
                            if (!isValid) {
                                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                } else if (field.getType().equalsIgnoreCase("RADIO")) {

                }
            }
        }
    }


    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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
        return gson.fromJson(json, new TypeToken<T>() {
        }.getType());
    }

    @Override
    public void onImageUpload(int position) {
        selectedImageViewPosition = position;
        imagePickerDialog = ImagePickerDialog.display(getSupportFragmentManager(), pickerConfiguration);
    }

    private void createPickerConfiguration() {
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
                        if (isInflator) {
                            adapter.setSelectedImage(sFilePath, selectedImageViewPosition);
                        } else {
                            childAdapter.setSelectedImage(sFilePath, selectedImageViewPosition);
                        }

                    }
                })
                .setSetCustomDialog(true);

    }


    /**
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}
