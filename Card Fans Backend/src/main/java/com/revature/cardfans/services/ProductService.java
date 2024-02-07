package com.revature.cardfans.services;

import java.util.List;
import java.util.Optional;

import com.revature.cardfans.models.Product;

public interface ProductService {

    public List<Product> retrieveProducts();

    public Optional<Product> getProductById(Integer productId);
    
    public Product getProductByName(String productName);

}
