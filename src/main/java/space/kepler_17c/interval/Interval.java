package space.kepler_17c.interval;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class Interval implements Comparable<Interval> {
    public final Rational min;
    public final Rational max;

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
        return calculate(other, Rational::divide);
    }

    private Interval calculate(Interval other, BiFunction<Rational, Rational, Rational> operator) {
        List<Rational> interimResults = new ArrayList<>();
        interimResults.add(operator.apply(this.min, other.min));
        interimResults.add(operator.apply(this.min, other.max));
        interimResults.add(operator.apply(this.max, other.min));
        interimResults.add(operator.apply(this.max, other.max));
        interimResults.sort(Rational::compareTo);
        if (interimResults.get(0).equals(Rational.NaN)) {
            return new Interval(Rational.NaN, Rational.NaN);
        }
        return new Interval(interimResults.get(0), interimResults.get(3));
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
        return Interval.of(value, value);
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
