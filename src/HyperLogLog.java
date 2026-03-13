import java.security.MessageDigest;
import java.math.BigInteger;

public class HyperLogLog {

    private int p;
    private int m;
    private int[] registers;

    // Constructor
    public HyperLogLog(int p) {

        this.p = p;
        this.m = 1 << p;

        registers = new int[m];

    }

    // HASH FONKSİYONU
    private long hash(String value) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] digest = md.digest(value.getBytes());

            BigInteger bigInt = new BigInteger(1, digest);

            return bigInt.longValue();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    // LEADING ZERO SAYMA
    private int leadingZeros(long x) {

        int count = 1;

        while ((x & (1L << 63)) == 0) {

            count++;

            x <<= 1;

        }

        return count;

    }

    // VERİ EKLEME
    public void add(String value) {

        long hash = hash(value);

        int bucket = (int)(hash >>> (64 - p));

        long remaining = hash << p;

        int zeros = leadingZeros(remaining);

        registers[bucket] = Math.max(registers[bucket], zeros);

    }

    // CARDINALITY TAHMİNİ
    public double estimate() {

        double alpha;

        if (m == 16)
            alpha = 0.673;
        else if (m == 32)
            alpha = 0.697;
        else if (m == 64)
            alpha = 0.709;
        else
            alpha = 0.7213 / (1 + 1.079 / m);

        double sum = 0.0;

        for (int register : registers) {

            sum += Math.pow(2, -register);

        }

        double estimate = alpha * m * m / sum;

        return estimate;

    }

    // MERGE
    public void merge(HyperLogLog other) {

        for (int i = 0; i < m; i++) {

            registers[i] = Math.max(registers[i], other.registers[i]);

        }

    }

}