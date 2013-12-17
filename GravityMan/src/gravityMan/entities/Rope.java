package gravityMan.entities;


import gravityMan.util.Vector2d;

public class Rope {
	public RopeNode[] nodes;
	public RopeNodeFixed anchor;

	protected double equilLen = 5;
	protected double springConst = .01;
	protected double dampening = .2;

	
	protected double nodeMass = 10;
	public Rope() {
		nodes = null;
	}

	public Rope(double x, double y, int num) {
		nodes = new RopeNode[num - 1];
		anchor = new RopeNodeFixed(x, y);
		for (int i = 0; i < num - 1; i++) {
			nodes[i] = new RopeNode(x, y, nodeMass);
		}
	}

	public void update(int delta) {
		TensionForces();
		anchor.update(delta);
		for (RopeNode r : nodes) {
			r.update(delta);
		}
	}

	private void TensionForces() {
		Vector2d dist;
		Vector2d x;
		Vector2d force;
		// TODO add null checks (1 or no nodes);
		dist = anchor.getLocation().subCpy(nodes[0].getLocation());
		if (dist.getMag() == 0) {
			x = new Vector2d(0, 0);
		} else {
			x = dist.scaleCpy(equilLen / dist.getMag());
		}
		force = dist.subCpy(x).scaleCpy(springConst);
		force.add(anchor.getVel().subCpy(nodes[0].getVel()).scaleCpy(dampening));
		
		nodes[0].applyForce(force);
		
		for(int i=1; i<nodes.length; i++){
			dist = nodes[i-1].getLocation().subCpy(nodes[i].getLocation());
			if (dist.getMag() == 0) {
				x = new Vector2d(0, 0);
			} else {
				x = dist.scaleCpy(equilLen / dist.getMag());
			}
			force = dist.subCpy(x).scaleCpy(springConst);
			force.add(nodes[i-1].getVel().subCpy(nodes[i].getVel()).scaleCpy(dampening));
			nodes[i-1].applyForce(force.scaleCpy(-1));
			nodes[i].applyForce(force);
		}
		//System.out.println("y0: "+nodes[0].getY()+"y1: "+nodes[1].getY());
	}

	public void draw() {
		anchor.draw();
		for (RopeNode r : nodes) {
			r.draw();
		}
	}
}
