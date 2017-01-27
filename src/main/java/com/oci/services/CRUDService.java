package com.oci.services;

/**
 * Created by maqsoodi on 1/26/2017.
 */
public interface CRUDService<T> {

    T getById(Integer id);

    T saveOrUpdate(T domainObject);
}
