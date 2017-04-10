import java.util.Scanner;

public class Star{
	public static void main(String[] args){
		int i=0, j=0, k=0;
		int n=3;
		for(i=0; i<n; i++){
			for(j=0; j<(n-i-1); j++)
				System.out.print(" ");
			k = 2*i+1;
			int t;
			for(t=0; t<k ; t++){
				System.out.print("*");
			}
			System.out.print("\n");
		}

		System.out.print("\n");

		for(i=n-1; i>=0; i--){
			for(j=0; j<(n-i-1); j++)
				System.out.print(" ");
			k = 2*i+1;
			int t;
			for(t=0; t<k ; t++){
				System.out.print("*");
			}
			System.out.print("\n");
		}
	}
}