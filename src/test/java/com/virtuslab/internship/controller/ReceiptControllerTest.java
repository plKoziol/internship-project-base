package com.virtuslab.internship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.AllDiscount;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReceiptControllerTest {


    @Autowired
    private MockMvc mvc;
    @Autowired
    private ReceiptGenerator receiptGenerator;

    Basket createBasket () {
        var productDb = new ProductDb();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");

        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(apple);
        return cart;
    }
    Basket createBasketWithDiscount () {
        var productDb = new ProductDb();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");
        var steak = productDb.getProduct("Steak");

        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(bread);
        cart.addProduct(bread);
        cart.addProduct(apple);
        cart.addProduct(steak);
        cart.addProduct(steak);
        return cart;
    }

    @Test
    void receipt() throws Exception{
        Basket cart = createBasket();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(cart);
        String responseJson = ow.writeValueAsString(receiptGenerator.generate(cart)).replace(" ","").replace("\n", "");
        mvc.perform(post("/receipts").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(content().string(responseJson));
    }


    @Test
    void receiptWithDiscount() throws Exception{
        Basket cart = createBasketWithDiscount();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(cart);
        String responseJson = ow.writeValueAsString(receiptGenerator.generate(cart)).replace(" ","").replace("\n", "");
        mvc.perform(post("/receipts").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(content().string(responseJson));
    }
}