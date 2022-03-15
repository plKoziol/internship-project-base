package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReceiptController {

    /**
     * @param Basket
     * @return Receipt
     * The Receipt method using the POST HTTP method approves / accepts a basket that must be sent in the form of JSON and in the respirator is returned by JSON from Receipt.
     * The basket is performed on the client side, on the server side, the calculations of the given products are performed.
     */

    private final ReceiptGenerator receiptGenerator;

    @PostMapping("/receipts")
    public Receipt receipt(@RequestBody Basket basket){
        Receipt receipt = receiptGenerator.generate(basket);
        return receipt;
    }
}
