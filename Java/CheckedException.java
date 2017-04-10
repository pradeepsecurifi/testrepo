import java.io.*;
 
public class CheckedException {
    // public static void main(String[] args) throws FileNotFoundException, IOException{
    //     FileReader file = new FileReader("C:\\test\\a.txt");
    //     BufferedReader fileInput = new BufferedReader(file);
         
    //     // Print first 3 lines of file "C:\test\a.txt"
    //     for (int counter = 0; counter < 3; counter++) 
    //         System.out.println(fileInput.readLine());
         
    //     fileInput.close();
    // }

	public static void main(String args[])
	{
		try{
		int arr[] = {1,2,3,4,5};
		System.out.println(arr[7]);
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("The specified index does not exist " +
					"in array. Please correct the error.");
		}
	}

}