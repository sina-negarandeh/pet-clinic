package org.springframework.samples.petclinic.owner;

import org.junit.Assume;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {
	private PetService petService;
	private static final Stack <Pet> petsCollection = new Stack<>();

	@BeforeEach
	void setUp(@Mock OwnerRepository owners, @Mock Logger logger) {
		PetTimedCache pets = mock(PetTimedCache.class);
		when(pets.get(anyInt()))
			.thenReturn(PetServiceTest.petsCollection.pop());

		this.petService = new PetService(pets, owners, logger);
	}

	@AfterEach
	public void tearDown() {
		this.petService = null;
	}

	@ParameterizedTest(name = "find {1} with petId: {0}")
	@MethodSource("providePetsForFindPet")
	@DisplayName("Should find pet")
	public void shouldFindPet(int petId, Pet pet) {
		Assume.assumeNotNull(petId);
		assertSame(this.petService.findPet(petId), pet);
	}

	private static Pet createPet(String name, int id) {
		Pet pet = new Pet();
		pet.setName(name);
		pet.setId(id);
		PetServiceTest.petsCollection.add(pet);
		return pet;
	}

	public static Collection<Object[]> providePetsForFindPet() {
		String fishName = "Nemo";
		int fishPetId = 1100;
		Pet fish = createPet(fishName, fishPetId);

		String catName = "Garfield";
		int catPetId = 1011;
		Pet cat = createPet(catName, catPetId);

		String dogName = "Snowy";
		int dogPetId = 1010;
		Pet dog = createPet(dogName, dogPetId);

		return Arrays.asList(new Object[][]{{dogPetId, dog}, {catPetId, cat}, {fishPetId, fish}});
	}
}
