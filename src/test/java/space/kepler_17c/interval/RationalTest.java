package space.kepler_17c.interval;

import java.lang.reflect.Field;
import java.math.BigInteger;
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
    public void cancelFractionTest() {
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
