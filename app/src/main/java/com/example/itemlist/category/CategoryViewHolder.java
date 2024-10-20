package com.example.itemlist.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemlist.R;

public  class CategoryViewHolder extends RecyclerView.ViewHolder {

    ImageView categoryImage;
    TextView categoryName;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryImage = itemView.findViewById(R.id.itemImage);
        categoryName = itemView.findViewById(R.id.itemTitle);
    }

    public TextView categoryName() {
        return categoryName;
    }

    public ImageView categoryImage() {
        return categoryImage;
    }
}
