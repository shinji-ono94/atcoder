import java.util.Scanner;

public class Main22 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int K = Integer.parseInt(s.next());
		
        if(K<60){
            System.out.println("21:" + String.format("%02d", K));
        }else{
            int O = K-60;
            System.out.println("22:" + String.format("%02d", O));
        }
    }
}
