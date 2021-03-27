package com.rollingstone.spring.service;

import com.rollingstone.spring.model.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {

   Product save(Product product);
   Optional<Product> get(long id);
   Page<Product> getProductsByPage(Integer pageNumber, Integer pageSize);
   void update(long id, Product product);
   void delete(long id);
}
