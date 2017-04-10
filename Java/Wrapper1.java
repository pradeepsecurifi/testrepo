public class Wrapper1 {
   public static void main(String args[]) {
      Integer x = 5; // boxes int to an Integer object
      x =  x + 10;   // unboxes the Integer to a int
      System.out.println(x);
      System.out.println(x instanceof Integer); 
   }
}