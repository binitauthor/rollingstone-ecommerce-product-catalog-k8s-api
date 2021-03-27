package com.rollingstone.spring.controller;

import com.rollingstone.events.ProductEvent;
import com.rollingstone.spring.model.Product;
import com.rollingstone.spring.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductController extends AbstractController {

  
   private ProductService  productService;
   
   public ProductController(ProductService productService) {
	   this.productService = productService;
   }

   /*---Add new Product---*/
   @PostMapping("/product")
   public ResponseEntity<?> createProduct(@RequestBody Product product) {
      Product savedProduct = productService.save(product);
      ProductEvent productCreatedEvent = new ProductEvent("One Product is created", savedProduct);
      eventPublisher.publishEvent(productCreatedEvent);
      return ResponseEntity.ok().body("New Product has been saved with ID:" + savedProduct.getId());
   }

   /*---Get a Product by id---*/
   @GetMapping("/product/{id}")
   public ResponseEntity<Product> getProduct(@PathVariable("id") long id) {
	  Optional<Product> returnedProduct = productService.get(id);
	  Product product  = returnedProduct.get(); 
	  
	  ProductEvent productCreatedEvent = new ProductEvent("One Product is retrieved", product);
      eventPublisher.publishEvent(productCreatedEvent);
      return ResponseEntity.ok().body(product);
   }

   /*---get all Product---*/
   @GetMapping("/product")
   public @ResponseBody Page<Product> getProductsByPage(
		   @RequestParam(value="pagenumber", required=true, defaultValue="0") Integer pageNumber,
		   @RequestParam(value="pagesize", required=true, defaultValue="20") Integer pageSize) {
      Page<Product> pagedProducts = productService.getProductsByPage(pageNumber, pageSize);
      return pagedProducts;
   }

   /*---Update a Product by id---*/
   @PutMapping("/product/{id}")
   public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
	  checkResourceFound(this.productService.get(id));
      productService.update(id, product);
      return ResponseEntity.ok().body("Product has been updated successfully.");
   }

   /*---Delete a Product by id---*/
   @DeleteMapping("/product/{id}")
   public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
	  checkResourceFound(this.productService.get(id));
      productService.delete(id);
      return ResponseEntity.ok().body("Product has been deleted successfully.");
   }
}