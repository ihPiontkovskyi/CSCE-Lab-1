
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CubicalSplineMethod {
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

    private static Double getFunction(Double x) {
        return points.get(x);
    }

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

    List<Double> calculateSpline() {
        List<Double> bSpline = new ArrayList<>();
        bSpline.add(getFunction(arrayX.get(0)));
        for (int i = 1; i < arrayX.size() - 1; ++i) {
            bSpline.add(getFunction(arrayX.get(i)));
            bSpline.add(getFunction(arrayX.get(i)));
        }
        bSpline.add(getFunction(arrayX.get(arrayX.size() - 1)));
        int size = bSpline.size();
        for (int i = 0; i < size; ++i) {
            bSpline.add(0.0);
        }
        bSpline.forEach(e1 -> System.out.print(e1 + " "));
        List<List<Double>> aSpline = new ArrayList<>();
        for (int i = 0; i < bSpline.size(); ++i) {
            List<Double> tmpList = new ArrayList<>();
            for (int j = 0; j < bSpline.size(); ++j) {
                tmpList.add(0.0);
            }
            aSpline.add(tmpList);
        }
        for (int i = 0; i < arrayX.size() - 1; ++i) {
            List<Double> first = aSpline.get(i * 2);
            List<Double> second = aSpline.get(i * 2 + 1);

            first.set(i * 4, arrayX.get(i) * arrayX.get(i) * arrayX.get(i));
            first.set(i * 4 + 1, arrayX.get(i) * arrayX.get(i));
            first.set(i * 4 + 2, arrayX.get(i));
            first.set(i * 4 + 3, 1.0);

            second.set(i * 4, arrayX.get(i + 1) * arrayX.get(i + 1) * arrayX.get(i + 1));
            second.set(i * 4 + 1, arrayX.get(i + 1) * arrayX.get(i + 1));
            second.set(i * 4 + 2, arrayX.get(i + 1));
            second.set(i * 4 + 3, 1.0);
        }

        for (int i = 1; i < arrayX.size() - 1; ++i) {
            List<Double> first = aSpline.get(size + (i - 1) * 2);
            List<Double> second = aSpline.get(size + (i - 1) * 2 + 1);

            first.set((i - 1) * 4, 3 * arrayX.get(i) * arrayX.get(i));
            first.set((i - 1) * 4 + 1, 2 * arrayX.get(i));
            first.set((i - 1) * 4 + 2, 1.0);
            first.set((i - 1) * 4 + 3, 0.0);
            first.set((i - 1) * 4 + 4, -3 * arrayX.get(i) * arrayX.get(i));
            first.set((i - 1) * 4 + 5, -2 * arrayX.get(i));
            first.set((i - 1) * 4 + 6, -1.0);
            first.set((i - 1) * 4 + 7, 0.0);

            second.set((i - 1) * 4, 6 * arrayX.get(i));
            second.set((i - 1) * 4 + 1, 2.0);
            second.set((i - 1) * 4 + 2, 0.0);
            second.set((i - 1) * 4 + 3, 0.0);
            second.set((i - 1) * 4 + 4, -6 * arrayX.get(i));
            second.set((i - 1) * 4 + 5, -2.0);
            second.set((i - 1) * 4 + 6, 0.0);
            second.set((i - 1) * 4 + 7, 0.0);
        }
        List<Double> first = aSpline.get(aSpline.size() - 2);
        List<Double> second = aSpline.get(aSpline.size() - 1);
        first.set(0, 6 * arrayX.get(0));
        first.set(1, 2.0);

        List<Double> coef;
        second.set(second.size() - 4, 6 * arrayX.get(arrayX.size() - 1));
        second.set(second.size() - 3, 2.0);
        coef = new ArrayList<>(bSpline.size());
        for (int i = 0; i < bSpline.size(); ++i) {
            coef.add(0.0);
        }
        return SqrtMt.solve(aSpline, bSpline, coef);
    }

    Double getSpline(Double x, List<Double> coef) {
        int index = 1;
        for (; index < arrayX.size(); ++index) {
            if (x <= arrayX.get(index)) {
                break;
            }
        }

        index--;

        double a = coef.get(index * 4);
        double b = coef.get(index * 4 + 1);
        double c = coef.get(index * 4 + 2);
        double d = coef.get(index * 4 + 3);

        return a * x * x * x + b * x * x + c * x + d;
    }
}
