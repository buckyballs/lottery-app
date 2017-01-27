package com.oci.services;

/**
 * Created by maqsoodi on 1/26/2017.
 */
public interface CRUDService<T> {

    T saveOrUpdate(T domainObject);
}
