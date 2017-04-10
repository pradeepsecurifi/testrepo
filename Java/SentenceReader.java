import java.util.Scanner;
class Hello{
	static{
		System.out.println(" *** In Hello static block *** ");
	}
}

public class SentenceReader{
	static{
		System.out.println(" *** Hey in static block1 *** ");
	}
	static{
		System.out.println(" *** Hey in static block2 *** ");
	}
	public static void main (String args[]){
		Hello hh = new Hello();
		Scanner scan= new Scanner(System.in);
		String sentence;
		int counterv=0, counters=0, counterd=0;
		System.out.println("Plz enter the line you want: ");
		// sentence=input.nextLine();
		// int L=sentence.length();
		// System.out.println(L);
		// System.out.println(sentence);
		int i = 10;
		String st1 = "Hello ";
		double d1 = 10.0;
		
        //Scanner sc = new Scanner(System.in).useDelimiter("\\n");
        int myInt = scan.nextInt();
        double mydouble = scan.nextDouble();
        String myStr = scan.nextLine();
        System.out.println(i+myInt);
        System.out.println(d1+mydouble);
        System.out.println(st1+myStr);
	}
}

