package gravityMan.entities;


abstract public class AbstractFixedMoveEntity extends AbstractMovableEntity {
	private Pair path[];
	
	public AbstractFixedMoveEntity(double x, double y, double width,
			double height) {
		super(x, y, width, height);
		path = null;
	}
	
	public void addToPath(Pair p){
		Pair temp[] = new Pair[path.length+1];
		System.arraycopy(path, 0, temp, 0, path.length);
		path = temp;
		path[path.length-1] = p;
	}

}
