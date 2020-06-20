package com.example.tabiangifts.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.example.tabiangifts.R;
import com.example.tabiangifts.databinding.ActivityMainBinding;
import com.example.tabiangifts.models.Product;
import com.example.tabiangifts.ui.dialog.ChooseQuantityDialog;
import com.example.tabiangifts.ui.fragments.MainFragment;
import com.example.tabiangifts.ui.fragments.ViewProductFragment;
import com.example.tabiangifts.util.IMainActivity;
import com.example.tabiangifts.util.PreferenceKeys;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initFragment();

        getOrUpdateShoppingCartFromSharedPreferences();
    }

    private void initFragment() {
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mainFragment, getString(R.string.fragment_main));
        fragmentTransaction.commit();
    }

    private void getOrUpdateShoppingCartFromSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> serialNumbers = sharedPreferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        mBinding.setNumCartItems(serialNumbers.size());
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

    @Override
    public void showQuantityDialog() {
        Log.d(LOG_TAG, "showQuantityDialog: showing Quantity Dialog.");

        ChooseQuantityDialog dialog = new ChooseQuantityDialog();
        dialog.show(getSupportFragmentManager(), getString(R.string.dialog_choose_quantity));
    }

    @Override
    public void setQuantity(int quantity) {
        Log.d(LOG_TAG, "selectQuantity: selected quantity: " + quantity);

        ViewProductFragment fragment = (ViewProductFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_view_product));
        if (fragment != null) {
            fragment.mBinding.getProductObservable().setQuantity(quantity);
        }
    }

    @Override
    public void addToCart(Product product, int quantity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> serialNumbers = sharedPreferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        serialNumbers.add(String.valueOf(product.getSerialNumber()));

        editor.putStringSet(PreferenceKeys.shopping_cart, serialNumbers);
        editor.commit();

        int currentQuantity = sharedPreferences.getInt(String.valueOf(product.getSerialNumber()), 0);
        editor.putInt(String.valueOf(product.getSerialNumber()), currentQuantity + quantity);
        editor.commit();

        getOrUpdateShoppingCartFromSharedPreferences();
    }
}
