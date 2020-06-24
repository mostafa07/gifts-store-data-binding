package com.example.tabiangifts.databinding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabiangifts.models.CartItem;
import com.example.tabiangifts.ui.adapters.CartItemAdapter;

import java.util.List;

public class ViewCartFragmentBindingAdapters {

    private static final String LOG_TAG = ViewCartFragmentBindingAdapters.class.getSimpleName();

    @BindingAdapter("cartItems")
    public static void setCartItems(RecyclerView view, List<CartItem> cartItems) {
        if (cartItems == null) {
            return;
        }

        final RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (layoutManager == null) {
            view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }

        final CartItemAdapter adapter = (CartItemAdapter) view.getAdapter();
        if (adapter == null) {
            view.setAdapter(new CartItemAdapter(view.getContext(), cartItems));
        } else {
            adapter.updateCartItems(cartItems);
        }
    }
}





















