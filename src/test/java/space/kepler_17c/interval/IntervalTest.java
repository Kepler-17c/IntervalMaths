package space.kepler_17c.interval;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

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
    public void sqrtTest() {
        // tmp vars
        Interval sqrt;
        Rational lower, upper;
        // tests
        sqrt = Interval.TWO.sqrt();
        lower = Rational.of("1.414213562373095048801688724209");
        upper = Rational.of("1.41421356237309504880168872421");
        checkInterval(lower, upper, sqrt);
        sqrt = Interval.of(4).sqrt();
        checkInterval(Rational.TWO, sqrt);
        // special values
        Assertions.assertEquals(Interval.NaN, Interval.NaN.sqrt());
        Assertions.assertEquals(Interval.NaN, Interval.of(-1).sqrt());
        Assertions.assertEquals(Interval.POSITIVE_INFINITY, Interval.POSITIVE_INFINITY.sqrt());
    }

    @Test
    public void expTest() {
        // tmp vars
        Interval exp;
        Rational lower, upper;
        // tests
        exp = Interval.ONE.exp();
        lower = Rational.of("2.718281828459045235360287471352");
        upper = Rational.of("2.718281828459045235360287471353");
        checkInterval(lower, upper, exp);
        exp = Interval.ZERO.exp();
        checkInterval(Rational.ONE, exp);
        exp = Interval.NEGATIVE_INFINITY.exp();
        checkInterval(Rational.ZERO, exp);
        exp = Interval.POSITIVE_INFINITY.exp();
        Assertions.assertEquals(Interval.POSITIVE_INFINITY, exp);
        exp = Interval.NaN.exp();
        Assertions.assertEquals(Interval.NaN, exp);
    }

    @Test
    public void logTest() {
        // tmp vars
        Interval log;
        Rational lower, upper;
        Rational accuracyTolerance = Rational.TWO.pow(10);
        // tests
        log = Interval.ONE.log();
        checkInterval(Rational.ZERO, log);
        log = Interval.of(-1).log();
        Assertions.assertEquals(Interval.NaN, log);
        log = Interval.ZERO.log();
        Assertions.assertEquals(Interval.NEGATIVE_INFINITY, log);
        log = Interval.POSITIVE_INFINITY.log();
        Assertions.assertEquals(Interval.POSITIVE_INFINITY, log);
        log = Interval.TWO.log();
        lower = Rational.of("0.6931471805599453094172321214581");
        upper = Rational.of("0.6931471805599453094172321214582");
        checkInterval(lower, upper, log, accuracyTolerance);
        log = Interval.ONE.exp().log();
        checkInterval(Rational.ONE, log, Rational.of(3));
        log = Interval.of(1000000).log10();
        checkInterval(Rational.of(6), log, accuracyTolerance);
        log = Interval.of(Rational.of(2, 3)).log10();
        lower = Rational.of("-0.1760912590556812420812890085306");
        upper = Rational.of("-0.1760912590556812420812890085307");
        checkInterval(lower, upper, log, accuracyTolerance);
        log = Interval.of(Rational.of(BigInteger.ONE.shiftLeft(8))).log2();
        checkInterval(Rational.of(8), log, accuracyTolerance);
        log = Interval.TWO.inverse().log2();
        checkInterval(Rational.ONE.negate(), log, accuracyTolerance);
        log = Interval.NaN.log();
        Assertions.assertEquals(Interval.NaN, log);
    }

    @Test
    public void powTest() {
        Interval pow;
        Rational accuracyTolerance = Rational.TWO.pow(16);
        // tests
        pow = Interval.TWO.pow(Interval.TEN);
        checkInterval(Rational.of(1024), pow, accuracyTolerance);
        pow = Interval.TWO.pow(Interval.ONE.negate());
        checkInterval(Rational.of(1, 2), pow, accuracyTolerance);
        // special values
        pow = Interval.ONE.negate().pow(Interval.ONE);
        Assertions.assertEquals(Interval.NaN, pow);
        pow = Interval.POSITIVE_INFINITY.pow(Interval.ONE);
        Assertions.assertEquals(Interval.POSITIVE_INFINITY, pow);
        pow = Interval.NEGATIVE_INFINITY.pow(Interval.ONE);
        Assertions.assertEquals(Interval.NaN, pow);
        pow = Interval.ZERO.pow(Interval.ONE);
        Assertions.assertEquals(Interval.ZERO, pow);
        pow = Interval.TWO.pow(Interval.ZERO);
        Assertions.assertEquals(Interval.ONE, pow);
        pow = Interval.ZERO.pow(Interval.ZERO);
        Assertions.assertEquals(Interval.NaN, pow);
        pow = Interval.NaN.pow(Interval.ONE);
        Assertions.assertEquals(Interval.NaN, pow);
        pow = Interval.ONE.pow(Interval.NaN);
        Assertions.assertEquals(Interval.NaN, pow);
        pow = Interval.NaN.pow(Interval.NaN);
        Assertions.assertEquals(Interval.NaN, pow);
    }

    @Test
    public void eTest() {
        Interval.e(); // initial call to test caching
        Rational lower = Rational.of("2.718281828459045235360287471352");
        Rational upper = Rational.of("2.718281828459045235360287471353");
        checkInterval(lower, upper, Interval.e());
    }

    @Test
    public void piTest() {
        Rational piAccuracy = Rational.TWO.pow(6);
        Interval.pi(); // initial call to test caching
        Rational lower = Rational.of("3.141592653589793238462643383279");
        Rational upper = Rational.of("3.141592653589793238462643383280");
        checkInterval(lower, upper, Interval.pi(), piAccuracy);
        String piString = "3." // more digits than calculated at default accuracy (2048 bits = around 600 decimals)
                + "14159265358979323846264338327950288419716939937510" // 50
                + "58209749445923078164062862089986280348253421170679" // 100
                + "82148086513282306647093844609550582231725359408128" // 150
                + "48111745028410270193852110555964462294895493038196" // 200
                + "44288109756659334461284756482337867831652712019091" // 250
                + "45648566923460348610454326648213393607260249141273" // 300
                + "72458700660631558817488152092096282925409171536436" // 350
                + "78925903600113305305488204665213841469519415116094" // 400
                + "33057270365759591953092186117381932611793105118548" // 450
                + "07446237996274956735188575272489122793818301194912" // 500
                + "98336733624406566430860213949463952247371907021798" // 550
                + "60943702770539217176293176752384674818467669405132" // 600
                + "00056812714526356082778577134275778960917363717872" // 650
                + "14684409012249534301465495853710507922796892589235" // 700
                + "42019956112129021960864034418159813629774771309960" // 750
                + "51870721134999999837297804995105973173281609631859" // 800
                + "50244594553469083026425223082533446850352619311881" // 850
                + "71010003137838752886587533208381420617177669147303" // 900
                + "59825349042875546873115956286388235378759375195778" // 950
                + "18577805321712268066130019278766111959092164201989"; // 1000
        checkInterval(Rational.of(piString), Interval.pi(), piAccuracy);
    }

    @Test
    public void degRadConversionTest() {
        List<List<Interval>> testDegRadPairs = List.of(
                List.of(Interval.of(0), Interval.ZERO),
                List.of(Interval.of(90), Interval.piHalf()),
                List.of(Interval.of(-90), Interval.piHalf().negate()),
                List.of(Interval.of(180), Interval.pi()),
                List.of(Interval.of(-180), Interval.pi().negate()),
                List.of(Interval.of(270), Interval.pi().add(Interval.piHalf())),
                List.of(Interval.of(-270), Interval.pi().add(Interval.piHalf()).negate()),
                List.of(Interval.of(360), Interval.pi().multiply(Interval.TWO)),
                List.of(Interval.of(-360), Interval.pi().multiply(Interval.TWO).negate()));
        for (List<Interval> pair : testDegRadPairs) {
            Interval deg = pair.get(0);
            Interval rad = pair.get(1);
            Assertions.assertTrue(deg.degInRad().intersects(rad));
            Assertions.assertTrue(rad.radInDeg().intersects(deg));
        }
    }

    @Test
    public void sinTest() {
        Interval expected, actual;
        Interval approxPi = Interval.of(
                Rational.of("3.141592653589793238462643383279"), Rational.of("3.141592653589793238462643383280"));
        Interval approxPiHalf = approxPi.divide(Interval.TWO);
        // special values
        Assertions.assertEquals(Interval.NaN, Interval.NaN.sin());
        Assertions.assertEquals(Interval.NaN, Interval.POSITIVE_INFINITY.sin());
        Assertions.assertEquals(Interval.NaN, Interval.NEGATIVE_INFINITY.sin());
        expected = Interval.of(-1, 1);
        actual = Interval.of(Rational.NEGATIVE_INFINITY, Rational.ZERO).sin();
        Assertions.assertEquals(expected, actual);
        actual = Interval.of(Rational.ZERO, Rational.POSITIVE_INFINITY).sin();
        Assertions.assertEquals(expected, actual);
        actual = Interval.of(Rational.NEGATIVE_INFINITY, Rational.POSITIVE_INFINITY)
                .sin();
        Assertions.assertEquals(expected, actual);
        // wide intervals
        actual = Interval.of(1, 10).sin();
        Assertions.assertEquals(expected, actual);
        actual = Interval.of(-10, -1).sin();
        Assertions.assertEquals(expected, actual);
        // extrema
        Assertions.assertTrue(Interval.ZERO.sin().contains(Rational.ZERO));
        Assertions.assertTrue(approxPiHalf.sin().contains(Rational.ONE));
        Assertions.assertTrue(approxPi.sin().contains(Rational.ZERO));
        Assertions.assertTrue(approxPi.add(approxPiHalf).sin().contains(Rational.ONE.negate()));
        Assertions.assertTrue(approxPi.multiply(Interval.TWO).sin().contains(Rational.ZERO));
        // known values
        Interval three = Interval.of(3);
        Interval four = Interval.of(4);
        Interval five = Interval.of(5);
        Interval six = Interval.of(6);
        List<List<Interval>> testPairs = List.of(
                List.of(six.sqrt().subtract(Interval.TWO.sqrt()).divide(four), Interval.of(15)),
                List.of(five.sqrt().subtract(Interval.ONE).divide(four), Interval.of(18)),
                List.of(Interval.TWO.inverse(), Interval.of(30)),
                List.of(Interval.TWO.sqrt().inverse(), Interval.of(45)),
                List.of(five.sqrt().add(Interval.ONE).divide(four), Interval.of(54)),
                List.of(three.sqrt().divide(Interval.TWO), Interval.of(60)));
        for (List<Interval> pair : testPairs) {
            expected = pair.get(0);
            Interval deg = pair.get(1);
            Assertions.assertTrue(expected.intersects(deg.degInRad().sin()));
        }
    }

    @Test
    public void cosTest() {
        Interval expected, actual;
        Interval approxPi = Interval.of(
                Rational.of("3.141592653589793238462643383279"), Rational.of("3.141592653589793238462643383280"));
        Interval approxPiHalf = approxPi.divide(Interval.TWO);
        // special values
        Assertions.assertEquals(Interval.NaN, Interval.NaN.cos());
        Assertions.assertEquals(Interval.NaN, Interval.POSITIVE_INFINITY.cos());
        Assertions.assertEquals(Interval.NaN, Interval.NEGATIVE_INFINITY.cos());
        expected = Interval.of(-1, 1);
        actual = Interval.of(Rational.NEGATIVE_INFINITY, Rational.ZERO).cos();
        Assertions.assertEquals(expected, actual);
        actual = Interval.of(Rational.ZERO, Rational.POSITIVE_INFINITY).cos();
        Assertions.assertEquals(expected, actual);
        actual = Interval.of(Rational.NEGATIVE_INFINITY, Rational.POSITIVE_INFINITY)
                .cos();
        Assertions.assertEquals(expected, actual);
        // wide intervals
        actual = Interval.of(1, 10).cos();
        Assertions.assertEquals(expected, actual);
        actual = Interval.of(-10, -1).cos();
        Assertions.assertEquals(expected, actual);
        // extrema
        Assertions.assertTrue(Interval.ZERO.cos().contains(Rational.ONE));
        Assertions.assertTrue(approxPiHalf.cos().contains(Rational.ZERO));
        Assertions.assertTrue(approxPi.cos().contains(Rational.ONE.negate()));
        Assertions.assertTrue(approxPi.add(approxPiHalf).cos().contains(Rational.ZERO));
        Assertions.assertTrue(approxPi.multiply(Interval.TWO).cos().contains(Rational.ONE));
        // known values
        Interval three = Interval.of(3);
        Interval four = Interval.of(4);
        Interval five = Interval.of(5);
        Interval six = Interval.of(6);
        List<List<Interval>> testPairs = List.of(
                List.of(six.sqrt().add(Interval.TWO.sqrt()).divide(four), Interval.of(15)),
                List.of(three.sqrt().divide(Interval.TWO), Interval.of(30)),
                List.of(five.sqrt().add(Interval.ONE).divide(four), Interval.of(36)),
                List.of(Interval.TWO.sqrt().inverse(), Interval.of(45)),
                List.of(Interval.TWO.inverse(), Interval.of(60)),
                List.of(five.sqrt().subtract(Interval.ONE).divide(four), Interval.of(72)),
                List.of(six.sqrt().subtract(Interval.TWO.sqrt()).divide(four), Interval.of(75)));
        for (List<Interval> pair : testPairs) {
            expected = pair.get(0);
            Interval deg = pair.get(1);
            Assertions.assertTrue(expected.intersects(deg.degInRad().cos()));
        }
    }

    @Test
    public void tanTest() {
        Interval expected, actual;
        Interval approxPi = Interval.of(
                Rational.of("3.141592653589793238462643383279"), Rational.of("3.141592653589793238462643383280"));
        Interval approxPiHalf = approxPi.divide(Interval.TWO);
        // special values
        Assertions.assertEquals(Interval.NaN, Interval.NaN.tan());
        Assertions.assertEquals(Interval.NaN, Interval.POSITIVE_INFINITY.tan());
        Assertions.assertEquals(Interval.NaN, Interval.NEGATIVE_INFINITY.tan());
        actual = Interval.of(Rational.NEGATIVE_INFINITY, Rational.ZERO);
        Assertions.assertEquals(Interval.NaN, actual.tan());
        actual = Interval.of(Rational.ZERO, Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Interval.NaN, actual.tan());
        actual = Interval.of(Rational.NEGATIVE_INFINITY, Rational.POSITIVE_INFINITY);
        Assertions.assertEquals(Interval.NaN, actual.tan());
        // wide intervals
        actual = Interval.of(1, 10).tan();
        Assertions.assertEquals(Interval.NaN, actual);
        actual = Interval.of(-10, -1).tan();
        Assertions.assertEquals(Interval.NaN, actual);
        // known values
        Interval sqrt3 = Interval.of(3).sqrt();
        List<List<Interval>> testPairs = List.of(
                List.of(Interval.ZERO, Interval.of(0)),
                List.of(sqrt3.negate().add(Interval.TWO), Interval.of(15)),
                List.of(sqrt3.inverse(), Interval.of(30)),
                List.of(Interval.ONE, Interval.of(45)),
                List.of(sqrt3, Interval.of(60)),
                List.of(sqrt3.add(Interval.TWO), Interval.of(75)));
        for (List<Interval> pair : testPairs) {
            expected = pair.get(0);
            Interval deg = pair.get(1);
            Assertions.assertTrue(expected.intersects(deg.degInRad().tan()));
        }
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
    public void clampTest() {
        Assertions.assertEquals(Interval.ONE, Interval.ZERO.clamp(Rational.ONE, Rational.ONE));
        Assertions.assertEquals(Interval.ONE, Interval.ONE.clamp(Rational.ONE, Rational.ONE));
        Assertions.assertEquals(Interval.ONE, Interval.TWO.clamp(Rational.ONE, Rational.ONE));
        Assertions.assertEquals(Interval.ONE, Interval.ZERO.clamp(Interval.ONE));
        Assertions.assertEquals(Interval.ONE, Interval.ONE.clamp(Interval.ONE));
        Assertions.assertEquals(Interval.ONE, Interval.TWO.clamp(Interval.ONE));
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
    public void intersectsTest() {
        Interval a, b;
        a = Interval.of(1, 3);
        Assertions.assertTrue(a.intersects(a));
        b = Interval.of(0, 2);
        Assertions.assertTrue(a.intersects(b));
        Assertions.assertTrue(b.intersects(a));
        a = Interval.of(0, 3);
        b = Interval.of(1, 2);
        Assertions.assertTrue(a.intersects(b));
        Assertions.assertTrue(b.intersects(a));
    }

    @Test
    public void comparisonsTest() {
        // compareTo
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
        // equals
        Assertions.assertNotEquals(Interval.of(0), null);
        // hashCode
        Assertions.assertEquals(Interval.of(1).hashCode(), Interval.ONE.hashCode());
        Assertions.assertNotEquals(Interval.ONE.hashCode(), Interval.TWO.hashCode());
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
        interval = Interval.of(List.of(Rational.ZERO, Rational.ONE, Rational.TWO));
        Assertions.assertEquals(Rational.ZERO, interval.min);
        Assertions.assertEquals(Rational.TWO, interval.max);
        interval = Interval.of(List.of(Rational.ZERO, Rational.NaN, Rational.TWO));
        Assertions.assertEquals(Rational.NaN, interval.min);
        Assertions.assertEquals(Rational.NaN, interval.max);
        interval = Interval.of(List.of());
        Assertions.assertEquals(Rational.NaN, interval.min);
        Assertions.assertEquals(Rational.NaN, interval.max);
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

    private static void checkInterval(Rational expectedValue, Interval actualInterval) {
        checkInterval(expectedValue, actualInterval, Rational.ONE);
    }

    private static void checkInterval(Rational expectedValue, Interval actualInterval, Rational accuracyRatioLimit) {
        Assertions.assertTrue(
                actualInterval.contains(expectedValue),
                "Interval [" + actualInterval.min.toDecimalString(8) + ", " + actualInterval.max.toDecimalString(8)
                        + "] doesn't contain expected value " + expectedValue.toDecimalString(8));
        Rational accuracyRatio = actualInterval.uncertainty().divide(Interval.getAccuracy());
        Assertions.assertTrue(
                accuracyRatio.isLessOrEqualTo(accuracyRatioLimit),
                "Accuracy ratio failed: expected less than " + accuracyRatioLimit.toDecimalString(8) + " but was "
                        + accuracyRatio.toDecimalString(8));
    }

    private static void checkInterval(Rational expectedLower, Rational expectedUpper, Interval actualInterval) {
        checkInterval(expectedLower, expectedUpper, actualInterval, Rational.ONE);
    }

    private static void checkInterval(
            Rational expectedLower, Rational expectedUpper, Interval actualInterval, Rational accuracyRatioLimit) {
        Assertions.assertTrue(Interval.of(expectedLower, expectedUpper).contains(actualInterval));
        Rational accuracyRatio = actualInterval.uncertainty().divide(Interval.getAccuracy());
        Assertions.assertTrue(
                accuracyRatio.isLessOrEqualTo(accuracyRatioLimit),
                "Accuracy ratio failed: expected less than " + accuracyRatioLimit.toDecimalString(8) + " but was "
                        + accuracyRatio.toDecimalString(8));
    }
}
