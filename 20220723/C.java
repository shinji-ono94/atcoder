import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class C {
    public static void main(String[] args) {
        FScanner s = new FScanner();
        int N = Integer.parseInt(s.next());

        String[] textArr = new String[N];

        for (int i = 0; i < N; i++) {
            textArr[i] = s.next();
        }

        Map<String, Integer> Record = new HashMap<>();

        for (int i = 0; i < N; i++) {

            int check = 0;

            for (int l = i - 1; l >= 0; l--) {
                if (textArr[l].equals(textArr[i])) {
                    check++;
                    if (Record.containsKey(textArr[i])) {
                        int cnt = Record.get(textArr[i]);
                        Record.put(textArr[i], cnt + 1);
                        break;
                    } else {
                        Record.put(textArr[i], 1);
                    }
                }
            }
            // 同じ文字列が存在しないならば、
            if (check == 0) {
                System.out.println(textArr[i]);
            } else {
                int ans_cnt = Record.get(textArr[i]);
                String ans = textArr[i] + "(" + ans_cnt + ")";
                System.out.println(ans);
            }
        }
    }

    static class FScanner {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer sb = new StringTokenizer("");
 
		String next(){
			while(!sb.hasMoreTokens()){
				try{
					sb = new StringTokenizer(br.readLine());
				} catch(IOException e){ }
			}
			return sb.nextToken();
		}
		String nextLine(){
			try{ return br.readLine(); } 
			catch(IOException e) { } return "";
		}
 
		int nextInt(){
			return Integer.parseInt(next());
		}
 
		long nextLong() {
			return Long.parseLong(next());
		}
 
		int[] readArray(int n) {
			int a[] = new int[n];
			for(int i=0;i<n;i++) a[i] = nextInt();
			return a;
		}
 
		float nextFloat(){
		return Float.parseFloat(next());
		}
		
		double nextDouble(){
		return Double.parseDouble(next());
		}
	}
}
