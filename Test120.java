import java.io.*;
import java.util.*;
public class Test120{
	public static void main(String[] args){
			Scanner scan = new Scanner(System.in);
			String str = scan.nextLine();
			System.out.println(str);
			String[] tokens = str.split("-|\\.| |\t|,|;|:");
			System.out.println(tokens);
			TreeMap<String, Integer> tMap = new TreeMap<String, Integer>();
			TreeMap<String, Integer> tMap1 = new TreeMap<String, Integer>();
			int temp;
			for(int i=0; i<tokens.length;i++){
				if(tMap.containsKey(tokens[i])){
					temp = tMap.get(tokens[i]);
					tMap.put(tokens[i], ++temp);
				}else{
					tMap.put(tokens[i], 1);
					tMap1.put(tokens[i], i);
				}	
			}
			temp = 0;
			int min = 10000;
			int minTemp = 0;
			String minStr = "";
			for(String s: tMap.keySet()){
				int value = tMap.get(s);
				if(value > 1){
					minTemp = tMap1.get(s);
					if(minTemp < min){
						minStr = s;
						min = minTemp;
					}
				}
			}
			System.out.println(minStr);
	}
}






    // static int maxMoney(int n, long k) {
    //     long sum=0;
    //     int lowest = 1;
    //     for(int i = 1; i<=n ; i++){
    //         sum = sum+i;
    //         if(sum == k){
    //             sum = sum - lowest;
    //             lowest = i;
    //         }
    //     }
    //     return (int)(sum % (Math.pow(10, 9)+7));
    // }

