package com.example.tabiangifts.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.example.tabiangifts.R;
import com.example.tabiangifts.databinding.ActivityMainBinding;
import com.example.tabiangifts.models.Product;
import com.example.tabiangifts.ui.fragments.MainFragment;
import com.example.tabiangifts.ui.fragments.ViewProductFragment;
import com.example.tabiangifts.util.IMainActivity;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initFragment();
    }

    private void initFragment() {
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mainFragment, getString(R.string.fragment_main));
        fragmentTransaction.commit();
    }

    @Override
    public void inflateViewProductFragment(Product product) {
        ViewProductFragment viewProductFragment = new ViewProductFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.intent_product), product);
        viewProductFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, viewProductFragment, getString(R.string.fragment_view_product));
        fragmentTransaction.addToBackStack(getString(R.string.fragment_view_product));
        fragmentTransaction.commit();
    }
}
