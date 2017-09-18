package com.common;

import com.common.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kirill Stoianov on 18/09/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createCustomer() {
        final Customer customer = new Customer("Name", "Surname", "123");
        ResponseEntity<Customer> responseEntity =
                restTemplate.postForEntity("/customer/create", customer,Customer.class);
        Customer createdCustomer = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(customer.getName(), createdCustomer.getName());
        assertEquals(customer.getSurname(), createdCustomer.getSurname());
        assertEquals(customer.getPhone(), createdCustomer.getPhone());
    }

    public void placeAnOrder() {
    }// TODO: 18/09/17
}
