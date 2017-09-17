package com.common.controller;

import com.common.Application;
import com.common.config.AppConfig;
import com.common.config.DataConfig;
import com.common.domain.Customer;
import com.common.domain.Order;
import com.common.service.impl.CustomerService;
import com.common.service.impl.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Kirill Stoianov on 16/09/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = {AppConfig.class, DataConfig.class})
@WebAppConfiguration
public class CustomerControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setName("Test name");
        customer.setSurname("Test surname");

        //convert object to json
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customer);

        //prepare request
        final MockHttpServletRequestBuilder postRequest = post("/customer/create")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        //execute request
        final ResultActions resultActions = mockMvc.perform(postRequest);

        //validate result
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        resultActions.andExpect(jsonPath("id", not(0)));
        resultActions.andExpect(jsonPath("name", is(customer.getName())));
        resultActions.andExpect(jsonPath("surname", is(customer.getSurname())));
        resultActions.andExpect(jsonPath("phone", is(customer.getPhone())));
    }

    @Test
    public void updateCustomer() throws Exception{
        // TODO: 16/09/17
    }

    @Test
    public void findCustomer() throws Exception{
        // TODO: 16/09/17
    }

    @Test
    public void findAllCustomers() throws Exception{
        // TODO: 16/09/17
    }

    @Test
    public void deleteCustomer() throws Exception{
        // TODO: 16/09/17
    }

    @Test
    public void deleteAllCustomer() throws Exception{
        // TODO: 16/09/17
    }

    @Test
    public void addOrder() throws  Exception{
        Customer customer = new Customer();
        customer.setName("Test name");
        customer.setSurname("Test surname");

        //convert object to json
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customer);

        //prepare request
        MockHttpServletRequestBuilder postRequest = post("/customer/create")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        //execute request
        ResultActions resultActions;

        resultActions = mockMvc.perform(postRequest);

        //validate result
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        resultActions.andExpect(jsonPath("id", not(1L)));
        resultActions.andExpect(jsonPath("name", is(customer.getName())));
        resultActions.andExpect(jsonPath("surname", is(customer.getSurname())));
        resultActions.andExpect(jsonPath("phone", is(customer.getPhone())));

        Order order = new Order();
        order.setDescription("Order description");
        order.setPrice(99.99);

        //convert object to json
        requestJson = ow.writeValueAsString(order);

        final String path = "/customer/1/orders/add";

        //prepare request
        postRequest = post(path)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        //execute request
        resultActions = mockMvc.perform(postRequest);

        //validate result
        resultActions.andExpect(status().isOk());


        final Customer savedCustomer = this.customerService.find(1L);

        final boolean isOrderAttachedToCustomer = savedCustomer.getOrders().stream().anyMatch(o -> {
            return o.getId() == 1L && o.getPrice() == order.getPrice() && o.getCustomer().getId() == 1L;
        });

        assertTrue(isOrderAttachedToCustomer);


        final Order savedOrder = this.orderService.find(1L);
        assertNotNull(savedOrder.getCustomer());
        assertEquals(1L,savedOrder.getCustomer().getId());
    }
}
