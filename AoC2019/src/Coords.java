
public class Coords {
	public int x;
	public int y;
	
	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @return Manhattan distance to (x0, y0)
	 */
	public int manhattanDistTo(int x0, int y0) {
		return Math.abs(this.x-x0) + Math.abs(this.y - y0);
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static Coords add(Coords a, Coords b) {
		int x = a.x + b.x;
		int y = a.y + b.y;
		return new Coords(x, y);
	}
	
	public void add(Coords a) {
		this.x = a.x + this.x;
		this.y = a.y + this.y;
	}
	
	public String toString() {
		return "(" + this.x + ", " + this.y + ") ";
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Coords a = (Coords) obj;
		return this.x == a.x && this.y == a.y;
	}
	
	@Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + ((Integer) this.x).hashCode();
        hash = 53 * hash + ((Integer) this.y).hashCode();
        return hash;
    }
	
}
