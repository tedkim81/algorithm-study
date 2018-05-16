package gcj.y2012.round1c;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class B_other {
    public void run(Scanner in, PrintWriter out, int nCase) {
        double d = in.nextDouble();
        int n = in.nextInt();
        int na = in.nextInt();

        List<Double> t = new ArrayList<Double>();
        List<Double> x = new ArrayList<Double>();

        for (int i = 0; i < n; ++i) {
            double ct = in.nextDouble();
            double cx = in.nextDouble();
            if (t.isEmpty()) {
                t.add(ct);
                x.add(cx);
            } else {
                double lt = t.get(t.size() - 1);
                double lx = x.get(x.size() - 1);
                if (lx < d) {
                    if (d < cx) {
                        t.add(lt + (ct - lt) * (d - lx) / (cx - lx));
                        x.add(d);
                    } else {
                        t.add(ct);
                        x.add(cx);
                    }
                }
            }
        }

        out.println("Case #" + nCase + ":");
        for (int ia = 0; ia < na; ++ia) {
            double a = in.nextDouble();
            double lastPossibleOffset = 0;

            for (int i = 0; i < t.size(); ++i) {
                double ct = t.get(i);
                double cx = x.get(i);
                double rt = Math.sqrt(2 * cx / a);
                if (rt <= ct) {
                    double offset = ct - rt;
                    if (offset >= lastPossibleOffset) {
                        lastPossibleOffset = offset;
                    }
                }
            }

            double ans = Math.sqrt(2 * d / a) + lastPossibleOffset;
            out.println(String.format("%.7f", ans));
            System.out.println(String.format("%.7f", ans));
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Locale.setDefault(Locale.US);
        String filename = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/test";

        Scanner in = new Scanner(new File(filename + ".in.txt"));
        PrintWriter out = new PrintWriter(filename + ".out2.txt");

        int nCases = in.nextInt();

        for (int i = 1; i <= nCases; ++i) {
            new B_other().run(in, out, i);
        }

        out.close();
        in.close();
    }
}
