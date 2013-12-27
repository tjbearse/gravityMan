package gravityMan.abstractEntities.entities;

import static org.lwjgl.opengl.GL11.*;
import gravityMan.abstractEntities.hitboxes.SimpleRectHitbox;
import gravityMan.entities.AbstractEntity;

public class FixedPlatform extends AbstractEntity {

	public FixedPlatform(double x, double y, double width, double height) {
		super(x, y, width, height);
		hitbox = new SimpleRectHitbox(x, y, width, height);
	}

	@Override
	public void draw() {
		glBegin(GL_QUADS);
		glColor3d(0, 1, 1);
		glVertex2d(pos.getX() + width / 2, pos.getY() - height / 2);
		glVertex2d(pos.getX() - width / 2, pos.getY() - height / 2);
		glColor3d(1, 1, 0);
		glVertex2d(pos.getX() - width / 2, pos.getY() + height / 2);
		glVertex2d(pos.getX() + width / 2, pos.getY() + height / 2);
		glEnd();
		
	}

	@Override
	public void update(int delta) {

	}

}
