package com.common.persistence.impl;

import com.common.domain.Customer;
import com.common.persistence.GenericDaoImpl;

/**
 * Created by Kirill Stoianov on 16/09/17.
 */
public class CustomerDao extends GenericDaoImpl<Customer,Long>{
    @Override
    protected Class getEntityClass() {
        // TODO: 16/09/17
        return null;
    }
}
