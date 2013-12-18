package gravityMan.entities;

import gravityMan.util.Vector2d;
import static org.lwjgl.opengl.GL11.*;

public class Rope {
	public RopeNode[] nodes;
	public RopeNodeFixed anchorA;
	public RopeNodeFixed anchorB;

	//protected double equilLen = 5;
	//protected double springConst = .2;
	//protected double dampening = .05;
	protected double equilLen = 5;
	protected double springConst = .05;
	

	

	protected double nodeMass = 100;

	public Rope() {
		nodes = null;
	}

	public void removeNode() {
		if (nodes.length == 1) {
			return;
		} else {
			System.arraycopy(nodes, 0, nodes, 0, nodes.length - 1);
		}
	}

	public void addNode() {
		RopeNode[] temp = nodes;
		System.arraycopy(nodes, 0, nodes, 0, nodes.length - 1);
	}

	public Rope(double x, double y, int num) {
		nodes = new RopeNode[num - 1];
		anchorA = new RopeNodeFixed(x, y);
		anchorB = new RopeNodeFixed(x, y);
		for (int i = 0; i < num - 1; i++) {
			nodes[i] = new RopeNode(x, y, nodeMass);
		}
	}
	
	public void attachB(AbstractFreeMoveEntity anchor){
		//anchorB = anchor;
	}

	public void update(int delta) {
		TensionForces();
		anchorA.update(delta);
		anchorB.update(delta);
		for (RopeNode r : nodes) {
			r.update(delta);
		}
		
	}

	private void TensionForces() {
		Vector2d dist;
		Vector2d x;
		Vector2d force;
		// TODO add null checks (1 or no nodes);

		for (int j = 1; j <= 4; j++) {
			for (int i = 0; i < j; i++) {
				dist = anchorA.getLocation().subCpy(nodes[i].getLocation());
				if (dist.getMag() > equilLen * (i + 1)) {
					x = dist.scaleCpy(equilLen / dist.getMag());
					force = dist.subCpy(x).scaleCpy(springConst);

					nodes[i].applyForce(force);
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

			for (int i = 0; i < j; i++) {
				dist = anchorB.getLocation().subCpy(
						nodes[nodes.length - i - 1].getLocation());
				if (dist.getMag() > equilLen * (i + 1)) {
					x = dist.scaleCpy(equilLen / dist.getMag());
					force = dist.subCpy(x).scaleCpy(springConst);

					nodes[nodes.length - i - 1].applyForce(force);
					//anchorB.applyForce(force.scale(-1));
				}
			}

		}

	}

	public void draw() {
		anchorA.draw();
		anchorB.draw();
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
