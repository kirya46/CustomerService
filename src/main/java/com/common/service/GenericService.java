package com.common.service;

import com.common.exception.CustomerExistingNameException;

import java.util.List;

/**
 * Created by Kirill Stoianov on 17/09/17.
 */
public interface GenericService <E,PK>{

    E save(E newInstance) throws Exception;

    void delete(PK primaryKey);

    public void deleteAll();

    void update(E persistentInstance);

    E find(PK primaryKey);

    List<E> findAll();
}
