package org.springframework.samples.petclinic.utility;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PriceCalculatorTest {
	private PriceCalculator priceCalculator = new PriceCalculator();
	List<Pet> pets = new ArrayList<Pet>();
	private static final int INFANT_YEARS = 2;
	private static final int BIRTHDAY_CASES = 3;
	private static final int VISITS_CASES = 8;
	private static final double BASE_CHARGE = 0.001;
	private static final double BASE_PRICE_PER_PET = 0.001;

	@BeforeEach
	public void setUp() {
		pets = new ArrayList<Pet>();
	}

	@Test
	public void Should_ReturnZero_When_PetsIsEmpty() {
		double totalPrice = this.priceCalculator.calcPrice(new ArrayList<Pet>(), BASE_CHARGE, BASE_PRICE_PER_PET);
		assertEquals(0, totalPrice);
	}

	@ParameterizedTest(name = "price for {0} is {1}")
	@MethodSource("provideInputForTest")
	@DisplayName("Should calculate totalPrice")
	public void Should_CalculatePriceCorrectly_When_GivenAnyNumberOfPets(int petCount, double output) {
		LocalDate now = LocalDate.now();
		LocalDate moreThanInfantYears = now.minusYears(INFANT_YEARS + 3);
		LocalDate equalToInfantYears = now.minusYears(INFANT_YEARS);
		LocalDate lessThanInfantYears = now.minusYears(INFANT_YEARS - 1);

		Visit visit100DaysAgo = new Visit();
		visit100DaysAgo.setDate(now.minusDays(100));

		Visit visitLessThan100DaysAgo = new Visit();
		visitLessThan100DaysAgo.setDate(now.minusDays(40));

		Visit visitMoreThan100DaysAgo = new Visit();
		visitMoreThan100DaysAgo.setDate(now.minusDays(150));

		for (int i = 0 ; i < petCount ; i ++) {
			Pet pet = new Pet();
			switch (i % BIRTHDAY_CASES) {
				case 0:
					pet.setBirthDate(lessThanInfantYears);
					break;
				case 1:
					pet.setBirthDate(equalToInfantYears);
					break;
				case 2:
					pet.setBirthDate(moreThanInfantYears);
					break;
			}

			switch (i % VISITS_CASES) {
				case 0:
					// Add Nothing
					break;
				case 1:
					// Add visit = 100
					pet.addVisit(visit100DaysAgo);
					break;
				case 2:
					// Add visit < 100
					pet.addVisit(visitLessThan100DaysAgo);
					break;
				case 3:
					// Add visit > 100
					pet.addVisit(visitMoreThan100DaysAgo);
					break;
				case 4:
					// All except visit > 100
					pet.addVisit(visitLessThan100DaysAgo);
					pet.addVisit(visit100DaysAgo);
					break;
				case 5:
					// All except visit = 100
					pet.addVisit(visitLessThan100DaysAgo);
					pet.addVisit(visitMoreThan100DaysAgo);
					break;
				case 6:
					// All except visit < 100
					pet.addVisit(visitMoreThan100DaysAgo);
					pet.addVisit(visit100DaysAgo);
					break;
				case 7:
					// All Visits
					pet.addVisit(visitLessThan100DaysAgo);
					pet.addVisit(visit100DaysAgo);
					pet.addVisit(visitMoreThan100DaysAgo);
					break;
			}

			pets.add(pet);
		}

		double totalPrice = this.priceCalculator.calcPrice(this.pets, BASE_CHARGE, BASE_PRICE_PER_PET);
		assertEquals(output, totalPrice);
	}

	public static Collection<Object[]> provideInputForTest() {
		return Arrays.asList(new Object[][]{{5, 0.00792}, {24, 1082776.4792800006}, {40, 3.2331532513877095E12}});
	}
}
