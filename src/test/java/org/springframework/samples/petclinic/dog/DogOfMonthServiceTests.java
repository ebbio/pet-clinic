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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

/**
 * Unit tests for {@link DogOfMonthService}.
 */
@ExtendWith(MockitoExtension.class)
class DogOfMonthServiceTests {

	@Mock
	private FeaturedDogRepository repository;

	private DogOfMonthService service;

	@BeforeEach
	void setUp() {
		service = new DogOfMonthService(repository);
	}

	// -----------------------------------------------------------------------
	// Rotation algorithm tests
	// -----------------------------------------------------------------------

	@Test
	void rotationSelectsCorrectDogForDate() {
		FeaturedDog dog3 = buildDog(3, "Luna", "Siberian Husky");
		given(repository.count()).willReturn(12L);
		// For date 2026-03-24: index = (2026 * 12 + 2) % 12 = (24312 + 2) % 12 = 24314 %
		// 12 = 2
		// dog at rotation_index 2 → but we stub findByRotationIndex(2) → dog3 is
		// rotationIndex 3?
		// Let's compute: (2026*12 + (3-1)) % 12 = (24312 + 2) % 12 = 24314 % 12 = 2
		given(repository.findByRotationIndex(2)).willReturn(Optional.of(dog3));

		Optional<FeaturedDog> result = service.getFeaturedDogForDate(LocalDate.of(2026, 3, 24));

		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo("Luna");
	}

	@Test
	void rotationCyclesAfterTwelveDogs() {
		// January 0001: (1*12 + 0) % 12 = 12 % 12 = 0
		// January 0002: (2*12 + 0) % 12 = 24 % 12 = 0
		FeaturedDog dog0 = buildDog(0, "Buddy", "Golden Retriever");
		given(repository.count()).willReturn(12L);
		given(repository.findByRotationIndex(0)).willReturn(Optional.of(dog0));

		Optional<FeaturedDog> jan1 = service.getFeaturedDogForDate(LocalDate.of(1, 1, 1));
		Optional<FeaturedDog> jan2 = service.getFeaturedDogForDate(LocalDate.of(2, 1, 1));

		assertThat(jan1).isPresent();
		assertThat(jan2).isPresent();
		assertThat(jan1.get().getName()).isEqualTo("Buddy");
		assertThat(jan2.get().getName()).isEqualTo("Buddy");
	}

	@Test
	void rotationAdvancesMonthByMonth() {
		// Feb 2026: (2026*12 + 1) % 12 = (24312+1) % 12 = 24313 % 12 = 1
		// Mar 2026: (2026*12 + 2) % 12 = 24314 % 12 = 2
		FeaturedDog dog1 = buildDog(1, "Bella", "Lab");
		FeaturedDog dog2 = buildDog(2, "Max", "Shepherd");
		given(repository.count()).willReturn(12L);
		given(repository.findByRotationIndex(1)).willReturn(Optional.of(dog1));
		given(repository.findByRotationIndex(2)).willReturn(Optional.of(dog2));

		Optional<FeaturedDog> feb = service.getFeaturedDogForDate(LocalDate.of(2026, 2, 1));
		Optional<FeaturedDog> mar = service.getFeaturedDogForDate(LocalDate.of(2026, 3, 1));

		assertThat(feb).isPresent();
		assertThat(mar).isPresent();
		assertThat(feb.get().getName()).isEqualTo("Bella");
		assertThat(mar.get().getName()).isEqualTo("Max");
	}

	@Test
	void rotationWithFewerThanTwelveDogsCyclesThroughAvailable() {
		// Only 3 dogs: rotationIndex is computed mod 3
		// Jan 2026: (2026*12 + 0) % 3 = 24312 % 3 = 0
		// Feb 2026: (2026*12 + 1) % 3 = 24313 % 3 = 1
		// Mar 2026: (2026*12 + 2) % 3 = 24314 % 3 = 2
		// Apr 2026: (2026*12 + 3) % 3 = 24315 % 3 = 0 ← cycles back
		FeaturedDog dog0 = buildDog(0, "Buddy", "Golden Retriever");
		given(repository.count()).willReturn(3L);
		given(repository.findByRotationIndex(0)).willReturn(Optional.of(dog0));

		Optional<FeaturedDog> jan = service.getFeaturedDogForDate(LocalDate.of(2026, 1, 1));
		Optional<FeaturedDog> apr = service.getFeaturedDogForDate(LocalDate.of(2026, 4, 1));

		assertThat(jan).isPresent().hasValueSatisfying(d -> assertThat(d.getName()).isEqualTo("Buddy"));
		assertThat(apr).isPresent().hasValueSatisfying(d -> assertThat(d.getName()).isEqualTo("Buddy"));
	}

	@Test
	void rotationUsesAtMostTwelveDogs() {
		// With 15 dogs, effective pool is 12
		// Jan 2026: (2026*12 + 0) % 12 = 24312 % 12 = 0
		FeaturedDog dog0 = buildDog(0, "Buddy", "Golden Retriever");
		given(repository.count()).willReturn(15L);
		given(repository.findByRotationIndex(0)).willReturn(Optional.of(dog0));

		Optional<FeaturedDog> result = service.getFeaturedDogForDate(LocalDate.of(2026, 1, 1));

		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo("Buddy");
	}

	@Test
	void returnsEmptyWhenNoDogs() {
		given(repository.count()).willReturn(0L);

		Optional<FeaturedDog> result = service.getFeaturedDogForDate(LocalDate.of(2026, 3, 1));

		assertThat(result).isEmpty();
	}

	@Test
	void yearEndTransitionDecemberToJanuary() {
		// Dec 2025: (2025*12 + 11) % 12 = (24300 + 11) % 12 = 24311 % 12 = 11
		// Jan 2026: (2026*12 + 0) % 12 = 24312 % 12 = 0
		FeaturedDog dog11 = buildDog(11, "Rosie", "Border Collie");
		FeaturedDog dog0 = buildDog(0, "Buddy", "Golden Retriever");
		given(repository.count()).willReturn(12L);
		given(repository.findByRotationIndex(11)).willReturn(Optional.of(dog11));
		given(repository.findByRotationIndex(0)).willReturn(Optional.of(dog0));

		Optional<FeaturedDog> dec = service.getFeaturedDogForDate(LocalDate.of(2025, 12, 31));
		Optional<FeaturedDog> jan = service.getFeaturedDogForDate(LocalDate.of(2026, 1, 1));

		assertThat(dec).isPresent().hasValueSatisfying(d -> assertThat(d.getName()).isEqualTo("Rosie"));
		assertThat(jan).isPresent().hasValueSatisfying(d -> assertThat(d.getName()).isEqualTo("Buddy"));
	}

	// -----------------------------------------------------------------------
	// Photo validation tests
	// -----------------------------------------------------------------------

	@Test
	void validJpgPhotoPassesValidation() {
		assertThat(service.isValidPhoto("jpg", 1024)).isTrue();
	}

	@Test
	void validJpegPhotoPassesValidation() {
		assertThat(service.isValidPhoto("jpeg", 1024)).isTrue();
	}

	@Test
	void validPngPhotoPassesValidation() {
		assertThat(service.isValidPhoto("png", 2 * 1024 * 1024L)).isTrue();
	}

	@Test
	void validGifPhotoPassesValidation() {
		assertThat(service.isValidPhoto("gif", 500_000L)).isTrue();
	}

	@Test
	void photoAtExactlyFiveMbIsValid() {
		assertThat(service.isValidPhoto("jpg", DogOfMonthService.MAX_FILE_SIZE)).isTrue();
	}

	@Test
	void photoExceedingFiveMbIsRejected() {
		assertThat(service.isValidPhoto("jpg", DogOfMonthService.MAX_FILE_SIZE + 1)).isFalse();
	}

	@Test
	void unsupportedFormatBmpIsRejected() {
		assertThat(service.isValidPhoto("bmp", 1024)).isFalse();
	}

	@Test
	void unsupportedFormatExeIsRejected() {
		assertThat(service.isValidPhoto("exe", 1024)).isFalse();
	}

	@Test
	void nullFileTypeIsRejected() {
		assertThat(service.isValidPhoto(null, 1024)).isFalse();
	}

	@Test
	void zeroFileSizeIsRejected() {
		assertThat(service.isValidPhoto("jpg", 0)).isFalse();
	}

	@Test
	void fileTypeIsCaseInsensitive() {
		assertThat(service.isValidPhoto("JPG", 1024)).isTrue();
		assertThat(service.isValidPhoto("PNG", 1024)).isTrue();
		assertThat(service.isValidPhoto("GIF", 1024)).isTrue();
	}

	// -----------------------------------------------------------------------
	// getFeaturedHistory tests
	// -----------------------------------------------------------------------

	@Test
	void featuredHistoryReturnsDogs() {
		FeaturedDog dog0 = buildDog(0, "Buddy", "Golden Retriever");
		FeaturedDog dog1 = buildDog(1, "Bella", "Lab");
		given(repository.findAll(any(org.springframework.data.domain.Pageable.class)))
			.willReturn(new PageImpl<>(List.of(dog0, dog1)));

		List<FeaturedDog> history = service.getFeaturedHistory(12, 0);

		assertThat(history).hasSize(2);
		assertThat(history.get(0).getName()).isEqualTo("Buddy");
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private FeaturedDog buildDog(int rotationIndex, String name, String breed) {
		FeaturedDog dog = new FeaturedDog();
		dog.setId(rotationIndex + 1);
		dog.setName(name);
		dog.setBreed(breed);
		dog.setAge(3);
		dog.setDescription("A wonderful dog.");
		dog.setRotationIndex(rotationIndex);
		return dog;
	}

}
