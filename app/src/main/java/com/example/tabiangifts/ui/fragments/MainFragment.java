package com.example.tabiangifts.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tabiangifts.data.Products;
import com.example.tabiangifts.databinding.FragmentMainBinding;
import com.example.tabiangifts.models.Product;
import com.example.tabiangifts.ui.adapters.ProductsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = MainFragment.class.getSimpleName();

    FragmentMainBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater);
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        setupProductsList();

        return mBinding.getRoot();
    }

    private void setupProductsList() {
        Products products = new Products();
        List<Product> productList = new ArrayList<>(Arrays.asList(products.PRODUCTS));
        mBinding.setProducts(productList);
    }

    @Override
    public void onRefresh() {
        Products products = new Products();
        List<Product> productList = new ArrayList<>(Arrays.asList(products.PRODUCTS));
        ((ProductsAdapter) mBinding.recyclerView.getAdapter()).refreshList(productList);
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        (mBinding.recyclerView.getAdapter()).notifyDataSetChanged();
        mBinding.swipeRefreshLayout.setRefreshing(false);
    }
}
