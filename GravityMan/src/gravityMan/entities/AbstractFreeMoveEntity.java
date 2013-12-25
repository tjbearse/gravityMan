package gravityMan.entities;

import gravityMan.util.Vector2d;

public abstract class AbstractFreeMoveEntity extends AbstractMovableEntity {
	protected double mass;
	protected Vector2d force;
	protected Vector2d momentum;

	public AbstractFreeMoveEntity(double x, double y, double width,
			double height, double mass) {
		super(x, y, width, height);
		
		this.mass = mass;
		force = new Vector2d(0, 0);
		momentum = new Vector2d(0, 0);
	}

	public void clearForce() {
		force.scale(0);
	}

	public void applyForce(Vector2d force) {
		this.force.add(force);
	}

	public double getMass(){
		return mass;
	}
	
	@Override
	public void update(int delta) {
		// TODO: change to improved Euler or RK4?
		momentum.add(force.scaleCpy(delta));
		force.zero();
		vel = momentum.scaleCpy(1 / mass);
		super.update(delta);
	}

}
