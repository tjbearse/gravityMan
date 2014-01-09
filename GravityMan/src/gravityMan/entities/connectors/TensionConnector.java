package gravityMan.entities.connectors;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;
import gravityMan.entities.abstractEntities.AbstractMovableEntity;
import gravityMan.util.Vector2d;

public class TensionConnector extends AbstractConnector {
	protected double springConstant, equilLen;

	public TensionConnector(AbstractMovableEntity anchorA,
			AbstractMovableEntity anchorB, double springConstant,
			double equilLen) {
		super(anchorA, anchorB);
		this.springConstant = springConstant;
		this.equilLen = equilLen;
	}

	public TensionConnector(AbstractMovableEntity anchorA, Vector2d dispA,
			AbstractMovableEntity anchorB, Vector2d dispB,
			double springConstant, double equilLen) {
		super(anchorA, dispA, anchorB, dispB);
		this.springConstant = springConstant;
		this.equilLen = equilLen;
	}

	@Override
	public void apply() {
		updateA();
		updateB();

		Vector2d dist = locA.sub(locB);
		Vector2d x;

		if (dist.getMag() > equilLen) {
			x = dist.scale(equilLen / dist.getMag());

			Vector2d force = dist.sub(x).scale(springConstant);

			a.applyForce(force.scale(-1), locA.sub(a.getLocation()));
			b.applyForce(force, locB.sub(b.getLocation()));
		}
	}

	@Override
	public void draw() {
		glBegin(GL_LINES);
		glColor3d(1, 1, 0);
		glVertex2d(locA.getX(), locA.getY());
		glVertex2d(locB.getX(), locB.getY());
		glEnd();
	}

}
