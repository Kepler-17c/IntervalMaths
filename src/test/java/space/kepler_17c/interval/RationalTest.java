package space.kepler_17c.interval;

import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RationalTest {
    @Test
    public void constantsTest() {
        inspectRational(0, 1, Rational.ZERO);
        inspectRational(1, 1, Rational.ONE);
        inspectRational(2, 1, Rational.TWO);
        inspectRational(10, 1, Rational.TEN);
        inspectRational(1, 0, Rational.POSITIVE_INFINITY);
        inspectRational(-1, 0, Rational.NEGATIVE_INFINITY);
        inspectRational(0, 0, Rational.NaN);
    }

    @Test
    public void mathsTest() {
        Rational expected, actual;
        // test normal numbers
        expected = Rational.of(1, 2);
        actual = Rational.of(1, 6).add(Rational.of(1, 3));
        Assertions.assertEquals(expected, actual);
        expected = Rational.of(1, 2);
        actual = Rational.of(2, 5).add(Rational.of(1, 10));
        Assertions.assertEquals(expected, actual);
        expected = Rational.of(19, 56);
        actual = Rational.of(5, 8).subtract(Rational.of(2, 7));
        Assertions.assertEquals(expected, actual);
        expected = Rational.of(-1, 6);
        actual = Rational.of(1, 2).subtract(Rational.of(2, 3));
        Assertions.assertEquals(expected, actual);
        expected = Rational.of(10, 21);
        actual = Rational.of(2, 3).multiply(Rational.of(5, 7));
        Assertions.assertEquals(expected, actual);
        expected = Rational.of(-2, 3);
        actual = Rational.of(-1, 2).multiply(Rational.of(4, 3));
        Assertions.assertEquals(expected, actual);
        expected = Rational.of(3, 2);
        actual = Rational.of(1, 4).divide(Rational.of(1, 6));
        Assertions.assertEquals(expected, actual);
        expected = Rational.of(-35, 6);
        actual = Rational.of(5, 3).divide(Rational.of(-2, 7));
        Assertions.assertEquals(expected, actual);
        // test special value addition
        actual = Rational.POSITIVE_INFINITY.add(Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, actual);
        actual = Rational.POSITIVE_INFINITY.add(Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.POSITIVE_INFINITY.add(Rational.NaN);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.POSITIVE_INFINITY.add(Rational.ONE);
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, actual);
        actual = Rational.POSITIVE_INFINITY.add(Rational.ONE.negate());
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, actual);
        actual = Rational.NEGATIVE_INFINITY.add(Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.NEGATIVE_INFINITY.add(Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, actual);
        actual = Rational.NEGATIVE_INFINITY.add(Rational.NaN);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.NEGATIVE_INFINITY.add(Rational.ONE);
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, actual);
        actual = Rational.NEGATIVE_INFINITY.add(Rational.ONE.negate());
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, actual);
        actual = Rational.NaN.add(Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.NaN.add(Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.NaN.add(Rational.NaN);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.NaN.add(Rational.ONE);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.NaN.add(Rational.ONE.negate());
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.ONE.add(Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, actual);
        actual = Rational.ONE.add(Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, actual);
        actual = Rational.ONE.add(Rational.NaN);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.ONE.negate().add(Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, actual);
        actual = Rational.ONE.negate().add(Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, actual);
        actual = Rational.ONE.negate().add(Rational.NaN);
        Assertions.assertEquals(Rational.NaN, actual);
        // test special value multiplication
        actual = Rational.POSITIVE_INFINITY.multiply(Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, actual);
        actual = Rational.POSITIVE_INFINITY.multiply(Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, actual);
        actual = Rational.POSITIVE_INFINITY.multiply(Rational.NaN);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.POSITIVE_INFINITY.multiply(Rational.ONE);
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, actual);
        actual = Rational.POSITIVE_INFINITY.multiply(Rational.ONE.negate());
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, actual);
        actual = Rational.NEGATIVE_INFINITY.multiply(Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, actual);
        actual = Rational.NEGATIVE_INFINITY.multiply(Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, actual);
        actual = Rational.NEGATIVE_INFINITY.multiply(Rational.NaN);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.NEGATIVE_INFINITY.multiply(Rational.ONE);
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, actual);
        actual = Rational.NEGATIVE_INFINITY.multiply(Rational.ONE.negate());
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, actual);
        actual = Rational.NaN.multiply(Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.NaN.multiply(Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.NaN.multiply(Rational.NaN);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.NaN.multiply(Rational.ONE);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.NaN.multiply(Rational.ONE.negate());
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.ONE.multiply(Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, actual);
        actual = Rational.ONE.multiply(Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, actual);
        actual = Rational.ONE.multiply(Rational.NaN);
        Assertions.assertEquals(Rational.NaN, actual);
        actual = Rational.ONE.negate().multiply(Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, actual);
        actual = Rational.ONE.negate().multiply(Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, actual);
        actual = Rational.ONE.negate().multiply(Rational.NaN);
        Assertions.assertEquals(Rational.NaN, actual);
    }

    @Test
    public void inverseTest() {
        Assertions.assertEquals(Rational.ONE, Rational.ONE.inverse());
        Assertions.assertEquals(Rational.TWO, Rational.TWO.inverse().inverse());
        Assertions.assertEquals(Rational.ZERO, Rational.POSITIVE_INFINITY.inverse());
        Assertions.assertEquals(Rational.ZERO, Rational.NEGATIVE_INFINITY.inverse());
        Assertions.assertEquals(
                Rational.POSITIVE_INFINITY, Rational.NEGATIVE_INFINITY.inverse().inverse());
        Assertions.assertEquals(Rational.NaN, Rational.NaN.inverse());
    }

    @Test
    public void comparisonsTest() {
        // zero
        Assertions.assertTrue(Rational.ZERO.isFinite());
        Assertions.assertFalse(Rational.ZERO.isInfinite());
        Assertions.assertFalse(Rational.ZERO.isNaN());
        // one
        Assertions.assertTrue(Rational.ONE.isFinite());
        Assertions.assertFalse(Rational.ONE.isInfinite());
        Assertions.assertFalse(Rational.ONE.isNaN());
        // +Inf
        Assertions.assertFalse(Rational.POSITIVE_INFINITY.isFinite());
        Assertions.assertTrue(Rational.POSITIVE_INFINITY.isInfinite());
        Assertions.assertFalse(Rational.POSITIVE_INFINITY.isNaN());
        // -Inf
        Assertions.assertFalse(Rational.NEGATIVE_INFINITY.isFinite());
        Assertions.assertTrue(Rational.NEGATIVE_INFINITY.isInfinite());
        Assertions.assertFalse(Rational.NEGATIVE_INFINITY.isNaN());
        // NaN
        Assertions.assertFalse(Rational.NaN.isFinite());
        Assertions.assertFalse(Rational.NaN.isInfinite());
        Assertions.assertTrue(Rational.NaN.isNaN());

        // equals
        Assertions.assertEquals(Rational.of(4, 4), Rational.ONE);
        Assertions.assertEquals(Rational.of(1, -1), Rational.ONE.negate());
        Assertions.assertEquals(Rational.of(2, 0), Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.of(-2, 0), Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.of(0, 0), Rational.NaN);
        Assertions.assertEquals(Rational.of(8, 4), Rational.TWO);
        Assertions.assertEquals(Rational.of(100, 10), Rational.TEN);

        // compareTo
        List<Rational> orderedTestValues = List.of(
                Rational.NaN,
                Rational.NEGATIVE_INFINITY,
                Rational.TEN.negate(),
                Rational.TWO.negate(),
                Rational.ONE.negate(),
                Rational.of(-1, 5),
                Rational.of(-1, 10),
                Rational.ZERO,
                Rational.of(1, 4),
                Rational.of(3, 8),
                Rational.of(3, 7),
                Rational.ONE,
                Rational.TWO,
                Rational.TEN,
                Rational.POSITIVE_INFINITY);
        for (int i = 0; i < orderedTestValues.size(); i++) {
            Rational rational = orderedTestValues.get(i);
            orderedTestValues.subList(0, i).forEach(r -> {
                Assertions.assertEquals(1, rational.compareTo(r), "Comparing " + rational + " to " + r);
                Assertions.assertEquals(-1, r.compareTo(rational), "Comparing " + r + " to " + rational);
            });
            orderedTestValues.subList(i + 1, orderedTestValues.size()).forEach(r -> {
                Assertions.assertEquals(-1, rational.compareTo(r), "Comparing " + rational + " to " + r);
                Assertions.assertEquals(1, r.compareTo(rational), "Comparing " + r + " to " + rational);
            });
        }
    }

    @Test
    public void toStringTest() {
        Assertions.assertEquals("0/1", Rational.ZERO.toString());
        Assertions.assertEquals("1/1", Rational.ONE.toString());
        Assertions.assertEquals("2/1", Rational.TWO.toString());
        Assertions.assertEquals("10/1", Rational.TEN.toString());
        Assertions.assertEquals("Infinity", Rational.POSITIVE_INFINITY.toString());
        Assertions.assertEquals("-Infinity", Rational.NEGATIVE_INFINITY.toString());
        Assertions.assertEquals("NaN", Rational.NaN.toString());
    }

    @Test
    public void normaliseFractionTest() {
        inspectRational(1, 2, Rational.of(2, 4));
        inspectRational(-1, 2, Rational.of(2, -4));
        inspectRational(-1, 2, Rational.of(-2, 4));
        inspectRational(1, 2, Rational.of(-2, -4));
        inspectRational(3, 7, Rational.of(3, 7));
        inspectRational(5, 1, Rational.of(5, 1));
    }

    @Test
    public void factoryFunctionsTest() {
        inspectRational(42, 1, Rational.of(42));
        inspectRational(-42, 1, Rational.of(-42));
        inspectRational(1L << 40, 1, Rational.of(Math.pow(2, 40)));
        inspectRational(1, 1L << 40, Rational.of(Math.pow(2, -40)));
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, Rational.of(Double.POSITIVE_INFINITY));
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, Rational.of(Double.NEGATIVE_INFINITY));
        Assertions.assertEquals(Rational.NaN, Rational.of(Double.NaN));
    }

    private static void inspectRational(long expectedNumerator, long expectedDenominator, Rational actual) {
        Assertions.assertEquals(BigInteger.valueOf(expectedNumerator), actual.numerator);
        Assertions.assertEquals(BigInteger.valueOf(expectedDenominator), actual.denominator);
    }
}
