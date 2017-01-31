package com.oci.services;

import java.util.List;

/**
 * Created by maqsoodi on 1/26/2017.
 */
public interface CRUDService<T> {

    List<?> listAll();

    T getById(Integer id);

    T saveOrUpdate(T domainObject);
}
