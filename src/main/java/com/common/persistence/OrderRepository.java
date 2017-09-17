package com.common.persistence;

import com.common.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kirill Stoianov on 17/09/17.
 */
public interface OrderRepository extends JpaRepository<Order,Long>{
}
