import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class D implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", Runtime.getRuntime().maxMemory()).start();
    }

    int o(char c) {
        return c - 'a';
    }

    public void run() {
        FastScanner sc = new FastScanner();
        PrintWriter pw = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        int ans = -1;
        boolean flg = false; 
        while(!flg){
            for (int i = 0; i < N; i++) {
                if(A[i]%2!=0){
                    flg=true;
                    break;
                } 
                A[i]/=2;
            }
            ans++;
        }

        pw.println(ans);
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

    public boolean hasNext() {
        skipUnprintable();
        return hasNextByte();
    }

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

    public int nextInt() {
        return (int) nextLong();
    }
}