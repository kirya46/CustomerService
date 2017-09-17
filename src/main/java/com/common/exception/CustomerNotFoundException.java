package com.common.exception;

import com.common.domain.Customer;

/**
 * Created by Kirill Stoianov on 17/09/17.
 */
@Deprecated
public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(Long id) {
        super(Customer.class.getSimpleName() + " with id = " + id + " is not found");
    }
}
