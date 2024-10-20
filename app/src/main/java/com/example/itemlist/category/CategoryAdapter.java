package com.example.itemlist.category;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.itemlist.Dto.Category;
import com.example.itemlist.R;
import com.example.itemlist.constants.Urls;



import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private final List<Category> itemList;

    public CategoryAdapter(List<Category> itemList) {
         this.itemList = itemList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category item = itemList.get(position);
        String url = Urls.BASE + "/images/200_"+ item.getImage();
        Glide.with(holder.itemView.getContext())
                .load(url)
                .apply(new RequestOptions().override(300))
                .into(holder.categoryImage);

        holder.categoryName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
