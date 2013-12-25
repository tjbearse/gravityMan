package gravityMan.entities;

import gravityMan.util.Vector2d;

public abstract class AbstractMovableEntity extends AbstractEntity implements
		MovableEntity {
	protected Vector2d vel;

	public AbstractMovableEntity(double x, double y, double width, double height) {
		super(x, y, width, height);
		vel = new Vector2d(0, 0);
	}

	@Override
	public void update(int delta) {
		// TODO change to RK4 or improved Euler
		pos.add(vel.scaleCpy(delta));
	}

	@Override
	public double getVelX() {
		return vel.getX();
	}

	@Override
	public double getVelY() {
		return vel.getY();

	}

	@Override
	public double getVelMag() {
		return vel.getMag();

	}

	@Override
	public double getVelAngleRad() {
		return vel.getAngleDeg();
	}

	@Override
	public void setVelX(double val) {
		vel.setX(val);

	}

	@Override
	public void setVelY(double val) {
		vel.setY(val);

	}

	public void setVel(Vector2d vec) {
		vel = new Vector2d(vec);
	}

	public Vector2d getVel() {
		return new Vector2d(vel);
	}

	@Override
	public void setVelAngleRad(double theta) {
		vel.setAngleRad(theta);

	}

	@Override
	public void scaleVel(double factor) {
		vel.scale(factor);

	}
	/*
	 * public void setTheta(double theta) { this.theta = theta; }
	 * 
	 * public double getTheta() { return theta; }
	 */
}
