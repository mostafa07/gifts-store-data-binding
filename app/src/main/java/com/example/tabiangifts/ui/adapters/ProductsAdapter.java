package com.example.tabiangifts.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabiangifts.R;
import com.example.tabiangifts.databinding.ProductItemBinding;
import com.example.tabiangifts.models.Product;
import com.example.tabiangifts.util.IMainActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.BindingHolder> {

    private static final String LOG_TAG = ProductsAdapter.class.getSimpleName();

    private List<Product> mProducts = new ArrayList<>();
    private Context mContext;

    public ProductsAdapter(Context context, List<Product> products) {
        mProducts = products;
        mContext = context;
    }

    public void refreshList(List<Product> products) {
        mProducts.clear();
        mProducts.addAll(products);
        notifyDataSetChanged();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProductItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.product_item, parent, false);

        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        Product product = mProducts.get(position);
        holder.binding.setProduct(product);
        holder.binding.setIMainActivity((IMainActivity) mContext);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {

        ProductItemBinding binding;

        public BindingHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}













