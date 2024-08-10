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
    public void propertyChecksTest() {
        Assertions.assertTrue(Rational.ZERO.isFinite());
        Assertions.assertFalse(Rational.ZERO.isInfinite());
        Assertions.assertFalse(Rational.ZERO.isNaN());
        Assertions.assertTrue(Rational.ZERO.isInteger());
        Assertions.assertTrue(Rational.ONE.isFinite());
        Assertions.assertFalse(Rational.ONE.isInfinite());
        Assertions.assertFalse(Rational.ONE.isNaN());
        Assertions.assertTrue(Rational.ONE.isInteger());
        Assertions.assertTrue(Rational.TWO.isFinite());
        Assertions.assertFalse(Rational.TWO.isInfinite());
        Assertions.assertFalse(Rational.TWO.isNaN());
        Assertions.assertTrue(Rational.TWO.isInteger());
        Assertions.assertTrue(Rational.ONE.divide(Rational.TWO).isFinite());
        Assertions.assertFalse(Rational.ONE.divide(Rational.TWO).isInfinite());
        Assertions.assertFalse(Rational.ONE.divide(Rational.TWO).isNaN());
        Assertions.assertFalse(Rational.ONE.divide(Rational.TWO).isInteger());
        Assertions.assertFalse(Rational.POSITIVE_INFINITY.isFinite());
        Assertions.assertTrue(Rational.POSITIVE_INFINITY.isInfinite());
        Assertions.assertFalse(Rational.POSITIVE_INFINITY.isNaN());
        Assertions.assertFalse(Rational.POSITIVE_INFINITY.isInteger());
        Assertions.assertFalse(Rational.NEGATIVE_INFINITY.isFinite());
        Assertions.assertTrue(Rational.NEGATIVE_INFINITY.isInfinite());
        Assertions.assertFalse(Rational.NEGATIVE_INFINITY.isNaN());
        Assertions.assertFalse(Rational.NEGATIVE_INFINITY.isInteger());
        Assertions.assertFalse(Rational.NaN.isFinite());
        Assertions.assertFalse(Rational.NaN.isInfinite());
        Assertions.assertTrue(Rational.NaN.isNaN());
        Assertions.assertFalse(Rational.NaN.isInteger());
        Assertions.assertEquals(1, Rational.ONE.signum());
        Assertions.assertEquals(0, Rational.ZERO.signum());
        Assertions.assertEquals(-1, Rational.ONE.negate().signum());
        Assertions.assertEquals(Rational.ONE, Rational.ONE.abs());
        Assertions.assertEquals(Rational.ONE, Rational.ONE.negate().abs());
    }

    @Test
    public void comparisonsTest() {
        // named comparisons
        Assertions.assertTrue(Rational.TWO.isGreaterThan(Rational.ONE));
        Assertions.assertFalse(Rational.TWO.isGreaterThan(Rational.TWO));
        Assertions.assertFalse(Rational.ONE.isGreaterThan(Rational.TWO));
        Assertions.assertTrue(Rational.TWO.isGreaterOrEqualTo(Rational.ONE));
        Assertions.assertTrue(Rational.TWO.isGreaterOrEqualTo(Rational.TWO));
        Assertions.assertFalse(Rational.ONE.isGreaterOrEqualTo(Rational.TWO));
        Assertions.assertFalse(Rational.TWO.isLessThan(Rational.ONE));
        Assertions.assertFalse(Rational.TWO.isLessThan(Rational.TWO));
        Assertions.assertTrue(Rational.ONE.isLessThan(Rational.TWO));
        Assertions.assertFalse(Rational.TWO.isLessOrEqualTo(Rational.ONE));
        Assertions.assertTrue(Rational.TWO.isLessOrEqualTo(Rational.TWO));
        Assertions.assertTrue(Rational.ONE.isLessOrEqualTo(Rational.TWO));

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
    public void toDecimalStringTest() {
        Assertions.assertEquals("0", Rational.ZERO.toDecimalString(16));
        Assertions.assertEquals("1", Rational.ONE.toDecimalString(16));
        Assertions.assertEquals("2", Rational.TWO.toDecimalString(16));
        Assertions.assertEquals("10", Rational.TEN.toDecimalString(16));
        Assertions.assertEquals("0.3333333333333333", Rational.of(1, 3).toDecimalString(16));
        Assertions.assertEquals("0.2500000000000000", Rational.of(1, 4).toDecimalString(16));
        Assertions.assertEquals("0.6666666666666666", Rational.of(2, 3).toDecimalString(16));
        Assertions.assertEquals("Infinity", Rational.POSITIVE_INFINITY.toDecimalString(16));
        Assertions.assertEquals("-Infinity", Rational.NEGATIVE_INFINITY.toDecimalString(16));
        Assertions.assertEquals("NaN", Rational.NaN.toDecimalString(16));
        Assertions.assertEquals("0", Rational.ZERO.toDecimalString(16, true));
        Assertions.assertEquals("1", Rational.ONE.toDecimalString(16, true));
        Assertions.assertEquals("2", Rational.TWO.toDecimalString(16, true));
        Assertions.assertEquals("10", Rational.TEN.toDecimalString(16, true));
        Assertions.assertEquals("0.3333333333333333", Rational.of(1, 3).toDecimalString(16, true));
        Assertions.assertEquals("0.25", Rational.of(1, 4).toDecimalString(16, true));
        Assertions.assertEquals("0.6666666666666666", Rational.of(2, 3).toDecimalString(16, true));
        Assertions.assertEquals("Infinity", Rational.POSITIVE_INFINITY.toDecimalString(16, true));
        Assertions.assertEquals("-Infinity", Rational.NEGATIVE_INFINITY.toDecimalString(16, true));
        Assertions.assertEquals("NaN", Rational.NaN.toDecimalString(16, true));
        Assertions.assertEquals("0", Rational.ZERO.toDecimalString(16, false));
        Assertions.assertEquals("1", Rational.ONE.toDecimalString(16, false));
        Assertions.assertEquals("2", Rational.TWO.toDecimalString(16, false));
        Assertions.assertEquals("10", Rational.TEN.toDecimalString(16, false));
        Assertions.assertEquals("0.3333333333333333", Rational.of(1, 3).toDecimalString(16, false));
        Assertions.assertEquals("0.2500000000000000", Rational.of(1, 4).toDecimalString(16, false));
        Assertions.assertEquals("0.6666666666666666", Rational.of(2, 3).toDecimalString(16, false));
        Assertions.assertEquals("Infinity", Rational.POSITIVE_INFINITY.toDecimalString(16, false));
        Assertions.assertEquals("-Infinity", Rational.NEGATIVE_INFINITY.toDecimalString(16, false));
        Assertions.assertEquals("NaN", Rational.NaN.toDecimalString(16, false));
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
    public void minMaxTest() {
        // min
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, Rational.min());
        Assertions.assertEquals(Rational.ONE, Rational.min(Rational.ONE));
        Assertions.assertEquals(Rational.ONE, Rational.min(Rational.ONE, Rational.TWO));
        Assertions.assertEquals(Rational.ONE, Rational.min(Rational.TWO, Rational.ONE));
        // max
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, Rational.max());
        Assertions.assertEquals(Rational.ONE, Rational.max(Rational.ONE));
        Assertions.assertEquals(Rational.TWO, Rational.max(Rational.ONE, Rational.TWO));
        Assertions.assertEquals(Rational.TWO, Rational.max(Rational.TWO, Rational.ONE));
    }

    @Test
    public void factoryFunctionsTest() {
        inspectRational(1, 10, Rational.of(BigInteger.ONE, BigInteger.TEN));
        inspectRational(-2, 3, Rational.of(BigInteger.TWO.negate(), BigInteger.valueOf(3)));
        inspectRational(5, 6, Rational.of(5, 6));
        inspectRational(-7, 4, Rational.of(7, -4));
        inspectRational(2, 1, Rational.of(BigInteger.TWO));
        inspectRational(31, 1, Rational.of(31));
        inspectRational(-17, 1, Rational.of(-17));
        inspectRational(1L << 40, 1, Rational.of(Math.pow(2, 40)));
        inspectRational(1, 1L << 40, Rational.of(Math.pow(2, -40)));
        Assertions.assertEquals("3.14159265", Rational.of(Math.PI).toDecimalString(8));
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, Rational.of(Double.POSITIVE_INFINITY));
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, Rational.of(Double.NEGATIVE_INFINITY));
        Assertions.assertEquals(Rational.NaN, Rational.of(Double.NaN));
        Assertions.assertThrows(NumberFormatException.class, () -> Rational.of(""));
        Assertions.assertEquals(Rational.NaN, Rational.of("NaN"));
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, Rational.of("Infinity"));
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, Rational.of("+Infinity"));
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, Rational.of("-Infinity"));
        Assertions.assertThrows(NumberFormatException.class, () -> Rational.of("."));
        Assertions.assertEquals(Rational.ZERO, Rational.of("0"));
        Assertions.assertEquals(Rational.ZERO, Rational.of("-0"));
        Assertions.assertEquals(Rational.ZERO, Rational.of("0."));
        Assertions.assertEquals(Rational.ZERO, Rational.of(".0"));
        Assertions.assertEquals(Rational.ZERO, Rational.of("0.0"));
        Assertions.assertEquals(Rational.of(123), Rational.of("123"));
        Assertions.assertEquals(Rational.of(-123), Rational.of("-123"));
        Assertions.assertEquals(Rational.of(314159, 100000), Rational.of("3.14159"));
        Assertions.assertEquals(Rational.of(314159, 10), Rational.of("314.159E2"));
        Assertions.assertEquals(Rational.of(1, 10000), Rational.of("1e-4"));
        Assertions.assertEquals(Rational.of(-123, 10000), Rational.of("-1.23E-2"));
        Assertions.assertThrows(NumberFormatException.class, () -> Rational.of(".5e"));
    }

    private static void inspectRational(long expectedNumerator, long expectedDenominator, Rational actual) {
        Assertions.assertEquals(BigInteger.valueOf(expectedNumerator), actual.numerator);
        Assertions.assertEquals(BigInteger.valueOf(expectedDenominator), actual.denominator);
    }
}
