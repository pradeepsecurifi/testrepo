class Table{
	synchronized void printThem(int n){
		try{
			int i=0;
			for(i=0; i<5;i++){
				Thread.sleep(1000);
				System.out.println(i*n);
			}
		}catch(InterruptedException e){
			System.out.println(" Now we are in Interrrupted Exception ");
		}
	}
}

class Thread1 extends Thread{
	Table t;
	Thread1(Table t){
		this.t = t;
	}
	public void run(){
		System.out.println(" In Thread1");
		t.printThem(10);
	}
}

class Thread2 extends Thread{
	Table t;
	Thread2(Table t){
		this.t = t;
	}
	public void run(){
		System.out.println(" In Thread2");
		t.printThem(100);
	}
}

public class ThreadSample{
	private String UserName;
	public static void main(String[] args){
		Table tt = new Table();
		Thread1 Con = new Thread1(tt);
		Con.start();
		Thread2 Con2 = new Thread2(tt);
		Con2.start();
	}
}