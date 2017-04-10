import java.util.*;

class Source{
  //public void
    public void printout(){
      System.out.println(" ** In printout .... ");
    }
}


class Thread1 extends Thread{
  Thread1(int n, Source ss){
    System.out.println(" In Thread "+n);
    synchronized(ss){
      try{
        for(int i=201; i<206; i++){
          Thread.sleep(2000);
          System.out.println(" After sleep "+i);
          ss.printout();
        }
      }catch(InterruptedException E){
          System.out.println(" In Interrupted Exception ");
      }
    }
  }

  public void run(){
    System.out.println(" In Thread ");

  }
}

class Thread2 extends Thread{
  Thread2(int n, Source ss){
    System.out.println(" In Thread "+n);
    synchronized(ss){
      try{
        for(int i=0; i<5; i++){
          Thread.sleep(2000);
          System.out.println(" After sleep "+i);
          ss.printout();
        }
      }catch(InterruptedException E){
          System.out.println(" In Interrupted Exception ");
      }
    }
  }
  public void run(){
    System.out.println(" In Thread ");
  }  
}

public class ThredTest {
   public static void main(String args[]) {
    Source ss = new Source();
    Thread1 t1 = new Thread1(1, ss);
    //Thread2 t2 = new Thread2(2, ss);
    //t1.start();
    t1.run();
    //t1.join();
    //t2.start(); 
   }
}