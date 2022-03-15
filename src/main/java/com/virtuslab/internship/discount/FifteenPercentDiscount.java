package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

import static com.virtuslab.internship.product.Product.Type.GRAINS;
@Service
public class FifteenPercentDiscount {

    public static String NAME = "FifteenPercentDiscount";

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85)).setScale(2, RoundingMode.HALF_UP);
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        return receipt.entries().stream()
                .filter(x -> x.product().type().equals(GRAINS))
                .map(x -> x.quantity())
                .reduce(0, (sum, x)-> sum + x)
                >=  3;
    }
}
