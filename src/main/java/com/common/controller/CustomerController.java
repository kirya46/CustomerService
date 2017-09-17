package com.common.controller;

import com.common.domain.Customer;
import com.common.domain.Order;
import com.common.service.impl.CustomerService;
import com.common.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kirill Stoianov on 16/09/17.
 */
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        final Customer savedCustomer = this.customerService.save(customer);
        return ResponseEntity.status(HttpStatus.OK).body(savedCustomer);
    }


    @RequestMapping(
            value = "{customerId}/orders/add",
            method = RequestMethod.POST
    )
    public ResponseEntity addOrder(@PathVariable("customerId") long customerId, @RequestBody Order order){

        final Customer customer = this.customerService.find(customerId);
        if (customer == null) {
            return ResponseEntity.badRequest().build();
        }

        final Order savedOrder = this.orderService.save(order);

        customer.getOrders().add(savedOrder);
        this.customerService.save(customer);
        return ResponseEntity.status(HttpStatus.OK).body(savedOrder);
    }
}
