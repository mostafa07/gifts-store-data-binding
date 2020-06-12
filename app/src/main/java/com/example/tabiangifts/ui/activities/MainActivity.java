package com.example.tabiangifts.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.example.tabiangifts.R;
import com.example.tabiangifts.databinding.ActivityMainBinding;
import com.example.tabiangifts.ui.fragments.ViewProductFragment;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initFragment();
    }

    private void initFragment() {
        ViewProductFragment viewProductFragment = new ViewProductFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, viewProductFragment);
        fragmentTransaction.commit();
    }
}
