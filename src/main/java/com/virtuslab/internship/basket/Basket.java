package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Basket {

    private List<Product> products;

    public Basket() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

}
