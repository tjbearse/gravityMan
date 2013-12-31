package gravityMan.entities;

import gravityMan.entities.abstractEntities.AbstractMovableEntity;
import gravityMan.util.Vector2d;
import static org.lwjgl.opengl.GL11.*;

public class Rope {
	public RectEntity[] nodes;
	public AbstractMovableEntity anchorA;
	public AbstractMovableEntity anchorB;

	// Relative attachment point
	protected Vector2d anchorADisp;
	protected Vector2d anchorBDisp;
	// Absolute attachment point
	protected Vector2d anchorALoc;
	protected Vector2d anchorBLoc;
	
	// Spring Constants
	protected double equilLen = 5;
	protected double springConst = .01;
	
	// Node attributes
	protected double nodeMass = 100;
	protected double nodeSize = 2;

	public Rope() {
		nodes = null;
	}

	public void removeNode() {
		if (nodes.length == 1) {
			return;
		} else {
			RectEntity[] temp = nodes;
			nodes = new RectEntity[nodes.length - 1];
			System.arraycopy(temp, 0, nodes, 0, nodes.length);
		}
	}

	public void addNode() {
		RectEntity[] temp = nodes;
		nodes = new RectEntity[nodes.length + 1];
		System.arraycopy(temp, 0, nodes, 0, temp.length);
		nodes[nodes.length - 1] = new RectEntity(
				nodes[nodes.length - 2].getX(), nodes[nodes.length - 2].getY(),
				nodeSize, nodeSize, nodeMass, nodeMass);
	}

	// TODO make rope builder class?
	public Rope(int num, AbstractMovableEntity a, Vector2d dispA,
			AbstractMovableEntity b, Vector2d dispB) {
		nodes = new RectEntity[num - 1];
		double xInc = (a.getX() - b.getX()) / num;
		double yInc = (a.getY() - b.getY()) / num;

		double x = a.getX();
		double y = a.getY();

		for (int i = 0; i < num - 1; i++) {
			nodes[i] = new RectEntity(x, y, nodeSize, nodeSize, nodeMass, nodeMass);
			x += xInc;
			y += yInc;
		}

		attachA(a, dispA);
		attachB(b, dispB);
	}

	// TODO way to generalize these functions / anchor concept?
	public void attachA(AbstractMovableEntity anchor, Vector2d disp) {
		if (anchor == null) {
			throw new NullPointerException();
		}
		anchorA = anchor;
		anchorADisp = disp;
		updateAnchorA();
	}

	public void attachB(AbstractMovableEntity anchor, Vector2d disp) {
		if (anchor == null) {
			throw new NullPointerException();
		}
		anchorB = anchor;
		anchorBDisp = disp;
		updateAnchorB();
	}

	public void update(int delta) {
		updateAnchorA();
		updateAnchorB();
		TensionForces();
		for (RectEntity r : nodes) {
			r.update(delta);
		}

	}

	private void updateAnchorB() {
		// find new anchor location
		// displacement
		anchorBLoc = new Vector2d(anchorBDisp);
		// rotation
		anchorBLoc.setAngleRad(anchorBLoc.getAngleRad() + anchorB.getTheta());
		// position
		anchorBLoc.add(anchorB.getLocation());
	}

	private void updateAnchorA() {
		// find new anchor location
		// displacement
		anchorALoc = new Vector2d(anchorADisp);
		// rotation
		anchorALoc.setAngleRad(anchorALoc.getAngleRad() + anchorA.getTheta());
		// position
		anchorALoc.add(anchorA.getLocation());
	}

	private void TensionForces() {
		Vector2d dist;
		Vector2d x;
		Vector2d force;
		// TODO add null checks (1 or no nodes);

		for (int j = 1; j <= 5; j++) {
			for (int i = 0; i < j && i < nodes.length; i++) {
				dist = anchorALoc.subCpy(nodes[i].getLocation());
				if (dist.getMag() > equilLen * (i + 1)) {
					x = dist.scaleCpy(equilLen * (i + 1) / dist.getMag());
					force = dist.subCpy(x).scaleCpy(springConst);

					nodes[i].applyForce(force.scale(j - i));

					Vector2d disp = anchorALoc.subCpy(anchorA.getLocation());
					anchorA.applyForce(force.scale(-1), disp);
				}
			}

			for (int i = j; i < nodes.length; i++) {
				dist = nodes[i - j].getLocation()
						.subCpy(nodes[i].getLocation());
				if (dist.getMag() > equilLen * j) {
					x = dist.scaleCpy(equilLen * j / dist.getMag());
					force = dist.subCpy(x).scale(springConst);

					nodes[i].applyForce(force);
					nodes[i - j].applyForce(force.scaleCpy(-1));
				}
			}
			for (int i = 0; i < j && nodes.length - i > 0; i++) {
				dist = anchorBLoc.subCpy(nodes[nodes.length - i - 1]
						.getLocation());
				if (dist.getMag() > equilLen * (i + 1)) {
					x = dist.scaleCpy(equilLen * (i + 1) / dist.getMag());
					force = dist.subCpy(x).scaleCpy(springConst);

					nodes[nodes.length - i - 1].applyForce(force.scale(j - i));

					Vector2d disp = anchorBLoc.subCpy(anchorB.getLocation());
					anchorB.applyForce(force.scale(-1), disp);
				}
			}

		}

	}

	public void draw() {
		for (RectEntity r : nodes) {
			r.draw();
		}
		glBegin(GL_LINES);
		glColor3d(0, .5, .5);
		glVertex2d(anchorA.getX(), anchorA.getY());
		for (int i = 0; i < nodes.length; i++) {
			// nodes[i].draw();
			glVertex2d(nodes[i].getX(), nodes[i].getY());
			glVertex2d(nodes[i].getX(), nodes[i].getY());
		}
		glVertex2d(anchorBLoc.getX(), anchorBLoc.getY());
		glEnd();
	}
}
