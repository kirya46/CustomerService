package com.common.persistence;

import com.common.Application;
import com.common.domain.Customer;
import com.common.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Kirill Stoianov on 17/09/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    private Order order;

    @Before
    public void setUp() throws Exception {
        this.repository.deleteAll();
        Order object  = new Order("Order test description",19.99);
        order = this.repository.save(object);
    }

    @Test
    public void save() throws Exception {
        assertNotEquals(0, order.getId());
        assertEquals(1,this.repository.findAll().size());
    }

    @Test
    public void get() {
        final Order existing = this.repository.getOne(1L);
        assertNotNull(existing);
    }

    @Test
    public void update(){
        this.order.setDescription("New order test description");
        this.repository.save(this.order);
        assertEquals(order.getDescription(),this.repository.findOne(this.order.getId()).getDescription());
    }

    @Test
    public void delete(){
        this.repository.delete(this.order.getId());
        assertEquals(0, this.repository.findAll().size());
    }
}
