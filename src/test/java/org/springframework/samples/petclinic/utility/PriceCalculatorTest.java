package org.springframework.samples.petclinic.utility;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PriceCalculatorTest {
	private PriceCalculator priceCalculator = new PriceCalculator();

	@Test
	public void Should_ReturnZero_When_PetsIsEmpty() {

	}

	@Test
	public void Should_ReturnCorrectly_When_PetVisitsAreEmptyANDAgeIsLessThanINFANT_YEARS() {

	}

	@Test
	public void Should_ReturnCorrectly_When_PetVisitsAreEmptyANDAgeIsMoreThanINFANT_YEARS() {

	}

	@Test
	public void Should_ReturnCorrectly_When_PetVisitsAreEmptyANDAgeIsEqualToINFANT_YEARS() {

	}

	@Test
	public void Should_ReturnCorrectly_When_PetVisitsAreNotEmptyANDAgeIsLessThanINFANT_YEARS() {

	}

	@Test
	public void Should_ReturnCorrectly_When_PetVisitsAreNotEmptyANDAgeIsMoreThanINFANT_YEARS() {

	}

	@Test
	public void Should_ReturnCorrectly_When_PetVisitsAreNotEmptyANDAgeIsEqualToINFANT_YEARS() {

	}

}
