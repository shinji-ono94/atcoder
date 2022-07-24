import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class D {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int N = Integer.parseInt(s.next());

        String[][] score = new String[N][N];

        for (int i = 0; i < N; i++) {
            String input_text = s.next();
            String[] rows = input_text.split("");
            int count = 0;
            for (int l = 0; l < rows.length; l++) {
                score[count][i] = rows[l];
                count++;
            }
        }

        int error = 0;

        for (int i = 0; i < N; i++) {
            for (int l = 0; l < N; l++) {
                if (i == l)
                    continue;
                if (score[i][l].equals("W")) {
                    if (!score[l][i].equals("L"))
                        error++;
                } else if (score[i][l].equals("L")) {
                    if (!score[l][i].equals("W"))
                        error++;
                } else {
                    if (!score[l][i].equals("D"))
                        error++;
                }
            }
        }

        if (error == 0) {
            System.out.println("correct");
        } else {
            System.out.println("incorrect");

        }

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
          for (int i = 0; i < n; i++) a[i] = (int) nextInt();
          return a;
        }
     
        public long[] nextLongArray(int n) {
          long[] a = new long[n];
          for (int i = 0; i < n; i++) a[i] = nextLong();
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
