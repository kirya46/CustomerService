package com.common.persistence;

import com.common.Application;
import com.common.domain.Customer;
import com.common.persistence.CustomerRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Kirill Stoianov on 17/09/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    private Customer customer;

    @Before
    public void setUp() throws Exception {
        this.repository.deleteAll();
        Customer object  = new Customer("Test name", "Test surname", "2131244");
        customer = this.repository.save(object);

    }

    @Test
    public void save() throws Exception {
        assertNotEquals(0,customer.getId());
        assertEquals(1,this.repository.findAll().size());
    }

    @Test
    public void get() {
        final Customer existingCustomer = this.repository.getOne(1L);
        assertNotNull(existingCustomer);
    }

    @Test
    public void update(){
        this.customer.setName("New test name");
        this.repository.save(this.customer);
        assertEquals(customer.getName(),this.repository.findOne(this.customer.getId()).getName());
    }

    @Test
    public void delete(){
        this.repository.delete(this.customer.getId());
        assertEquals(0, this.repository.findAll().size());
    }
}
