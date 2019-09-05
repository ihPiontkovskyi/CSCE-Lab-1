
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SqrtMt {
    private static Double sign(Double z) {
        if (z > 0)
            return 1.0;
        else
            return -1.0;
    }

    private static ArrayList<ArrayList<Double>> transpose(ArrayList<ArrayList<Double>> a) {
        int i, j;
        int n = a.size();
        ArrayList<ArrayList<Double>> b = new ArrayList<ArrayList<Double>>(n){};
        for (i = 0; i < n; i++) {
            b.add(new ArrayList<>((Collections.nCopies(n, 0.0))));
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                b.get(j).set(i, a.get(i).get(j));
        }
        return b;
    }

    static List<Double> solve(final List<List<Double>> a, final List<Double> b, List<Double> x) {
        int i, j, k;
        int n = a.size();

        boolean flag = true;
        ArrayList<ArrayList<Double>> at = new ArrayList<ArrayList<Double>>(n){};
        ArrayList<ArrayList<Double>> rab = new ArrayList<ArrayList<Double>>(n){};
        ArrayList<ArrayList<Double>> s = new ArrayList<ArrayList<Double>>(n){};
        ArrayList<ArrayList<Double>> d = new ArrayList<ArrayList<Double>>(n){};
        ArrayList<Double> y = new ArrayList<Double>((Collections.nCopies(n, 0.0))){};
        ArrayList<Double> b1 = new ArrayList<Double>((Collections.nCopies(n, 0.0))){};


        for (i = 0; i < n; i++) {
            at.add(new ArrayList<>((Collections.nCopies(n, 0.0))));
            rab.add(new ArrayList<>((Collections.nCopies(n, 0.0))));
            s.add(new ArrayList<>((Collections.nCopies(n, 0.0))));
            d.add(new ArrayList<>((Collections.nCopies(n, 0.0))));
        }

        for (i = 0; i < n; ++i) {
            for (j = 0; j < n; j++) {
                if (!a.get(i).get(j).equals(a.get(j).get(i))) {
                    flag = false;
                }
                d.get(i).set(j, 0.0);
                s.get(i).set(j, 0.0);
            }
        }
        if (!flag) {
            {
                for (i = 0; i < n; i++) {
                    for (j = 0; j < n; j++)
                        at.get(j).set(i, a.get(i).get(j));
                }
                for (i = 0; i < n; i++) {
                    for (j = 0; j < n; j++) {
                        rab.get(i).set(j, 0.0);
                        b1.set(i, 0.0);
                        for (k = 0; k < n; k++) {
                            rab.get(i).set(j, rab.get(i).get(j) + at.get(i).get(k) * a.get(k).get(j));
                            b1.set(i, b1.get(i) + at.get(i).get(k) * b.get(k));
                        }
                    }
                }
            }
        } else {
            for (i = 0; i < n; i++) {
                b1.set(i, b.get(i));
                for (j = 0; j < n; j++)
                    rab.get(i).set(j, a.get(i).get(j));
            }
        }
        for (i = 0; i < n; i++) {
            for (k = 0; k < (i + 1); k++) {
                Double sum = 0.0;
                for (j = 0; j < k; j++)
                    sum += s.get(i).get(j) * s.get(k).get(j) * d.get(j).get(j);
                if (i == k) {
                    s.get(i).set(k, Math.sqrt(rab.get(i).get(i) - sum));
                    d.get(i).set(k, sign(rab.get(i).get(i) - sum));
                } else
                    s.get(i).set(k, 1.0 / s.get(k).get(k) * (rab.get(i).get(k) - sum));
            }

        }
        s = transpose(s);
        for (i = 0; i < n; i++) {
            double sum = 0;
            for (k = 0; k <= i - 1; k++)
                sum += y.get(k) * s.get(k).get(i);
            y.set(i, (b1.get(i) - sum) / s.get(i).get(i));

        }

        for (i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (k = i + 1; k <= n - 1; k++)
                sum += s.get(i).get(k) * x.get(k);
            x.set(i, (y.get(i) - sum) / s.get(i).get(i));
        }
        return x;
    }
}
