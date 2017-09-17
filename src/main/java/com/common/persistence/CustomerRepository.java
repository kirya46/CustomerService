package com.common.persistence;

import com.common.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Kirill Stoianov on 16/09/17.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
