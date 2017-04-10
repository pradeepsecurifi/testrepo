import java.io.*;
import java.util.*;
public class FileRead{
	public static void main(String[] args){
		try{
			System.out.println(" File Read **** ");
			FileInputStream f = new FileInputStream("./CompareObj2.java");
			BufferedReader in = new BufferedReader(new InputStreamReader(f));
			String line = in.readLine();
			while(line != null){
				System.out.println(line);
				line = in.readLine();
			}
			in.close();
			f.close();
		}catch(FileNotFoundException e){
			System.out.println(" File Not Found ");
		}catch(IOException e){
			System.out.println(" In IOException ");
		}
	}
}

