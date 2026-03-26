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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service providing business logic for the dog food store.
 */
@Service
public class DogFoodStoreService {

	private final DogFoodProductRepository repository;

	public DogFoodStoreService(DogFoodProductRepository repository) {
		this.repository = repository;
	}

	/**
	 * Returns all dog food products.
	 */
	@Transactional(readOnly = true)
	public List<DogFoodProduct> findAllProducts() {
		return repository.findAll();
	}

	/**
	 * Returns only the products that are currently in stock.
	 */
	@Transactional(readOnly = true)
	public List<DogFoodProduct> findInStockProducts() {
		return repository.findByInStockTrue();
	}

}
