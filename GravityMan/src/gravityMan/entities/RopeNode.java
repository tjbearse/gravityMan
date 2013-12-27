package gravityMan.entities;

import static org.lwjgl.opengl.GL11.*;
import gravityMan.abstractEntities.hitboxes.SimpleRectHitbox;
import gravityMan.util.RungeKutta;
import gravityMan.util.Vector2d;

//TODO move to rope package
public class RopeNode extends AbstractFreeMoveEntity {

	public RopeNode(double x, double y, double mass) {
		super(x, y, 3, 3, mass, mass);
		//TODO: make this a point hitbox?
		hitbox = new SimpleRectHitbox(x, y, 3, 3);
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
