package com.common.exception;

import com.common.domain.Customer;

/**
 * Created by Kirill Stoianov on 18/09/17.
 */
public class CustomerExistingNameException extends Exception {
    public CustomerExistingNameException(String name) {
        super(Customer.class.getSimpleName() + " with name = " + name + " has already exist in database.");
    }
}
