package com.jciterceros.vr_online_backend.repository;

import com.jciterceros.vr_online_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
