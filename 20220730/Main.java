import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", Runtime.getRuntime().maxMemory()).start();
    }

    static boolean isPalindrome(char[] s) {
        int n = s.length;
        for (int i = 0; i < n / 2; i++) {
            if (s[i] != s[n - i - 1]) {
                return false;
            }
        }
        return true;
    }

    public void replaceArr(char[] s) {
        int n = s.length;
        for (int i = 0; i < n / 2; i++) {
            if (s[i] != s[n - i - 1]) {
                if (s[i] == 'A') {
                    if (i != 0 && s[i - 1] == 'A') {
                        s[i] = 'B';
                    } else if (i != 0) {
                        s[n - i - 1] = 'A';
                    }
                } else {
                    s[i] = 'A';
                    s[i + 1] = 'B';
                }
            }
        }
    }

    // もっと簡単に考えてみよう
    public void run() {
        FastScanner sc = new FastScanner();
        PrintWriter pw = new PrintWriter(System.out);

        int N = sc.nextInt();
        char[] S = new char[N];
        S = sc.next().toCharArray();

        char[] t1 = new char[N];
        char[] t2 = new char[N];

        for (int i = 0; i < S.length; i++) {
            t1[i] = S[i];
            t2[i] = S[i];
        }

        replaceArr(S);

        boolean ans = isPalindrome(S);

        if (ans) {
            pw.println("Yes");
        } else {
            pw.println("No");
        }
        pw.close();
    }

    void tr(Object... objects) {
        System.err.println(Arrays.deepToString(objects));
    }
}

class FastScanner {
    private final InputStream in = System.in;
    private final byte[] buffer = new byte[1024];
    private int ptr = 0;
    private int buflen = 0;

    private boolean hasNextByte() {
        if (ptr < buflen) {
            return true;
        } else {
            ptr = 0;
            try {
                buflen = in.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (buflen <= 0) {
                return false;
            }
        }
        return true;
    }

    private int readByte() {
        if (hasNextByte())
            return buffer[ptr++];
        else
            return -1;
    }

    private static boolean isPrintableChar(int c) {
        return 33 <= c && c <= 126;
    }

    private void skipUnprintable() {
        while (hasNextByte() && !isPrintableChar(buffer[ptr]))
            ptr++;
    }

    // 次のByte値を返す。
    public boolean hasNext() {
        skipUnprintable();
        return hasNextByte();
    }

    // 次のStringを返す。
    public String next() {
        if (!hasNext())
            throw new NoSuchElementException();
        StringBuilder sb = new StringBuilder();
        int b = readByte();
        while (isPrintableChar(b)) {
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }

    // 次のlong値を返す。
    public long nextLong() {
        if (!hasNext())
            throw new NoSuchElementException();
        long n = 0;
        boolean minus = false;
        int b = readByte();
        if (b == '-') {
            minus = true;
            b = readByte();
        }
        if (b < '0' || '9' < b) {
            throw new NumberFormatException();
        }
        while (true) {
            if ('0' <= b && b <= '9') {
                n *= 10;
                n += b - '0';
            } else if (b == -1 || !isPrintableChar(b)) {
                return minus ? -n : n;
            } else {
                throw new NumberFormatException();
            }
            b = readByte();
        }
    }

    // 次のint値を返す。
    public int nextInt() {
        return (int) nextLong();
    }

    // 次のdouble値を返す。
    public double nextDouble() {
        return Double.parseDouble(next());
    }

    // 次のint配列を返す。 読み出し値に対して、dを加算すること可能。
    public int[] nextIntArray(int n, int d) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = nextInt() + d;
        return a;
    }

    // 次のdouble配列を返す。
    public double[] nextDoubleArray(int n) {
        double[] a = new double[n];
        for (int i = 0; i < n; i++)
            a[i] = nextDouble();
        return a;
    }

    // 次のlong配列を返す。
    public long[] nextLongArray(int n) {
        long[] a = new long[n];
        for (int i = 0; i < n; i++)
            a[i] = nextLong();
        return a;
    }

    // 次のchar多次元配列を返す。
    public char[][] nextCharMap(int n, int m) {
        char[][] map = new char[n][m];
        for (int i = 0; i < n; i++)
            map[i] = next().toCharArray();
        return map;
    }

    // 次のint多次元配列を返す。
    public int[][] nextIntMap(int n, int m) {
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++)
            map[i] = nextIntArray(m, 0);
        return map;
    }
}