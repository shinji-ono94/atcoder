import java.util.Arrays;
import java.util.Scanner;

public class B {
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
}
