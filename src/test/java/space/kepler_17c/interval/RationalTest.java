package space.kepler_17c.interval;

import java.lang.reflect.Field;
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
        inspectRational(0, 0, Rational.NAN);
    }

    @Test
    public void comparisonsTest() {
        // zero
        Assertions.assertTrue(Rational.ZERO.isFinite());
        Assertions.assertFalse(Rational.ZERO.isInfinite());
        Assertions.assertFalse(Rational.ZERO.isNan());
        // one
        Assertions.assertTrue(Rational.ONE.isFinite());
        Assertions.assertFalse(Rational.ONE.isInfinite());
        Assertions.assertFalse(Rational.ONE.isNan());
        // +Inf
        Assertions.assertFalse(Rational.POSITIVE_INFINITY.isFinite());
        Assertions.assertTrue(Rational.POSITIVE_INFINITY.isInfinite());
        Assertions.assertFalse(Rational.POSITIVE_INFINITY.isNan());
        // -Inf
        Assertions.assertFalse(Rational.NEGATIVE_INFINITY.isFinite());
        Assertions.assertTrue(Rational.NEGATIVE_INFINITY.isInfinite());
        Assertions.assertFalse(Rational.NEGATIVE_INFINITY.isNan());
        // NaN
        Assertions.assertFalse(Rational.NAN.isFinite());
        Assertions.assertFalse(Rational.NAN.isInfinite());
        Assertions.assertTrue(Rational.NAN.isNan());

        // equals
        Assertions.assertEquals(Rational.valueOf(4, 4), Rational.ONE);
        Assertions.assertEquals(Rational.valueOf(1, -1), Rational.ONE.negate());
        Assertions.assertEquals(Rational.valueOf(2, 0), Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Rational.valueOf(-2, 0), Rational.NEGATIVE_INFINITY);
        Assertions.assertEquals(Rational.valueOf(0, 0), Rational.NAN);
        Assertions.assertEquals(Rational.valueOf(8, 4), Rational.TWO);
        Assertions.assertEquals(Rational.valueOf(100, 10), Rational.TEN);

        // compareTo
        List<Rational> orderedTestValues = List.of(
                Rational.NAN,
                Rational.NEGATIVE_INFINITY,
                Rational.TEN.negate(),
                Rational.TWO.negate(),
                Rational.ONE.negate(),
                Rational.valueOf(-1, 5),
                Rational.valueOf(-1, 10),
                Rational.ZERO,
                Rational.valueOf(1, 4),
                Rational.valueOf(3, 8),
                Rational.valueOf(3, 7),
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
        Assertions.assertEquals("NaN", Rational.NAN.toString());
    }

    @Test
    public void normaliseFractionTest() {
        inspectRational(1, 2, Rational.valueOf(2, 4));
        inspectRational(-1, 2, Rational.valueOf(2, -4));
        inspectRational(-1, 2, Rational.valueOf(-2, 4));
        inspectRational(1, 2, Rational.valueOf(-2, -4));
        inspectRational(3, 7, Rational.valueOf(3, 7));
        inspectRational(5, 1, Rational.valueOf(5, 1));
    }

    private static void inspectRational(int expectedNumerator, int expectedDenominator, Rational actual) {
        try {
            Field numeratorField = Rational.class.getDeclaredField("numerator");
            numeratorField.setAccessible(true);
            BigInteger numerator = (BigInteger) numeratorField.get(actual);
            Assertions.assertEquals(BigInteger.valueOf(expectedNumerator), numerator);
            Field denominatorField = Rational.class.getDeclaredField("denominator");
            denominatorField.setAccessible(true);
            BigInteger denominator = (BigInteger) denominatorField.get(actual);
            Assertions.assertEquals(BigInteger.valueOf(expectedDenominator), denominator);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
