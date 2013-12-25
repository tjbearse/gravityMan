package gravityMan.entities;

import static org.lwjgl.opengl.GL11.*;
import gravityMan.util.RungeKutta;
import gravityMan.util.Vector2d;

public class RopeNode extends AbstractFreeMoveEntity {

	public RopeNode(double x, double y, double mass) {
		super(x, y, 3, 3, mass);
		
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
