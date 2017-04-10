import java.util.*;
public class Exception1{

    public static void main(String[] args) {
        try {
            //doSomething();
            String st = null;
            st.indexOf("n");
        } catch (NullPointerException e) {
            System.out.println("Caught the NullPointerException");
            System.out.println(e);
        }
    }

    public static void doSomething() {
        String nullString = null;
        nullString.endsWith("test");
    }
}