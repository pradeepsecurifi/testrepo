


import java.util.*;

public class Palindrome{

	public static void isPalindrome(String curString){
		int i, temp;
		Map<Character,Integer> charCounter=new TreeMap<Character,Integer>();
		Map<Character,Integer> charCounter2=new TreeMap<Character,Integer>(Collections.reverseOrder());
		for(i=0; i<curString.length(); i++){
			if(charCounter.containsKey(curString.charAt(i))){
				temp = charCounter.get(curString.charAt(i));
				charCounter.put(curString.charAt(i), ++temp);
				charCounter2.put(curString.charAt(i), temp);
			}else{
				charCounter.put(curString.charAt(i), 1);
				charCounter2.put(curString.charAt(i), 1);
			}
		}
		System.out.println(charCounter);
		int oddCounter=0;
		for(Character key:charCounter.keySet()){	
           System.out.println(key+""+charCounter.get(key));
           if(charCounter.get(key)%2 != 0){
           		oddCounter++;
           }
       	}
       	System.out.println(" *** Counter *** "+oddCounter);
       	if(oddCounter > 1){
       		System.out.println(" Not a Palindrome ");
       	}else{
       		System.out.println(" It is a Palindrome ");
       		int temp2; String finalized=""; char saveIt='a';
       		int i2;
       		for(Character key:charCounter.keySet()){
       		   temp2 = charCounter.get(key);
       		   if(charCounter.get(key)%2 != 0){
       		   		saveIt = key;
       		   }
       		  
       		   for(i2=0; i2< temp2/2; i2++){
       		   	finalized+=key;
       		   }		
       		}
       		finalized+=saveIt;
       		System.out.println("finalized "+finalized);
       		for(Character key:charCounter2.keySet()){	
       		   temp2=charCounter2.get(key);	
	           System.out.println(key+""+charCounter2.get(key));
	           for(i2=0; i2< temp2/2; i2++){
       		   	finalized+=key;
       		   }
       		}
       		System.out.println("finalized end **** "+finalized);
       	}
	}

	public static void main(String[] args){
		String strArray[] = { "aaaammmmd", "hero" };
		int length=strArray.length;
		System.out.println(length);
		int i=0;
		for(i=0; i<length; i++){
			System.out.println(strArray[i]);
			isPalindrome(strArray[i]);
		}
	}
}