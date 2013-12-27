package gravityMan.entities;

import gravityMan.util.Vector2d;
import static org.lwjgl.opengl.GL11.*;

//TODO: migrate to ropes package
public class Rope {
	public RopeNode[] nodes;
	public RopeNodeFixed anchorA;
	public AbstractFreeMoveEntity anchorB;

	protected Vector2d anchorBDisp; // attachment point, relative to unrotated
	protected Vector2d anchorBLoc; // actual attachment point
	// These vals work (even with attached item)
	// protected double equilLen = 5;
	// protected double springConst = .06;
	// protected double nodeMass = 100;
	protected double equilLen = 5;
	protected double springConst = .01;
	protected double nodeMass = 100;

	public Rope() {
		nodes = null;
	}

	public void removeNode() {
		if (nodes.length == 1) {
			return;
		} else {
			RopeNode[] temp = nodes;
			nodes = new RopeNode[nodes.length - 1];
			System.arraycopy(temp, 0, nodes, 0, nodes.length);
		}
	}

	public void addNode() {
		RopeNode[] temp = nodes;
		nodes = new RopeNode[nodes.length + 1];
		System.arraycopy(temp, 0, nodes, 0, temp.length);
		nodes[nodes.length - 1] = new RopeNode(nodes[nodes.length - 2].getX(),
				nodes[nodes.length - 2].getY(), nodeMass);
	}

	public Rope(double x, double y, int num) {
		nodes = new RopeNode[num - 1];
		anchorA = new RopeNodeFixed(x, y);
		// anchorB = new RopeNodeFixed(x, y);
		for (int i = 0; i < num - 1; i++) {
			nodes[i] = new RopeNode(x, y, nodeMass);
		}
		anchorBDisp = new Vector2d(0, 0);
	}

	public void attachB(AbstractFreeMoveEntity anchor, Vector2d disp) {
		anchorB = anchor;
		anchorBDisp = disp;
		updateAnchorB();
	}

	public void update(int delta) {
		updateAnchorB();
		TensionForces();
		anchorA.update(delta);
		// anchorB.update(delta);
		for (RopeNode r : nodes) {
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

	private void TensionForces() {
		Vector2d dist;
		Vector2d x;
		Vector2d force;
		// TODO add null checks (1 or no nodes);

		for (int j = 1; j <= 5; j++) {
			for (int i = 0; i < j && i < nodes.length; i++) {
				dist = anchorA.getLocation().subCpy(nodes[i].getLocation());
				if (dist.getMag() > equilLen * (i + 1)) {
					x = dist.scaleCpy(equilLen * (i + 1) / dist.getMag());
					force = dist.subCpy(x).scaleCpy(springConst);

					nodes[i].applyForce(force.scale(j - i));
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

					Vector2d disp = anchorB.getLocation().subCpy(anchorBLoc);
					System.out.println(disp.getMag());
					anchorB.applyForce(force.scale(-1), disp);
				}
			}

		}

	}

	public void draw() {
		anchorA.draw();
		// anchorB.draw();
		for (RopeNode r : nodes) {
			r.draw();
		}
		glBegin(GL_LINES);
		glVertex2d(anchorA.getX(), anchorA.getY());
		for (int i = 0; i < nodes.length; i++) {
			// nodes[i].draw();
			glVertex2d(nodes[i].getX(), nodes[i].getY());
			glVertex2d(nodes[i].getX(), nodes[i].getY());
		}
		glVertex2d(anchorB.getX(), anchorB.getY());
		glEnd();
	}
}
