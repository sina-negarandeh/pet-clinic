package bdd;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddPetFeatureSteps {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	private Owner owner;
	private PetType petType;

	@Before("@add_pet")
	public void setup() {
		// sample setup code
	}

	@Given("There is an owner called {string} who wants a pet")
	public void thereIsAOwnerCalled(String name) {
		String[] names = name.split(" ");
		this.owner = new Owner();
		this.owner.setFirstName(names[0]);
		this.owner.setLastName(names[0]);
		this.owner.setAddress("Brooklyn");
		this.owner.setCity("New York");
		this.owner.setTelephone("09901231234");
		this.owner.setId(1);
		ownerRepository.save(this.owner);
	}

	@When("He adds a pet for himself by calling save method from pet service")
	public void addPetForOwner() {
		Owner foundOwner = petService.findOwner(this.owner.getId());

		Pet pet = new Pet();
		pet.setType(petType);
		pet.setId(1);
		petService.savePet(pet, foundOwner);
	}

	@Then("The pet is added successfully")
	public void petIsSaved() {
		Pet pet = petService.findPet(1);
		assertNotNull(pet);
	}

	@Given("The pet type {string} exists")
	public void thereIsSomePredefinedPetTypesLike(String petTypeName) {
		petType = new PetType();
		petType.setName(petTypeName);
		petTypeRepository.save(petType);
	}
}
