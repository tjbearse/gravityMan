package gravityMan.abstractEntities.entities;

import gravityMan.abstractEntities.hitboxes.SimpleRectHitbox;
import gravityMan.entities.AbstractFreeMoveEntity;
import gravityMan.util.RungeKutta;
import gravityMan.util.Vector2d;
import static org.lwjgl.opengl.GL11.*;

public class TestObject extends AbstractFreeMoveEntity {
	private double width, height;
	public TestObject(double x, double y, double width, double height,
			double mass, double inertia) {
		super(x, y, mass, inertia);
		this.width = width;
		this.height = height;
		hitbox = new SimpleRectHitbox(x, y, width, height);
	}

	@Override
	public void doDraw() {
		glBegin(GL_QUADS);
		glColor3d(0, 1, 0);
		glVertex2d(width / 2, height / 2);
		glVertex2d(width / 2, -height / 2);
		glVertex2d(-width / 2, -height / 2);
		glVertex2d(-width / 2, height / 2);
		glEnd();

	}

}
