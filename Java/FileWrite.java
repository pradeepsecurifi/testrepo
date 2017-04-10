import java.io.*;
import java.util.*;
public class FileWrite{
	public static void main(String[] args){
		try{
			//File ff = new File("./FileWritten");
			System.out.println(" ** In main() *** ");
			FileOutputStream fout = new FileOutputStream("./FileWritten2");
			BufferedWriter buf = new BufferedWriter(new OutputStreamWriter(fout));
			for(int k=0; k<10; k++){
				buf.write("Hello");
				buf.newLine();
			}
			buf.close();
			fout.close();
		}catch(FileNotFoundException e){

		}catch(IOException e){

		}
	}
}

