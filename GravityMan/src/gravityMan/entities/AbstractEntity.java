package gravityMan.entities;

import gravityMan.abstractEntities.hitboxes.Hitbox;
import gravityMan.util.Vector2d;


public abstract class AbstractEntity implements Entity {
	protected double width, height;
	protected Vector2d pos;
	protected Hitbox hitbox;
	
	public AbstractEntity(double x, double y, double width, double height) {
		pos = new Vector2d(x, y);
		this.width = width;
		this.height = height;
	}

	public boolean intersects(AbstractEntity other) {
		// need to update hitbox locations/rotations first
		return hitbox.intersects(hitbox);
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

	public Vector2d getLocation(){
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

	

}
