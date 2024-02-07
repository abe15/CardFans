package com.revature.springpractice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import com.revature.cardfans.CardFans;
import com.revature.cardfans.controllers.UserController;
import com.revature.cardfans.dao.ProductDao;
import com.revature.cardfans.models.Product;
import com.revature.cardfans.services.ProductService;
import com.revature.cardfans.services.ProductServiceImpl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.restdocs.*;
import org.springframework.restdocs.mockmvc.*;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.security.core.Authentication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest(classes = CardFans.class)
public class TrialProductModules {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	private static List<Product> prodList = new ArrayList<Product>();
	private static ProductService prodService;
	private static ProductDao prodDao;
	private static Authentication auth;

	public TrialProductModules() {
	}

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(restDocumentation))
				.build();
		prodDao = Mockito.mock(ProductDao.class);
		auth = Mockito.mock(Authentication.class);
		prodList.add(new Product(
				1,
				"Aviary",
				"red, black",
				"standard",
				"birds",
				5.99,
				"Bird lovers from all over flock together to find a copy of this lovely deck",
				17,
				"standard", ""));
		prodList.add(new Product(4,
				"Dragon",
				"red, black",
				"fancy",
				"myth",
				5.99,
				"Mythical beasts of majesty dawn the backs of these gorgeous cards",
				77,
				"fancy", ""));
		prodList.add(new Product(7,
				"Stargazer",
				"same",
				"standard",
				"space",
				5.99,
				"Lovers of the stars unite! These cards will let you see far into the reaches of outer space any time of day",
				33,
				"standard", ""));

	}

	@AfterEach
	public void resetMockAfter() throws Exception {
		Mockito.reset(prodDao);
	}

	@DisplayName("1. Test controller get for products")
	@Test
	public void shouldReturnProductList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(document("index"));

	}

	@DisplayName("2. Test Product service, get all products")
	@Test
	public void testProductServiceRetrieve() throws Exception {

		prodService = new ProductServiceImpl(prodDao);

		when(prodDao.findAll()).thenReturn(prodList);

		// act (do the service call)
		List<Product> result = prodService.retrieveProducts();
		assertEquals(prodList, result);
		verify(prodDao, times(1)).findAll();
	}

	@DisplayName("3. Test Product service, find prod by id, Item exits")
	@Test
	public void testProductServiceFindByIdExisting() throws Exception {

		prodService = new ProductServiceImpl(prodDao);

		when(prodDao.findById(7)).thenReturn(Optional.of(prodList.get(2)));

		// act (do the service call)
		Optional<Product> result = prodService.getProductById(7);
		assertEquals(prodList.get(2), result.get());
		verify(prodDao, times(1)).findById(7);
	}

	@DisplayName("4. Test Product service, find prod by id, Item not exists")
	@Test
	public void testProductServiceFindByIdNotExisting() throws Exception {

		prodService = new ProductServiceImpl(prodDao);

		when(prodDao.findById(100)).thenReturn(Optional.empty());

		// act (do the service call)
		Optional<Product> result = prodService.getProductById(100);
		assertEquals(true, result.isEmpty());

		verify(prodDao, times(1)).findById(100);
	}

}
