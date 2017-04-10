import java.io.*;
import java.util.*;
public class BasicTest1{
	static int myVar = 10;
	int normalVar = 29;
	static{
		myVar = 22;
		//normalVar = 33;
		BasicTest1 bb = new BasicTest1();
		bb.normalVar = 33;
		System.out.println(" *** In Static *** ");
		System.out.println(bb.normalVar);
	}
	public void display(){
		System.out.println(" Displaying the function ");
	}
	private void privateDisplay(){
		System.out.println(" ** In privaate display *** ");
	}

	public void changeObj(BasicTest1 bb){
		bb.normalVar = 129;
	}

	private static final int finalVar = 101;
	public static void main(String[] args){
		System.out.println(myVar);
		BasicTest1 bt = new BasicTest1();
		bt.display();
		bt.privateDisplay();
		System.out.println(bt.normalVar);
		bt.changeObj(bt);
		System.out.println(bt.normalVar);
		System.out.println(finalVar);
		System.out.println(myVar);
		System.out.println(bt.myVar);
		//display();

	}
}

