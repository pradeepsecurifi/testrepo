import java.util.*;
class Student{
	int age;
	String name;
	Student(int age, String name){
		this.age = age;
		this.name = name;
	}
}

public class CollectionsTest1{
	public static void main(String[] args){
		ArrayList<Student> al = new ArrayList<Student>();
		al.add(new Student(25, "Likitha"));
		al.add(new Student(27, "Pradeep"));
		al.add(new Student(26, "Srikanth"));
		//Collections.sort(al);
		Iterator it = al.iterator();
		while(it.hasNext()){
			Student ss = (Student)it.next();
			System.out.println(ss.age);
		}

		ArrayList<String> intArr = new ArrayList<String>();
		intArr.add("F");
		intArr.add("A");
		intArr.add("Z");
		Collections.sort(intArr);
		Iterator it2 = intArr.iterator();
		while(it2.hasNext()){
			System.out.println(it2.next());
		}

		TreeMap<Character, Integer> tMap = new TreeMap<Character, Integer>();

		tMap.put('k', 2);
		tMap.put('a', 10);
		tMap.put('z', 12);
		tMap.put('x', 3);
		for(Character key: tMap.keySet()){
			System.out.println(tMap.get(key));
		}
	}
}
