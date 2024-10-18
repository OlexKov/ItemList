package com.example.itemlist.activities;

import android.content.Intent;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.itemlist.Dto.Category;
import com.example.itemlist.R;

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
        Button loadPhotoButton = findViewById(R.id.load_photo_button);
        categoryImage = findViewById(R.id.category_image);
        Button saveButton = findViewById(R.id.save_button);

        loadPhotoButton.setOnClickListener(view -> getImage.launch("image/*"));

        saveButton.setOnClickListener(view -> {
            String name = categoryName.getText().toString();
            if (name.isEmpty()) {
                categoryName.setError("Введіть назву");
                return;
            } else if (imageUri == null) {
                // Вивести повідомлення про необхідність вибору фото
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("category", new Category(0,imageUri.toString(),name) );
            setResult(RESULT_OK,intent);
            finish();
        });
    }


    private final ActivityResultLauncher<String> getImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    imageUri = uri;
                    // Встановлення вибраного фото в ImageView
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        categoryImage.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
}