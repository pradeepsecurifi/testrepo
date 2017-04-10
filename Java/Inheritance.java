import java.util.*;

class Source{
  //public void
    public void printout(){
      System.out.println(" ** In parent printout .... ");
    }

    private void privateprintout(){
      System.out.println(" ** In parent privateprintout .... ");
    }

}

public class Inheritance extends Source{
  // public void printout(){
  //   System.out.println(" ** In child new printout .... ");
  // }
   public void childprintout(){
      System.out.println(" In child printout");
   }

   public static void main(String args[]) {
    Source ss = new Source();
    //ss.privateprintout();
    ss.printout();
    Source ss2 = new Inheritance();
    ss2.printout();
    ss2.privateprintout();
    //ss2.childprintout();
    Inheritance ii = new Inheritance();
    ii.printout();
    ii.childprintout();
    //Inheritance i2 = new Source();
    //i2.
   }
}

Infinity

2.5 yrs

