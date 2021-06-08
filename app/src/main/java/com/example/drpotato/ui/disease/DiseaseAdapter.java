package com.example.drpotato.ui.disease;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.drpotato.R;
import com.example.drpotato.model.Potato;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder> {

    public DiseaseAdapter() {
    }

    class DiseaseViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView titlePlaceholder;
        public DiseaseViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.adapter_disease_title);
            imageView = itemView.findViewById(R.id.adapter_disease_image);
            titlePlaceholder = itemView.findViewById(R.id.adapter_disease_title_placeholder);
            titlePlaceholder.setText("Tipe");
        }
    }

    interface OnItemClickCallback{
        void onItemClick(Potato potato);
    }

    private List<Potato> potatoes = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setPotatoes(List<Potato> potatoes) {
        this.potatoes = potatoes;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public DiseaseViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_disease, parent, false);
        return new DiseaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DiseaseViewHolder holder, int position) {
        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1000) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.7f) //the alpha of the underlying children
                .setHighlightAlpha(0.6f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();

        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);

        Glide.with(holder.imageView.getContext())
                .load(potatoes.get(position).getImageUrl())
                .apply(
                        RequestOptions.overrideOf(60, 60)
                                .placeholder(shimmerDrawable)
                                .error(R.drawable.ic_broken_image_24)
                )
                .into(holder.imageView);
        holder.textView.setText(potatoes.get(position).getName());
        holder.itemView.setOnClickListener(view -> {
            onItemClickCallback.onItemClick(potatoes.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return potatoes.size();
    }
}
