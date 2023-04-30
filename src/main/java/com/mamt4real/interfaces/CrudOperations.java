package com.mamt4real.interfaces;

import java.util.List;
import java.util.Optional;

public interface CrudOperations<T> {
    long createOne(T data);
    List<T> getAll();
    Optional<T> getOne(long id);
    T update(T data);
    void delete(T data);
}
