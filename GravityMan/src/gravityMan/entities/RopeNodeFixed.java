package gravityMan.entities;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class RopeNodeFixed extends AbstractFixedMoveEntity {

	public RopeNodeFixed(double x, double y) {
		super(x, y, 8, 8);
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
