package com.revature.cardfans.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.revature.cardfans.models.Product;
import com.revature.cardfans.services.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = ("/api/v1/products"), produces = "application/json")
@CrossOrigin(origins = "*")
@EnableWebMvc
public class ProductController {

    private ProductService pServ;

    @Autowired
    public ProductController(ProductService pServ) {
        this.pServ = pServ;

    }

    /*
     * Gets all products
     */
    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = pServ.retrieveProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);

    }
    
    /*
     * Gets a product by its name
     */
    @GetMapping("/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
    	Product product = pServ.getProductByName(name);
    	System.out.println("Got product: " + product);
    	return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
