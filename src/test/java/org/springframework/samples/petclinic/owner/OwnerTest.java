package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {
	private Owner owner;

	@BeforeEach
	public void setUp() {
		this.owner = new Owner();
		this.owner.setFirstName("John");
		this.owner.setLastName("Doe");
	}

	@Test
	@DisplayName("Should get address")
	public void shouldGetAddress() {
		assertNull(this.owner.getAddress());
	}

	@Test
	@DisplayName("Should set address")
	public void shouldSetAddress() {
		String address = "Apartment 1c 2725 Atha Drive Street";
		this.owner.setAddress(address);
		assertEquals(this.owner.getAddress(), address);
	}

	@Test
	@DisplayName("Should get city")
	public void shouldGetCity() {
		String city = "Lancaster";
		this.owner.setCity(city);
		assertEquals(this.owner.getCity(), city);
	}

	@Test
	@DisplayName("Should get telephone")
	public void shouldGetTelephone() {
		String telephone = "661-728-8754";
		this.owner.setTelephone(telephone);
		assertEquals(this.owner.getTelephone(), telephone);
	}

	@Test
	@DisplayName("Should get empty PetsInternal")
	public void shouldGetEmptyPetsInternal() {
		Set<Pet> pets = this.owner.getPetsInternal();
		assertTrue(pets instanceof HashSet);
	}

	@Test
	@DisplayName("Should get PetsInternal")
	public void shouldGetPetsInternal() {
		Set<Pet> pets = new HashSet<>();
		pets.add(new Pet());
		this.owner.setPetsInternal(pets);

		assertSame(this.owner.getPetsInternal(), pets);
		assertFalse(this.owner.getPetsInternal().isEmpty());
		assertEquals(this.owner.getPetsInternal().size(), 1);
	}

	@Test
	@DisplayName("Should add new pet to PetsInternal")
	public void shouldAddNewPetToPetsInternal() {
		String petName = "Snowy";
		Pet pet = new Pet();
		pet.setName(petName);
		this.owner.addPet(pet);

		assertTrue(pet.isNew());
		assertSame(this.owner.getPet(petName), pet);
	}

	@Test
	@DisplayName("Should not add not new pet to PetsInternal")
	public void shouldNotAddNotNewPetToPetsInternal() {
		String petName = "Snowy";
		Integer petId = 1010;
		Pet pet = new Pet();
		pet.setName(petName);
		pet.setId(petId);
		this.owner.addPet(pet);

		assertFalse(pet.isNew());
		assertNull(this.owner.getPet(petName));
	}

	@Test
	@DisplayName("Should get new pet by name")
	public void shouldGetNewPetByName() {
		String petName = "Snowy";
		Pet pet = new Pet();
		pet.setName(petName);
		this.owner.addPet(pet);

		assertTrue(pet.isNew());
		assertSame(this.owner.getPet(petName, false), pet);
	}

	@Test
	@DisplayName("Should get not new pet by name")
	public void shouldGetNotNewPetByName() {
		String petName = "Snowy";
		Integer petId = 1010;
		Pet pet = new Pet();
		pet.setName(petName);
		this.owner.addPet(pet);

		pet.setId(petId);
		assertFalse(pet.isNew());
		assertSame(this.owner.getPet(petName, true), pet);
	}

	@Test
	@DisplayName("Should remove pet")
	public void shouldRemovePet() {
		String dogName = "Snowy";
		Pet dog = new Pet();
		dog.setName(dogName);
		this.owner.addPet(dog);

		String catName = "Garfield";
		Pet cat = new Pet();
		cat.setName(catName);
		this.owner.addPet(cat);

		String FishName = "Nemo";
		Pet fish = new Pet();
		fish.setName(FishName);
		this.owner.addPet(fish);

		List<Pet> pets = new ArrayList<>();
		pets.add(cat);
		pets.add(fish);

		this.owner.removePet(dog);
		assertEquals(this.owner.getPets(), pets);
	}

	@AfterEach
	public void tearDown() {
		this.owner = null;
	}
}
