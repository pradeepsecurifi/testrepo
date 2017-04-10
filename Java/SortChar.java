import java.util.Arrays;
import java.util.*;

public class SortChar
{
   public static void main(String[] args) {

	   // initializing unsorted int array
	   int iArr[] = {2, 1, 9, 4, 6};

	   // let us print all the elements available in list
	   System.out.println(iArr);
	   for (int number : iArr) {
	   System.out.println("Number = " + number);
	   }

	   // sorting array
	   Arrays.sort(iArr);

	   // let us print all the elements available in list
	   System.out.println("The sorted int array is:");
	   for (int number : iArr) {
	   	System.out.println("Number = " + number);
	   }

	   Arrays.asList(iArr);

	   System.out.println("Final .... :");
	   for (int number : iArr) {
	   	System.out.println("Number = " + number);
	   }

	   int result = Arrays.binarySearch(iArr, 1, 4, 6);
	   System.out.println(result);
	   System.out.println(Arrays.copyOf(iArr, 2));

	   String[] str = {"Hello", "Hai", "Amma", "Naana"};
	   Arrays.sort(str);
	   System.out.println(str);
	   for(String st: str){
	   	System.out.println(st);
	   }

   }

}

/*

    public static void main(String[] args)
    {
    	int aa[] = {5,7,8,2,1,3,22,43,12,11}; 
    	ArrayList<Integer> arrList = new ArrayList<Integer>(2);
    	arrList.add(10);
    	arrList.add(8);
    	System.out.println(arrList);
    	//System.out.println(arrList.sort());
        String original = "edcba";
        char[] chars = original.toCharArray();
        System.out.println(aa);
        Arrays.sort(aa);
        System.out.println(aa);
        System.out.println(" Chars are ... ");
        System.out.println(chars);
        String sorted = new String(chars);
        //System.out.println(sorted);
    }

*/