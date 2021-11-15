package org.springframework.samples.petclinic.model.priceCalculators;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;
import org.springframework.security.core.parameters.P;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class priceCalculatorsTests {
	private CustomerDependentPriceCalculator customerDependentPriceCalculator = new CustomerDependentPriceCalculator();

	@Test
	public void Should_ReturnCorrectValue_When_UserTypeIsNewAndDiscountCounterIsHigherThanFlag() {
		List<Pet> pets = new ArrayList<>();

		PetType dogType = new PetType();
		dogType.setName("Dog");

		Pet dog = new Pet();
		dog.setBirthDate(new Date(110, 1, 1));
		dog.setType(dogType);

		PetType catType = new PetType();
		catType.setName("Cat");

		Pet cat = new Pet();
		cat.setBirthDate(new Date(121, 1, 1));
		cat.setType(catType);

		PetType fishType = new PetType();
		fishType.setName("Fish");
		PetType spyFishType = Mockito.spy(fishType);
		when(spyFishType.getRare()).thenReturn(false);


		Pet fish = new Pet();
		fish.setBirthDate(new Date(121, 1, 1));
		fish.setType(spyFishType);

		pets.add(dog);
		pets.add(cat);
		pets.add(fish);

		PetType spiderType = new PetType();
		Pet spider = new Pet();
		spider.setBirthDate(new Date(115, 1, 1));
		spider.setType(spiderType);

		for (int i = 0; i < 9; i++) {
			pets.add(spider);
		}

		double baseCharge = 1000;
		double basePricePerPet = 100;
		UserType userType = UserType.NEW;

		double totalPrice = this.customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);

		assertEquals(totalPrice, 2413.6);
	}

	@Test
	public void Should_ReturnCorrectValue_When_UserTypeIsNotNewAndDiscountCounterIsHigherThanFlag() {
		List<Pet> pets = new ArrayList<>();

		PetType dogType = new PetType();

		Pet dog = new Pet();
		dog.setBirthDate(new Date(118, 1, 1));
		dog.setType(dogType);

		PetType catType = new PetType();

		Pet cat = new Pet();
		cat.setBirthDate(new Date(121, 1, 1));
		cat.setType(catType);

		PetType fishType = new PetType();
		PetType spyFishType = Mockito.spy(fishType);
		when(spyFishType.getRare()).thenReturn(false);

		Pet fish = new Pet();
		fishType.setName("Fish");
		fish.setBirthDate(new Date(121, 1, 1));
		fish.setType(spyFishType);

		pets.add(dog);
		pets.add(cat);
		pets.add(fish);

		PetType spiderType = new PetType();
		Pet spider = new Pet();
		spider.setBirthDate(new Date(115, 1, 1));
		spider.setType(spiderType);

		for (int i = 0; i < 9; i++) {
			pets.add(spider);
		}

		double baseCharge = 1000;
		double basePricePerPet = 100;
		UserType userType = UserType.SILVER;

		double totalPrice = this.customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);

		assertEquals(totalPrice, 2239.2000000000003);
	}

	@Test
	public void Should_ReturnCorrectValue_When_UserTypeIsGoldAndDiscountCounterIsLessThanFlag() {
		List<Pet> pets = new ArrayList<>();

		PetType dogType = new PetType();

		Pet dog = new Pet();
		dog.setBirthDate(new Date(118, 1, 1));
		dog.setType(dogType);

		PetType catType = new PetType();

		Pet cat = new Pet();
		cat.setBirthDate(new Date(121, 1, 1));
		cat.setType(catType);

		PetType fishType = new PetType();
		fishType.setName("Fish");
		PetType spyFishType = Mockito.spy(fishType);
		when(spyFishType.getRare()).thenReturn(false);

		Pet fish = new Pet();
		fish.setBirthDate(new Date(121, 1, 1));
		fish.setType(spyFishType);

		pets.add(dog);
		pets.add(cat);
		pets.add(fish);

		double baseCharge = 1000;
		double basePricePerPet = 100;
		UserType userType = UserType.GOLD;

		double totalPrice = this.customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);

		assertEquals(totalPrice, 1326.4);
	}

	@Test
	public void Should_ReturnCorrectValue_When_UserTypeIsSilverAndDiscountCounterIsLessThanFlag() {
		List<Pet> pets = new ArrayList<>();

		PetType dogType = new PetType();

		Pet dog = new Pet();
		dog.setBirthDate(new Date(118, 1, 1));
		dog.setType(dogType);

		PetType catType = new PetType();

		Pet cat = new Pet();
		cat.setBirthDate(new Date(121, 1, 1));
		cat.setType(catType);

		PetType fishType = new PetType();
		fishType.setName("Fish");
		PetType spyFishType = Mockito.spy(fishType);
		when(spyFishType.getRare()).thenReturn(false);

		Pet fish = new Pet();
		fish.setBirthDate(new Date(121, 1, 1));
		fish.setType(spyFishType);

		pets.add(dog);
		pets.add(cat);
		pets.add(fish);

		double baseCharge = 1000;
		double basePricePerPet = 100;
		UserType userType = UserType.SILVER;

		double totalPrice = this.customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);

		assertEquals(totalPrice, 408.0);
	}

	@Test
	public void Should_ReturnCorrectValue_When_PetIsNotRareAndIsOldAndDiscountCounterIsHigherThanFlag() {
		List<Pet> pets = new ArrayList<>();

		PetType dogType = new PetType();
		dogType.setName("Dog");

		Pet dog = new Pet();
		dog.setBirthDate(new Date(110, 1, 1));
		dog.setType(dogType);

		PetType catType = new PetType();
		catType.setName("Cat");

		Pet cat = new Pet();
		cat.setBirthDate(new Date(121, 1, 1));
		cat.setType(catType);

		PetType fishType = new PetType();
		fishType.setName("Fish");
		PetType spyFishType = Mockito.spy(fishType);
		when(spyFishType.getRare()).thenReturn(false);


		Pet oldFish = new Pet();
		oldFish.setBirthDate(new Date(10, 1, 1));
		oldFish.setType(spyFishType);

		pets.add(dog);
		pets.add(cat);
		pets.add(oldFish);

		PetType spiderType = new PetType();
		Pet spider = new Pet();
		spider.setBirthDate(new Date(115, 1, 1));
		spider.setType(spiderType);

		for (int i = 0; i < 9; i++) {
			pets.add(spider);
		}

		double baseCharge = 1000;
		double basePricePerPet = 100;
		UserType userType = UserType.NEW;

		double totalPrice = this.customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);

		assertEquals(totalPrice, 2394.6);
	}

}
