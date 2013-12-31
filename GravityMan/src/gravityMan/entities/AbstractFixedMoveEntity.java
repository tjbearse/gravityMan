package gravityMan.entities;

import gravityMan.util.Vector2d;

abstract public class AbstractFixedMoveEntity extends AbstractMovableEntity {
	public AbstractFixedMoveEntity(double x, double y) {
		super(x, y);
	}

	public void applyForce(Vector2d force) {
	}

	public void applyForce(Vector2d force, Vector2d disp) {
	}
}
