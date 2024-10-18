package space.kepler_17c.interval;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rational implements Comparable<Rational> {
    private static final String INFINITY_STR = "Infinity";
    private static final String NaN_STR = "NaN";
    private static final char DECIMAL_SEPARATOR = '.';
    private static final Character[] SIGN_CHARS = {'+', '-'};
    private static final Character[] EXPONENT_PREFIX_CHARS = {'e', 'E'};

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
        if (this.isNaN() || other.isNaN()) {
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

    public Rational pow(int exponent) {
        if (isNaN()) {
            return NaN;
        }
        if (isInfinite()) {
            if (exponent == 0) {
                return ONE;
            }
            if (exponent < 0) {
                return ZERO;
            }
            return signum() < 0 ? (exponent % 2 == 0 ? POSITIVE_INFINITY : NEGATIVE_INFINITY) : POSITIVE_INFINITY;
        }
        if (exponent < 0) {
            return inverse().pow(-exponent);
        }
        if (exponent == 0) {
            return equals(ZERO) ? NaN : Rational.ONE;
        }
        Rational result = Rational.ONE;
        for (int mask = Integer.highestOneBit(exponent); mask != 0; mask >>>= 1) {
            result = result.multiply(result);
            if ((exponent & mask) != 0) {
                result = result.multiply(this);
            }
        }
        return result;
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

    public boolean isNaN() {
        return numerator.equals(BigInteger.ZERO) && denominator.equals(BigInteger.ZERO);
    }

    public boolean isInteger() {
        return denominator.equals(BigInteger.ONE);
    }

    public int signum() {
        return numerator.signum();
    }

    public Rational negate() {
        return new Rational(numerator.negate(), denominator);
    }

    public Rational abs() {
        return signum() < 0 ? negate() : this;
    }

    public Rational floor() {
        if (!isFinite()) {
            return this;
        }
        BigInteger rounded = numerator.divide(denominator);
        return rounded.multiply(denominator).compareTo(numerator) <= 0
                ? Rational.of(rounded)
                : Rational.of(rounded.subtract(BigInteger.ONE));
    }

    public Rational floorToMultipleOf(Rational value) {
        return this.divide(value).floor().multiply(value);
    }

    public Rational ceil() {
        if (!isFinite()) {
            return this;
        }
        BigInteger rounded = numerator.divide(denominator);
        return rounded.multiply(denominator).compareTo(numerator) >= 0
                ? Rational.of(rounded)
                : Rational.of(rounded.add(BigInteger.ONE));
    }

    public Rational ceilToMultipleOf(Rational value) {
        return this.divide(value).ceil().multiply(value);
    }

    public Rational clamp(Rational lower, Rational upper) {
        if (upper.isLessThan(lower)) {
            return NaN;
        }
        if (isLessThan(lower)) {
            return lower;
        }
        if (isGreaterThan(upper)) {
            return upper;
        }
        return this;
    }

    public Rational cutAccuracy(int bitCount, boolean roundingUp) {
        int maxBitLength = Math.max(numerator.bitLength(), denominator.bitLength());
        if (maxBitLength <= bitCount) {
            return this;
        }
        int shift = maxBitLength - bitCount;
        BigInteger shiftedNum =
                numerator.signum() < 0 ? numerator.negate().shiftRight(shift).negate() : numerator.shiftRight(shift);
        BigInteger shiftedDen = denominator.shiftRight(shift);
        Rational shifted = Rational.of(shiftedNum, shiftedDen);
        int diffSignum = shifted.compareTo(this);
        // no need to check for diffSignum == 0 because that's impossible in minimal fractions
        if (diffSignum > 0 == roundingUp) {
            return shifted;
        }
        if (signum() >= 0 == diffSignum > 0) {
            return Rational.of(shiftedNum, shiftedDen.add(BigInteger.ONE));
        }
        return signum() >= 0
                ? Rational.of(shiftedNum.add(BigInteger.ONE), shiftedDen)
                : Rational.of(shiftedNum.subtract(BigInteger.ONE), shiftedDen);
    }

    public int magnitude() {
        return numerator.bitLength() - denominator.bitLength();
    }

    public boolean isGreaterThan(Rational other) {
        return this.compareTo(other) > 0;
    }

    public boolean isGreaterOrEqualTo(Rational other) {
        return this.compareTo(other) >= 0;
    }

    public boolean isLessThan(Rational other) {
        return this.compareTo(other) < 0;
    }

    public boolean isLessOrEqualTo(Rational other) {
        return this.compareTo(other) <= 0;
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
        if (isNaN() && other.isNaN()) {
            return 0;
        }
        if (isNaN()) {
            return -1;
        }
        if (other.isNaN()) {
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
            return (signum() < 0 ? "-" : "") + INFINITY_STR;
        }
        return NaN_STR;
    }

    public String toDecimalString(int digits, boolean cutTrailingZeros) {
        String raw = toDecimalString(digits);
        int decimalPointIndex;
        if (cutTrailingZeros && (decimalPointIndex = raw.indexOf(DECIMAL_SEPARATOR)) >= 0) {
            int lastNonZeroDigitIndex;
            for (int i = raw.length() - 1; ; i--) {
                if (raw.charAt(i) != '0') {
                    lastNonZeroDigitIndex = i;
                    break;
                }
            }
            return raw.substring(0, Math.max(decimalPointIndex + 2, lastNonZeroDigitIndex + 1));
        } else {
            return raw;
        }
    }

    public String toDecimalString(int digits) {
        if (isFinite()) {
            if (isInteger()) {
                return numerator.toString();
            }
            BigInteger[] divisionResult = numerator.divideAndRemainder(denominator);
            String integerPart = divisionResult[0].toString();
            String fractionPart = divisionResult[1]
                    .abs()
                    .multiply(BigInteger.TEN.pow(digits))
                    .divide(denominator)
                    .toString();
            String leadingZeros = IntStream.range(0, digits - fractionPart.length())
                    .map(i -> 0)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining());
            return integerPart + DECIMAL_SEPARATOR + leadingZeros + fractionPart;
        }
        if (isInfinite()) {
            return (signum() < 0 ? "-" : "") + INFINITY_STR;
        }
        return NaN_STR;
    }

    private Rational normaliseFraction() {
        if (isNaN()) {
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

    public static Rational min(Rational... values) {
        return Arrays.stream(values).min(Rational::compareTo).orElse(Rational.POSITIVE_INFINITY);
    }

    public static Rational max(Rational... values) {
        return Arrays.stream(values).max(Rational::compareTo).orElse(Rational.NEGATIVE_INFINITY);
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
            return new Rational(numerator, denominator)
                    .multiply(Rational.TWO.pow(exponent))
                    .normaliseFraction();
        }
        if (Double.isInfinite(value)) {
            return Math.signum(value) > 0 ? POSITIVE_INFINITY : NEGATIVE_INFINITY;
        }
        return NaN;
    }

    public static Rational of(String value) {
        // handle special cases
        if (value.isEmpty()) {
            throw new NumberFormatException("Cannot parse empty string");
        }
        if (value.equals(NaN_STR)) {
            return NaN;
        }
        if (value.endsWith(INFINITY_STR)) {
            if (value.length() == INFINITY_STR.length()) {
                return POSITIVE_INFINITY;
            }
            if (value.length() == INFINITY_STR.length() + 1) {
                switch (value.charAt(0)) {
                    case '+':
                        return POSITIVE_INFINITY;
                    case '-':
                        return NEGATIVE_INFINITY;
                    default:
                        // fall through to exception
                }
            }
            throw new NumberFormatException("Malformed " + INFINITY_STR + " term");
        }
        // prepare readers for single chars or digit groups
        AtomicInteger index = new AtomicInteger();
        Function<Character[], Optional<Character>> charReader = (charOptions) -> {
            if (index.get() >= value.length()) {
                return Optional.empty();
            }
            char valChar = value.charAt(index.get());
            for (char c : charOptions) {
                if (valChar == c) {
                    index.incrementAndGet();
                    return Optional.of(c);
                }
            }
            return Optional.empty();
        };
        Supplier<String> digitReader = () -> {
            int start = index.get();
            int end;
            for (end = start; end < value.length(); end++) {
                char c = value.charAt(end);
                if (c < '0' || '9' < c) {
                    break;
                }
            }
            index.set(end);
            return value.substring(start, end);
        };
        // read significand
        Optional<Character> sign = charReader.apply(SIGN_CHARS);
        String integerPart = digitReader.get();
        String fractionPart =
                charReader.apply(new Character[] {DECIMAL_SEPARATOR}).isPresent() ? digitReader.get() : "";
        if (integerPart.isEmpty() && fractionPart.isEmpty()) {
            throw new NumberFormatException("Malformed integer & fraction part");
        }
        // read exponent
        Optional<Character> exponentSign = Optional.empty();
        String exponent = "";
        if (charReader.apply(EXPONENT_PREFIX_CHARS).isPresent()) {
            exponentSign = charReader.apply(SIGN_CHARS);
            exponent = digitReader.get();
            if (exponent.isEmpty()) {
                throw new NumberFormatException("Malformed exponent");
            }
        }
        // compile result
        int effectiveExponent;
        if (exponent.isEmpty()) {
            effectiveExponent = 0;
        } else {
            effectiveExponent = Integer.parseInt(exponentSign.orElse('+') + exponent);
        }
        effectiveExponent -= fractionPart.length();
        Rational magnitudeFactor = Rational.TEN.pow(effectiveExponent);
        return Rational.of(new BigInteger(sign.orElse('+') + integerPart + fractionPart))
                .multiply(magnitudeFactor);
    }
}
