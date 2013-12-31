package gravityMan.entities;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;
import gravityMan.entities.abstractEntities.AbstractFixedMoveEntity;
import gravityMan.entities.hitboxes.RectHitbox;

//TODO migrate to ropes package
public class RopeNodeFixed extends AbstractFixedMoveEntity {
	private double width = 8, height = 8;
	public RopeNodeFixed(double x, double y) {
		super(x, y);
		hitbox = new RectHitbox(x, y, width, height);
	}

	@Override
	public void doDraw() {
		glBegin(GL_QUADS);
		glVertex2d(width / 2, +height / 2);
		glVertex2d(width / 2, -height / 2);
		glVertex2d(-width / 2, -height / 2);
		glVertex2d(-width / 2, +height / 2);
		glEnd();
	}
}
