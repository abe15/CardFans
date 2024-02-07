package com.revature.cardfans.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.revature.cardfans.models.Product;
import com.revature.cardfans.models.User;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
	
	Product findByProductName (String productName);
	
}