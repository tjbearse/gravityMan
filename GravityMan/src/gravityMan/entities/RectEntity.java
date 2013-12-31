package gravityMan.entities;

import static org.lwjgl.opengl.GL11.*;
import gravityMan.entities.abstractEntities.AbstractFreeMoveEntity;
import gravityMan.entities.hitboxes.RectHitbox;

public class RectEntity extends AbstractFreeMoveEntity {
	private double width, height;
	public RectEntity(double x, double y, double width, double height, double mass, double inertia) {
		super(x, y, mass, inertia);
		this.width = width;
		this.height = height;
		hitbox = new RectHitbox(x, y, width, height);
	}

	
	@Override
	public void doDraw() {
		glBegin(GL_QUADS);
		glColor3d(1, 0, 0);
		glVertex2d(width / 2, height / 2);
		glVertex2d(width / 2, - height / 2);
		glVertex2d(- width / 2, - height / 2);
		glVertex2d(- width / 2, height / 2);
		glEnd();
	}

}
