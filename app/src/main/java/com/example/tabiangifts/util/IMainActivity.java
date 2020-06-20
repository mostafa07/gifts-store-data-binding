package com.example.tabiangifts.util;

import com.example.tabiangifts.models.Product;

public interface IMainActivity {

    void inflateViewProductFragment(Product product);

    void showQuantityDialog();

    void setQuantity(int quantity);

    void addToCart(Product product, int quantity);
}
