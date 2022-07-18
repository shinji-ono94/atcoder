import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) { 
        Scanner s = new Scanner(System.in);
        int N = Integer.parseInt(s.next());

        int[][] map = new int[N][N];
        int max = -1;

        //map作成＆max値を拾う
        for(int i=0; i<N; i++){
            int num = Integer.parseInt(s.next());
            // System.out.println(num);
            int d = (int)Math.pow(10, N - 1);
            // System.out.println(d);
            
            for(int l=0; l<N; l++){
                map[i][l]=(num/d);
                if((num/d)>max){
                    max=(num/d);
                }
                num%=d;
                d/=10;
            }
        }

        List<Integer> row = new ArrayList<Integer>(); 
        List<Integer> line = new ArrayList<Integer>(); 
        //maxを探す
        for(int i=0; i<N; i++){
            for(int l=0; l<N; l++){
                if(map[i][l]==max){
                    row.add(i);
                    line.add(l);
                }
            }
        }

        int maxValue = -1;
        List<Integer> numList = new ArrayList<Integer>();

        //上下左右&斜め値取得
        for(int i=0;i<row.size();i++){
            //上
            numList.clear();
            for(int l=0;l<N;l++){
                if((row.get(i)-l)>=0){
                    numList.add(map[(row.get(i)-l)][line.get(i)]);
                }else{
                    numList.add(map[(row.get(i)-l+N)][line.get(i)]);
                }
            }
            if(num(numList)>maxValue){
                maxValue= num(numList);
            }
            //下
            numList.clear();
            for(int l=0;l<N;l++){
                if((row.get(i)+l)<N){
                    numList.add(map[(row.get(i)+l)][line.get(i)]);
                }else{
                    numList.add(map[(row.get(i)+l-N)][line.get(i)]);
                }
            }
            if(num(numList)>maxValue){
                maxValue= num(numList);
            }
            //右
            numList.clear();
            for(int l=0;l<N;l++){
                if((line.get(i)+l)<N){
                    numList.add(map[row.get(i)][(line.get(i)+l)]);
                }else{
                    numList.add(map[row.get(i)][(line.get(i)+l-N)]);
                }
            }
            if(num(numList)>maxValue){
                maxValue= num(numList);
            }
            //左
            numList.clear();
            for(int l=0;l<N;l++){
                if((line.get(i)-l)>=0){
                    numList.add(map[row.get(i)][(line.get(i)-l)]);
                }else{
                    numList.add(map[row.get(i)][(line.get(i)-l+N)]);
                }
            }
            if(num(numList)>maxValue){
                maxValue= num(numList);
            }
            //左
            numList.clear();
            for(int l=0;l<N;l++){
                if((line.get(i)-l)>=0){
                    numList.add(map[row.get(i)][(line.get(i)-l)]);
                }else{
                    numList.add(map[row.get(i)][(line.get(i)-l+N)]);
                }
            }
            if(num(numList)>maxValue){
                maxValue= num(numList);
            }
        }

        System.out.println(maxValue);
    }

    public static int num(List<Integer> numList) {
        int rtnnum = 0;
        for(int i=0; i<numList.size();i++){
            rtnnum += numList.get((numList.size()-(i+1))) * (10^(i));
            System.out.println(rtnnum);
        }
        return rtnnum;
    }
}
