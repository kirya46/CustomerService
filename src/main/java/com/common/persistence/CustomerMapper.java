package com.common.persistence;

import com.common.domain.Customer;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Kirill Stoianov on 15/09/17.
 */
@Service
@Transactional
public class CustomerMapper implements GenericMapper<Customer,Integer> {

    @Autowired
    HibernateTemplate template;

    @Override
    public List<Customer> getAll() {

        return (List<Customer>) template.findByCriteria(DetachedCriteria.forClass(Customer.class));
    }

    @Override
    public Customer findById(Integer id) {
        return  template.get(Customer.class, id);
    }

    @Override

    public void insert(Customer newInstance) {
//        final Session currentSession = template.getSessionFactory().getCurrentSession();
        final Serializable save = template.save(newInstance);

        System.out.println("Result: " + save);
    }
}
