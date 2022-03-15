package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.AllDiscount;
import com.virtuslab.internship.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class ReceiptGenerator {

    private final AllDiscount allDiscount;

    public Receipt generate(Basket basket) {

        var receipt = getReceipt(basket);
        return allDiscount.apply(receipt);
    }

    private Receipt getReceipt(Basket basket) {
        List<ReceiptEntry> receiptEntries = createReceiptEntry(basket);
        return new Receipt(receiptEntries);
    }

    private List<ReceiptEntry> createReceiptEntry (Basket basket){
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        Map<Product, Integer> mapOfBasket = new HashMap<>();
        basket.getProducts().stream().forEach(x -> mapOfBasket.put(x,countProducts(mapOfBasket,x)));
        mapOfBasket.forEach((k,v) -> receiptEntries.add(new ReceiptEntry(k,v)));
        return receiptEntries;
    }

    private Integer countProducts (Map<Product, Integer> map, Product product){
        if (map.containsKey(product)){
            return map.get(product)+1;
        } else return 1;
    }
}
