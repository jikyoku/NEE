package edu.bupt.http;

/**
 * Created by shixu on 2017/1/4.
 */
import java.util.*;
import java.text.*;

public class DateDemo {

   public static void main(String args[]) throws ParseException {
      SimpleDateFormat ft = new SimpleDateFormat ("MM月dd日");
      Date date;
      String input = "4月2日";
       date = ft.parse(input);
       System.out.println(date);
   }
}