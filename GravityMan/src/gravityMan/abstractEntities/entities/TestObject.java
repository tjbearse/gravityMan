package gravityMan.abstractEntities.entities;

import gravityMan.entities.AbstractFreeMoveEntity;
import static org.lwjgl.opengl.GL11.*;

public class TestObject extends AbstractFreeMoveEntity {

	public TestObject(double x, double y, double width, double height, double mass) {
		super(x, y, width, height, mass);
	}

	@Override
	public void draw() {
		//glPointSize(10);
		glBegin(GL_QUADS);
		glVertex2d(pos.getX() + width / 2, pos.getY() + height / 2);
		glVertex2d(pos.getX() + width / 2, pos.getY() - height / 2);
		glVertex2d(pos.getX() - width / 2, pos.getY() - height / 2);
		glVertex2d(pos.getX() - width / 2, pos.getY() + height / 2);
		glEnd();
	}

}
