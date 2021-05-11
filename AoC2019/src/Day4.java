import java.util.*;

public class Day4 {
	
	// range
	static final int LOW  = 172851;
	static final int HIGH = 675869;
	static final int NUM = 6; // #digits

	public static void main(String[] args) {
		tasks(false);
		tasks(true);

	}
	
	private static void tasks(boolean checkGroup){
		int curr = LOW;
		int count = 0;
		curr = next(curr);
		while(curr <= HIGH) {
			if(!checkGroup || checkGroup(curr)) {
				count++;
				// System.out.println(count + ": " + curr);
			}
			curr++;
			curr = next(curr);
		}
		System.out.println("Result: " + count);
	}
	
	/**
	 * 
	 * @param n
	 * @return true if there is a group of exactly two adjacent & equal digits
	 */
	private static boolean checkGroup(int n) {
		int[] digits = getDigits(n);
		int same = 0;
		for(int i=1; i<digits.length; i++) {
			if(same == 1 && digits[i-1] != digits[i]){
				return true;
			}
			if(digits[i-1] == digits[i]) {
				same++;
			}else {
				same = 0;
			}
		}
		return same == 1;
	}
	
	/**
	 * 
	 * @param digits: int written as an array of digits
	 * @return integer value of digits plus 1
	 */
	private static int incr(int[] digits) {
		int n = digitsToInt(digits);
		return n+1;
	}
	
	/**
	 * 
	 * @param n
	 * @return a number >= n whose digits are monotonically increasing and 
	 *  who hast at least two adjacent digits that are the same
	 */
	private static int next(int n) {
		int[] digits = getDigits(n);
		boolean adj = false;
		for(int i=1; i<digits.length; i++) {
			if(digits[i-1]>digits[i]) {
				for(; i<digits.length; i++) {
					digits[i] = digits[i-1];
				}
				return digitsToInt(digits);
			}
			if(digits[i-1] == digits[i]) {
				adj = true;
			}
		}
		if(adj) {
			return digitsToInt(digits);
		}else {
			// System.out.println("Invalid: " + digitsToInt(digits));
			return next(incr(digits));
		}
		
	}

	/**
	 * 
	 * @param n
	 * @return The number n represented as an array of digits. The first element of
	 * the array corresponds to the most significant digit (left-most)
	 */
	private static int[] getDigits(int n) {
		// int N = n;
		int[] digits = new int[NUM];
		for(int i=NUM-1; i>=0; i--) {
			digits[i] = n % 10;
			n = n / 10;
		}
		// System.out.println("getDigits(" + N + ") = " + Arrays.toString(digits));
		return digits;
	}
	
	/**
	 * 
	 * @param digits: A number n represented as an array of digits.
	 * @return the number n
	 */
	private static int digitsToInt(int[] digits) {
		int n = 0;
		for(int i=0; i<digits.length; i++) {
			n = n * 10 + digits[i];
		}
		// System.out.println("digitsToInt(" + Arrays.toString(digits) + ") = " + n);
		return n;
	}

}
