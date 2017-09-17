package com.common.service;

import java.util.List;

/**
 * Created by Kirill Stoianov on 17/09/17.
 */
public interface GenericService <E,PK>{

    E save(E newInstance);

    void delete(PK primaryKey);

    public void deleteAll();

    void update(E persistentInstance);

    E find(PK primaryKey);

    List<E> findAll();
}
