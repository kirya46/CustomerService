import com.common.Application;
import com.common.config.AppConfig;
import com.common.config.DataConfig;
import com.common.domain.Customer;
import com.common.domain.Order;
import com.common.service.impl.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

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

    @Autowired
    private CustomerService customerService;

    private MockMvc mockMvc;

    private Customer customer;

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
        this.customer = this.customerService.save(new Customer("Test name", "Test surname", "2131244"));
        System.out.println("after save: " + customerService.findAll());

        Order order = new Order();
        order.setDescription("Order description");
        order.setPrice(99.99);

        //convert object to json
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(order);

        final String path = "/customer/" + this.customer.getId() + "/orders/add";
        System.out.println(path);// FIXME: 17/09/17
        //prepare request
        final MockHttpServletRequestBuilder postRequest = post(path)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);



//        System.out.println(customer.toString());
//        System.out.println(order.toString());
//        System.out.println(requestJson);



        //execute request
        final ResultActions resultActions = mockMvc.perform(postRequest);

        //validate result
//        resultActions.andExpect(status().isOk());
//        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
//        resultActions.andExpect(jsonPath("id", not(0)));
//        resultActions.andExpect(jsonPath("name", is(customer.getName())));
//        resultActions.andExpect(jsonPath("surname", is(customer.getSurname())));
//        resultActions.andExpect(jsonPath("phone", is(customer.getPhone())));
//        final MvcResult mvcResult = resultActions.andReturn();
//        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
