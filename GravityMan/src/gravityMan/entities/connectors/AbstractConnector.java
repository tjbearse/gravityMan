package gravityMan.entities.connectors;

import gravityMan.entities.abstractEntities.AbstractMovableEntity;
import gravityMan.util.Vector2d;

public abstract class AbstractConnector {
	protected AbstractMovableEntity a, b;
	// relative location before rotation
	protected Vector2d dispA, dispB;
	// absolute location after rotation
	protected Vector2d locA, locB;

	public AbstractConnector(AbstractMovableEntity anchorA,
			AbstractMovableEntity anchorB) {
		if (anchorA == null || anchorB == null) {
			throw new NullPointerException();
		}
		a = anchorA;
		b = anchorB;

		dispA = new Vector2d(0, 0);
		dispB = new Vector2d(0, 0);
		locA = new Vector2d(0, 0);
		locB = new Vector2d(0, 0);
	}

	public AbstractConnector(AbstractMovableEntity anchorA, Vector2d dispA,
			AbstractMovableEntity anchorB, Vector2d dispB) {
		if (anchorA == null || anchorB == null) {
			throw new NullPointerException();
		}
		a = anchorA;
		b = anchorB;

		this.dispA = new Vector2d(dispA);
		this.dispB = new Vector2d(dispB);
		locA = new Vector2d(0, 0);
		locB = new Vector2d(0, 0);
		
		updateA();
		updateB();
	}

	protected void updateA() {
		// find new anchor location
		// displacement
		locA = new Vector2d(dispA);
		// rotation
		locA.setAngleRad(locA.getAngleRad() + a.getTheta());
		// position
		locA.add(a.getLocation());
	}
	
	protected void updateB() {
		// find new anchor location
		// displacement
		locB = new Vector2d(dispB);
		// rotation
		locB.setAngleRad(locB.getAngleRad() + b.getTheta());
		// position
		locB.add(b.getLocation());
	}

	public abstract void apply();

	public abstract void draw();
}
