package com.common.service.impl;

import com.common.domain.Customer;
import com.common.persistence.CustomerRepository;
import com.common.service.GenericService;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Kirill Stoianov on 16/09/17.
 */
@Service
public class CustomerService implements GenericService<Customer,Long>{

    @Autowired
    private CustomerRepository repository;


    @Override
    @Transactional
    public Customer save(Customer customer){
        return this.repository.save(customer);
    }


    public void deleteAll(){
        this.repository.deleteAll();
    }

    @Override
    public void update(Customer customer){
        this.repository.save(customer);
    }


    @Override
    @Transactional
    public List<Customer> findAll(){
        return this.repository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.repository.delete(id);
    }

    @Override
    public Customer find(Long id) {
        return this.repository.findOne(id);
    }
}
