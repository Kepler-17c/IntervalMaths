package space.kepler_17c.interval;

import java.math.BigInteger;
import java.util.Objects;

public class Rational implements Comparable<Rational> {
    public static final Rational ZERO = new Rational(BigInteger.ZERO, BigInteger.ONE);
    public static final Rational ONE = new Rational(BigInteger.ONE, BigInteger.ONE);
    public static final Rational TWO = new Rational(BigInteger.TWO, BigInteger.ONE);
    public static final Rational TEN = new Rational(BigInteger.TEN, BigInteger.ONE);
    public static final Rational POSITIVE_INFINITY = new Rational(BigInteger.ONE, BigInteger.ZERO);
    public static final Rational NEGATIVE_INFINITY = new Rational(BigInteger.ONE.negate(), BigInteger.ZERO);
    public static final Rational NaN = new Rational(BigInteger.ZERO, BigInteger.ZERO);

    public final BigInteger numerator;
    public final BigInteger denominator;

    private Rational(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Rational add(Rational other) {
        if (this.isFinite() && other.isFinite()) {
            BigInteger thisNum = numerator.multiply(other.denominator);
            BigInteger otherNum = other.numerator.multiply(denominator);
            BigInteger newNum = thisNum.add(otherNum);
            BigInteger newDen = denominator.multiply(other.denominator);
            return new Rational(newNum, newDen).normaliseFraction();
        }
        if (this.isNan() || other.isNan()) {
            return NaN;
        }
        if (this.isInfinite() && other.isInfinite()) {
            return this.signum() == other.signum() ? this : NaN;
        }
        return this.isInfinite() ? this : other;
    }

    public Rational subtract(Rational other) {
        return this.add(other.negate());
    }

    public Rational multiply(Rational other) {
        BigInteger newNum = numerator.multiply(other.numerator);
        BigInteger newDen = denominator.multiply(other.denominator);
        return new Rational(newNum, newDen).normaliseFraction();
    }

    public Rational divide(Rational other) {
        return this.multiply(new Rational(other.denominator, other.numerator));
    }

    public Rational inverse() {
        return new Rational(denominator, numerator).normaliseFraction();
    }

    public boolean isFinite() {
        return !denominator.equals(BigInteger.ZERO);
    }

    public boolean isInfinite() {
        return !numerator.equals(BigInteger.ZERO) && denominator.equals(BigInteger.ZERO);
    }

    public boolean isNan() {
        return numerator.equals(BigInteger.ZERO) && denominator.equals(BigInteger.ZERO);
    }

    public int signum() {
        return numerator.signum();
    }

    public Rational negate() {
        return new Rational(numerator.negate(), denominator);
    }

    @Override
    public int compareTo(Rational other) {
        if (isFinite() && other.isFinite()) {
            BigInteger thisNum = numerator.multiply(other.denominator);
            BigInteger otherNum = other.numerator.multiply(denominator);
            return thisNum.compareTo(otherNum);
        }
        // only combinations with NaN or infinity remain
        // first, deal with NaN
        if (isNan() && other.isNan()) {
            return 0;
        }
        if (isNan()) {
            return -1;
        }
        if (other.isNan()) {
            return 1;
        }
        // only combinations of finite and infinity remain
        if (isInfinite() && other.isInfinite()) {
            return numerator.compareTo(other.numerator);
        }
        // only one is infinite, the other one is finite
        return isInfinite() ? signum() : -other.signum();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rational r) {
            return this.numerator.equals(r.numerator) && this.denominator.equals(r.denominator);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    @Override
    public String toString() {
        if (isFinite()) {
            return numerator + "/" + denominator;
        }
        if (isInfinite()) {
            return signum() > 0 ? "Infinity" : "-Infinity";
        }
        return "NaN";
    }

    private Rational normaliseFraction() {
        if (isNan()) {
            return this;
        } else if (numerator.equals(BigInteger.ZERO)) {
            return ZERO;
        } else if (isInfinite()) {
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

    public static Rational of(BigInteger numerator, BigInteger denominator) {
        return new Rational(numerator, denominator).normaliseFraction();
    }

    public static Rational of(long numerator, long denominator) {
        return Rational.of(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    public static Rational of(BigInteger value) {
        return new Rational(value, BigInteger.ONE);
    }

    public static Rational of(long value) {
        return Rational.of(BigInteger.valueOf(value));
    }

    public static Rational of(double value) {
        if (Double.isFinite(value)) {
            // prepare masking
            long signMask = (1L << 63); // 1 bit
            long exponentOffset = 52;
            long exponentMask = 0x7ffL; // 11 bits
            int exponentBias = 0x3ff;
            int mantissaBitCount = 52;
            long implicitOne = (1L << mantissaBitCount);
            long mantissaMask = implicitOne - 1; // 52 bits
            // extract sign, exponent, mantissa
            long valueBits = Double.doubleToLongBits(value);
            boolean sign = (valueBits & signMask) != 0L;
            int characteristic = (int) ((valueBits >>> exponentOffset) & exponentMask);
            boolean isSubNormal = characteristic == 0;
            int exponent = (isSubNormal ? 1 : characteristic) - exponentBias - mantissaBitCount;
            long mantissa = (isSubNormal ? 0 : implicitOne) | (valueBits & mantissaMask);
            // build fraction
            BigInteger numerator = sign ? BigInteger.valueOf(-mantissa) : BigInteger.valueOf(mantissa);
            BigInteger denominator = BigInteger.ONE;
            return (exponent > 0
                            ? new Rational(numerator.shiftLeft(exponent), denominator)
                            : new Rational(numerator, denominator.shiftLeft(-exponent)))
                    .normaliseFraction();
        }
        if (Double.isInfinite(value)) {
            return value > .0 ? POSITIVE_INFINITY : NEGATIVE_INFINITY;
        }
        return NaN;
    }
}
