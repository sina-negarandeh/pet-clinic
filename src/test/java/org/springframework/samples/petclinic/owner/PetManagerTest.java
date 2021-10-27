package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

/*
* For each test that has been written, the following has been commented.
* Type of Test Doubles: Dummy Object, Test Stub, Test Spy, Mock Object or Fake Object
* TDD style: Classical or Mockist Testing
* Philosophy of Testing: State or Behavior Verification
* */

@ExtendWith(MockitoExtension.class)
class PetManagerTest {
	private PetManager petManager;
	// Dummy Objects if not specified later in each test
	@Mock PetTimedCache petTimedCache;
	@Mock OwnerRepository ownerRepository;
	@Mock Logger logger;

	public Owner createOwner(int ownerId) {
		Owner owner = new Owner();
		owner.setId(ownerId);
		return owner;
	}

	public Pet createPet(int petId) {
		Pet pet = new Pet();
		pet.setId(petId);
		return pet;
	}


	/*
	* Type of Test Doubles: Dummy Object, Mock Object
	* TDD style: Mockist Testing
	* Philosophy of Testing: State Verification
	* */

	@Test
	@DisplayName("Should return owner when ownerId is given")
	public void Should_ReturnOwner_When_OwnerIdIsGiven() {
		this.petManager = new PetManager(petTimedCache, ownerRepository, logger);

		List<Owner> owners = new ArrayList<>();

		Owner firstOwner = createOwner(427052389);
		owners.add(firstOwner);

		Owner secondOwner = createOwner(509501274);
		owners.add(secondOwner);

		Owner thirdOwner = createOwner(317602273);
		owners.add(thirdOwner);

		int existingOwnerId = secondOwner.getId();

		when(ownerRepository.findById(anyInt())).thenAnswer((invocation) -> {
			Object[] args = invocation.getArguments();
			int ownerId = (Integer) args[0];
			return owners.stream()
				.filter(owner -> owner.getId() == ownerId)
				.findAny().orElse(null);
		});

		assertEquals(existingOwnerId, petManager.findOwner(existingOwnerId).getId());
	}


	/*
	 * Type of Test Doubles: Dummy Object, Test Spy
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: Behavior Verification
	 * */

	@Test
	@DisplayName("Given ownerId when finding owner then log info")
	public void Given_OwnerId_When_FindingOwner_Then_LogInfo() {
		Logger spyLogger = Mockito.spy(logger);
		this.petManager = new PetManager(petTimedCache, ownerRepository, spyLogger);

		Owner owner = createOwner(427052389);

		petManager.findOwner(owner.getId());

		Mockito.verify(spyLogger).info("find owner {}", owner.getId());
	}


	/*
	 * Type of Test Doubles: Dummy Object
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: State Verification
	 * */

	@Test
	@DisplayName("Should add a new pet for owner when owner is given")
	public void Should_AddNewPetForOwner_When_OwnerIsGiven() {
		this.petManager = new PetManager(petTimedCache, ownerRepository, logger);

		Owner owner = createOwner(427052389);

		assertEquals(owner, petManager.newPet(owner).getOwner());
	}


	/*
	 * Type of Test Doubles: Dummy Object, Test Spy
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: Behavior Verification
	 * */

	@Test
	@DisplayName("Given owner when adding a new pet then log info")
	public void Given_Owner_When_AddingNewPet_Then_LogInfo() {
		Logger spyLogger = Mockito.spy(logger);
		this.petManager = new PetManager(petTimedCache, ownerRepository, spyLogger);

		Owner owner = createOwner(427052389);

		petManager.newPet(owner);

		Mockito.verify(spyLogger).info("add pet for owner {}", owner.getId());
	}


	/*
	 * Type of Test Doubles: Dummy Object, Mock Object
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: State Verification
	 * */

	@Test
	@DisplayName("Should return pet when petId is given")
	public void Should_ReturnPet_When_PetIdIsGiven() {
		this.petManager = new PetManager(petTimedCache, ownerRepository, logger);

		List<Pet> pets = new ArrayList<>();

		Pet dog = createPet(533960);
		pets.add(dog);

		Pet cat = createPet(580597);
		pets.add(cat);

		Pet fish = createPet(813671);
		pets.add(fish);

		when(petTimedCache.get(anyInt())).thenAnswer((invocation) -> {
				Object[] args = invocation.getArguments();
				int petId = (Integer) args[0];
				return pets.stream().filter(pet -> petId == pet.getId()).findAny().orElse(null);
		});

		assertEquals(cat, petManager.findPet(cat.getId()));
	}


	/*
	 * Type of Test Doubles: Dummy Object, Test Spy
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: Behavior Verification
	 * */

	@Test
	@DisplayName("Given petId when finding pet then log info")
	public void Given_PetId_When_FindingPet_Then_LogInfo() {
		PetTimedCache spyPetTimedCache = Mockito.spy(petTimedCache);
		Logger spyLogger = Mockito.spy(logger);
		this.petManager = new PetManager(spyPetTimedCache, ownerRepository, spyLogger);

		Pet pet = createPet(533960);

		petManager.findPet(pet.getId());

		Mockito.verify(spyLogger).info("find pet by id {}", pet.getId());
		Mockito.verify(spyPetTimedCache, times(1)).get(pet.getId());
	}


	/*
	 * Type of Test Doubles: Dummy Object, Test Spy
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: Behavior Verification
	 * */

	@Test
	@DisplayName("Given pet and owner when saving pet then log info")
	public void Given_PetAndOwner_When_SavingPet_Then_LogInfo() {
		PetTimedCache spyPetTimedCache = Mockito.spy(petTimedCache);
		Logger spyLogger = Mockito.spy(logger);
		this.petManager = new PetManager(spyPetTimedCache, ownerRepository, spyLogger);

		Owner owner = createOwner(427052389);

		Pet cat = createPet(533960);

		petManager.savePet(cat, owner);

		Mockito.verify(spyPetTimedCache, times(1)).save(cat);
		Mockito.verify(spyLogger).info("save pet {}", cat.getId());
	}


	/*
	 * Type of Test Doubles: Dummy Object, Test Stub
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: State Verification
	 * */

	@Test
	@DisplayName("Should return owner pets when ownerId is given")
	public void Should_ReturnOwnerPets_When_ownerIdIsGiven() {
		this.petManager = new PetManager(petTimedCache, ownerRepository, logger);

		Owner owner = createOwner(427052389);

		Pet cat = createPet(533960);

		Set<Pet> pets = new HashSet<>();
		pets.add(cat);

		owner.setPetsInternal(pets);

		when(ownerRepository.findById(anyInt())).thenReturn(owner);

		petManager.getOwnerPets(owner.getId());

		List<Pet> newPets = new ArrayList<>(pets);

		assertEquals(newPets, petManager.getOwnerPets(owner.getId()));
	}


	/*
	 * Type of Test Doubles: Dummy Object, Test Stub, Test Spy
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: Behavior Verification
	 * */

	@Test
	@DisplayName("Given ownerId when getting owner pets then log info")
	public void Given_OwnerId_When_GettingOwnerPets_Then_LogInfo() {
		Logger spyLogger = Mockito.spy(logger);
		this.petManager = new PetManager(petTimedCache, ownerRepository, spyLogger);

		Owner owner = createOwner(427052389);

		Owner spyOwner = Mockito.spy(owner);

		when(ownerRepository.findById(anyInt())).thenReturn(spyOwner);

		petManager.getOwnerPets(spyOwner.getId());

		Mockito.verify(spyLogger).info("finding the owner's pets by id {}", owner.getId());
		Mockito.verify(spyOwner, times(1)).getPets();
	}


	/*
	 * Type of Test Doubles: Dummy Object, Test Stub
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: State Verification
	 * */

	@Test
	@DisplayName("Should return pet types when ownerId is given")
	public void Should_ReturnPetTypes_When_OwnerIdIsGiven() {
		this.petManager = new PetManager(petTimedCache, ownerRepository, logger);

		Owner owner = createOwner(427052389);

		PetType catType = new PetType();
		catType.setName("Cat");

		Pet cat = createPet(533960);
		cat.setType(catType);

		PetType dogType = new PetType();
		dogType.setName("Dog");

		Pet dog = createPet(580597);
		dog.setType(dogType);

		Set<Pet> pets = new HashSet<>();
		pets.add(cat);
		pets.add(dog);

		owner.setPetsInternal(pets);

		when(ownerRepository.findById(anyInt())).thenReturn(owner);

		Set<PetType> newPets = new HashSet<>();
		newPets.add(catType);
		newPets.add(dogType);

		assertEquals(newPets, petManager.getOwnerPetTypes(dog.getId()));
	}


	/*
	 * Type of Test Doubles: Dummy Object, Test Stub, Test Spy
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: Behavior Verification
	 * */

	@Test
	@DisplayName("Given ownerId when getting owner pet types then log info")
	public void Given_OwnerId_When_GettingOwnerPetTypes_Then_LogInfo() {
		Logger spyLogger = Mockito.spy(logger);
		this.petManager = new PetManager(petTimedCache, ownerRepository, spyLogger);

		Owner owner = createOwner(427052389);

		Owner spyOwner = Mockito.spy(owner);

		when(ownerRepository.findById(anyInt())).thenReturn(spyOwner);

		petManager.getOwnerPetTypes(spyOwner.getId());

		Mockito.verify(spyLogger).info("finding the owner's petTypes by id {}", owner.getId());
		Mockito.verify(spyOwner, times(1)).getPets();
	}


	/*
	 * Type of Test Doubles: Dummy Object, Test Stub
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: State Verification
	 * */

	@Test
	@DisplayName("Should return visits when start and end dates are given for a pet with petId")
	public void Should_ReturnVisits_When_StartAndEndDatesAreGivenForPetWithPetId() {
		this.petManager = new PetManager(petTimedCache, ownerRepository, logger);

		Pet cat = createPet(533960);

		LinkedHashSet<Visit> visits = new LinkedHashSet<>();

		Visit firstVisit = new Visit().setDate(LocalDate.of(2020, 12, 1));
		Visit secondVisit = new Visit().setDate(LocalDate.of(2020, 12, 4));
		Visit thirdVisit = new Visit().setDate(LocalDate.of(2020, 12, 7));
		Visit fourthVisit = new Visit().setDate(LocalDate.of(2021, 1, 2));
		Visit fifthVisit = new Visit().setDate(LocalDate.of(2021, 3, 7));
		Visit sixthVisit = new Visit().setDate(LocalDate.of(2021, 4, 15));

		visits.add(firstVisit);
		visits.add(secondVisit);
		visits.add(thirdVisit);
		visits.add(fourthVisit);
		visits.add(fifthVisit);
		visits.add(sixthVisit);

		cat.setVisitsInternal(visits);

		when(petTimedCache.get(anyInt())).thenReturn(cat);

		assertEquals(
			cat.getVisitsBetween(firstVisit.getDate(), sixthVisit.getDate()),
			petManager.getVisitsBetween(cat.getId(), firstVisit.getDate(), sixthVisit.getDate())
		);
		assertEquals(
			cat.getVisitsBetween(secondVisit.getDate(), fifthVisit.getDate()),
			petManager.getVisitsBetween(cat.getId(), secondVisit.getDate(), fifthVisit.getDate())
		);
		assertEquals(
			cat.getVisitsBetween(thirdVisit.getDate(), fourthVisit.getDate()),
			petManager.getVisitsBetween(cat.getId(), thirdVisit.getDate(), fourthVisit.getDate())
		);
	}


	/*
	 * Type of Test Doubles: Dummy Object, Test Stub, Test Spy
	 * TDD style: Mockist Testing
	 * Philosophy of Testing: Behavior Verification
	 * */

	@Test
	@DisplayName("Given petId, start and end dates when getting visits between then log info")
	public void Given_PetIdStartAndEndDates_When_GettingVisitsBetween_Then_LogInfo() {
		Logger spyLogger = Mockito.spy(logger);
		this.petManager = new PetManager(petTimedCache, ownerRepository, spyLogger);

		Pet pet = createPet(533960);

		LinkedHashSet<Visit> visits = new LinkedHashSet<>();

		Visit firstVisit = new Visit().setDate(LocalDate.of(2020, 12, 1));
		Visit secondVisit = new Visit().setDate(LocalDate.of(2020, 12, 4));

		visits.add(firstVisit);
		visits.add(secondVisit);

		pet.setVisitsInternal(visits);

		Pet spyPet = Mockito.spy(pet);

		when(petTimedCache.get(anyInt())).thenReturn(spyPet);

		petManager.getVisitsBetween(spyPet.getId(), firstVisit.getDate(), secondVisit.getDate());

		Mockito.verify(spyLogger)
			.info(
				"get visits for pet {} from {} since {}",
				spyPet.getId(),
				firstVisit.getDate(),
				secondVisit.getDate()
			);
		Mockito.verify(spyPet, times(1))
			.getVisitsBetween(firstVisit.getDate(), secondVisit.getDate());
	}

}
