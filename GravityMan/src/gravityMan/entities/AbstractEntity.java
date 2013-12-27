package gravityMan.entities;

import gravityMan.abstractEntities.hitboxes.Hitbox;
import gravityMan.abstractEntities.hitboxes.SAT;
import gravityMan.util.Vector2d;
import static org.lwjgl.opengl.GL11.*;

public abstract class AbstractEntity implements Entity {
	protected double width, height, theta;
	protected Vector2d pos;
	public Hitbox hitbox;

	public AbstractEntity(double x, double y, double width, double height) {
		pos = new Vector2d(x, y);
		this.width = width;
		this.height = height;
		theta = 0;
	}

	public final void draw(){
		
		glPushMatrix();
		glTranslated(pos.getX(), pos.getY(), 0);
		glRotated(Math.toDegrees(theta), 0, 0, 1);
		this.doDraw();
		
		glPopMatrix();
	}
	
	
	abstract protected void doDraw();
	// All doDraw functions are to draw the item as if it were at origin without
	// any rotation. Draw takes care of translation and rotation.
	
	public boolean intersects(AbstractEntity other) {
		// need to update hitbox locations/rotations first
		return SAT.intersects(this.hitbox, other.hitbox);
	}

	@Override
	public void setLocation(double x, double y) {
		pos.set(x, y);
	}

	public void setLocation(Vector2d vec) {
		pos = new Vector2d(vec);
	}

	@Override
	public void setX(double x) {
		pos.setX(x);
	}

	@Override
	public void setY(double y) {
		pos.setY(y);
	}

	@Override
	public void setWidth(double width) {
		this.width = width;
	}

	@Override
	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public double getX() {
		return pos.getX();
	}

	@Override
	public double getY() {
		return pos.getY();
	}

	public Vector2d getLocation() {
		return new Vector2d(pos);
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public double getWidth() {
		return width;
	}

	public void setTheta(double theta) {
		this.theta = theta % (2 * Math.PI);
	}

	public double getTheta() {
		return theta;
	}

}
