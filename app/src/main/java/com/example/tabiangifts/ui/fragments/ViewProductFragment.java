package com.example.tabiangifts.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tabiangifts.databinding.FragmentViewProductBinding;
import com.example.tabiangifts.data.Products;

public class ViewProductFragment extends Fragment {

    private static final String LOG_TAG = ViewProductFragment.class.getSimpleName();

    FragmentViewProductBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentViewProductBinding.inflate(inflater);

        Products products = new Products();
        mBinding.setProduct(products.PRODUCTS[0]);

        mBinding.setQty(1);

        return mBinding.getRoot();
    }
}














