package gravityMan;

import java.util.ArrayList;

import gravityMan.abstractEntities.entities.FixedPlatform;
import gravityMan.abstractEntities.entities.TestObject;
import gravityMan.entities.AbstractEntity;
import gravityMan.entities.AbstractMovableEntity;
import gravityMan.entities.Entity;
import gravityMan.entities.Rope;
import gravityMan.entities.RopeNode;
import gravityMan.util.Vector2d;

import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;

public class Game {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 480;

	private boolean isRunning = true;
	private int nodeChangeCooldown = 0;
	private int nodeChangeCooldownNum = 10;

	private TestObject unit;
	private Rope rope;
	private AbstractMovableEntity[] entities;

	private double unitProp = .3;

	private double airFricLinear = .4;
	private double airFricRot = .6;
	
	private Vector2d gravity = new Vector2d(0, -.00098);

	// private double AngFric = .5;

	public Game() {
		setUpDisplay();
		setUpOpenGL();
		setUpEntities();
		setUpTimer();
		while (isRunning) {
			render();
			logic(getDelta());
			input();
			Display.update();
			Display.sync(100);

			if (Display.isCloseRequested()) {
				isRunning = false;
			}
		}
		Display.destroy();
	}

	private void logic(int delta) {
		for (AbstractMovableEntity e : entities) {
			// gravity
			 e.applyForce(gravity.scaleCpy(unit.getMass()));
			// air friction
			//		linear
			Vector2d fricForce = e.getVel().scale(
					-airFricLinear * unit.getVelMag());
			e.applyForce(fricForce);
			//		rotational
			fricForce = new Vector2d(unit.getAngVel() * -airFricRot, 0);
			Vector2d disp = new Vector2d(0, 1);
			e.applyForce(fricForce, disp);
			e.applyForce(fricForce.scale(-1));
		}
		// TODO move rope nodes into entities container? (avoids repetition
		// here)
		for (RopeNode n : rope.nodes) {
			// gravity
			// n.applyForce(gravity.scaleCpy(n.getMass()));
			// air friction
			n.applyForce(n.getVel().scale(-airFricLinear * n.getVelMag()));
		}

		for (AbstractMovableEntity e : entities) {
			e.update(delta);
		}

		rope.update(delta);

		// collisions
		// TODO restructure to use quadtree

		for (int i = 0; i < entities.length; i++) {
			for (int j = i + 1; j < entities.length; j++) {
				if (entities[i].intersects(entities[j])) {
					Vector2d vec = entities[i].hitbox.getCenter();
					vec.sub(entities[j].hitbox.getCenter());
				}
			}
		}
	}

	private void input() {
		double magY;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			magY = unitProp;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			magY = -unitProp;
		} else {
			magY = 0;
		}

		double magX;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			magX = unitProp;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			magX = -unitProp;
		} else {
			magX = 0;
		}

		if (nodeChangeCooldown != 0) {
			nodeChangeCooldown--;
		} else {
			if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) {
				rope.addNode();
				nodeChangeCooldown = nodeChangeCooldownNum;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
				rope.removeNode();
				nodeChangeCooldown = nodeChangeCooldownNum;
			}
		}
		Vector2d propulsion = new Vector2d(magX, magY);
		unit.applyForce(propulsion, new Vector2d(0, 0));
		// rope.anchorA.setVel(propulsion);

	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		rope.draw();
		//TODO need to do z-layering somehow
		for (AbstractMovableEntity e : entities) {
			e.draw();
		}
	}

	private long lastFrame;

	private void setUpTimer() {
		lastFrame = getTime();
	}

	private void setUpEntities() {
		entities = new AbstractMovableEntity[3];
		unit = new TestObject(WIDTH / 2, 3 * HEIGHT / 4, 40, 10, 5000, 1000000);
		rope = new Rope(WIDTH / 2, 49 * HEIGHT / 50, 10);
		rope.attachB(unit, new Vector2d(20, 0));

		// TODO change to quadtree setup
		entities[0] = unit;
		entities[1] = new FixedPlatform(0, HEIGHT / 2, 20, HEIGHT);
		entities[2] = new FixedPlatform(WIDTH/2, 0 , WIDTH, 20);

	}

	private void setUpOpenGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	private void setUpDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Gravity Man!");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	private long getTime() {
		return (Sys.getTime() * 1000 / Sys.getTimerResolution()); // miliseconds
	}

	private int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = currentTime;
		return delta;
	}

	public static void main(String[] args) {
		new Game();
	}
}
