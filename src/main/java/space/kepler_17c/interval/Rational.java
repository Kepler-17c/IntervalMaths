package space.kepler_17c.interval;

import java.math.BigInteger;

public class Rational {
    public static final Rational ZERO = new Rational(BigInteger.ZERO, BigInteger.ONE);
    public static final Rational ONE = new Rational(BigInteger.ONE, BigInteger.ONE);
    public static final Rational TWO = new Rational(BigInteger.TWO, BigInteger.ONE);
    public static final Rational TEN = new Rational(BigInteger.TEN, BigInteger.ONE);
    public static final Rational POSITIVE_INFINITY = new Rational(BigInteger.ONE, BigInteger.ZERO);
    public static final Rational NEGATIVE_INFINITY = new Rational(BigInteger.ONE.negate(), BigInteger.ZERO);
    public static final Rational NAN = new Rational(BigInteger.ZERO, BigInteger.ZERO);

    private final BigInteger numerator;
    private final BigInteger denominator;

    private Rational(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    private Rational normaliseFraction() {
        if (numerator.equals(BigInteger.ZERO) && denominator.equals(BigInteger.ZERO)) {
            return this;
        } else if (numerator.equals(BigInteger.ZERO)) {
            return ZERO;
        } else if (denominator.equals(BigInteger.ZERO)) {
            return numerator.signum() > 0 ? POSITIVE_INFINITY : NEGATIVE_INFINITY;
        } else {
            boolean negative = false;
            BigInteger tmpNum = numerator;
            BigInteger tmpDen = denominator;
            if (tmpNum.signum() < 0) {
                negative = true;
                tmpNum = tmpNum.negate();
            }
            if (tmpDen.signum() < 0) {
                negative = !negative;
                tmpDen = tmpDen.negate();
            }
            BigInteger gcd = numerator.gcd(denominator);
            tmpNum = tmpNum.divide(gcd);
            tmpDen = tmpDen.divide(gcd);
            if (negative) {
                tmpNum = tmpNum.negate();
            }
            return new Rational(tmpNum, tmpDen);
        }
    }

    public static Rational valueOf(int numerator, int denominator) {
        return new Rational(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator)).normaliseFraction();
    }

    public static Rational valueOf(int value) {
        return Rational.valueOf(value, 1);
    }
}
