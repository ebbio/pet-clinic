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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
 * Tests for {@link DogController}.
 */
@WebMvcTest(DogController.class)
@DisabledInNativeImage
@DisabledInAotMode
class DogControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private DogOfMonthService service;

	private FeaturedDog buddy;

	@BeforeEach
	void setUp() {
		buddy = new FeaturedDog();
		buddy.setId(1);
		buddy.setName("Buddy");
		buddy.setBreed("Golden Retriever");
		buddy.setAge(3);
		buddy.setDescription("A wonderful dog.");
		buddy.setRotationIndex(0);

		DogPhoto photo = new DogPhoto();
		photo.setId(1);
		photo.setPhotoUrl("https://placedog.net/640/480?id=1");
		photo.setCaption("Buddy in the park");
		photo.setDisplayOrder(1);
		photo.setFileSize(204800L);
		photo.setFileType("jpg");

		List<DogPhoto> photos = new ArrayList<>();
		photos.add(photo);
		buddy.setPhotos(photos);
	}

	// -----------------------------------------------------------------------
	// HTML view tests
	// -----------------------------------------------------------------------

	@Test
	void showDogOfMonthPageWithFeaturedDog() throws Exception {
		given(service.getCurrentFeaturedDog()).willReturn(Optional.of(buddy));

		mockMvc.perform(get("/dogs/dog-of-month"))
			.andExpect(status().isOk())
			.andExpect(view().name("dogs/dogOfMonth"))
			.andExpect(model().attributeExists("featuredDog"))
			.andExpect(model().attributeExists("currentMonth"))
			.andExpect(model().attributeExists("currentYear"));
	}

	@Test
	void showDogOfMonthPageWithNoDog() throws Exception {
		given(service.getCurrentFeaturedDog()).willReturn(Optional.empty());

		mockMvc.perform(get("/dogs/dog-of-month"))
			.andExpect(status().isOk())
			.andExpect(view().name("dogs/dogOfMonth"))
			.andExpect(model().attribute("featuredDog", (Object) null));
	}

	// -----------------------------------------------------------------------
	// REST API tests
	// -----------------------------------------------------------------------

	@Test
	void getDogOfMonthApiReturnsDogJson() throws Exception {
		given(service.getCurrentFeaturedDog()).willReturn(Optional.of(buddy));

		mockMvc.perform(get("/api/dogs/dog-of-month").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.name").value("Buddy"))
			.andExpect(jsonPath("$.breed").value("Golden Retriever"))
			.andExpect(jsonPath("$.age").value(3))
			.andExpect(jsonPath("$.description").value("A wonderful dog."))
			.andExpect(jsonPath("$.photos").isArray())
			.andExpect(jsonPath("$.photos[0].url").value("https://placedog.net/640/480?id=1"))
			.andExpect(jsonPath("$.photos[0].caption").value("Buddy in the park"))
			.andExpect(jsonPath("$.featuredMonth").isNumber())
			.andExpect(jsonPath("$.featuredYear").isNumber());
	}

	@Test
	void getDogOfMonthApiReturnsNotFoundWhenNoDog() throws Exception {
		given(service.getCurrentFeaturedDog()).willReturn(Optional.empty());

		mockMvc.perform(get("/api/dogs/dog-of-month").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}

	@Test
	void getFeaturedHistoryReturnsDogsArray() throws Exception {
		given(service.getFeaturedHistory(12, 0)).willReturn(List.of(buddy));

		mockMvc.perform(get("/api/dogs/featured-history").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name").value("Buddy"))
			.andExpect(jsonPath("$[0].breed").value("Golden Retriever"));
	}

	@Test
	void getFeaturedHistoryWithCustomLimitAndOffset() throws Exception {
		given(service.getFeaturedHistory(5, 10)).willReturn(List.of(buddy));

		mockMvc.perform(get("/api/dogs/featured-history?limit=5&offset=10").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name").value("Buddy"));
	}

	@Test
	void getFeaturedHistoryReturnsEmptyArrayWhenNoDogs() throws Exception {
		given(service.getFeaturedHistory(12, 0)).willReturn(List.of());

		mockMvc.perform(get("/api/dogs/featured-history").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$").isEmpty());
	}

}
