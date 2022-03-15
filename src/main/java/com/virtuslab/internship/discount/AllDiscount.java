package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.virtuslab.internship.product.Product.Type.GRAINS;
@Service
@RequiredArgsConstructor
public class AllDiscount {

    private final FifteenPercentDiscount fifteenPercentDiscount;
    private final TenPercentDiscount tenPercentDiscount;

    public Receipt apply(Receipt receipt) {
        Receipt sumOfDiscount = fifteenPercentDiscount.apply(receipt);
        sumOfDiscount = tenPercentDiscount.apply(sumOfDiscount);
        return sumOfDiscount;
    }


}
