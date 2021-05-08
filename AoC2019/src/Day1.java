import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1 {

	public static void main(String[] args) {
		task1();
		task2();

	}
	
	private static void task1() {
		int sum = 0;
		try {
		      File file = new File("data/day1-input.txt");
		      Scanner in = new Scanner(file);
		      while (in.hasNextLine()) {
		        String data = in.nextLine();
		        //System.out.print(data + " - ");
		        int res = compute(Integer.parseInt(data));
		        sum += res;
		        // System.out.println(res);
		      }
		      in.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		System.out.println("Task 1: " + sum);
	}
	
	private static int compute(int value) {
		return value / 3 - 2;
	}
	
	private static void task2() {
		int sum = 0;
		try {
		      File file = new File("data/day1-input.txt");
		      Scanner in = new Scanner(file);
		      while (in.hasNextLine()) {
		        String data = in.nextLine();
		        // System.out.print(data + " - ");
		        int res = compute(Integer.parseInt(data));
		        while(res >= 0) {
		        	sum += res;
		        	// System.out.print(res + ", ");
		        	res = compute(res);
		        }
		        // System.out.println();
		      }
		      in.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		System.out.println("Task 2: " + sum);
	}

}
