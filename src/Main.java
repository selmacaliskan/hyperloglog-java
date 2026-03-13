public class Main {

    public static void main(String[] args) {

        HyperLogLog hll = new HyperLogLog(10);

        for (int i = 0; i < 100000; i++) {

            hll.add("user" + i);

        }

        System.out.println("Tahmini değer: " + hll.estimate());

    }

}