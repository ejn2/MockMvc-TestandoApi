package com.api.productapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.productapi.models.ProductModel;
import com.api.productapi.repository.ProductRepository;

@RequestMapping("/products")
@RestController
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;

	
	
	@GetMapping
	public ResponseEntity<List<ProductModel>> showAllProducts(){
		
		List<ProductModel> productList = this.productRepository.findAll();
		
		if(productList.size() < 1) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		return new ResponseEntity<>(productList, HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("{id}")
	public ResponseEntity<ProductModel> getProduct(@PathVariable Long id) {
		
		Optional<ProductModel> product = this.productRepository.findById(id);
		
		if(!product.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return new ResponseEntity<>(product.get(), HttpStatus.OK);
		
	}
	
	
	
	@PostMapping
	public ResponseEntity<ProductModel> createNewProduct(@RequestBody ProductModel product){
	
		ProductModel createdProduct = this.productRepository.save(product);
			
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("{id}")
	public ResponseEntity<ProductModel> updateProduct(@PathVariable Long id, @RequestBody ProductModel product) {
		
		Optional<ProductModel> findProduct = this.productRepository.findById(id);
		
		if(!findProduct.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		}
		
		findProduct.get().setId(id);
		
		return new ResponseEntity<>(this.productRepository.save(findProduct.get()), HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<ProductModel> deleteProduct(@PathVariable Long id) {
		
		Optional<ProductModel> findProduct = this.productRepository.findById(id);
		
		if(!findProduct.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
	
	
}
