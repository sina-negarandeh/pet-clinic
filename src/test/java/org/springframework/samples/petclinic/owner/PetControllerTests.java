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

	@Test
	void Given_SendingGETRequest_When_InitFormCreation_Then_SuccessfulConnection() throws Exception {
		mockMvc.perform(get("/owners/1/pets/new"))
		.andExpect(model().attributeExists("pet"))
		.andExpect(status().isOk());
	}

	@Test
	void Given_SendingPOSTRequest_When_ProcessFormCreation_Then_SuccessfulCreation() throws Exception {
		mockMvc.perform(post("/owners/1/pets/new")
		.param("type", "Dog")
		.param("name", "Snowy")
		.param("birthDate", "2020-08-08"))
		.andExpect(status().is3xxRedirection());
	}

	@Test
	void Given_SendingPOSTRequest_When_ProcessFormCreation_Then_FailedConnection() throws Exception {
		mockMvc.perform(post("/owners/1/pets/new")
		.param("name", "Snowy")
		.param("birthDate", "2020-08-08"))
		.andExpect(model().attributeHasFieldErrors("pet", "type"))
		.andExpect(model().attributeHasFieldErrorCode("pet", "type", "required"))
		.andExpect(model().attributeHasErrors("pet"))
		.andExpect(model().attributeHasNoErrors("owner"))
		.andExpect(status().isOk());
	}

	@Test
	void Given_SendingGETRequest_When_InitEditForm_Then_SuccessfulUpdate() throws Exception {
		mockMvc.perform(get("/owners/1/pets/1/edit"))
		.andExpect(model().attributeExists("pet"))
		.andExpect(status().isOk());
	}

	@Test
	public void Given_SendingPOSTRequest_When_ProcessEditForm_Then_FailedUpdate() throws Exception {
		List<PetType> pets = new ArrayList<>();

		PetType fish = new PetType();
		fish.setName("Fish");

		PetType mouse = new PetType();
		fish.setName("Mouse");

		pets.add(this.petType);
		pets.add(fish);
		pets.add(mouse);

		when(petRepository.findPetTypes()).thenReturn(pets);

		mockMvc.perform(post("/owners/1/pets/1/edit")
		.param("type", "Dog")
		.param("name", "Snowy")
		.param("birthDate", "2020-08-08"))
		.andExpect(status().is3xxRedirection());
	}

	@Test
	void Given_SendingPOSTRequest_When_ProcessEditForm_Then_SuccessfulCreation() throws Exception {
		mockMvc.perform(post("/owners/1/pets/1/edit")
		.param("name", "Snowy")
		.param("type", "Dog")
		.param("birthDate", "2020-08-08"))
		.andExpect(status().is3xxRedirection());
	}

	@Test
	void Given_SendingPOSTRequest_When_ProcessEditForm_Then_FailedConnection() throws Exception {
		mockMvc.perform(post("/owners/1/pets/1/edit")
		.param("birthDate", "2020-08-08")
		.param("name", "Snowy"))
		.andExpect(model().attributeHasNoErrors("owner"))
		.andExpect(model().attributeHasErrors("pet"))
		.andExpect(status().isOk());
	}
}
