package com.common;

import com.common.domain.Customer;
import com.common.domain.Order;
import com.common.service.impl.CustomerService;
import com.common.service.impl.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import junit.framework.TestCase;
import org.aspectj.weaver.ast.Or;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Kirill Stoianov on 18/09/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerService service;


    @Autowired
    private OrderService orderService;

    private MockMvc mockMvc;
    private Customer customer;
    private ObjectMapper mapper;
    private ObjectWriter ow;

    @Before
    public void setUp() throws Exception {

        customer = new Customer();
        customer.setName("Test name " + UUID.randomUUID().toString().substring(0,4));
        customer.setSurname("Test surname");

        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = mapper.writer().withDefaultPrettyPrinter();

    }


    @Test
    public void createCustomer() {
        customer = new Customer("Customer Test Name", "Surname", "123");
        ResponseEntity<Customer> responseEntity =
                restTemplate.postForEntity("/customer/create", customer,Customer.class);
        Customer createdCustomer = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(customer.getName(), createdCustomer.getName());
        assertEquals(customer.getSurname(), createdCustomer.getSurname());
        assertEquals(customer.getPhone(), createdCustomer.getPhone());
    }

    @Test
    public void createCustomerWithExistingName(){
        final Customer customer = new Customer("Name", "Surname", "123");
        ResponseEntity<Customer> responseEntity =
                restTemplate.postForEntity("/customer/create", customer,Customer.class);
        Customer createdCustomer = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(customer.getName(), createdCustomer.getName());
        assertEquals(customer.getSurname(), createdCustomer.getSurname());
        assertEquals(customer.getPhone(), createdCustomer.getPhone());

        //create new customer with the same name as previous, expected BAD_REQUEST(400)
        responseEntity = restTemplate.postForEntity("/customer/create", customer,Customer.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        System.out.println(service.findAll().toString());
    }

    @Test
    public void placeAnOrder() throws Exception {
        final Customer customer = new Customer("Customer Name", "Surname", "123");
        ResponseEntity<Customer> responseEntity =
                restTemplate.postForEntity("/customer/create", customer,Customer.class);
        Customer createdCustomer = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(customer.getName(), createdCustomer.getName());
        assertEquals(customer.getSurname(), createdCustomer.getSurname());
        assertEquals(customer.getPhone(), createdCustomer.getPhone());

        //=====
        Order order = new Order();
        order.setDescription("Order description");
        order.setPrice(99.99);

        ResponseEntity<Order>orderResponseEntity =
                restTemplate.postForEntity("/customer/1/orders/add", order,Order.class);
        assertEquals(HttpStatus.CREATED, orderResponseEntity.getStatusCode());

        //=====


        final Customer savedCustomer = this.service.find(1L);

        final boolean isOrderAttachedToCustomer = savedCustomer.getOrders().stream().anyMatch(o -> {
            return o.getId() == 1L && o.getPrice() == order.getPrice() && o.getCustomer().getId() == 1L;
        });

        assertTrue(isOrderAttachedToCustomer);


        final Order savedOrder = this.orderService.find(1L);
        assertNotNull(savedOrder.getCustomer());
        TestCase.assertEquals(1L,savedOrder.getCustomer().getId());
    }
}
