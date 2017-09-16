package com.common.persistence;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kirill Stoianov on 15/09/17.
 */
public interface GenericMapper <E, PK extends Serializable>{

    List<E> getAll();

    E findById(PK id);

    void insert(E object);

}
