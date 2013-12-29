package gravityMan.entities;

import gravityMan.util.Vector2d;


abstract public class AbstractFixedMoveEntity extends AbstractMovableEntity {
	public AbstractFixedMoveEntity(double x, double y, double width,
			double height) {
		super(x, y, width, height);
	}
	
	public void applyForce(Vector2d force){	}
	public void applyForce(Vector2d force, Vector2d disp){ }
}
