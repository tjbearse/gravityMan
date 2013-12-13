package gravityMan.entities;

public abstract class AbstractMovableEntity extends AbstractEntity implements
		MovableEntity {
	protected Vector2d vel;
	protected double theta; // object rotation
	protected double rotvel; // rotational velocity

	public AbstractMovableEntity(double x, double y, double width, double height) {
		super(x, y, width, height);
		vel = new Vector2d(0, 0);
	}

	@Override
	public void update(int delta) {
		//TODO change to RK4 or improved Euler
		x += vel.getX() * delta;
		y += vel.getY() * delta;
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

	@Override
	public void setVelAngleRad(double theta) {
		vel.setAngleRad(theta);

	}

	@Override
	public void scaleVel(double factor) {
		vel.scale(factor);

	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getTheta() {
		return theta;
	}

}
