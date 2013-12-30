package gravityMan.abstractEntities.entities;

import static org.lwjgl.opengl.GL11.*;
import gravityMan.abstractEntities.hitboxes.SimpleRectHitbox;
import gravityMan.entities.AbstractEntity;
import gravityMan.entities.AbstractMovableEntity;
import gravityMan.util.Vector2d;

public class FixedPlatform extends AbstractMovableEntity {

	public FixedPlatform(double x, double y, double width, double height) {
		super(x, y, width, height);
		hitbox = new SimpleRectHitbox(x, y, width, height);
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

	@Override
	public void applyForce(Vector2d force) {}
	@Override
	public void applyForce(Vector2d force, Vector2d disp){ }

}
