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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller that serves the dog food store HTML page and REST API endpoints.
 */
@Controller
class DogFoodStoreController {

	private final DogFoodStoreService service;

	DogFoodStoreController(DogFoodStoreService service) {
		this.service = service;
	}

	// -----------------------------------------------------------------------
	// HTML view
	// -----------------------------------------------------------------------

	@GetMapping("/store/dog-food")
	public String showDogFoodStore(Model model) {
		List<DogFoodProduct> products = service.findAllProducts();
		model.addAttribute("products", products);
		return "store/dogFoodStore";
	}

	// -----------------------------------------------------------------------
	// REST endpoints
	// -----------------------------------------------------------------------

	@GetMapping("/api/store/dog-food")
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> getDogFoodProductsApi() {
		List<Map<String, Object>> response = service.findAllProducts()
			.stream()
			.map(this::toApiResponse)
			.collect(Collectors.toList());
		return ResponseEntity.ok(response);
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private Map<String, Object> toApiResponse(DogFoodProduct product) {
		return Map.of("id", product.getId() != null ? product.getId() : 0, "name",
				product.getName() != null ? product.getName() : "", "brand",
				product.getBrand() != null ? product.getBrand() : "", "description",
				product.getDescription() != null ? product.getDescription() : "", "price",
				product.getPrice() != null ? product.getPrice() : 0, "weightKg",
				product.getWeightKg() != null ? product.getWeightKg() : 0, "inStock", product.isInStock(), "imageUrl",
				product.getImageUrl() != null ? product.getImageUrl() : "");
	}

}
