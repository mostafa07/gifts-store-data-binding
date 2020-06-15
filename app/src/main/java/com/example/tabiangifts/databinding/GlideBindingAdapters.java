package com.example.tabiangifts.databinding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.example.tabiangifts.R;

public class GlideBindingAdapters {

    @BindingAdapter("imageSource")
    public static void setImage(ImageView imageView, int imageResourceId) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background))
                .load(imageResourceId)
                .into(imageView);
    }

    @BindingAdapter("imageSource")
    public static void setImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background))
                .load(imageUrl)
                .into(imageView);
    }

    @BindingAdapter({"requestListener", "imageResource"})
    public static void bindRequestListener(ImageView imageView, RequestListener requestListener, int imageResource) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background))
                .load(imageResource)
                .listener(requestListener)
                .into(imageView);
    }
}
