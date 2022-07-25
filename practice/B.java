import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class B {
    public static void main(String[] args) {
        FastScanner sc = new FastScanner(System.in);
        int N = sc.nextInt(), Q = sc.nextInt();
        String[] s = new String[N];
        for (int i = 0; i < N; i++)
            s[i] = String.valueOf((char) ('A' + i));

        int count = 0;

        for (int i = 0; i < Q; i++) {
            for (int j = 0; j < N - 1; j++) {
                System.out.println("? " + s[j] + " " + s[j + 1]);
                String ans;
                ans = sc.next();
                if (ans.equals(">")) {
                    String tmp = s[j];
                    s[j] = s[j + 1];
                    s[j + 1] = tmp;
                }
                count++;
                if (count == Q)
                    break;
            }
            if (count == Q)
                break;
        }

        String ans = String.join("", s);
        System.out.println("! " + ans);
    }

    static class FastScanner {

        private BufferedReader reader = null;
        private StringTokenizer tokenizer = null;

        public FastScanner(InputStream in) {
            reader = new BufferedReader(new InputStreamReader(in));
            tokenizer = null;
        }

        public String next() {
            if (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public String nextLine() {
            if (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    return reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return tokenizer.nextToken("\n");
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = (int) nextInt();
            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }
    }

    public static class Sort implements Comparable<Sort> {

        private int a, i;
        private List<Sort> list = new ArrayList<>();

        public Sort(int a, int i) {
            this.a = a;
            this.i = i;
            this.list.add(this);
        }

        public int getA() {
            return a;
        }

        public int getI() {
            return i;
        }

        public int compareTo(Sort sort) {
            return this.a - sort.a;
        }
    }
}
