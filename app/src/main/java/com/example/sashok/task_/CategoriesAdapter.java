package com.example.sashok.task_;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sashok.task_.Answer.Category;

import java.util.List;

/**
 * Created by sashok on 7.4.17.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyHolder> {
    public List<Category> categories;
    private MainActivity activity;

    public CategoriesAdapter(MainActivity activity, List<Category> categories) {
        this.activity = activity;
        this.categories = categories;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category, parent, false);
        MyHolder pvh = new MyHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final Category category = categories.get(position);
        holder.category_name.setText(category.getName());
        Glide.with(activity).load(category
                .getIconUrl())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.category_icon);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView category_name;
        ImageView category_icon;

        public MyHolder(View itemView) {
            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            category_icon = (ImageView) itemView.findViewById(R.id.category_icon);
        }
    }


}