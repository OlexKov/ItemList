package com.example.itemlist.services;
import com.example.itemlist.models.Item;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ItemService {
    @GET("get")
    Call<List<Item>> getItems();
}
