package com.example.tabiangifts.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tabiangifts.databinding.FragmentViewCartBinding;
import com.example.tabiangifts.util.IMainActivity;

public class ViewCartFragment extends Fragment {

    private static final String LOG_TAG = ViewCartFragment.class.getSimpleName();

    FragmentViewCartBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentViewCartBinding.inflate(inflater);
        mBinding.setIMainActivity((IMainActivity) getActivity());

        return mBinding.getRoot();
    }
}
