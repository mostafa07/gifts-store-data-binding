package com.example.tabiangifts.databinding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabiangifts.models.Product;
import com.example.tabiangifts.ui.adapters.ProductsAdapter;

import java.util.List;

public class MainFragmentBindingAdapters {

    private static final int GRID_NUM_COLUMNS = 2;

    @BindingAdapter("productsList")
    public static void setProductsList(RecyclerView recyclerView, List<Product> products) {
        if (products == null) {
            return;
        }

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), GRID_NUM_COLUMNS));
        }

        final ProductsAdapter adapter = (ProductsAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            recyclerView.setAdapter(new ProductsAdapter(recyclerView.getContext(), products));
        }
    }
}
