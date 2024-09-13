package com.jciterceros.vr_online_backend.service;

import com.jciterceros.vr_online_backend.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> listAll();
    Product create(Product product);
    Product update(Product product);
    void delete(Long id);
}
