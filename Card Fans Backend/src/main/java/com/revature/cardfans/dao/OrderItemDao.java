package com.revature.cardfans.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.revature.cardfans.models.OrderItem;
import com.revature.cardfans.models.User;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {

}