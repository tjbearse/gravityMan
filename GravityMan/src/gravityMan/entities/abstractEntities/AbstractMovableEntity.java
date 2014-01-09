package gravityMan.entities.abstractEntities;

import gravityMan.util.Vector2d;

public abstract class AbstractMovableEntity extends AbstractEntity implements
		MovableEntity {
	protected Vector2d vel;
	protected double angVel;

	public abstract void applyForce(Vector2d force);
	public abstract void applyForce(Vector2d force, Vector2d disp);

	public AbstractMovableEntity(double x, double y) {
		super(x, y);
		vel = new Vector2d(0, 0);
		angVel = 0;
	}

	@Override
	public void update(int delta) {
		// TODO change to RK4 or improved Euler
		// Linear
		pos = pos.add(vel.scale(delta));
		hitbox.setLocation(pos);
		// Rotational
		theta += angVel;
		theta %= 2 * Math.PI;
		hitbox.setRotation(theta);
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
		vel = vel.scale(factor);

	}

	public double getAngVel() {
		return angVel;
	}

	public void setAngVel(double angVel) {
		this.angVel = angVel;
	}
}
