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

import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

/**
 * JPA entity representing a featured dog for the "Dog of the Month" section.
 */
@Entity
@Table(name = "featured_dogs")
public class FeaturedDog extends BaseEntity {

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "breed", length = 80)
	private String breed;

	@Column(name = "age")
	private Integer age;

	@Column(name = "description", length = 200)
	private String description;

	@Column(name = "rotation_index")
	private Integer rotationIndex;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "dog_id")
	@OrderBy("displayOrder ASC")
	private List<DogPhoto> photos = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRotationIndex() {
		return rotationIndex;
	}

	public void setRotationIndex(Integer rotationIndex) {
		this.rotationIndex = rotationIndex;
	}

	public List<DogPhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<DogPhoto> photos) {
		this.photos = photos;
	}

}
