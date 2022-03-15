package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AllDiscountTest {

    @Autowired
    private AllDiscount discount;

    @Test
    void shouldApplyAllDiscount() throws Exception {
        var productDb = new ProductDb();
        var cereals = productDb.getProduct("Cereals");
        var bread = productDb.getProduct("Bread");
        var steak = productDb.getProduct("Steak");
        var tomato = productDb.getProduct("Tomato");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cereals, 2));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));
        receiptEntries.add(new ReceiptEntry(tomato, 1));

        var receipt = new Receipt(receiptEntries);
        var receiptAfterDiscount = discount.apply(receipt);
        var expectedTotalPrice = cereals.price()
                .add(cereals.price())
                .add(bread.price())
                .add(steak.price())
                .add(tomato.price())
                .multiply(BigDecimal.valueOf(0.85))
                .multiply(BigDecimal.valueOf(0.9))
                .setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(2, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApplyFifteenPercentDiscount() throws Exception {
        var productDb = new ProductDb();
        var cereals = productDb.getProduct("Cereals");
        var bread = productDb.getProduct("Bread");
        var tomato = productDb.getProduct("Tomato");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cereals, 2));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(tomato, 1));


        var receipt = new Receipt(receiptEntries);
        var receiptAfterDiscount = discount.apply(receipt);
        var expectedTotalPrice = cereals.price()
                .add(cereals.price())
                .add(bread.price())
                .add(tomato.price())
                .multiply(BigDecimal.valueOf(0.85))
                .setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApplyTenPercentDiscount() throws Exception {
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt(receiptEntries);
        var receiptAfterDiscount = discount.apply(receipt);
        var expectedTotalPrice = cheese.price().add(steak.price()).multiply(BigDecimal.valueOf(0.9));

        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApplyDiscount() throws Exception {
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentDiscount();
        var receiptAfterDiscount = discount.apply(receipt);
        var expectedTotalPrice = cheese.price().add(cereals.price()).multiply(BigDecimal.valueOf(2));

        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }
}