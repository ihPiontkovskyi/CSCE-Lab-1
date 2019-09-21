import java.util.*;

class LeastSquareMethod {
    private static Map<Double, Double> points = new HashMap<Double, Double>() {
        {
            put(-1.6, 4.3200);
            put(-1.2, 3.2800);
            put(-0.8, 2.8800);
            put(-0.4, 3.1200);
            put(0.0, 4.0000);
            put(0.4, 5.5200);
            put(0.8, 7.6800);
            put(1.2, 10.4800);
            put(1.6, 13.9200);
            put(2.0, 18.0000);
        }
    };
    private static List<Double> arrayX = new ArrayList<Double>() {
        {
            this.add(-1.6);
            this.add(-1.2);
            this.add(-0.8);
            this.add(-0.4);
            this.add(0.0);
            this.add(0.4);
            this.add(0.8);
            this.add(1.2);
            this.add(1.6);
            this.add(2.0);
        }
    };
    private final Integer N = 3;

    List<Double> calculateCoefficients() {
        List<List<Double>> arrayA = new ArrayList<>();
        ArrayList<Double> arrayB = new ArrayList<Double>(Collections.nCopies(N, 0.0)) {
        };
        for (int i = 0; i < N; i++) {
            arrayA.add(new ArrayList<>(Collections.nCopies(N, 0.0)));

            for (int j = 0; j < N; j++) {

                double temp = 0;
                for (int k = 0; k < points.size(); k++) {
                    temp += Math.pow(arrayX.get(k), i + j);
                }
                arrayA.get(i).set(j, temp);
            }
            double y = 0;
            for (int k = 0; k < points.size(); k++) {
                y += points.get(arrayX.get(k)) * Math.pow(arrayX.get(k), i);
            }
            arrayB.set(i, y);
        }
        ArrayList<Double> coef = new ArrayList<>(Collections.nCopies(N, 0.0));
        return SqrtMt.solve(arrayA, arrayB, coef);
    }

    Double getY(double x, List<Double> coef) {
        double res = 0;
        for (int i = 0; i < coef.size(); i++) {
            res += coef.get(i) * Math.pow(x, i);
        }
        return res;
    }

}
