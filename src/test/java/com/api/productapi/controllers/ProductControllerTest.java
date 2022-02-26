package com.api.productapi.controllers;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.api.productapi.models.ProductModel;
import com.api.productapi.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ProductControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ProductRepository productRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	ProductModel product = new ProductModel(1L, "Celular", 100.9f);
	
	
	
	//Deve retornar successo quando listar todos os produtos.
	
	@Test
	public void deveRetornarSucessoQuandoListarTodosOsProdutos() throws Exception {
		
		ProductModel product2 = new ProductModel(2L, "Notbook", 1000.80f);
		
		List<ProductModel> productList = new ArrayList<>();
		productList.add(product2);
		productList.add(this.product);
		
		when(this.productRepository.findAll())
			.thenReturn(productList);
		
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/products")
				.contentType(MediaType.APPLICATION_JSON))
		
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	
	
	
	
	//Deve retornar successo quando Procurar um unico produto.
	
	@Test
	public void deveRetornarSucessoQuandoProcurarUmUnicoProduto() throws Exception {
		
		Optional<ProductModel> optionalProduct = Optional.of(this.product);
		
		when(this.productRepository.findById(1L))
			.thenReturn(optionalProduct);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	
	
	
	
	//Deve retornar erro quando procurar um produto que não esxiste.
	
	@Test
	public void deveRetornarErroQuandoProcurarUmProdutoInexistente() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", 1L))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	
	
	
	
	//Deve retornar successo quando um novo produto for criado.
	
	@Test
	public void deveRetornarSucessoQuandoUmProdutoForCriado() throws Exception {
		
		String dataJson = this.objectMapper.writeValueAsString(this.product);
		
		when(this.productRepository.save(this.product))
			.thenReturn(this.product);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dataJson))
		.andExpect(MockMvcResultMatchers.status().isCreated());
			
	}
	
	
	
	
	//Deve retornar successo quando um  produto for alterado
	
	@Test
	public void deveRetornarSucessoQuandoUmProdutoForAlterado() throws Throwable {
		
		Optional<ProductModel> optionalProduct = Optional.of(this.product);
		
		String dataJson = this.objectMapper.writeValueAsString(this.product);
		
		when(this.productRepository.findById(1L))
			.thenReturn(optionalProduct);
		
		
		when(this.productRepository.save(this.product))
			.thenReturn(this.product);
		
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(dataJson))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	
	
	
	//Deve retornar error quando um  for tentado alterar um produto que não existe.
	
	@Test
	public void deveRetornarErroQuandoTentarAlterarUmProdutoInexistente() throws Exception {
		
		String dataJson = this.objectMapper.writeValueAsString(this.product);
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(dataJson))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	
	
	
	//Deve retornar sucesso quando um produto for deletado.
	
	@Test
	public void deveRetornarSucessoQuandoUmProdutoForDeletado() throws Exception {
		
		Optional<ProductModel> optionalProduct = Optional.of(this.product);
	
		when(this.productRepository.findById(1L))
			.thenReturn(optionalProduct);
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", 1L))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
			
		
	}
	
	
	
	
	//Deve retornar erro quando for tentado deletar um produto que não existe.
	
	@Test
	public void deveRetornarErroQuandoTentarDeletarUmProdutoInexistente() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", 1L))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	
}
