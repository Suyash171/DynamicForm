package com.example.dynamicformdemo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gipl.imagepicker.ImageResult;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyImageViewHolder> {

   private ArrayList<ImageResult> imageResults = new ArrayList<>();
  private  Context mContext;
    // Provide a suitable constructor (depends on the kind of dataset)
    public ImageAdapter(ArrayList<ImageResult> imageResultList, Context context) {
        imageResults = imageResultList;
        mContext = context;
    }

    public ImageAdapter() {
    }

    @NonNull
    @Override
    public MyImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_images_item, parent, false);
        return new MyImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyImageViewHolder holder, int position) {
        String imageResult = imageResults.get(position).getsImagePath();
        holder.imageView.setImageURI(Uri.parse(imageResult));
    }

    @Override
    public int getItemCount() {
        return imageResults.size();
    }

    public void addAll(ArrayList<ImageResult> results){
        imageResults.addAll(results);
        notifyDataSetChanged();
    }

    public static class MyImageViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView imageView;

        public MyImageViewHolder(View v) {
            super(v);
            imageView = itemView.findViewById(R.id.iv_photo);

                // llvContainer = itemView.findViewById(R.id.insert_point);
        }
    }

}
