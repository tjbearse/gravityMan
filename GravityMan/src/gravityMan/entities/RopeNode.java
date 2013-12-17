package gravityMan.entities;

import static org.lwjgl.opengl.GL11.*;

public class RopeNode extends AbstractFreeMoveEntity {

	public RopeNode(double x, double y, double mass) {
		super(x, y, 5, 5, mass);
		
	}

	@Override
	public void draw() {
		glBegin(GL_QUADS);
		glVertex2d(pos.getX() + width / 2, pos.getY() + height / 2);
		glVertex2d(pos.getX() + width / 2, pos.getY() - height / 2);
		glVertex2d(pos.getX() - width / 2, pos.getY() - height / 2);
		glVertex2d(pos.getX() - width / 2, pos.getY() + height / 2);
		glEnd();
	}

}
