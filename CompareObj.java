import java.util.*;
class Student{
	int age;
	String name;
	Student(int age, String name){
		this.age = age;
		this.name = name;
	}
}

class ageComparator implements Comparator{
	public int compare(Object b1, Object b2){
		Student s1 = (Student)b1;
		Student s2 = (Student)b2;
		if(s1.age==s2.age)
			return 0;
		else if(s1.age > s2.age)
			return 1;
		else
			return -1;

	}
}

class nameComparator implements Comparator{
	public int compare(Object b1, Object b2){
		Student s1 = (Student)b1;
		Student s2 = (Student)b2;
		return s1.name.compareTo(s2.name);
	}
}

public class CompareObj{
	public static void main(String[] args){
		ArrayList al = new ArrayList();
		al.add(new Student(25, "Likitha"));
		al.add(new Student(27, "Pradeep"));
		al.add(new Student(26, "Srikanth"));
		Collections.sort(al, new ageComparator());
		Iterator it = al.iterator();
		while(it.hasNext()){
			Student ss = (Student)it.next();
			System.out.println(ss.age);
		}
	}
}