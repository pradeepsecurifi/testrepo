public class Flag{
	public static void main(String[] args){
		System.out.println("Started the application");
		int looper = 0, equals = 16;
		for(int i=1; i<=16; i++){
			looper = 0;
			if(i%2 == 1 && i<=10){
				looper = 6;
			}
			if(i%2 == 0 && i<=10){
				looper = 5;
			}
		    for(int k=0; k<looper; k++)	
			  System.out.print("* ");
			for(int k=0; k<10; k++)	
			  System.out.print("=");			
			equals = 10;
			if(looper == 0);
				equals = 16;
			for(int k=0; k<equals; k++)	
			  System.out.print("=");			
			System.out.println("");
		}
	}
}