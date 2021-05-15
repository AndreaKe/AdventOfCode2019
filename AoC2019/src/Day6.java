import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day6 {

	public static void main(String[] args) {
		solve();

	}
	
	
	private static void solve() {
		try {
		      File file = new File("data/day6-input.txt");
		      Scanner in = new Scanner(file);
		      Map<String, Set<String>> surrounded_by = new HashMap<String, Set<String>>();
		      Map<String, Set<String>> orbit_around = new HashMap<String, Set<String>>();
		      Set<String> all_objects = new HashSet<String>();
		      buildMap(surrounded_by, orbit_around, all_objects, in);
		      int result = get_num_orbits(surrounded_by, "COM", all_objects, 1);
		      System.out.println("Task 1: " + result);
		      int shortest = shortestPath("YOU", "SAN", surrounded_by, orbit_around);
		      System.out.println("Task 2: " + shortest);
		      in.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param surrounded_by - out: map from object A to set of all objects which orbit around A
	 * @param orbit_around - out: map from object B to set of all objects which B orbits around
	 * @param all_objects - out: set of all objects which exist in the universe
	 * @param in: scanner reading the input
	 * 
	 * reads each line of "in" and updates the parameters accordingly
	 */
	private static void buildMap(Map<String, Set<String>> surrounded_by, Map<String, Set<String>> orbit_around,
			Set<String> all_objects, Scanner in) {
		
		if(!in.hasNextLine()) {
			return;
		}
		
		String[] line = in.nextLine().split("[)]");
		
		// always check whether key already exists. Update or create map entry.
		if(surrounded_by.containsKey(line[0])) {
			surrounded_by.get(line[0]).add(line[1]);
		}else {
			Set<String> tmp = new HashSet<String>();
			tmp.add(line[1]);
			surrounded_by.put(line[0], tmp);
		}
		
		if(orbit_around.containsKey(line[1])) {
			orbit_around.get(line[1]).add(line[0]);
		}else {
			Set<String> tmp = new HashSet<String>();
			tmp.add(line[0]);
			orbit_around.put(line[1], tmp);
		}
		
		all_objects.add(line[0]);
		all_objects.add(line[1]);
		
		buildMap(surrounded_by, orbit_around, all_objects, in);
	}
	
	/**
	 * @return #direct and indirect orbits
	 */
	private static int get_num_orbits(Map<String, Set<String>> surrounded_by, String curr,
			Set<String> all_objects, int depth) {
		// basically a depth-first search which sums up the #orbits while traversing
		// the graph
		int count = 0;
		if(!surrounded_by.containsKey(curr)) {
			return 0;
		}
		for(String s : surrounded_by.get(curr)) {
			if(all_objects.contains(s)) {
				all_objects.remove(s);
				count += depth;
				count += get_num_orbits(surrounded_by, s, all_objects, depth+1);
			}else {
				System.out.println("LOOP"); // just to be sure
			}
		}
		return count;
	}
	
	/**
	 * @return length of the shortest path between the object orbited by 
	 * the objects "from" and the object orbited by "to"
	 */
	private static int shortestPath(String from, String to, 
			Map<String, Set<String>> surrounded_by, Map<String, Set<String>> orbit_around) {
		// compute shortest path between "from" and any other object recursively until you
		// find shortest path to "to"
		Set<String> workset = new HashSet<String>();
		workset.add(from);
		
		// maps object to length of shortest path to "from"
		Map<String, Integer> distances = new HashMap<String, Integer>();
		distances.put(from, 0);
		
		// compute shortest paths recursively until path to "to" found
		while(!distances.containsKey(to)) {
			workset = aux(surrounded_by, orbit_around, distances, workset);
		}
		return distances.get(to)-2; 
		// -2 because we want distance between **object orbited by** from and to
		// (not distance between from and to)
	}
	
	/**
	 * visits all children of the objects in the workset and updates distances accordingly
	 * @return new_workset which is the union of all children whose distances have been updated
	 * (e.g. objects for which we just found a shortest path)
	 */
	private static Set<String> aux(Map<String, Set<String>> surrounded_by, Map<String, Set<String>> orbit_around,
			Map<String, Integer> distances, Set<String> workset){
		
		Set<String> new_workset = new HashSet<String>();
		for(String s: workset) {
			int d = distances.get(s);
			
			// within one step you can orbit to an object that orbits around s
			if(surrounded_by.containsKey(s)) {
				update_distances(distances, surrounded_by.get(s), d+1, new_workset);
			}
			
			// or to an object which s orbits around
			if(orbit_around.containsKey(s)) {
				update_distances(distances, orbit_around.get(s), d+1, new_workset);
			}
		}
		
		return new_workset;
	}
	
	/**
	 *  @param toAdd: set of all objects whose distance might be updated
	 *  @param new_workset - out
	 *  
	 *  updates the distances for all objects in "toAdd" iff there doesn't exist not a smaller 
	 *  distance for this object already
	 *  and also adds this object to new_workset (iff the distance was updated)
	 */
	private static void update_distances(Map<String, Integer> distances, Set<String> toAdd, int dist,
			Set<String> new_workset) {
		
		for(String child : toAdd) {
			if(!distances.containsKey(child)) { // else: ignore because shorter path exists
				distances.put(child, dist);
				new_workset.add(child);
			}
		}
	}

}
