package com.api.productapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.productapi.models.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long>{

}
