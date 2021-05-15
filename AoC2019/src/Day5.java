import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day5 {
	

	public static void main(String[] args) {
		solve();

	}

	private static void solve() {
		try {
		      File file = new File("data/day5-input.txt");
		      Scanner in = new Scanner(file);
		      String[] raw_data = in.nextLine().split(",");
		      int[] data = convert(raw_data);
		      int[] orig = Arrays.copyOf(data, data.length);
		      // System.out.println(Arrays.toString(data));
		      int result = runIntcode(data, 0, 1, 0);
		      System.out.println("Task 1: " + result);
		      result = runIntcode(orig, 0, 5, 0);
		      System.out.println("Task 2: " + result);
		      in.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
	
	private static int runIntcode(int[] data, int index, int input, int output) throws ArrayIndexOutOfBoundsException{
		int instr_type = data[index];
		// System.out.println(Arrays.toString(data));
		int opcode = instr_type % 100; // 2 right-most digits
		if(opcode == 99) {
			return output;
		}
		int oper1 = get_oper(data, index+1, (instr_type / 100) % 10);
		int oper2;
		switch(opcode) {
		case 1:
			oper2 = get_oper(data, index+2, (instr_type / 1000) % 10);
			data[data[index+3]] = oper1 + oper2;
			return runIntcode(data, index + 4, input, output);
		case 2:
			oper2 = get_oper(data, index+2, (instr_type / 1000) % 10);
			data[data[index+3]] = oper1 * oper2;
			return runIntcode(data, index + 4, input, output);
		case 3: 
			data[data[index+1]] = input;
			return runIntcode(data, index + 2, input, output);
		case 4:
			return runIntcode(data, index + 2, input, oper1);
		case 5:
			oper2 = get_oper(data, index+2, (instr_type / 1000) % 10);
			if(oper1 != 0) {
				return runIntcode(data, oper2, input, output);
			}
			return runIntcode(data, index + 3, input, output);
		case 6:
			oper2 = get_oper(data, index+2, (instr_type / 1000) % 10);
			if(oper1 == 0) {
				return runIntcode(data, oper2, input, output);
			}
			return runIntcode(data, index + 3, input, output);
		case 7:
			oper2 = get_oper(data, index+2, (instr_type / 1000) % 10);
			data[data[index+3]] = oper1 < oper2? 1:0;
			return runIntcode(data, index + 4, input, output);
		case 8:
			oper2 = get_oper(data, index+2, (instr_type / 1000) % 10);
			data[data[index+3]] = (oper1 == oper2)? 1:0;
			return runIntcode(data, index + 4, input, output);
		default: 
			System.out.println("Error");
			return 0;
		}
	}

	private static int[] convert(String[] raw_data) {
		int[] res = new int[raw_data.length];
		for(int i=0; i<raw_data.length; i++) {
			res[i] = Integer.parseInt(raw_data[i]);
		}
		return res;
	}
	

	private static int get_oper(int[] data, int index, int mode) {
		if(mode == 1) {
			return data[index];
		}
		return data[data[index]];
	}
	
	

}
