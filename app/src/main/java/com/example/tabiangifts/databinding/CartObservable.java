package com.example.tabiangifts.databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.tabiangifts.BR;
import com.example.tabiangifts.data.Prices;
import com.example.tabiangifts.models.CartItem;
import com.example.tabiangifts.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.List;

public class CartObservable extends BaseObservable {

    private List<CartItem> cartItems;
    private boolean isCartVisible;

    @Bindable
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyPropertyChanged(BR.cartItems);
    }

    @Bindable
    public boolean isCartVisible() {
        return isCartVisible;
    }

    public void setCartVisible(boolean cartVisible) {
        isCartVisible = cartVisible;
        notifyPropertyChanged(BR.cartVisible);
    }

    public String getProductsQuantitiesString() {
        int totalNumItems = 0;
        for (CartItem item : cartItems) {
            totalNumItems += item.getQuantity();
        }

        final String s = totalNumItems == 1 ? "" : "s";
        return ("(" + totalNumItems + " item" + s + ")");
    }

    public String getTotalCostString() {
        double totalCost = 0.0;
        for (CartItem item : cartItems) {
            final double itemCost = Prices.getPrices().get(String.valueOf(item.getProduct().getSerialNumber())).doubleValue();
            totalCost += (item.getQuantity() * itemCost);
        }
        return ("$" + BigDecimalUtil.getValue(new BigDecimal(totalCost)));
    }
}
