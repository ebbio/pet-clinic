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
package org.springframework.samples.petclinic.dog;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller that serves the "Dog of the Month" HTML page and REST API endpoints.
 */
@Controller
class DogController {

	private final DogOfMonthService service;

	DogController(DogOfMonthService service) {
		this.service = service;
	}

	// -----------------------------------------------------------------------
	// HTML view
	// -----------------------------------------------------------------------

	@GetMapping("/dogs/dog-of-month")
	public String showDogOfMonth(Model model) {
		LocalDate today = LocalDate.now(ZoneOffset.UTC);
		Optional<FeaturedDog> dog = service.getCurrentFeaturedDog();
		model.addAttribute("featuredDog", dog.orElse(null));
		model.addAttribute("currentMonth", today.getMonth().name());
		model.addAttribute("currentYear", today.getYear());
		return "dogs/dogOfMonth";
	}

	// -----------------------------------------------------------------------
	// REST endpoints
	// -----------------------------------------------------------------------

	@GetMapping("/api/dogs/dog-of-month")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getDogOfMonthApi() {
		LocalDate today = LocalDate.now(ZoneOffset.UTC);
		Optional<FeaturedDog> dog = service.getCurrentFeaturedDog();
		if (dog.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(toApiResponse(dog.get(), today.getMonthValue(), today.getYear()));
	}

	@GetMapping("/api/dogs/featured-history")
	@ResponseBody
	public List<Map<String, Object>> getFeaturedHistoryApi(@RequestParam(defaultValue = "12") int limit,
			@RequestParam(defaultValue = "0") int offset) {
		return service.getFeaturedHistory(limit, offset)
			.stream()
			.map(d -> toApiResponse(d, null, null))
			.collect(Collectors.toList());
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private Map<String, Object> toApiResponse(FeaturedDog dog, Integer month, Integer year) {
		List<Map<String, Object>> photos = dog.getPhotos()
			.stream()
			.map(p -> Map.<String, Object>of("id", p.getId() != null ? p.getId() : 0, "url",
					p.getPhotoUrl() != null ? p.getPhotoUrl() : "", "caption",
					p.getCaption() != null ? p.getCaption() : "", "displayOrder",
					p.getDisplayOrder() != null ? p.getDisplayOrder() : 0))
			.collect(Collectors.toList());

		return Map.of("id", dog.getId() != null ? dog.getId() : 0, "name", dog.getName() != null ? dog.getName() : "",
				"breed", dog.getBreed() != null ? dog.getBreed() : "", "age", dog.getAge() != null ? dog.getAge() : 0,
				"description", dog.getDescription() != null ? dog.getDescription() : "", "photos", photos,
				"featuredMonth", month != null ? month : 0, "featuredYear", year != null ? year : 0);
	}

}
