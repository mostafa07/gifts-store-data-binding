package com.example.tabiangifts.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tabiangifts.data.Products;
import com.example.tabiangifts.databinding.CartObservable;
import com.example.tabiangifts.databinding.FragmentViewCartBinding;
import com.example.tabiangifts.models.CartItem;
import com.example.tabiangifts.util.IMainActivity;
import com.example.tabiangifts.util.PreferenceKeys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewCartFragment extends Fragment {

    private static final String LOG_TAG = ViewCartFragment.class.getSimpleName();

    FragmentViewCartBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentViewCartBinding.inflate(inflater);
        mBinding.setIMainActivity((IMainActivity) getActivity());
        mBinding.getIMainActivity().setCartVisibility(true);

        getShoppingCartList();

        return mBinding.getRoot();
    }

    private void getShoppingCartList() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());

        Products products = new Products();
        List<CartItem> cartItems = new ArrayList<>();
        for (String serialNumber : serialNumbers) {
            int quantity = preferences.getInt(serialNumber, 0);
            cartItems.add(new CartItem(products.PRODUCT_MAP.get(serialNumber), quantity));
        }
        CartObservable cartObservable = new CartObservable();
        cartObservable.setCartItems(cartItems);
        mBinding.setCartObservable(cartObservable);
    }

    public void updateCartItems() {
        getShoppingCartList();
    }

    @Override
    public void onDestroy() {
        mBinding.getIMainActivity().setCartVisibility(false);
        super.onDestroy();
    }
}
