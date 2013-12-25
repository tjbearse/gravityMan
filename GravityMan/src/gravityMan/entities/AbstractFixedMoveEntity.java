package gravityMan.entities;

import gravityMan.util.Vector2d;


abstract public class AbstractFixedMoveEntity extends AbstractMovableEntity {
	//TODO move path to lower class
	private Vector2d path[];
	
	public AbstractFixedMoveEntity(double x, double y, double width,
			double height) {
		super(x, y, width, height);
		path = null;
	}
	
	public void addToPath(Vector2d p){
		Vector2d temp[] = new Vector2d[path.length+1];
		System.arraycopy(path, 0, temp, 0, path.length);
		path = temp;
		path[path.length-1] = p;
	}

}
