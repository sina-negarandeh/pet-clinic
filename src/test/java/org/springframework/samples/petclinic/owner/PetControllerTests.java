package org.springframework.samples.petclinic.owner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;


@WebMvcTest(value = PetController.class,
	includeFilters = {
		@ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetService.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = LoggerConfig.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetTimedCache.class, type = FilterType.ASSIGNABLE_TYPE),
	}
)
class PetControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetRepository petRepository;

	@MockBean
	private OwnerRepository owners;

	private PetType petType;
	private Pet pet;
	private Set<Pet> pets;

	private Owner owner;

	@BeforeEach
	void setup() {
		this.petType = new PetType();
		this.petType.setName("Dog");

		this.pet = new Pet();
		this.pet.setId(1010);

		this.pets = new HashSet<>();
		this.pets.add(pet);

		this.owner = new Owner();

		when(this.petRepository.findById(anyInt())).thenReturn((this.pet));
		when(this.owners.findById(anyInt())).thenReturn(this.owner);
		when(this.petRepository.findPetTypes()).thenReturn(Lists.newArrayList(this.petType));
	}
}
