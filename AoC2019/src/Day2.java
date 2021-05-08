import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day2 {

	public static void main(String[] args) {
		task1();
		task2();

	}
	
	private static void task2() {
		try {
		      File file = new File("data/day2-input.txt");
		      Scanner in = new Scanner(file);
		      String[] raw_data = in.nextLine().split(",");
		      int[] orig_data = convert(raw_data);
		      int result = get_output(orig_data);     
		      System.out.println("Task 2: " + result);
		      in.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
		

	private static int get_output(int[] orig_data) {
		for(int i=0; i<100; i++) {
	    	  for(int j=0; j<100; j++) {
	    		  int[] data = Arrays.copyOf(orig_data, orig_data.length);
			      data[1] = i;
			      data[2] = j;
			      int[] result = runIntcode(data, 0);
			      if(result[0] == 19690720) {
			    	  return 100 * i + j;
			      }
	    	  }
		}
		System.out.println("Not found");
		return 0;
	}

	private static void task1() {
		try {
		      File file = new File("data/day2-input.txt");
		      Scanner in = new Scanner(file);
		      String[] raw_data = in.nextLine().split(",");
		      int[] data = convert(raw_data);
		      data[1] = 12;
		      data[2] = 2;
		      // System.out.println(Arrays.toString(data));
		      int[] result = runIntcode(data, 0);
		      System.out.println("Task 1: " + result[0]);
		      in.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}

	private static int[] runIntcode(int[] data, int index) throws ArrayIndexOutOfBoundsException{
//		System.out.println("---------");
//		System.out.println(Arrays.toString(data));
//		System.out.println(data[index] + ": " + data[index+1] + " op " +data[index+2] + " -> " 
//				+data[index+3]);
		int oper1 = data[data[index + 1]];
		int oper2 = data[data[index + 2]];
		int output = data[index + 3];
		switch(data[index]) {
		case 1:
			data[output] = oper1 + oper2;
			return runIntcode(data, index + 4);
		case 2:
			data[output] = oper1 * oper2;
			return runIntcode(data, index + 4);
		case 99: return data;
		default: System.out.println("Error");
		}
		return null;
	}

	private static int[] convert(String[] raw_data) {
		int[] res = new int[raw_data.length];
		for(int i=0; i<raw_data.length; i++) {
			res[i] = Integer.parseInt(raw_data[i]);
		}
		return res;
	}
	
	

}
