package gravityMan.entities;


public abstract class AbstractFreeMoveEntity extends AbstractMovableEntity {
	protected double mass;
	protected Vector2d force;
	protected Vector2d momentum;
	
	public AbstractFreeMoveEntity(double x, double y, double width,
			double height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void clearForce(){
		force.scale(0);
	}
	
	public void applyForce(Vector2d force){
		this.force.add(force);
	}

	@Override
	public void update(int delta) {
		//TODO: change to improved Euler or RK4?
		momentum.add( force.cpScale(delta) );
		force.zero();
		vel = momentum.cpScale(1/mass);	
		super.update(delta);
	}
	
	
}
