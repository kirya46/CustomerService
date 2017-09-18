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

import java.util.List;

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
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        final Customer savedCustomer = this.customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Customer> update(@RequestBody Customer customer) {
        final Customer updatedCustomer = this.customerService.save(customer);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

    @RequestMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Customer> get(@PathVariable("customerId") Long id) {
        final Customer customer = this.customerService.find(id);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @RequestMapping(
            value = "/all",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<List<Customer>> getAll() {
        final List<Customer> all = this.customerService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }


    @RequestMapping(value = "/delete/{customerId}")
    public ResponseEntity delete(@PathVariable("customerId") Long id) {
        if (this.customerService.find(id) != null) {
            this.customerService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(
            value = "{customerId}/orders/add",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity addOrder(@PathVariable("customerId") long customerId, @RequestBody Order order) {

        final Customer customer = this.customerService.find(customerId);
        if (customer == null) {
            return ResponseEntity.badRequest().build();
        }

        final Order savedOrder = this.orderService.save(order);

        customer.addOrder(savedOrder);
        this.customerService.save(customer);

        return ResponseEntity.status(HttpStatus.OK).body(savedOrder);
    }
}
