import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input_text = s.nextLine();

        String[] newStr = input_text.split("\\s+");

        int L1 = Integer.parseInt(newStr[0]);
        int R1 = Integer.parseInt(newStr[1]);
        int L2 = Integer.parseInt(newStr[2]);
        int R2 = Integer.parseInt(newStr[3]);

        int start = 0;
        int last = 0;

        if (R2 > R1) {
            last = R1;
        } else {
            last = R2;
        }

        if (L1 > L2) {
            start = L1;
        } else {
            start = L2;
        }

        int ans = last - start;

        if (ans < 0) {
            ans = 0;
        }

        System.out.println(ans);
    }
}
