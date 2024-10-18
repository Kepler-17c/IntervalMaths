package space.kepler_17c.interval;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public class Interval implements Comparable<Interval> {
    // accuracy config
    private static final int MINIMAL_BIT_COUNT = 64;
    private static final int DEFAULT_BIT_COUNT = 2048;
    private static int accuracyBitCount = 0;
    private static Rational accuracy = Rational.ZERO;

    static {
        setAccuracyBits(DEFAULT_BIT_COUNT);
    }

    // constant caches
    private static final Map<Integer, Interval> SQRT_2_CACHE = new HashMap<>();
    private static final Map<Integer, Interval> LN_SQRT_2_CACHE = new HashMap<>();
    private static final Map<Integer, Interval> LN_2_CACHE = new HashMap<>();
    private static final Map<Integer, Interval> LN_10_CACHE = new HashMap<>();
    private static final Map<Integer, Interval> E_CACHE = new HashMap<>();
    private static final Map<Integer, Interval> PI_CACHE = new HashMap<>();

    // public constants
    public static final Interval ZERO = new Interval(Rational.ZERO);
    public static final Interval ONE = new Interval(Rational.ONE);
    public static final Interval TWO = new Interval(Rational.TWO);
    public static final Interval TEN = new Interval(Rational.TEN);
    public static final Interval POSITIVE_INFINITY = new Interval(Rational.POSITIVE_INFINITY);
    public static final Interval NEGATIVE_INFINITY = new Interval(Rational.NEGATIVE_INFINITY);
    public static final Interval NaN = new Interval(Rational.NaN);

    public final Rational min;
    public final Rational max;

    private Interval(Rational value) {
        this(value, value);
    }

    private Interval(Rational min, Rational max) {
        this.min = min;
        this.max = max;
    }

    public Interval add(Interval other) {
        return calculate(other, Rational::add);
    }

    public Interval subtract(Interval other) {
        return calculate(other, Rational::subtract);
    }

    public Interval multiply(Interval other) {
        return calculate(other, Rational::multiply);
    }

    public Interval divide(Interval other) {
        return other.min.signum() != other.max.signum() ? NaN : calculate(other, Rational::divide);
    }

    public Interval negate() {
        return Interval.of(min.negate(), max.negate());
    }

    public Interval inverse() {
        return Interval.of(min.inverse(), max.inverse());
    }

    public Rational avg() {
        return min.add(max).divide(Rational.TWO);
    }

    public Rational uncertainty() {
        return max.subtract(min);
    }

    public Interval sqrt() {
        return min.signum() < 0 || this.equals(NaN) ? NaN : sqrt(min).mergeWith(sqrt(max));
    }

    private static Interval sqrt(Rational value) {
        if (value.isInfinite()) {
            return POSITIVE_INFINITY;
        }
        // Newton's method
        Rational result = value;
        Rational next;
        Rational diff;
        do {
            next = value.divide(result).add(result).divide(Rational.TWO);
            diff = result.subtract(next);
            result = next;
        } while (diff.abs().compareTo(accuracy) > 0);
        return Interval.of(result, result.subtract(diff));
    }

    public Interval exp() {
        return this.equals(NaN) ? NaN : exp(min).mergeWith(exp(max));
    }

    private static Interval exp(Rational value) {
        if (value.isInfinite()) {
            return value.signum() < 0 ? ZERO : POSITIVE_INFINITY;
        }
        // derived from Taylor series
        Rational pow = Rational.ONE;
        BigInteger factorial = BigInteger.ONE;
        int factorialCounter = 1;
        Rational result = Rational.ZERO;
        Rational interimResult;
        do {
            interimResult = pow.divide(Rational.of(factorial)).cutAccuracy(accuracyBitCount * 2, false);
            result = result.add(interimResult).cutAccuracy(accuracyBitCount * 2, false);
            pow = pow.multiply(value).cutAccuracy(accuracyBitCount * 2, false);
            factorial = factorial.multiply(BigInteger.valueOf(factorialCounter++));
        } while (interimResult.abs().compareTo(accuracy) > 0);
        return value.signum() < 0
                ? Interval.of(result, result.subtract(interimResult))
                : Interval.of(result, result.add(interimResult));
    }

    public Interval log() {
        return min.signum() < 0 || this.equals(NaN) ? NaN : log(min).mergeWith(log(max));
    }

    private static Interval log(Rational value) {
        if (value.isInfinite()) {
            return POSITIVE_INFINITY;
        }
        if (value.equals(Rational.ZERO)) {
            return NEGATIVE_INFINITY;
        }
        /*
         * based on ln(x) = 2*artanh((x-1)/(x+1)) = sum(k=0, inf, 2/(2k+1) * ((x-1)/(x+1))^(2k+1))
         *
         * the series is most efficient near 1, so the following transform is applied:
         * ln(x) = ln(2^n / 2^n * x) = n*ln(2) + ln(x / 2^n)
         * with ln(2) = 2*ln(sqrt(2)) this yields:
         * ln(x) = 2n*ln(sqrt(2)) + ln(x / 2^n)
         * => choose n such that (x / 2^n) lies in [1/sqrt(x), sqrt(x)]
         *
         * Reduce loop complexity by re-using the interim result as remainder term.
         * Applying the transform above guarantees its absolute value will always be greater.
         */
        Interval optimalRange = new Interval(sqrt2Lookup().inverse().min, sqrt2Lookup().max);
        boolean noTransform = optimalRange.contains(value);
        int magnitude = value.magnitude();
        Rational effectiveValue;
        if (noTransform) {
            effectiveValue = value;
        } else {
            Rational tmpEffective = value.divide(Rational.TWO.pow(magnitude));
            while (tmpEffective.isLessThan(optimalRange.min)) {
                tmpEffective = tmpEffective.multiply(Rational.TWO);
                magnitude--;
            }
            while (tmpEffective.isGreaterThan(optimalRange.max)) {
                tmpEffective = tmpEffective.divide(Rational.TWO);
                magnitude++;
            }
            effectiveValue = tmpEffective;
        }
        // actual calculation
        Rational result = Rational.ZERO;
        Rational interimResult;
        int index = 1;
        Rational fractionPower = effectiveValue.subtract(Rational.ONE).divide(effectiveValue.add(Rational.ONE));
        Rational fractionSquare = fractionPower.pow(2);
        do {
            // calc step
            Rational indexInverse = Rational.TWO.divide(Rational.of(index));
            interimResult = indexInverse.multiply(fractionPower);
            result = result.add(interimResult).cutAccuracy(accuracyBitCount * 2, false);
            // prepare next
            fractionPower = fractionPower.multiply(fractionSquare).cutAccuracy(accuracyBitCount * 2, false);
            index += 2;
        } while (interimResult.abs().isGreaterThan(accuracy));
        Interval resultInterval = Interval.of(result, result.add(interimResult));
        return noTransform
                ? resultInterval
                : Interval.of(2L * magnitude).multiply(lnSqrt2Lookup()).add(resultInterval);
    }

    private static Interval sqrt2Lookup() {
        if (!SQRT_2_CACHE.containsKey(accuracyBitCount)) {
            SQRT_2_CACHE.put(accuracyBitCount, sqrt(Rational.TWO));
        }
        return SQRT_2_CACHE.get(accuracyBitCount);
    }

    private static Interval lnSqrt2Lookup() {
        if (!LN_SQRT_2_CACHE.containsKey(accuracyBitCount)) {
            LN_SQRT_2_CACHE.put(accuracyBitCount, sqrt2Lookup().log());
        }
        return LN_SQRT_2_CACHE.get(accuracyBitCount);
    }

    private static Interval ln2Lookup() {
        if (!LN_2_CACHE.containsKey(accuracyBitCount)) {
            LN_2_CACHE.put(accuracyBitCount, log(Rational.TWO));
        }
        return LN_2_CACHE.get(accuracyBitCount);
    }

    private static Interval ln10Lookup() {
        if (!LN_10_CACHE.containsKey(accuracyBitCount)) {
            LN_10_CACHE.put(accuracyBitCount, log(Rational.TEN));
        }
        return LN_10_CACHE.get(accuracyBitCount);
    }

    public Interval log2() {
        return log().divide(ln2Lookup());
    }

    public Interval log10() {
        return log().divide(ln10Lookup());
    }

    public Interval pow(Interval other) {
        if (this.equals(NaN) || other.equals(NaN)) {
            return NaN;
        }
        if (this.min.signum() > 0) {
            // a^b = e^(ln(a^b)) = e^(b*ln(a))
            return this.log().multiply(other).exp();
        }
        if (this.min.signum() < 0) {
            return NaN;
        }
        return other.contains(Rational.ZERO) ? NaN : ZERO;
    }

    private static Interval calcE() {
        BigInteger factorial = BigInteger.ONE;
        int factorialCounter = 1;
        Rational result = Rational.ZERO;
        Rational interimResult;
        do {
            interimResult = Rational.ONE.divide(Rational.of(factorial)).cutAccuracy(accuracyBitCount * 2, false);
            result = result.add(interimResult).cutAccuracy(accuracyBitCount * 2, false);
            factorial = factorial.multiply(BigInteger.valueOf(factorialCounter++));
        } while (interimResult.compareTo(accuracy) > 0);
        return Interval.of(result, result.add(interimResult));
    }

    public static Interval e() {
        if (!E_CACHE.containsKey(accuracyBitCount)) {
            E_CACHE.put(accuracyBitCount, calcE());
        }
        return E_CACHE.get(accuracyBitCount);
    }

    private static Interval calcPi() {
        // constants
        final BigInteger a = BigInteger.valueOf(1103);
        final BigInteger b = BigInteger.valueOf(26390);
        final BigInteger c = BigInteger.valueOf(396).pow(4);
        final Interval d = Interval.of(9801);
        final BigInteger four = BigInteger.valueOf(4);
        final BigInteger three = BigInteger.valueOf(3);
        // tmp vars
        Rational resultInv = Rational.of(a);
        Rational interimResult;
        BigInteger fact4 = BigInteger.ONE;
        BigInteger lin = BigInteger.ZERO;
        BigInteger factPow = BigInteger.ONE;
        BigInteger pow = BigInteger.ONE;
        BigInteger index = BigInteger.ONE;
        BigInteger idxFour = four;
        do {
            fact4 = fact4.multiply(idxFour.subtract(three))
                    .multiply(idxFour.subtract(BigInteger.TWO))
                    .multiply(idxFour.subtract(BigInteger.ONE))
                    .multiply(idxFour);
            lin = lin.add(b);
            BigInteger numerator = a.add(lin).multiply(fact4);
            factPow = factPow.multiply(index).multiply(index).multiply(index).multiply(index);
            pow = pow.multiply(c);
            BigInteger denominator = factPow.multiply(pow);
            interimResult = Rational.of(numerator, denominator);
            resultInv = resultInv.add(interimResult);
            index = index.add(BigInteger.ONE);
            idxFour = idxFour.add(four);
        } while (interimResult.isGreaterThan(accuracy));
        Interval resInvIv = Interval.of(resultInv, resultInv.add(interimResult));
        return d.divide(sqrt2Lookup().multiply(TWO)).divide(resInvIv);
    }

    public static Interval pi() {
        if (!PI_CACHE.containsKey(accuracyBitCount)) {
            PI_CACHE.put(accuracyBitCount, calcPi());
        }
        return PI_CACHE.get(accuracyBitCount);
    }

    private Interval calculate(Interval other, BiFunction<Rational, Rational, Rational> operator) {
        List<Rational> interimResults = new ArrayList<>();
        interimResults.add(operator.apply(this.min, other.min));
        interimResults.add(operator.apply(this.min, other.max));
        interimResults.add(operator.apply(this.max, other.min));
        interimResults.add(operator.apply(this.max, other.max));
        interimResults.sort(Rational::compareTo);
        if (interimResults.get(0).equals(Rational.NaN)) {
            return NaN;
        }
        return Interval.of(
                interimResults.get(0).cutAccuracy(accuracyBitCount, false),
                interimResults.get(3).cutAccuracy(accuracyBitCount, true));
    }

    public Interval mergeWith(Interval other) {
        return this.equals(NaN) || other.equals(NaN)
                ? NaN
                : Interval.of(Rational.min(this.min, other.min), Rational.max(this.max, other.max));
    }

    public boolean contains(Rational value) {
        return !this.equals(NaN) && min.isLessOrEqualTo(value) && max.isGreaterOrEqualTo(value);
    }

    public boolean contains(Interval other) {
        if (this.equals(NaN) || other.equals(NaN)) {
            return false;
        }
        return min.isLessOrEqualTo(other.min) && max.isGreaterOrEqualTo(other.max);
    }

    public boolean intersects(Interval other) {
        return contains(other.min) || contains(other.max) || other.contains(min) || other.contains(max);
    }

    @Override
    public int compareTo(Interval other) {
        int lowerCmp = this.min.compareTo(other.min);
        if (lowerCmp != 0) {
            return lowerCmp;
        }
        return this.max.compareTo(other.max);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Interval other) {
            return this.min.equals(other.min) && this.max.equals(other.max);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return "[" + min + ", " + max + "]";
    }

    public static void setAccuracyBits(int bitCount) {
        accuracyBitCount = Math.max(bitCount, MINIMAL_BIT_COUNT);
        accuracy = Rational.of(BigInteger.ONE.shiftLeft(accuracyBitCount)).inverse();
    }

    public static int getAccuracyBits() {
        return accuracyBitCount;
    }

    public static Rational getAccuracy() {
        return accuracy;
    }

    public static Interval of(Rational limitA, Rational limitB) {
        if (limitA.isNaN() || limitB.isNaN()) {
            return NaN;
        }
        return limitA.compareTo(limitB) < 0 ? new Interval(limitA, limitB) : new Interval(limitB, limitA);
    }

    public static Interval of(BigInteger limitA, BigInteger limitB) {
        return Interval.of(Rational.of(limitA), Rational.of(limitB));
    }

    public static Interval of(long limitA, long limitB) {
        return Interval.of(Rational.of(limitA), Rational.of(limitB));
    }

    public static Interval of(double limitA, double limitB) {
        return Interval.of(Rational.of(limitA), Rational.of(limitB));
    }

    public static Interval of(Rational value) {
        return new Interval(value, value);
    }

    public static Interval of(BigInteger value) {
        return Interval.of(Rational.of(value));
    }

    public static Interval of(long value) {
        return Interval.of(Rational.of(value));
    }

    public static Interval of(double value) {
        return Interval.of(Rational.of(value));
    }
}
