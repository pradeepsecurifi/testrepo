class Shape{  
void draw(){System.out.println("drawing...");}  
void draw2(){System.out.println("drawing rectangle...");}  
}  
class Rectangle extends Shape{  
void draw(){System.out.println("drawing rectangle...");}  
void draw2(){System.out.println("drawing2 rectangle2...");}  
}  
class Circle extends Shape{  
void draw(){System.out.println("drawing circle...");}  
}  
class Triangle extends Shape{  
void draw(){System.out.println("drawing triangle...");}  
}  
public class TestPolymorphism2{  
	public static void main(String args[]){  
		Shape s;  
		s=new Rectangle();  
		s.draw();  
		s.draw2();  
		s=new Circle();  
		s.draw();  
		s=new Triangle();  
		s.draw();  
	}  
}  