package com.example.tabiangifts.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tabiangifts.R;
import com.example.tabiangifts.databinding.FragmentViewProductBinding;
import com.example.tabiangifts.databinding.ProductObservable;
import com.example.tabiangifts.models.Product;
import com.example.tabiangifts.util.IMainActivity;

public class ViewProductFragment extends Fragment {

    private static final String LOG_TAG = ViewProductFragment.class.getSimpleName();

    public FragmentViewProductBinding mBinding;

    private Product mProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mProduct = bundle.getParcelable(getString(R.string.intent_product));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentViewProductBinding.inflate(inflater);
        mBinding.setIMainActivity((IMainActivity) requireActivity());

        ProductObservable productObservable = new ProductObservable();
        productObservable.setProduct(mProduct);
        productObservable.setQuantity(1);
        mBinding.setProductObservable(productObservable);

        return mBinding.getRoot();
    }
}
