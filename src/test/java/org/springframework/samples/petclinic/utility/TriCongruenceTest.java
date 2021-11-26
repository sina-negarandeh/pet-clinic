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
	 * explanation of why CUTPNFP doesn't subsume UTPC
	 *
	 * For TriCongruence method:
	 *
	 * UTPC: Given minimal DNF representations of a predicate f and its negation f', TR contains a unique true point for each implicant in f and f'.
	 * f = a + b + c
	 * f' = a'b'c'
	 * Implicants: {a, b, c, a'b'c'}
	 * Unique true point: {a: TFF, b: FTF, c: FFT, a'b'c': FFF}
	 *
	 * CUTPNFP: Given a minimal DNF representation of a predicate f, for each clause c in each implicant i, TR contains a unique true point for i and a near false point for c such that the points differ only in the truth value of c.
	 * Implicants: {a, b, c}
	 * unique true points: {TFF, FTF, FFT} near false points: {FFF}
	 *
	 * Explanation with example:
	 *
	 * Consider f = ab + bc'
	 * UTPC: f = ab + bc' and f' = b' + a'c
	 * Implicants: {ab, bc', b', a'c}
	 * Unique true points: {ab: {TTT}, bc': {FTF}, b': {FFF, TFF, TFT}, ac': {FTT}}
	 * Set: {TTT, FTF, FFF, TFF, TFT, FTT}
	 *
	 * CUTPNFP: f = ab + bc'
	 * Implicants: {ab, bc'}
	 * unique true points: {ab: {TTT}, bc': {FTF}}
	 * near false points: {a: {FTT}, b: {TFT, FFF}, c': {FTT}} -> FTT is duplicate
	 * Set: {TTT, FTF, FTT, TFT, FFF}
	 *
	 * UTPC needs 6 tests but CUTPNFP needs 5 (TFF is not covered), so UTPC is not covered in CUTPNFP and hence CUTPNFP doesn't subsume UTPC
	 */

	@Test
	public void question2TestCaseWithUTPC() {
		Assertions.assertTrue(question2(true, true, true));
		Assertions.assertTrue(question2(false, true, false));
		Assertions.assertTrue(question2Negate(false, false, false));
		Assertions.assertTrue(question2Negate(true, false, false));
		Assertions.assertTrue(question2Negate(true, false, true));
		Assertions.assertTrue(question2Negate(false, true, true));
	}

	@Test
	public void question2TestCaseWithCUTPNFP() {
		Assertions.assertTrue(question2(true, true, true));
		Assertions.assertTrue(question2(false, true, false));
		Assertions.assertFalse(question2(false, false, false));
		Assertions.assertFalse(question2(true, false, false));
		Assertions.assertFalse(question2(false, true, true));
	}

	private static boolean question2Negate(boolean a, boolean b, boolean c) {
		boolean predicate = (!b) || (!a && c);
		return predicate;
	}

	private static boolean question2(boolean a, boolean b, boolean c) {
		boolean predicate = (a && b) || (b && !c);
		return predicate;
	}
}
