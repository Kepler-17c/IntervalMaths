package space.kepler_17c.interval;

import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IntervalTest {
    @Test
    public void constantsTest() {
        Assertions.assertEquals(Rational.ZERO, Interval.ZERO.min);
        Assertions.assertEquals(Rational.ZERO, Interval.ZERO.max);
        Assertions.assertEquals(Rational.ONE, Interval.ONE.min);
        Assertions.assertEquals(Rational.ONE, Interval.ONE.max);
        Assertions.assertEquals(Rational.TWO, Interval.TWO.min);
        Assertions.assertEquals(Rational.TWO, Interval.TWO.max);
        Assertions.assertEquals(Rational.TEN, Interval.TEN.min);
        Assertions.assertEquals(Rational.TEN, Interval.TEN.max);
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, Interval.POSITIVE_INFINITY.min);
        Assertions.assertEquals(Rational.POSITIVE_INFINITY, Interval.POSITIVE_INFINITY.max);
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, Interval.NEGATIVE_INFINITY.min);
        Assertions.assertEquals(Rational.NEGATIVE_INFINITY, Interval.NEGATIVE_INFINITY.max);
        Assertions.assertEquals(Rational.NaN, Interval.NaN.min);
        Assertions.assertEquals(Rational.NaN, Interval.NaN.max);
    }

    @Test
    public void mathsTest() {
        // tmp variables
        Interval ivA, ivB;
        Interval expected, actual;
        // add
        ivA = Interval.of(20, 4);
        ivB = Interval.of(-15, -10);
        expected = Interval.of(-11, 10);
        actual = ivA.add(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(-11, 16);
        ivB = Interval.of(-6, -14);
        expected = Interval.of(-25, 10);
        actual = ivA.add(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(-18, 18);
        ivB = Interval.of(-18, 5);
        expected = Interval.of(-36, 23);
        actual = ivA.add(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(18, 2);
        ivB = Interval.of(17, -5);
        expected = Interval.of(-3, 35);
        actual = ivA.add(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(-13, 8);
        ivB = Interval.of(-18, -16);
        expected = Interval.of(-31, -8);
        actual = ivA.add(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(-10, -19);
        ivB = Interval.of(-9, -9);
        expected = Interval.of(-19, -28);
        actual = ivA.add(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(-10, 1);
        ivB = Interval.of(-18, -15);
        expected = Interval.of(-28, -14);
        actual = ivA.add(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(1, 3);
        ivB = Interval.of(-13, -5);
        expected = Interval.of(-12, -2);
        actual = ivA.add(ivB);
        Assertions.assertEquals(expected, actual);
        // subtract
        ivA = Interval.of(-1, -8);
        ivB = Interval.of(-17, -6);
        expected = Interval.of(16, -2);
        actual = ivA.subtract(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(0, 9);
        ivB = Interval.of(-14, 11);
        expected = Interval.of(-11, 23);
        actual = ivA.subtract(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(-20, -14);
        ivB = Interval.of(-16, -14);
        expected = Interval.of(-6, 2);
        actual = ivA.subtract(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(7, 13);
        ivB = Interval.of(-12, -6);
        expected = Interval.of(13, 25);
        actual = ivA.subtract(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(-16, -11);
        ivB = Interval.of(13, -14);
        expected = Interval.of(-29, 3);
        actual = ivA.subtract(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(0, -13);
        ivB = Interval.of(-12, -11);
        expected = Interval.of(-2, 12);
        actual = ivA.subtract(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(17, 2);
        ivB = Interval.of(17, 20);
        expected = Interval.of(0, -18);
        actual = ivA.subtract(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(6, -16);
        ivB = Interval.of(0, 5);
        expected = Interval.of(6, -21);
        actual = ivA.subtract(ivB);
        Assertions.assertEquals(expected, actual);
        // multiply
        ivA = Interval.of(-3, -17);
        ivB = Interval.of(17, 10);
        expected = Interval.of(-30, -289);
        actual = ivA.multiply(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(2, -14);
        ivB = Interval.of(16, 3);
        expected = Interval.of(32, -224);
        actual = ivA.multiply(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(19, 6);
        ivB = Interval.of(-20, 17);
        expected = Interval.of(-380, 323);
        actual = ivA.multiply(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(11, 20);
        ivB = Interval.of(10, 16);
        expected = Interval.of(110, 320);
        actual = ivA.multiply(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(1, 6);
        ivB = Interval.of(-3, 3);
        expected = Interval.of(-18, 18);
        actual = ivA.multiply(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(-8, -11);
        ivB = Interval.of(-14, 18);
        expected = Interval.of(154, -198);
        actual = ivA.multiply(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(19, 0);
        ivB = Interval.of(-12, -16);
        expected = Interval.of(-304, 0);
        actual = ivA.multiply(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(-2, -9);
        ivB = Interval.of(13, -14);
        expected = Interval.of(126, -117);
        actual = ivA.multiply(ivB);
        Assertions.assertEquals(expected, actual);
        // divide
        ivA = Interval.of(-19, 6);
        ivB = Interval.of(-9, -16);
        expected = Interval.of(Rational.of(-19, -9), Rational.of(-2, 3));
        actual = ivA.divide(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(0, 2);
        ivB = Interval.of(11, 6);
        expected = Interval.of(Rational.ZERO, Rational.of(1, 3));
        actual = ivA.divide(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(4, -15);
        ivB = Interval.of(14, 8);
        expected = Interval.of(Rational.of(1, 2), Rational.of(-15, 8));
        actual = ivA.divide(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(-20, -20);
        ivB = Interval.of(9, 17);
        expected = Interval.of(Rational.of(-20, 9), Rational.of(-20, 17));
        actual = ivA.divide(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(-6, 20);
        ivB = Interval.of(-15, 16);
        actual = ivA.divide(ivB);
        Assertions.assertEquals(Interval.NaN, actual);
        ivA = Interval.of(-20, -15);
        ivB = Interval.of(-16, 4);
        actual = ivA.divide(ivB);
        Assertions.assertEquals(Interval.NaN, actual);
        ivA = Interval.of(-1, 6);
        ivB = Interval.of(14, 20);
        expected = Interval.of(Rational.of(-1, 14), Rational.of(6, 14));
        actual = ivA.divide(ivB);
        Assertions.assertEquals(expected, actual);
        ivA = Interval.of(3, -5);
        ivB = Interval.of(-14, -19);
        expected = Interval.of(Rational.of(3, -14), Rational.of(5, 14));
        actual = ivA.divide(ivB);
        Assertions.assertEquals(expected, actual);
        actual = Interval.ZERO.divide(Interval.ZERO);
        Assertions.assertEquals(Interval.NaN, actual);
    }

    @Test
    public void negateTest() {
        Interval expected, actual;
        expected = Interval.of(-1);
        actual = Interval.ONE.negate();
        Assertions.assertEquals(expected, actual);
        expected = Interval.of(-1, 1);
        actual = expected.negate();
        Assertions.assertEquals(expected, actual);
        expected = Interval.ONE;
        actual = Interval.of(-1).negate();
        Assertions.assertEquals(expected, actual);
        expected = Interval.of(-3, 5);
        actual = Interval.of(-5, 3).negate();
        Assertions.assertEquals(expected, actual);
        expected = Interval.of(2, 3);
        actual = Interval.of(-3, -2).negate();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void inverseTest() {
        Interval expected, actual;
        expected = Interval.ONE;
        actual = Interval.ONE.inverse();
        Assertions.assertEquals(expected, actual);
        expected = Interval.of(.5);
        actual = Interval.TWO.inverse();
        Assertions.assertEquals(expected, actual);
        expected = Interval.of(.25, .5);
        actual = Interval.of(2, 4).inverse();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void avgTest() {
        Rational expected, actual;
        expected = Rational.ONE;
        actual = Interval.ONE.avg();
        Assertions.assertEquals(expected, actual);
        expected = Rational.of("5.5");
        actual = Interval.of(1, 10).avg();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void uncertaintyTest() {
        Rational expected, actual;
        expected = Rational.ZERO;
        actual = Interval.ONE.uncertainty();
        Assertions.assertEquals(expected, actual);
        expected = Rational.of(9);
        actual = Interval.of(1, 10).uncertainty();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void mergeWithTest() {
        Interval expected, actual;
        expected = Interval.of(1, 10);
        actual = Interval.ONE.mergeWith(Interval.TEN);
        Assertions.assertEquals(expected, actual);
        expected = Interval.of(0, 10);
        actual = Interval.ZERO.mergeWith(Interval.ONE).mergeWith(Interval.TWO).mergeWith(Interval.TEN);
        Assertions.assertEquals(expected, actual);
        expected = Interval.of(Rational.NEGATIVE_INFINITY, Rational.POSITIVE_INFINITY);
        actual = Interval.NEGATIVE_INFINITY.mergeWith(Interval.POSITIVE_INFINITY);
        Assertions.assertEquals(expected, actual);
        actual = Interval.ONE.mergeWith(Interval.NaN);
        Assertions.assertEquals(Interval.NaN, actual);
        actual = Interval.NaN.mergeWith(Interval.NaN);
        Assertions.assertEquals(Interval.NaN, actual);
        actual = Interval.NaN.mergeWith(Interval.NaN);
        Assertions.assertEquals(Interval.NaN, actual);
    }

    @Test
    public void containsTest() {
        // contains Rational
        Assertions.assertTrue(Interval.of(0, 2).contains(Rational.ZERO));
        Assertions.assertTrue(Interval.of(0, 2).contains(Rational.ONE));
        Assertions.assertTrue(Interval.of(0, 2).contains(Rational.TWO));
        Assertions.assertFalse(Interval.of(0, 2).contains(Rational.TEN));
        Assertions.assertFalse(Interval.of(0, 2).contains(Rational.NaN));
        Assertions.assertFalse(Interval.NaN.contains(Rational.TWO));
        Assertions.assertFalse(Interval.NaN.contains(Rational.NaN));
        // contains Interval
        Assertions.assertFalse(Interval.of(0, 2).contains(Interval.of(-1, 1)));
        Assertions.assertFalse(Interval.of(0, 2).contains(Interval.of(-1, 2)));
        Assertions.assertTrue(Interval.of(0, 2).contains(Interval.of(0, 2)));
        Assertions.assertTrue(Interval.of(0, 2).contains(Interval.of(1, 2)));
        Assertions.assertTrue(Interval.of(0, 2).contains(Interval.of(2)));
        Assertions.assertFalse(Interval.of(0, 2).contains(Interval.of(2, 3)));
        Assertions.assertFalse(Interval.of(0, 2).contains(Interval.of(-1, 3)));
        Assertions.assertFalse(Interval.of(0, 2).contains(Interval.NaN));
        Assertions.assertFalse(Interval.NaN.contains(Interval.of(1, 2)));
        Assertions.assertFalse(Interval.NaN.contains(Interval.NaN));
    }

    @Test
    public void comparisonsTest() {
        List<Interval> orderedTestValues = List.of(
                Interval.of(Rational.NEGATIVE_INFINITY),
                Interval.of(Rational.TEN.negate()),
                Interval.of(Rational.TEN.negate(), Rational.ONE.negate()),
                Interval.of(Rational.ONE.negate()),
                Interval.of(Rational.ONE.negate(), Rational.ZERO),
                Interval.of(Rational.ONE.negate(), Rational.ONE),
                Interval.of(Rational.ZERO),
                Interval.of(Rational.ZERO, Rational.ONE),
                Interval.of(Rational.ONE),
                Interval.of(Rational.TEN),
                Interval.of(Rational.POSITIVE_INFINITY));
        for (int i = 0; i < orderedTestValues.size(); i++) {
            Interval interval = orderedTestValues.get(i);
            orderedTestValues.subList(0, i).forEach(iv -> {
                Assertions.assertEquals(1, interval.compareTo(iv), "Comparing " + interval + " to " + iv);
                Assertions.assertEquals(-1, iv.compareTo(interval), "Comparing " + iv + " to " + interval);
            });
            orderedTestValues.subList(i + 1, orderedTestValues.size()).forEach(iv -> {
                Assertions.assertEquals(-1, interval.compareTo(iv), "Comparing " + interval + " to " + iv);
                Assertions.assertEquals(1, iv.compareTo(interval), "Comparing " + iv + " to " + interval);
            });
        }
    }

    @Test
    public void accuracyTest() {
        int initialAccuracyBits = Interval.getAccuracyBits();
        Interval.setAccuracyBits(1234);
        Assertions.assertEquals(1234, Interval.getAccuracyBits());
        Interval.setAccuracyBits(4321);
        Assertions.assertEquals(4321, Interval.getAccuracyBits());
        Interval.setAccuracyBits(initialAccuracyBits);
        Assertions.assertEquals(initialAccuracyBits, Interval.getAccuracyBits());
    }

    @Test
    public void factoryFunctionsTest() {
        Interval interval;
        // from Rational
        interval = Interval.of(Rational.ONE);
        Assertions.assertEquals(Rational.ONE, interval.min);
        Assertions.assertEquals(Rational.ONE, interval.max);
        interval = Interval.of(Rational.ONE, Rational.TWO.negate());
        Assertions.assertEquals(Rational.TWO.negate(), interval.min);
        Assertions.assertEquals(Rational.ONE, interval.max);
        interval = Interval.of(Rational.NaN, Rational.ONE);
        Assertions.assertEquals(Interval.NaN, interval);
        interval = Interval.of(Rational.ONE, Rational.NaN);
        Assertions.assertEquals(Interval.NaN, interval);
        interval = Interval.of(Rational.NaN, Rational.NaN);
        Assertions.assertEquals(Interval.NaN, interval);
        // from BigInteger
        interval = Interval.of(BigInteger.ZERO);
        Assertions.assertEquals(Rational.ZERO, interval.min);
        Assertions.assertEquals(Rational.ZERO, interval.max);
        interval = Interval.of(BigInteger.ONE.negate(), BigInteger.TEN);
        Assertions.assertEquals(Rational.ONE.negate(), interval.min);
        Assertions.assertEquals(Rational.TEN, interval.max);
        // from long
        interval = Interval.of(1234);
        Assertions.assertEquals(Rational.of(1234), interval.min);
        Assertions.assertEquals(Rational.of(1234), interval.max);
        interval = Interval.of(-2, -1);
        Assertions.assertEquals(Rational.TWO.negate(), interval.min);
        Assertions.assertEquals(Rational.ONE.negate(), interval.max);
        // from double
        interval = Interval.of(.25);
        Assertions.assertEquals(Rational.TWO.add(Rational.TWO).inverse(), interval.min);
        Assertions.assertEquals(Rational.TWO.add(Rational.TWO).inverse(), interval.max);
        interval = Interval.of(.5, .75);
        Assertions.assertEquals(Rational.TWO.inverse(), interval.min);
        Assertions.assertEquals(Rational.of(3, 4), interval.max);
    }
}