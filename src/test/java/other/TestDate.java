package other;

import java.util.Calendar;

public class TestDate {
	
	public static void main(String[] args) {
		printDate();
		printDate2();
		
	}
	
	public static void printDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		System.out.println(calendar.getTime());
	}
	
	public static void printDate2(){
		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.DATE, -1);
		System.out.println(calendar.getTime());
	}

}
