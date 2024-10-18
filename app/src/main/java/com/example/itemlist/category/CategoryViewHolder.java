package com.example.itemlist.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemlist.R;

public  class CategoryViewHolder extends RecyclerView.ViewHolder {

    ImageView categoryImage;
    TextView categoryTitle;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryImage = itemView.findViewById(R.id.itemImage);
        categoryTitle = itemView.findViewById(R.id.itemTitle);
    }

    public TextView categoryName() {
        return categoryTitle;
    }

    public ImageView categoryImage() {
        return categoryImage;
    }
}
