package org.springframework.samples.petclinic.model.priceCalculators;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;

import static org.mockito.Mockito.when;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SimplePriceCalculatorTests {
	private SimplePriceCalculator simplePriceCalculator = new SimplePriceCalculator();

	@Test
	public void Should_ReturnCorrectValue_When_DoesNotEnterForLoop() {
		List<Pet> pets = new ArrayList<>();
		double baseCharge = 1000;
		double basePricePerPet = 100;
		UserType userType = UserType.NEW;

		double totalPrice = this.simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);

		assertEquals(totalPrice,  baseCharge * 0.95);
	}

	@Test
	public void Should_ReturnCorrectValue_When_DoesEnterForLoopAndNestedIf() {
		List<Pet> pets = new ArrayList<>();

		PetType dogType = new PetType();

		Pet dog = new Pet();
		dog.setName("sina");
		dog.setType(dogType);

		pets.add(dog);

		double baseCharge = 1000;
		double basePricePerPet = 100;
		UserType userType = UserType.NEW;

		double totalPrice = this.simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);

		assertEquals(totalPrice,  1064);
	}

	@Test
	public void Should_ReturnCorrectValue_When_DoesEnterForLoopAndNotNestedIf() {
		List<Pet> pets = new ArrayList<>();

		PetType dogType = new PetType();
		PetType spyPetType = Mockito.spy(dogType);

		Pet dog = new Pet();
		dog.setName("sina");
		dog.setType(spyPetType);

		pets.add(dog);

		when(spyPetType.getRare()).thenReturn(false);

		pets.add(dog);

		double baseCharge = 1000;
		double basePricePerPet = 100;
		UserType userType = UserType.NEW;

		double totalPrice = this.simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);

		assertEquals(totalPrice,  1140);
	}

	@Test
	public void Should_ReturnCorrectValue_When_DoesEnterForLoopAndNotUserTypeIf() {
		List<Pet> pets = new ArrayList<>();
		double baseCharge = 1000;
		double basePricePerPet = 100;
		UserType userType = UserType.GOLD;

		double totalPrice = this.simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);

		assertEquals(totalPrice,  1000.0);
	}
}
