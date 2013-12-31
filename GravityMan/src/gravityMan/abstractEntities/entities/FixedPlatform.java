package gravityMan.abstractEntities.entities;

import static org.lwjgl.opengl.GL11.*;
import gravityMan.abstractEntities.hitboxes.RectHitbox;
import gravityMan.entities.AbstractFixedMoveEntity;

public class FixedPlatform extends AbstractFixedMoveEntity {
	private double width, height;
	public FixedPlatform(double x, double y, double width, double height) {
		super(x, y);
		this.width = width;
		this.height = height;
		hitbox = new RectHitbox(x, y, width, height);
	}

	@Override
	public void doDraw() {
		glBegin(GL_QUADS);
		glColor3d(.5, .5, .5);
		glVertex2d(width / 2, -height / 2);
		glVertex2d(- width / 2,- height / 2);
		glVertex2d(- width / 2,height / 2);
		glVertex2d(width / 2, height / 2);
		glEnd();
		
	}

	@Override
	public void update(int delta) {}

}
