package gravityMan.entities;

import java.util.ArrayList;

import gravityMan.entities.abstractEntities.AbstractMovableEntity;
import gravityMan.entities.connectors.AbstractConnector;
import gravityMan.entities.connectors.SpringConnector;
import gravityMan.entities.connectors.TensionConnector;
import gravityMan.util.Vector2d;
import static org.lwjgl.opengl.GL11.*;

public class RopeV2 {
	public ArrayList<AbstractConnector> connectors;
	public RectEntity[] nodes;
	public AbstractMovableEntity a, b;
	public Vector2d dispA, dispB;
	/*
	 * // Relative attachment point protected Vector2d anchorADisp; protected
	 * Vector2d anchorBDisp; // Absolute attachment point protected Vector2d
	 * anchorALoc; protected Vector2d anchorBLoc;
	 */

	// Spring Constants
	protected double equilLen = 2;
	protected double interConst = 0, interConnect = 8;
	protected double springConst = .03;

	// Node attributes
	protected double nodeMass = 100;
	protected double nodeSize = 2;

	public RopeV2() {
		nodes = null;
	}

	public RopeV2(int num, AbstractMovableEntity a, AbstractMovableEntity b) {
		this.a = a;
		this.b = b;
		this.dispA = new Vector2d(0, 0);
		this.dispB = new Vector2d(0, 0);
		setup(num);
	}

	public RopeV2(int num, AbstractMovableEntity a, Vector2d dispA,
			AbstractMovableEntity b, Vector2d dispB) {
		this.a = a;
		this.b = b;
		this.dispA = dispA;
		this.dispB = dispB;
		setup(num);
	}

	private void setup(int num) {
		// TODO add null check for a and b
		nodes = new RectEntity[num];
		connectors = new ArrayList<AbstractConnector>();

		double xInc = (b.getX() - a.getX()) / (num + 1);
		double yInc = (b.getY() - a.getY()) / (num + 1);

		double x = a.getX() + xInc;
		double y = a.getY() + yInc;

		for (int i = 0; i < num; i++) {
			x += xInc;
			y += yInc;

			nodes[i] = new RectEntity(x, y, nodeSize, nodeSize, nodeMass,
					nodeMass);
		}

		connect();
	}

	protected void connect() {
		// destroy connections
		

		for (int j = 0; j < interConnect; j+=2) {
			connectors.add(new SpringConnector(a, dispA, nodes[j],
					new Vector2d(0, 0), springConst, equilLen
							* (j + 1 + (j * interConst))));

			for (int i = j + 1; i < nodes.length; i++) {
				connectors.add(new SpringConnector(nodes[i - 1 - j], nodes[i],
						springConst, equilLen * (j + 1 + (j * interConst))));
			}

			connectors.add(new SpringConnector(nodes[nodes.length - 1 - j],
					new Vector2d(0, 0), b, dispB, springConst, equilLen
							* (j + 1 + (j * interConst))));
		}
		for (int j = 1; j < interConnect; j+=2) {
			connectors.add(new TensionConnector(a, dispA, nodes[j],
					new Vector2d(0, 0), springConst, equilLen
					* (j + 1 + (j * interConst))));
			
			for (int i = j + 1; i < nodes.length; i++) {
				connectors.add(new TensionConnector(nodes[i - 1 - j], nodes[i],
						springConst, equilLen * (j + 1 + (j * interConst))));
			}
			
			connectors.add(new TensionConnector(nodes[nodes.length - 1 - j],
					new Vector2d(0, 0), b, dispB, springConst, equilLen
					* (j + 1 + (j * interConst))));
		}
	}

	public void update(int delta) {
		TensionForces();
		for (RectEntity r : nodes) {
			r.update(delta);
		}

	}

	private void TensionForces() {
		for (AbstractConnector c : connectors) {
			c.apply();
		}
	}

	public void draw() {
		for (AbstractConnector c : connectors) {
			c.draw();
		}
		for (AbstractMovableEntity n : nodes) {
			n.draw();
		}
	}

}
