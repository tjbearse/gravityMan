package gravityMan.abstractEntities.entities;

import gravityMan.abstractEntities.hitboxes.SimpleRectHitbox;
import gravityMan.entities.AbstractFreeMoveEntity;
import gravityMan.util.RungeKutta;
import gravityMan.util.Vector2d;
import static org.lwjgl.opengl.GL11.*;

public class TestObject extends AbstractFreeMoveEntity {

	public TestObject(double x, double y, double width, double height,
			double mass) {
		super(x, y, width, height, mass);
		hitbox = new SimpleRectHitbox(x, y, width, height);
	}

	@Override
	public void draw() {
		// glPointSize(10);
		glBegin(GL_QUADS);
		glColor3d(0, 1, 0);
		glVertex2d(pos.getX() + width / 2, pos.getY() + height / 2);
		glVertex2d(pos.getX() + width / 2, pos.getY() - height / 2);
		glVertex2d(pos.getX() - width / 2, pos.getY() - height / 2);
		glVertex2d(pos.getX() - width / 2, pos.getY() + height / 2);
		glEnd();
	}

}
