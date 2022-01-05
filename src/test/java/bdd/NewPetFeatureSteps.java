package bdd;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NewPetFeatureSteps {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	private Owner owner;

	@Before("@add_new_pet")
	public void setup() {
		// sample setup code
	}

	@Given("Owner {string} already exists")
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

	@When("He adds a new pet for himself by calling newPet method from pet service")
	public void addNewPetForOwner() {
		Owner foundOwner = petService.findOwner(this.owner.getId());

		Pet pet = petService.newPet(foundOwner);
		pet.setId(2);
	}

	@Then("The new pet is added to owner successfully")
	public void petIsSaved() {
		Pet pet = petService.findPet(2);
		assertNotNull(pet);
	}
}
