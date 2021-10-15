package org.springframework.samples.petclinic.owner;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.Assumptions;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Theories.class)
public class OwnerTheoriesTest {
	@DataPoints
	public static String[] positiveIntegers() {
		return new String[] {
			"Snowy",
			"Luke",
			"M",
			"rosy",
			"jorge"
		};
	}

	@Theory
	public void shouldGetVisits(String firstPetName, String secondPetName) {
		Assumptions.assumeTrue(firstPetName != null && secondPetName != null);

		Owner owner = new Owner();

		Pet firstPet = new Pet();
		firstPet.setName(firstPetName);
		owner.addPet(firstPet);

		Pet secondPet = new Pet();
		secondPet.setName(secondPetName);
		owner.addPet(secondPet);


		assertTrue(
			Objects.equals(owner.getPet(firstPetName).getName(), firstPetName) &&
				Objects.equals(owner.getPet(secondPetName).getName(), secondPetName)
		);
	}
}
