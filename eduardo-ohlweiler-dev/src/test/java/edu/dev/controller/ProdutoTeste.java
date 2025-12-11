package edu.dev.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;

import edu.dev.app.SpringBootApp;
import edu.dev.entity.Produto;
import edu.dev.repository.ProdutoRepository;
import edu.dev.service.ProdutoService;
import edu.dev.test.TesteGeneric;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringBootApp.class, 
               webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProdutoTeste extends TesteGeneric {
    
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	@DisplayName("1 - GET /api/produtos - Deve retornar lista de produtos")
	void testeListarTodos1() {
		
		produtoRepository.deleteAll();
		
		Produto p1 = new Produto("Produto A");
		Produto p2 = new Produto("Produto B");
		
		p1 = produtoService.salvar(p1);
		p2 = produtoService.salvar(p2);
		
		
		ResponseEntity<Produto[]> response = 
				                  restTemplate.getForEntity(url("api/produtos"), 
				                   Produto[].class);
		
		List<Produto> produtos = Arrays.asList(response.getBody());
		
		assertEquals(2, produtos.size());
		assertEquals(p1.getId(), produtos.get(0).getId());
		assertEquals(p2.getId(), produtos.get(1).getId());
		
		
		assertEquals(p1.getNome(), produtos.get(0).getNome());
		assertEquals(p2.getNome(), produtos.get(1).getNome());
		
	}
	
	
	@Test
	@DisplayName("2 - GET /api/produtos - Deve retornar lista de produtos JSON")
	void testeListarTodos2() throws Exception {
		
		produtoRepository.deleteAll();
		
		Produto p1 = new Produto("Produto A");
		Produto p2 = new Produto("Produto B");
		
		p1 = produtoService.salvar(p1);
		p2 = produtoService.salvar(p2);
		
		getMockMvc().perform(get("/api/produtos"))
		             .andExpect(status().isOk())
		             .andExpect(jsonPath("$.length()").value(2))
		             .andExpect(jsonPath("$[0].id").value(p1.getId()))
		             .andExpect(jsonPath("$[0].nome").value(p1.getNome()))
		             .andExpect(jsonPath("$[1].id").value(p2.getId()))
		             .andExpect(jsonPath("$[1].nome").value(p2.getNome()));
		
		
	}
	
	
	
	
	@Test
	@DisplayName("3 - GET /api/produtos - Deve retornar lista de produtos String JSON")
	void testeListarTodos3() {
		
		produtoRepository.deleteAll();
		
		Produto p1 = new Produto("Produto A");
		Produto p2 = new Produto("Produto B");
		
		p1 = produtoService.salvar(p1);
		p2 = produtoService.salvar(p2);
		
		
		ResponseEntity<String> response = 
				                  restTemplate.getForEntity(url("api/produtos"), 
				                   String.class);
		

        //List<Produto> produtos = new Gson().fromJson(response.getBody(), List.class);
        
        Produto[] produtos = new Gson().fromJson(response.getBody(), Produto[].class);
        
        assertEquals(2, produtos.length);
		assertEquals(p1.getId(), produtos[0].getId());
		assertEquals(p2.getId(), produtos[1].getId());
		
		
		assertEquals(p1.getNome(), produtos[0].getNome());
		assertEquals(p2.getNome(), produtos[1].getNome());
		
		
		Map<String, Object>[] listaMap = new Gson().fromJson(response.getBody(), Map[].class);
		
		for (Map<String, Object> map : listaMap) {
			System.out.println("ID : " + map.get("id"));
			System.out.println("Nome : " + map.get("nome"));
		}
		
	}
	
	
	
	@Test
	@DisplayName("4 - POST /api/produtos - Criar e salvar Produto")
	void testeListarTodos4() {
	    Produto produto = new Produto("Teclado mecanico");
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity<Produto> request = new HttpEntity<Produto>(produto, headers);
	    
	    ResponseEntity<Produto> response = restTemplate
	    		                   .postForEntity(url("api/produtos"), 
	    		                	    request, 
	    		                	    Produto.class);
	    
	    System.out.println("Produto cadastrado: " + response.getBody());
	    
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertNotNull(response.getBody());
	    assertEquals("Teclado mecanico", response.getBody().getNome());
	
	}
	
	
	@Test
	@DisplayName("5 - PUT /api/produtos - Atualizar Produto")
	void testeListarTodos5() {
		
		
		  Produto produto = new Produto("Teclado mecanico");
		  
		  produto = produtoService.salvar(produto);
		  
		  produto.setNome("Teclado digital");
		  
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		    
		  ResponseEntity<Produto> response = restTemplate.exchange(url("api/produtos"),
				                             HttpMethod.PUT, 
				                             new HttpEntity<Produto>(produto, headers), 
				                             Produto.class);
		  
		    assertEquals(HttpStatus.OK, response.getStatusCode());
		    assertNotNull(response.getBody());
		    assertEquals("Teclado digital", response.getBody().getNome());
		  
	}
	
	
	
	@Test
	@DisplayName("6 - GET ID /api/produtos - Get ID Produto")
	void testeListarTodos6() {
		
        Produto produto = new Produto();
        produto.setNome("Teclado Mecânico");
        
        produto = produtoService.salvar(produto);
        
	    ResponseEntity<Produto> response = restTemplate
                                          .getForEntity(url("api/produtos/" + produto.getId()), 
             	                           Produto.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("Teclado Mecânico", response.getBody().getNome());
		
	}
	
	
	
	@Test
	@DisplayName("7 - Delete ID /api/produtos - Delete ID Produto")
	void testeListarTodos7() {
		
		
        Produto produto = new Produto();
        produto.setNome("Teclado Mecânico Delete");
        
        produto = produtoService.salvar(produto);
        
        
        restTemplate.delete(url("api/produtos/" + produto.getId()));
        
	    ResponseEntity<Produto> response = restTemplate
                                .getForEntity(url("api/produtos/" + produto.getId()), 
                                 Produto.class);
	    
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        
        
	}
	
	
	
}
