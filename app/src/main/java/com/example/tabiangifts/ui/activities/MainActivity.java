package com.example.tabiangifts.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.example.tabiangifts.R;
import com.example.tabiangifts.data.Products;
import com.example.tabiangifts.databinding.ActivityMainBinding;
import com.example.tabiangifts.databinding.CartObservable;
import com.example.tabiangifts.models.CartItem;
import com.example.tabiangifts.models.Product;
import com.example.tabiangifts.ui.dialog.ChooseQuantityDialog;
import com.example.tabiangifts.ui.fragments.MainFragment;
import com.example.tabiangifts.ui.fragments.ViewCartFragment;
import com.example.tabiangifts.ui.fragments.ViewProductFragment;
import com.example.tabiangifts.util.IMainActivity;
import com.example.tabiangifts.util.PreferenceKeys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;

    private boolean mClickToExit = true;
    private Runnable mCheckoutRunnable;
    private Handler mCheckoutHandler;
    private int mCheckoutTimer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.cart.setOnTouchListener(new CartTouchListener());
        mBinding.proceedToCheckout.setOnClickListener(mCheckOutListener);

        initFragment();

        getOrUpdateShoppingCartFromSharedPreferences();
    }

    @Override
    public void onBackPressed() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(LOG_TAG, "onBackPressed: backstack count: " + backStackCount);
        if (backStackCount == 0 && mClickToExit) {
            super.onBackPressed();
        }
        if (backStackCount == 0 && !mClickToExit) {
            Toast.makeText(this, "1 more click to exit.", Toast.LENGTH_SHORT).show();
            mClickToExit = true;
        } else {
            mClickToExit = false;
            super.onBackPressed();
        }
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

        Products products = new Products();
        List<CartItem> cartItems = new ArrayList<>();
        for (String serialNumber : serialNumbers) {
            final int quantity = sharedPreferences.getInt(serialNumber, 0);
            cartItems.add(new CartItem(products.PRODUCT_MAP.get(serialNumber), quantity));
        }

        CartObservable cartObservable = new CartObservable();
        cartObservable.setCartItems(cartItems);
        try {
            cartObservable.setCartVisible(mBinding.getCartObservable().isCartVisible());
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "getShoppingCart: NullPointerException: " + e.getMessage());
        }
        mBinding.setCartObservable(cartObservable);
    }

    public void removeViewCartFragment() {
        getSupportFragmentManager().popBackStack();
        ViewCartFragment fragment = (ViewCartFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_view_cart));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            transaction.remove(fragment);
            transaction.commit();
        }
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

    @Override
    public void inflateViewCartFragment() {
        ViewCartFragment fragment = (ViewCartFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_view_cart));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = new ViewCartFragment();
            transaction.replace(R.id.fragment_container, fragment, getString(R.string.fragment_view_cart));
            transaction.addToBackStack(getString(R.string.fragment_view_cart));
            transaction.commit();
        }
    }

    @Override
    public void setCartVisibility(boolean visibility) {
        mBinding.getCartObservable().setCartVisible(visibility);
    }

    @Override
    public void updateQuantity(Product product, int quantity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int currentQuantity = sharedPreferences.getInt(String.valueOf(product.getSerialNumber()), 0);
        editor.putInt(String.valueOf(product.getSerialNumber()), currentQuantity + quantity);
        editor.commit();

        getOrUpdateShoppingCartFromSharedPreferences();
    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(String.valueOf(cartItem.getProduct().getSerialNumber()));
        editor.commit();

        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        if (serialNumbers.size() == 1) {
            editor.remove(PreferenceKeys.shopping_cart);
        } else {
            serialNumbers.remove(String.valueOf(cartItem.getProduct().getSerialNumber()));
            editor.putStringSet(PreferenceKeys.shopping_cart, serialNumbers);
        }
        editor.commit();

        getOrUpdateShoppingCartFromSharedPreferences();

        ViewCartFragment fragment = (ViewCartFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_view_cart));
        if (fragment != null) {
            fragment.updateCartItems();
        }
    }

    public void checkout() {
        mBinding.progressBar.setVisibility(View.VISIBLE);

        mCheckoutHandler = new Handler();
        mCheckoutRunnable = new Runnable() {
            @Override
            public void run() {
                mCheckoutHandler.postDelayed(mCheckoutRunnable, 200);
                mCheckoutTimer += 200;
                if (mCheckoutTimer >= 1600) {
                    emptyCart();
                    mBinding.progressBar.setVisibility(View.GONE);
                    mCheckoutHandler.removeCallbacks(mCheckoutRunnable);
                    mCheckoutTimer = 0;
                }
            }
        };
        mCheckoutRunnable.run();
    }

    private void emptyCart() {
        Log.d(LOG_TAG, "emptyCart: emptying cart.");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        SharedPreferences.Editor editor = preferences.edit();

        for (String serialNumber : serialNumbers) {
            editor.remove(serialNumber);
            editor.commit();
        }

        editor.remove(PreferenceKeys.shopping_cart);
        editor.commit();
        Toast.makeText(this, "Thanks for shopping!", Toast.LENGTH_SHORT).show();
        removeViewCartFragment();
        getOrUpdateShoppingCartFromSharedPreferences();
    }


    public View.OnClickListener mCheckOutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkout();
        }
    };


    public static class CartTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.setBackgroundColor(view.getContext().getResources().getColor(R.color.blue4));
                view.performClick();

                IMainActivity iMainActivity = (IMainActivity) view.getContext();
                iMainActivity.inflateViewCartFragment();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                view.setBackgroundColor(view.getContext().getResources().getColor(R.color.blue6));
            }
            return true;
        }
    }
}
