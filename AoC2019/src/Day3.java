import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day3 {

	public static void main(String[] args) {
		tasks(1);
		tasks(2);

	}
	
	private static void tasks(int task_num) {
		try {
		      File file = new File("data/day3-input.txt");
		      Scanner in = new Scanner(file);
		      
		      String[] first = in.nextLine().split(",");
		      Map<Coords, Integer> first_coords = getCoords(first);
		      
		      String[] second = in.nextLine().split(",");
		      Map<Coords, Integer> sec_coords = getCoords(second);
		      
		      Set<Coords> keys = first_coords.keySet();
		      keys.retainAll(sec_coords.keySet()); // intersection
		      
		      int result = 0;
		      switch(task_num) {
		      case 1:
		    	  result = getClosestMan(keys);
		    	  break;
		      case 2:
		    	  result = getClosestSteps(keys, first_coords, sec_coords);
		    	  break;
		      default:
		    	  System.out.println("Invalid task_num");
		      }
		      
		      System.out.println("Task " + task_num + ": " + result);
		      in.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param coords: Set of all Coords reached by both lines
	 * @return int: smallest Manhattan distance to origin
	 */
	private static int getClosestMan(Set<Coords> coords) {
		int dist = Integer.MAX_VALUE;
		for(Coords c : coords) {
			int tmp = c.manhattanDistTo(0, 0);
			if(tmp < dist) {
				dist = tmp;
			}
		}
		return dist;
	}
	
	/**
	 * 
	 * @param coords: Set of all Coords reached by both lines
	 * @return int: smallest #combined steps needed to reach intersection
	 */
	private static int getClosestSteps(Set<Coords> coords, Map<Coords, Integer> m1, Map<Coords, Integer> m2) {
		int count = Integer.MAX_VALUE;
		for(Coords c : coords) {
			int tmp1 = m1.get(c);
			int tmp2 = m2.get(c);
			if(tmp1 + tmp2 < count) {
				count = tmp1 + tmp2;
			}
		}
		return count;
	}

	/**
	 * 
	 * @return Map containing all Coords that are reached as a key
	 *  and the #steps needed to reach this Coords as a value
	 */
	private static Map<Coords, Integer> getCoords(String[] line){
		Map<Coords, Integer> m = new HashMap<Coords, Integer>();
		Coords curr = new Coords(0, 0);
		Coords incr = new Coords(0, 0);
		int steps = 0;
		for(int i = 0; i<line.length; i++) {
			switch(line[i].charAt(0)) {
			case 'R':
				incr.set(1, 0);
				break;
			case 'L':
				incr.set(-1, 0);
				break;
			case 'U':
				incr.set(0, 1);
				break;
			case 'D':
				incr.set(0, -1);
				break;
			default: 
				incr.set(0, 0);
			}
			int num = Integer.parseInt(line[i].substring(1));
			for(int j=0; j<num; j++) {
				curr.add(incr);
				Coords new_el = new Coords(curr.x, curr.y);
				steps++;
				if(!m.containsKey(new_el)) {
					m.put(new_el, steps);
				}
			}
		}
		return m;
	}

}
