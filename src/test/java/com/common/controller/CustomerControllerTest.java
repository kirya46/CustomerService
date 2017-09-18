package com.common.controller;

import com.common.Application;
import com.common.config.AppConfig;
import com.common.config.DataConfig;
import com.common.domain.Customer;
import com.common.domain.Order;
import com.common.persistence.CustomerRepository;
import com.common.persistence.OrderRepository;
import com.common.service.impl.CustomerService;
import com.common.service.impl.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Kirill Stoianov on 16/09/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class,CustomerRepository.class,OrderRepository.class})
@ContextConfiguration(classes = {AppConfig.class, DataConfig.class})
@WebAppConfiguration
public class CustomerControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    private MockMvc mockMvc;
    private Customer customer;
    private ObjectMapper mapper;
    private ObjectWriter ow;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        customer = new Customer();
        customer.setName("Test name");
        customer.setSurname("Test surname");

        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = mapper.writer().withDefaultPrettyPrinter();

    }

    @Test
    public void createCustomer() throws Exception {

        //convert object to json

        String requestJson = ow.writeValueAsString(customer);

        //prepare request
        MockHttpServletRequestBuilder postRequest = post("/customer/create")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        //execute request
        ResultActions resultActions;

        resultActions = mockMvc.perform(postRequest);

        //validate result
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        resultActions.andExpect(jsonPath("id", not(0)));
        resultActions.andExpect(jsonPath("name", is(customer.getName())));
        resultActions.andExpect(jsonPath("surname", is(customer.getSurname())));
        resultActions.andExpect(jsonPath("phone", is(customer.getPhone())));

        //update id field in customer
        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(contentAsString);
        final long id = jsonObject.getLong("id");
        this.customer.setId(id);
    }

    @Test
    public void updateCustomer() throws Exception{

        //convert object to json
        String requestJson = ow.writeValueAsString(customer);

        //prepare request
         MockHttpServletRequestBuilder postRequest = post("/customer/create")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        //execute request
         ResultActions resultActions = mockMvc.perform(postRequest);

        //validate result
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        resultActions.andExpect(jsonPath("id", not(0)));
        resultActions.andExpect(jsonPath("name", is(customer.getName())));
        resultActions.andExpect(jsonPath("surname", is(customer.getSurname())));
        resultActions.andExpect(jsonPath("phone", is(customer.getPhone())));

        final String newName = "New name";
        customer.setName(newName);

        requestJson = mapper.writeValueAsString(customer);

        postRequest = post("/customer/update")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        //execute request
        resultActions = mockMvc.perform(postRequest);

        //validate result
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        resultActions.andExpect(jsonPath("id", not(0)));
        resultActions.andExpect(jsonPath("name", is(newName)));
        resultActions.andExpect(jsonPath("surname", is(customer.getSurname())));
        resultActions.andExpect(jsonPath("phone", is(customer.getPhone())));

    }

    @Test
    public void findCustomer() throws Exception{

        //convert object to json
        String requestJson = ow.writeValueAsString(customer);

        //prepare request
        MockHttpServletRequestBuilder postRequest = post("/customer/create")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        //execute request
        ResultActions resultActions;

        resultActions = mockMvc.perform(postRequest);

        //validate result
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        resultActions.andExpect(jsonPath("id", not(0)));
        resultActions.andExpect(jsonPath("name", is(customer.getName())));
        resultActions.andExpect(jsonPath("surname", is(customer.getSurname())));
        resultActions.andExpect(jsonPath("phone", is(customer.getPhone())));

        //update id field in customer
         String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(contentAsString);
        final long id = jsonObject.getLong("id");
        this.customer.setId(id);

        MockHttpServletRequestBuilder getRequest = get("/customer/"+this.customer.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        final ResultActions perform = mockMvc.perform(getRequest);
        perform.andExpect(status().isOk());

        contentAsString = perform.andReturn().getResponse().getContentAsString();
        assertNotSame(0,contentAsString.length());
    }

    @Test
    public void findAllCustomers() throws Exception{

        //convert object to json
        String requestJson = ow.writeValueAsString(customer);

        //prepare request
        MockHttpServletRequestBuilder postRequest = post("/customer/create")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        //execute request
        ResultActions resultActions = mockMvc.perform(postRequest);

        //validate result
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        resultActions.andExpect(jsonPath("id", not(0)));
        resultActions.andExpect(jsonPath("name", is(customer.getName())));
        resultActions.andExpect(jsonPath("surname", is(customer.getSurname())));
        resultActions.andExpect(jsonPath("phone", is(customer.getPhone())));


        //get all users, expected 1
        MockHttpServletRequestBuilder getRequest = get("/customer/all")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        final ResultActions perform = mockMvc.perform(getRequest);
        final String contentAsString = perform.andReturn().getResponse().getContentAsString();
        JSONArray jsonObject = new JSONArray(contentAsString);
        assertEquals(1,jsonObject.length());
    }

    @Test
    public void deleteCustomer() throws Exception{
        //convert object to json
        String requestJson = ow.writeValueAsString(customer);

        //prepare request
        MockHttpServletRequestBuilder postRequest = post("/customer/create")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        //execute request
        ResultActions resultActions;

        resultActions = mockMvc.perform(postRequest);

        //validate result
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        resultActions.andExpect(jsonPath("id", not(0)));
        resultActions.andExpect(jsonPath("name", is(customer.getName())));
        resultActions.andExpect(jsonPath("surname", is(customer.getSurname())));
        resultActions.andExpect(jsonPath("phone", is(customer.getPhone())));

        //update id field in customer
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(contentAsString);
        final long id = jsonObject.getLong("id");
        this.customer.setId(id);

        MockHttpServletRequestBuilder getRequest = get("/customer/delete/"+this.customer.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        final ResultActions perform = mockMvc.perform(getRequest);
        perform.andExpect(status().isOk());
    }

    @Test
    public void addOrder() throws  Exception{

        //convert object to json
        String requestJson = ow.writeValueAsString(customer);

        //prepare request
        MockHttpServletRequestBuilder postRequest = post("/customer/create")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        //execute request
        ResultActions resultActions = mockMvc.perform(postRequest);

        //validate result
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        resultActions.andExpect(jsonPath("id", not(0)));
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
