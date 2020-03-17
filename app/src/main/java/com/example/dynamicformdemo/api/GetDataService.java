package com.example.dynamicformdemo.api;

import com.example.dynamicformdemo.Field;
import com.example.dynamicformdemo.FieldsModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("bins/gpuki")
    Call<FieldsModel> getAllJson();
}
