package com.example.tabiangifts.databinding;

import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.tabiangifts.BR;
import com.example.tabiangifts.models.CartItem;
import com.example.tabiangifts.util.IMainActivity;

public class CartItemObservable extends BaseObservable {

    private static final String LOG_TAG = CartItemObservable.class.getSimpleName();

    private CartItem cartItem;

    @Bindable
    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        Log.d(LOG_TAG, "setQuantity: updating cart");

        this.cartItem = cartItem;
        notifyPropertyChanged(BR.cartItem);
    }

    public String getQuantityString(CartItem cartItem) {
        return ("Qty: " + cartItem.getQuantity());
    }

    public void increaseQuantity(Context context) {
        final int currentQuantity = cartItem.getQuantity();
        cartItem.setQuantity(currentQuantity + 1);
        setCartItem(cartItem);
        IMainActivity iMainActivity = (IMainActivity) context;
        iMainActivity.updateQuantity(cartItem.getProduct(), 1);
    }

    public void decreaseQuantity(Context context) {
        CartItem cartItem = getCartItem();
        IMainActivity iMainActivity = (IMainActivity) context;

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            setCartItem(cartItem);
            iMainActivity.updateQuantity(cartItem.getProduct(), -1);
        } else if (cartItem.getQuantity() == 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            setCartItem(cartItem);
            iMainActivity.removeCartItem(cartItem);
        }
    }
}



























