// Program to print all prime factors
import java.util.*;
//import java.io.Scanner;
import java.lang.Math;
 
class TestClass{
    public static int largestNumber(int data) {
        int num = data;
        int[] times = new int[10];
        while (num != 0) {
            if (num == 0) {
                break;
            }
            int val = num % 10;
            times[val]++;
            num /= 10;
        }
        String largestNumber = "";
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < times[i]; j++) {
                largestNumber += i;
            }
        }
        return Integer.parseInt(largestNumber);
    }
     
    public static void main (String[] args){
        Scanner scan = new Scanner(System.in);
        int testCases = scan.nextInt();
        int input;
        String strInput="";
        for(int i=0; i<testCases; i++){
            input = scan.nextInt();
            System.out.println(input);
            strInput+=input;
        }
        System.out.println(largestNumber(Integer.parseInt(strInput)));
    }
}