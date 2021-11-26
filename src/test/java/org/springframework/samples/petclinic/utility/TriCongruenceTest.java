package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)
class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	@Test
	public void sampleTest() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(7, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	/**
	 * 14)
	 *
	 * 	 |   | a | b | a ∨ b |
	 * 	 |---|---|---|-------|
	 * 	 | 1 | T | T |   T   |
	 * 	 | 2 | T | F |   T   |
	 * 	 | 3 | F | T |   T   |
	 * 	 | 4 | F | F |   F   |
	 *
	 * 	 a: t1arr[0] < 0
	 * 	 b: t1arr[0] + t1arr[1] < t1arr[2]
	 * 	 p: t1arr[0] < 0 || t1arr[0] + t1arr[1] < t1arr[2] -> a || b -> a ∨ b
	 *
	 * 	 Clause Coverage (CC): {2, 3} -> {2} is not possible hence {1, 4}
	 * 	 Correlated Active Clause Coverage (CACC): {3, 4}
	 */

	@Test
	public void testCase1() {
		Triangle t1 = new Triangle(-1, 1, 1);
		Triangle t2 = new Triangle(-1, 1, 1);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@Test
	public void testCase3() {
		Triangle t1 = new Triangle(1, 2, 5);
		Triangle t2 = new Triangle(1, 2, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@Test
	public void testCase4() {
		Triangle t1 = new Triangle(2, 3, 3);
		Triangle t2 = new Triangle(2, 3, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	/**
	 * 15)
	 *
	 * 	 a: t1arr[0] != t2arr[0]
	 * 	 b: t1arr[1] != t2arr[1]
	 * 	 c: t1arr[2] != t2arr[2]
	 * 	 p: t1arr[0] != t2arr[0] || t1arr[1] != t2arr[1] || t1arr[2] != t2arr[2] -> a || b || c -> a ∨ b ∨ c
	 *
	 * 	 Corresponding Unique True Points - Near False Point Coverage (CUTPNFP): {TFF, FTF, FFT, FFF}
	 */

	@Test
	public void line15TestCaseWhenTFF() {
		Triangle t1 = new Triangle(1, 3, 3);
		Triangle t2 = new Triangle(2, 3, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@Test
	public void line15TestCaseWhenFTF() {
		Triangle t1 = new Triangle(2, 4, 5);
		Triangle t2 = new Triangle(2, 3, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@Test
	public void line15TestCaseWhenFFT() {
		Triangle t1 = new Triangle(2, 3, 5);
		Triangle t2 = new Triangle(2, 3, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@Test
	public void line15TestCaseWhenFFF() {
		Triangle t1 = new Triangle(2, 3, 3);
		Triangle t2 = new Triangle(2, 3, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	/**
	 * TODO
	 * explain your answer here
	 */
	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = false;
//		predicate = a predicate with any number of clauses
		return predicate;
	}
}
