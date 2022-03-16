package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReceiptController {

    /**
     * @param Basket
     * @return Receipt
     * The Receipt method is using the POST HTTP method: approved basket must be sent in the JSON format and in response return Receipt as JSON format.
     * The basket is performed on the client side, the calculations are performed on the server side.
     */

    private final ReceiptGenerator receiptGenerator;

    @PostMapping("/receipts")
    public Receipt receipt(@RequestBody Basket basket){
        Receipt receipt = receiptGenerator.generate(basket);
        return receipt;
    }
}
