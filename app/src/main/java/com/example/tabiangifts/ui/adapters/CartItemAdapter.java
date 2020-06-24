package com.example.tabiangifts.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabiangifts.R;
import com.example.tabiangifts.databinding.CartItemBinding;
import com.example.tabiangifts.databinding.CartItemObservable;
import com.example.tabiangifts.models.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.BindingHolder> {

    private static final String LOG_TAG = CartItemAdapter.class.getSimpleName();

    private List<CartItem> mCartItems;
    private Context mContext;

    public CartItemAdapter(Context context, List<CartItem> cartItems) {
        mContext = context;
        mCartItems = cartItems;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), R.layout.cart_item, parent, false);

        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        CartItem cartItem = mCartItems.get(position);
        CartItemObservable cartItemObservable = new CartItemObservable();
        cartItemObservable.setCartItem(cartItem);
        holder.binding.setCartItemObservable(cartItemObservable);
        holder.binding.executePendingBindings();
    }

    public void updateCartItems(List<CartItem> cartItems) {
        mCartItems.clear();
        mCartItems.addAll(cartItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }


    public class BindingHolder extends RecyclerView.ViewHolder {

        CartItemBinding binding;

        public BindingHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}



















