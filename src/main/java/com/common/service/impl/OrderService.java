package com.common.service.impl;

import com.common.domain.Order;
import com.common.persistence.CustomerRepository;
import com.common.persistence.OrderRepository;
import com.common.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kirill Stoianov on 17/09/17.
 */
@Service
public class OrderService implements GenericService<Order,Long>{

    @Autowired
    private OrderRepository repository;

    @Override
    public Order save(Order newInstance) {
        return this.repository.save(newInstance);
    }

    @Override
    public void delete(Long primaryKey) {
        this.repository.delete(primaryKey);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Override
    public void update(Order persistentInstance) {
        this.repository.save(persistentInstance);
    }

    @Override
    public Order find(Long primaryKey) {
        return this.repository.findOne(primaryKey);
    }

    @Override
    public List<Order> findAll() {
        return this.repository.findAll();
    }
}
