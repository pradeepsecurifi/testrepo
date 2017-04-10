import java.util.TreeSet;
 
class Dog implements Comparable<Dog> {
 
    int size;
 
    Dog(int s) {
        size = s;
    }
 
    public int compareTo(Dog o) {
        System.out.println(" *** In *** compareTo "+o.size);
        if(size == o.size)
            return 0;
        else if(size > o.size)
            return 1;
        else
            return -1;
        //return size - o.size;
        //return 2;
    }
}
 
public class TreeSetComp {
 
    public static void main(String[] args) {
 
        TreeSet<Dog> d = new TreeSet<Dog>();
        d.add(new Dog(1));
        d.add(new Dog(1));
        d.add(new Dog(1));
 
        TreeSet<Integer> i = new TreeSet<Integer>();
        i.add(1);
        i.add(2);
        i.add(3);
 
        System.out.println(d.size() +" "+ i.size());
    }
}