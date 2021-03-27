package com.rollingstone.spring.dao;

import com.rollingstone.spring.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductDaoRepository extends PagingAndSortingRepository<Product, Long> {

	Page<Product> findAll(Pageable pageable);
}
