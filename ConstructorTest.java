class SuperClass{
 private int superClassInt=100;
 public void superClassFunc(){
 	System.out.println(" In SuperClass ... "+ superClassInt);
 }
}


public class ConstructorTest extends SuperClass{
	private String UserName;
	ConstructorTest(){
		System.out.println(" *** Created this constructor *** to test this *** ");
	}
	ConstructorTest(String st){
		this.UserName = st;
		System.out.println(st);
	}
	public void superClassFunc(){
 		System.out.println(" In subClass method overriding... ");
 	}

 	public void overloadMethod(){
 		System.out.println(" In subClass method overloading ... ");
 	}

 	public void overloadMethod(int t){
 		System.out.println(" In subClass method overloading... "+t);
 	}

 	public static void newMethod(){
 		System.out.println(" In new method ");
 		// String args[]= {"Hai", "Hello"};
 		// main(args);
 	}

	public static void main(String[] args){
		ConstructorTest Con = new ConstructorTest();
		SuperClass superClassObj = new SuperClass();
		SuperClass superClassObj2 = new ConstructorTest("Paramaterized Constructor");
		//System.out.println(superClassObj.superClassInt);
		superClassObj.superClassFunc();
		superClassObj2.superClassFunc();
		Con.overloadMethod();
		Con.overloadMethod(200);
		Con.superClassFunc();
		newMethod();
		System.out.println(Con.UserName);
		System.out.println(" Hello Sir Ji ");
	}
}