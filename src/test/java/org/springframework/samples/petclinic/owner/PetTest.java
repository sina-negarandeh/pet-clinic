package org.springframework.samples.petclinic.owner;

import org.junit.Assume;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.visit.Visit;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(Theories.class)
public class PetTest {
	@DataPoints
	public static Visit[] provideDates() {
		return new Visit[] {
			new Visit().setDate(LocalDate.of(2021, 4, 15)),
			new Visit().setDate(LocalDate.of(2021, 3, 7)),
			new Visit().setDate(LocalDate.of(2021, 1, 2)),
			new Visit().setDate(LocalDate.of(2020, 12, 4)),
			new Visit().setDate(LocalDate.of(2020, 12, 7)),
			new Visit().setDate(LocalDate.of(2020, 12, 1)),

		};
	}

	@Theory
	public void shouldGetVisits(Visit firstVisit, Visit secondVisit, Visit thirdVisit) {
		LocalDate date = LocalDate.of(2021, 10, 27);
		Assume.assumeTrue(
			firstVisit.getDate().isBefore(date) &&
				secondVisit.getDate().isBefore(date) &&
				thirdVisit.getDate().isBefore(date)
		);

		Pet pet = new Pet();
		pet.addVisit(firstVisit);
		pet.addVisit(secondVisit);
		pet.addVisit(thirdVisit);

		Set<Visit> resultSet = new HashSet<>();
		resultSet.add(firstVisit);
		resultSet.add(secondVisit);
		resultSet.add(thirdVisit);

		assertTrue(pet.getVisits().containsAll(resultSet));
	}

	@Theory
	public void shouldGetVisitsInOrder(Visit firstVisit, Visit secondVisit, Visit thirdVisit) {
		Assume.assumeTrue(
			firstVisit.getDate().isBefore(LocalDate.now())
				&& secondVisit.getDate().isBefore(LocalDate.now())
				&& thirdVisit.getDate().isBefore(LocalDate.now())
		);

		Pet pet = new Pet();
		pet.addVisit(firstVisit);
		pet.addVisit(secondVisit);
		pet.addVisit(thirdVisit);

		List<Visit> visits = pet.getVisits();

		int FIRST_VISIT = 0;
		int SECOND_VISIT = 1;
		int THIRD_VISIT = 2;

		assertEquals(3, visits.size());
		assertTrue(visits.get(FIRST_VISIT).getDate().isEqual(visits.get(SECOND_VISIT).getDate()) ||
			visits.get(FIRST_VISIT).getDate().isAfter(visits.get(SECOND_VISIT).getDate()));
		assertTrue((visits.get(SECOND_VISIT).getDate().isEqual(visits.get(THIRD_VISIT).getDate()) ||
					visits.get(SECOND_VISIT).getDate().isAfter(visits.get(THIRD_VISIT).getDate())));
	}
}
