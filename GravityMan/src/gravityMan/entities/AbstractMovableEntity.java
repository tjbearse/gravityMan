package gravityMan.entities;

public abstract class AbstractMovableEntity extends AbstractEntity implements
		MovableEntity {
	protected Vector2d vel;

	public AbstractMovableEntity(double x, double y, double width, double height) {
		super(x, y, width, height);
		vel = new Vector2d(0, 0);
	}

	@Override
	public void update(int delta) {
		x += vel.getX() * delta;
		y += vel.getY() * delta;
	}

	@Override
	public double getVelX() {
		return vel.getX();
	}

	@Override
	public double getVelY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getVelMag() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getVelAngleRad() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVelX(double val) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVelY(double val) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVelAngleRad(double theta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scaleVel(double factor) {
		// TODO Auto-generated method stub

	}

}
