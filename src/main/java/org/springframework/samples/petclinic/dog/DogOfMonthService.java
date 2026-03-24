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
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service providing business logic for the "Dog of the Month" feature.
 *
 * <p>
 * The featured dog is determined by a stateless round-robin algorithm: the rotation index
 * for the current month is computed as
 * {@code ((year * 12) + (month - 1)) % effectiveDogs} where
 * {@code effectiveDogs = min(totalDogs, 12)}.
 * </p>
 */
@Service
public class DogOfMonthService {

	/** Maximum allowed file size for dog photos (5 MB). */
	static final long MAX_FILE_SIZE = 5 * 1024 * 1024L;

	/** Supported image formats. */
	static final Set<String> ALLOWED_FILE_TYPES = Set.of("jpg", "jpeg", "png", "gif");

	/** Maximum number of dogs participating in the rotation. */
	static final int MAX_ROTATION_POOL = 12;

	private final FeaturedDogRepository repository;

	public DogOfMonthService(FeaturedDogRepository repository) {
		this.repository = repository;
	}

	/**
	 * Returns the featured dog for the current UTC calendar month. If no dog is found at
	 * the computed rotation index, falls back to the most recently added dog.
	 */
	public Optional<FeaturedDog> getCurrentFeaturedDog() {
		return getFeaturedDogForDate(LocalDate.now(ZoneOffset.UTC));
	}

	/**
	 * Returns the featured dog for the given date (exposed for testing).
	 */
	Optional<FeaturedDog> getFeaturedDogForDate(LocalDate date) {
		long totalDogs = repository.count();
		if (totalDogs == 0) {
			return Optional.empty();
		}
		int effectiveDogs = (int) Math.min(totalDogs, MAX_ROTATION_POOL);
		int rotationIndex = ((date.getYear() * 12) + (date.getMonthValue() - 1)) % effectiveDogs;
		return repository.findByRotationIndex(rotationIndex);
	}

	/**
	 * Returns a page of all featured dogs sorted by rotation index ascending.
	 * @param limit maximum number of results
	 * @param offset zero-based row offset
	 */
	public List<FeaturedDog> getFeaturedHistory(int limit, int offset) {
		int pageSize = Math.max(1, limit);
		int pageNumber = offset / pageSize;
		return repository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("rotationIndex"))).getContent();
	}

	/**
	 * Validates that a photo file type and size meet the requirements.
	 * @param fileType file extension (e.g. "jpg", "png", "gif")
	 * @param fileSize file size in bytes
	 * @return {@code true} if the photo is valid
	 */
	public boolean isValidPhoto(String fileType, long fileSize) {
		if (fileType == null || fileSize <= 0) {
			return false;
		}
		return ALLOWED_FILE_TYPES.contains(fileType.toLowerCase()) && fileSize <= MAX_FILE_SIZE;
	}

}
