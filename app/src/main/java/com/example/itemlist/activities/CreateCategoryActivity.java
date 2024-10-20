package com.example.itemlist.activities;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.itemlist.Dto.Category;
import com.example.itemlist.R;
import com.example.itemlist.services.ApplicationNetwork;

import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCategoryActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView categoryImage;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText categoryName = findViewById(R.id.category_name);
        EditText description = findViewById(R.id.description);
        Button loadPhotoButton = findViewById(R.id.load_photo_button);
        categoryImage = findViewById(R.id.category_image);
        Button saveButton = findViewById(R.id.save_button);

        loadPhotoButton.setOnClickListener(view -> getImage.launch("image/*"));

        saveButton.setOnClickListener(view -> {
            String name = categoryName.getText().toString();
            if (name.isEmpty()) {
                categoryName.setError("Введіть назву");
                return;
            } else if (name.length() < 3 || name.length() > 255 ) {
                categoryName.setError("Назва повинна мати від 3 до 255 літер...");
                return;
            }
            String descriptionText = description.getText().toString();
            if(!descriptionText.isEmpty()){
                if (descriptionText.length() < 3 || descriptionText.length() > 4000 ) {
                    categoryName.setError("Опис повинен мати від 3 до 4000 літер...");
                    return;
                }
            }
            // Створення RequestBody для файлу
            MultipartBody.Part filePart = null;
            if(imageUri != null){
                File imageFile = new File(getRealPathFromURI(this,imageUri));
                RequestBody fileRequestBody = RequestBody.create( imageFile,MediaType.parse("image/jpeg"));
                filePart = MultipartBody.Part.createFormData("imageFile", imageFile.getName(), fileRequestBody);
            }
            // Створення RequestBody для текстових полів
            RequestBody idRequestBody = RequestBody.create(String.valueOf(0),MediaType.parse("text/plain"));
            RequestBody nameRequestBody = RequestBody.create(name,MediaType.parse("text/plain"));
            RequestBody descriptionRequestBody = RequestBody.create(descriptionText,MediaType.parse("text/plain"));

            ApplicationNetwork.getInstance()
                    .getCategoriesApi()
                    .create(filePart,nameRequestBody,descriptionRequestBody,idRequestBody)
                    .enqueue(new Callback<Category>() {
                        @Override
                        public void onResponse(Call<Category> call, Response<Category> response) {
                            setResult(RESULT_OK);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Category> call, Throwable throwable) {
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                    });


        });
    }
    public String getRealPathFromURI(Context context, Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String realPath = cursor.getString(columnIndex);
            cursor.close();
            return realPath;
        }
        return null;
    }

    private final ActivityResultLauncher<String> getImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    imageUri = uri;
                    //
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        categoryImage.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
}