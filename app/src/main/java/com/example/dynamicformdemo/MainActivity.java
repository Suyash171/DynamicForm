package com.example.dynamicformdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView name;
    private RecyclerView recyclerView;
    private RecylerViewAdapter adapter;
    private ArrayList<Field> dataset = new ArrayList<>();

    String jsonString = "{\"name\":\"Mahesh\", \"type\":1}";
    //String dummyForm = "[{\"name\":\"fieldA\",\"type\":\"STRING\",\"minCharacters\":10,\"maxCharacters\":100},{\"name\":\"fieldB\",\"type\":\"INTEGER\",\"min\":10,\"max\":100},{\"name\":\"fieldC\",\"type\":\"BOOLEAN_CHECKBOX\",\"defaultValue\":true}]";
    String dummyForm = "[{\"name\":\"fieldC\",\"type\":\"STRING\",\"defaultValue\":true},{\"name\":\"fieldA\",\"type\":\"RADIO\",\"minCharacters\":10,\"maxCharacters\":100},{\"name\":\"fieldB\",\"type\":\"TEXT\",\"min\":10,\"max\":100},{\"name\":\"fieldC\",\"type\":\"CHECKBOX\",\"defaultValue\":true}]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecylerViewAdapter(dataset,this);
        recyclerView.setAdapter(adapter);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        //LinearLayout lv = findViewById(R.id.lv);

        Gson gson = builder.create();
       // Model model = gson.fromJson(dummyForm, Model.class);
        //List<FieldsModel> objects = toList(dummyForm, FieldsModel.class);

        ArrayList<Field> playersList= (ArrayList<Field>) fromJson(dummyForm, new TypeToken<ArrayList<Field>>() {}.getType());

        if (playersList != null && playersList.size() != 0){
            dataset.addAll(playersList);
        }
    }


    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }



    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (null == json) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<T>(){}.getType());
    }
}
