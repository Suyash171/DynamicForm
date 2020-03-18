package com.example.dynamicformdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dynamicformdemo.api.GetDataService;
import com.example.dynamicformdemo.api.RetrofitClientInstance;
import com.example.dynamicformdemo.viewpager.NewFormModel;
import com.example.dynamicformdemo.viewpager.ParentRecylerViewItem;
import com.gipl.imagepicker.ImagePickerDialog;
import com.gipl.imagepicker.ImageResult;
import com.gipl.imagepicker.PickerConfiguration;
import com.gipl.imagepicker.PickerListener;
import com.gipl.imagepicker.PickerResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ParentRecylerViewItem parentRecylerViewItem;

    private static final int defaultStep = 5;
    private int moveCounter;


    String jsonString = "{\"name\":\"Mahesh\", \"type\":1}";
    //String dummyForm = "[{\"name\":\"fieldA\",\"type\":\"STRING\",\"minCharacters\":10,\"maxCharacters\":100},{\"name\":\"fieldB\",\"type\":\"INTEGER\",\"min\":10,\"max\":100},{\"name\":\"fieldC\",\"type\":\"BOOLEAN_CHECKBOX\",\"defaultValue\":true}]";
    //String dummyForm = "[{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldA\",\"type\":\"RADIO\",\"minCharacters\":10,\"maxCharacters\":100},{\"name\":\"fieldB\",\"type\":\"TEXT\",\"min\":10,\"max\":100},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true}]";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldA\",\"type\":\"RADIO\",\"minCharacters\":10,\"maxCharacters\":100},{\"name\":\"fieldB\",\"type\":\"TEXT\",\"min\":10,\"max\":100},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";


    // String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";
    // String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":101,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":102,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":1,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":2,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":7,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":8,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true}]}";
    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":101,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":102,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"radio 1\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"radio 2\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true}]}";


    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":false},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":false,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":101,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":102,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"radio 1\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"radio 2\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]},{\"name\":\"Name\",\"type\":\"TEXT\",\"defaultValue\":true,\"componentType\":\"NAME\",\"emailvalidation\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":false},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":false},{\"name\":\"Email\",\"type\":\"TEXT\",\"defaultValue\":true,\"componentType\":\"EMAIL\",\"emailvalidation\":true}]}";


    String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":false},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":false,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":101,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":102,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"radio 1\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"radio 2\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]},{\"name\":\"Name\",\"type\":\"TEXT\",\"defaultValue\":true,\"componentType\":\"NAME\",\"emailvalidation\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":false},{\"name\":\"Email\",\"type\":\"TEXT\",\"defaultValue\":true,\"componentType\":\"EMAIL\",\"emailvalidation\":true},{\"name\":\"Who are you ?\",\"type\":\"QUESTIONS\",\"defaultValue\":true,\"values\":[{\"name\":\"Male\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"Female\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false},{\"name\":\"Don't know\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"Why should I tell you\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"Email\",\"type\":\"TEXT\",\"defaultValue\":true,\"componentType\":\"EMAIL\",\"emailvalidation\":true},{\"name\":\"Is Corona at your place ?\",\"type\":\"QUESTIONS\",\"defaultValue\":true,\"values\":[{\"name\":\"Yes\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"No\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false},{\"name\":\"Don't know\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false}]},{\"name\":\"Label\",\"type\":\"STRING\",\"defaultValue\":true,\"componentType\":\"NAME\",\"emailvalidation\":true},{\"name\":\"Select your language?\",\"type\":\"QUESTIONS\",\"defaultValue\":true,\"values\":[{\"name\":\"English\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"HINDI\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false},{\"name\":\"MARATHI\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false}]},{\"name\":\"Name\",\"type\":\"TEXT\",\"defaultValue\":false,\"componentType\":\"NAME\",\"emailvalidation\":false},{\"name\":\"Do you like this form?\",\"type\":\"QUESTIONS\",\"defaultValue\":true,\"values\":[{\"name\":\"YES\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"NO\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]}]}";


    //String dummyForm = "{\"players\":[{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":101,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":102,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":103,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":104,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":3,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":4,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"UPLOAD_IMAGE\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":1,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":2,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"TEXT\",\"defaultValue\":true},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true,\"values\":[{\"name\":\"check 1\",\"type\":\"STRING\",\"max\":105,\"defaultValue\":false},{\"name\":\"check 2\",\"type\":\"STRING\",\"max\":106,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"RADIO\",\"defaultValue\":true,\"values\":[{\"name\":\"Radio 1\",\"type\":\"STRING\",\"max\":7,\"defaultValue\":false},{\"name\":\"Radio 2\",\"type\":\"STRING\",\"max\":8,\"defaultValue\":false}]},{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true}]}";
    ProgressDialog progressDoalog;

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

       // addData();

        if (isNetworkConnected(this)) {
            getJsonFromServer();
        }else {
            addData();
        }

        //sortAndCreateParentAdapter();
        createPickerConfiguration();

        btnSubmit.setOnClickListener(view -> {
            checkValidations(form.getFielsModel());
        });
    }

    private void prepareAdapter(){
        if (isInflator) {
            adapter = new RecylerViewAdapter(this);
            adapter.setOnClickListener(this);
            recyclerView.setAdapter(adapter);
        } else {
            childAdapter = new ChildAdapter(this);
            childAdapter.setiOnClickListener(this);
            recyclerView.setAdapter(childAdapter);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortAndCreateParentAdapter(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FieldsModel model = gson.fromJson(dummyForm, FieldsModel.class);
        NewFormModel newFormModel = new NewFormModel();

        if (model != null){
            final AtomicInteger counter = new AtomicInteger(0);
            final int size = 2;

            Collection<List<Field>> partitioned = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                partitioned = model.getFields().stream()
                        .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                        .values();
            }
            partitioned.forEach(System.out::println);
            List theList = new ArrayList(partitioned);
            newFormModel.setParentList(theList);
            parentRecylerViewItem.addAll((ArrayList<Field>) newFormModel.getParentList());
            //Log.d("You Value is  :-" , partitioned.forEach(System.out::println));
        }

    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }


    /**
     *
     */
    private void addData(){
        prepareAdapter();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        //LinearLayout lv = findViewById(R.id.lv);

        Gson gson = builder.create();
        //Model model = gson.fromJson(dummyForm, Model.class);
        //List<FieldsModel> objects = toList(dummyForm, FieldsModel.class);
        FieldsModel model = gson.fromJson(dummyForm, FieldsModel.class);

        // ArrayList<FieldsModel> playersList= (ArrayList<FieldsModel>) fromJson(dummyForm, new TypeToken<ArrayList<Field>>() {}.getType());
        addDataToAdapters(model);
    }

    private void addDataToAdapters(FieldsModel fieldsModel){
        if (fieldsModel != null) {
            form.setFielsModel((ArrayList<Field>) fieldsModel.getFields());
            if (isInflator) {
                adapter.addAll(form.getFielsModel());
            } else {
                childAdapter.addAll(form.getFielsModel());
            }
        }
    }

    /**
     *
     */
    private void getJsonFromServer() {
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<FieldsModel> call = service.getAllJson();
        call.enqueue(new Callback<FieldsModel>() {
            @Override
            public void onResponse(Call<FieldsModel> call, Response<FieldsModel> response) {
                progressDoalog.dismiss();
                FieldsModel fieldsModel = response.body();
                if (fieldsModel != null) {
                    prepareAdapter();
                    addDataToAdapters(fieldsModel);
                }
            }
            @Override
            public void onFailure(Call<FieldsModel> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MainActivity.this, "Exception " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * @param fields
     */
    private void checkValidations(ArrayList<Field> fields) {

        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            if (field.getDefaultValue()) {
                //check if edit text
                if (field.getType().equalsIgnoreCase("TEXT")) {
                    //check component type
                    if (field.getComponentType().equalsIgnoreCase("NAME")) {
                        if (field.getEnteredValue() == null || field.getEnteredValue().isEmpty()) {
                            //Toast.makeText(this, "Please enter " + field.getName(), Toast.LENGTH_SHORT).show();
                            field.setErrorPosition(i);
                            field.setSetError("Please enter " + field.getName());
                            adapter.notifyItemChanged(i);
                            recyclerView.scrollToPosition(i);
                            return;
                        } else {
                            field.setErrorPosition(-1);
                        }
                    } else if (field.getComponentType().equalsIgnoreCase("EMAIL")) {
                        if (field.getEmailvalidation()) {
                            boolean isValid = isValidEmail(field.getEnteredValue());
                            if (!isValid) {
                                // Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                                field.setErrorPosition(i);
                                field.setSetError("Please enter valid email");
                                adapter.notifyItemChanged(i);
                                recyclerView.scrollToPosition(i);
                                return;
                            } else {
                                field.setErrorPosition(-1);
                            }
                        }
                    }
                } else if (field.getType().equalsIgnoreCase("RADIO")) {
                    if (field.getValues() != null && field.getValues().size() != 0) {
                        if (field.getSetSelectedRadioButton() == null) {
                            Toast.makeText(this, "Please selected Radio ", Toast.LENGTH_SHORT).show();
                            recyclerView.scrollToPosition(i);
                            return;
                        }
                    }
                }
            }
        }
    }

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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
}
