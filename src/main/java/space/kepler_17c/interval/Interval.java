package space.kepler_17c.interval;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class Interval implements Comparable<Interval> {
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
        return Interval.of(interimResults.get(0), interimResults.get(3));
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
