package com.common.controller;

import com.common.domain.Customer;
import com.common.persistence.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Kirill Stoianov on 16/09/17.
 */
@RestController
public class CustomerController {

    @Autowired
    private CustomerMapper customerMapper;

    @RequestMapping(value = "/test")
    public void test(){

        Customer customer = new Customer();
        customer.setName("Name customer");
        customer.setSurname("Surname customer");
        customer.setPhone("12345");
        customerMapper.insert(customer);

        System.out.println(customerMapper.getAll()+"");


    }
}
