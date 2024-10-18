package com.example.itemlist.network;
import com.example.itemlist.Dto.Category;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesApi {
    @GET("/get")
    Call<List<Category>> categoriesList();
}
