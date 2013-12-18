package gravityMan.entities;

import gravityMan.entities.hitboxes.Hitbox;


public abstract class AbstractEntity implements Entity {
	protected double x, y, width, height;
	protected Hitbox hitbox;
	
	public AbstractEntity(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean intersects(AbstractEntity other) {
		// need to update hitbox locations/rotations first
		return hitbox.intersects(hitbox);
	}
	
	@Override
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
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
		return x;
	}

	@Override
	public double getY() {
		return y;
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
