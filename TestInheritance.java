import java.util.*;

class Source{
  //public void
    public Source(){
      System.out.println(" In Source Base class Constructor ... ");
    }
    public void printout(){
      System.out.println(" ** In parent printout .... ");
    }

    private void privateprintout(){
      System.out.println(" ** In parent privateprintout .... ");
    }

    public void printBraces(){
      int n=3;
      for(int i=0; i<=n; i++){
        System.out.println(draw(i)+draw(n-i));
      }
    }

    public String draw(int value){
      if(value == 0)
        return "";
      return "("+draw(--value)+")";
    }
}

public class TestInheritance extends Source{
  // public void printout(){
  //   System.out.println(" ** In child new printout .... ");
  // }
   public void childprintout(){
      System.out.println(" In childprintout");
   }

   public void printout(){
      System.out.println(" ** In child overloaded printout .... ");
   }

   public static void main(String args[]) {
    Source ss = new Source();
    ss.printout();
    ss.printBraces();
    Source ssHybrid = new TestInheritance();
    ssHybrid.printout();
    TestInheritance TI = new TestInheritance();
    TI.printout();
   }
}

