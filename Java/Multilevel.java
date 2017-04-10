
class Aves
{
  private void nature()
  {
    System.out.println("Generally, Aves fly");
  }
  //   public void eat()
  // {
  //   System.out.println("Eats In Aves");
  // }
}
class Bird extends Aves
{ 
  public static void eat()
  {
    //super.nature();
    System.out.println("Eats to live");
  }
}
public class Multilevel extends Bird
{
  Multilevel(){
    super.eat();
  }
  public void food()
  {
    System.out.println("Parrot eats seeds and fruits");
  }
  public static void main(String args[])
  {
    Multilevel p1 = new Multilevel();
    p1.food();                       // calling its own
    p1.eat();                        // calling super class Bird method
    //p1.nature();                     // calling super class Aves method
  }
}