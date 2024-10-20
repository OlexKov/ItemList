package com.example.itemlist.network;
import com.example.itemlist.Dto.Category;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CategoriesApi {
    @GET("/get")
    Call<List<Category>> categoriesList();

    @Multipart
    @POST("create")
    Call<Category> create(
            @Part MultipartBody.Part imageFile,
            @Part("name") RequestBody name,
            @Part("description") RequestBody description,
            @Part("id") RequestBody id
    );
}
