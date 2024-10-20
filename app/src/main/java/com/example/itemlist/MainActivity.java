package com.example.itemlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.itemlist.Dto.Category;
import com.example.itemlist.activities.CreateCategoryActivity;
import com.example.itemlist.category.CategoryAdapter;
import com.example.itemlist.constants.Urls;
import com.example.itemlist.services.ApplicationNetwork;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "--Activity Main LOG--";
    private RecyclerView categoryListView;
    private CategoryAdapter adapter;
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.i(TAG, "Run MAIN");
        categoryListView = findViewById(R.id.itemList);
        categoryListView.setLayoutManager(new GridLayoutManager(this, 2));
        LoadCategories();

        Button addButton = findViewById(R.id.new_category_button);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateCategoryActivity.class);
            startActivityLauncher.launch(intent);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private final ActivityResultLauncher<Intent> startActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK ) {
                    LoadCategories();
                    Toast.makeText(this, "Категорія успішно збережена", Toast.LENGTH_SHORT).show();
                }
                else if(result.getResultCode() != RESULT_CANCELED){
                    new AlertDialog.Builder(this)
                            .setTitle("Помилка")
                            .setMessage("Помилка збереження категорії" )
                            .setPositiveButton("ОК", (dialog, which) -> {
                               dialog.cancel();
                            })
                           .show();
                }
            });


    private void LoadCategories(){
        ApplicationNetwork.getInstance()
                .getCategoriesApi()
                .categoriesList().enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        if(response.isSuccessful()) {
                            categoryList = response.body();
                            adapter = new CategoryAdapter(categoryList);
                            categoryListView.setAdapter(adapter);
                            Log.d(TAG,Integer.toString(categoryList.size()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable throwable) {

                    }
                });
    }


}