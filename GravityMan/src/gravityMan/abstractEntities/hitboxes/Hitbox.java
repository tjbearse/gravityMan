package gravityMan.abstractEntities.hitboxes;

import gravityMan.abstractEntities.hitboxes.SAT.Range;
import gravityMan.util.Matrix;
import gravityMan.util.Vector2d;

abstract public class Hitbox {
	protected Vector2d pos;
	protected double theta;

	public void setLocation(Vector2d vec) {
		pos = new Vector2d(vec);
	}
	
	abstract public Vector2d[] getFaces();

	abstract public Range getProjRange(Matrix proj);
	
	public Vector2d getCenter(){
		return new Vector2d(pos);
	}

	public void setRotation(double theta) {
		this.theta = theta;
	}
}
