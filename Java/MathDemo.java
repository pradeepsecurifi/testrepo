//package com.tutorialspoint;
import java.lang.*;
public class MathDemo {
   public static void main(String[] args) {
      // get two double numbers numbers
      double x = 45;
      double y = -180;
      // convert them to radians
      x = Math.toRadians(x);
      y = Math.toRadians(y);
      // print the trigonometric sine for these doubles
      System.out.println("Math.sin(" + x + ")=" + Math.sin(x));
      System.out.println("Math.sin(" + y + ")=" + Math.sin(y));
      System.out.println(Math.acos(20));
      int lats[] = {0, 0, 70};
      int lons[] = {90, 0, 45};
      // int lats[] = {0, 20, 55};
      // int lons[] = {-20, 85, 42};
      double shortDist[] = {0, 0, 0};
      System.out.println(lats[2]);
      String[] paths = {"2", "0 2", "0 1"};
      String[] temp;
      // radius * acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lon1 - lon2))
      for(int i=0; i<paths.length; i++){
         System.out.println(paths[i]);
         temp = paths[i].split(" ");
         for(int j=0; j<temp.length; j++){
            int value = Integer.parseInt(temp[j]);
            int lat1 = lats[i];
            int lon1 = lons[i];
            int lat2 = lats[value];
            int lon2 = lons[value];
            // radius * acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lon1 - lon2))
            double dd = 4000 * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
            System.out.println(" Paths ... "+i+" "+value);
            System.out.println(" Lats Lons ... "+lat1+" "+lon1+" "+lat2+" "+lon2);
            System.out.println(" Distance is "+dd);
            if(shortDist[value] == 0){
               System.out.println(" Removing Zero ");
               shortDist[value] = dd;
            }else if(dd+shortDist[i] < shortDist[value]){
               shortDist[value] = dd+shortDist[i];
            }
         }
      }
      for(int i=0; i<shortDist.length; i++){
         System.out.println(shortDist[i]);
      }
   }
}