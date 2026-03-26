/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.store;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Tests for {@link DogFoodStoreController}.
 */
@WebMvcTest(DogFoodStoreController.class)
@DisabledInNativeImage
@DisabledInAotMode
class DogFoodStoreControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private DogFoodStoreService service;

	private DogFoodProduct chickenKibble;

	@BeforeEach
	void setUp() {
		chickenKibble = new DogFoodProduct();
		chickenKibble.setId(1);
		chickenKibble.setName("Premium Chicken Kibble");
		chickenKibble.setBrand("PawsFirst");
		chickenKibble.setDescription("High-protein dry food.");
		chickenKibble.setPrice(new BigDecimal("29.99"));
		chickenKibble.setWeightKg(5.0);
		chickenKibble.setInStock(true);
		chickenKibble.setImageUrl("https://example.com/kibble.jpg");
	}

	// -----------------------------------------------------------------------
	// HTML view tests
	// -----------------------------------------------------------------------

	@Test
	void showDogFoodStorePageWithProducts() throws Exception {
		given(service.findAllProducts()).willReturn(List.of(chickenKibble));

		mockMvc.perform(get("/store/dog-food"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/dogFoodStore"))
			.andExpect(model().attributeExists("products"));
	}

	@Test
	void showDogFoodStorePageWithNoProducts() throws Exception {
		given(service.findAllProducts()).willReturn(List.of());

		mockMvc.perform(get("/store/dog-food"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/dogFoodStore"))
			.andExpect(model().attributeExists("products"));
	}

	// -----------------------------------------------------------------------
	// REST API tests
	// -----------------------------------------------------------------------

	@Test
	void getDogFoodProductsApiReturnsProductList() throws Exception {
		given(service.findAllProducts()).willReturn(List.of(chickenKibble));

		mockMvc.perform(get("/api/store/dog-food").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name").value("Premium Chicken Kibble"))
			.andExpect(jsonPath("$[0].brand").value("PawsFirst"))
			.andExpect(jsonPath("$[0].price").value(29.99))
			.andExpect(jsonPath("$[0].weightKg").value(5.0))
			.andExpect(jsonPath("$[0].inStock").value(true))
			.andExpect(jsonPath("$[0].imageUrl").value("https://example.com/kibble.jpg"));
	}

	@Test
	void getDogFoodProductsApiReturnsEmptyArrayWhenNoProducts() throws Exception {
		given(service.findAllProducts()).willReturn(List.of());

		mockMvc.perform(get("/api/store/dog-food").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$").isEmpty());
	}

}
