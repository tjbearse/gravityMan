package gravityMan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import gravityMan.entities.FixedPlatform;
import gravityMan.entities.Rope;
import gravityMan.entities.RectEntity;
import gravityMan.entities.RopeV2;
import gravityMan.entities.TestObject;
import gravityMan.entities.abstractEntities.AbstractMovableEntity;
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
	private RopeV2 rope;
	private ArrayList<AbstractMovableEntity> entities;

	private double unitProp = .6;

	private double airFricLinear = .9;
	private double airFricRot = .6;

	private Vector2d gravity = new Vector2d(0, -.00098);

	// private double AngFric = .5;
	double frameCount;
	public Game() {
		frameCount = 0;
		setUpDisplay();
		setUpOpenGL();
		setUpEntities();
		setUpTimer();
		while (isRunning) {
			if(frameCount  == 410){
				frameCount = frameCount;
			}
			render();
			logic(17);
			input();
			Display.update();
			Display.sync(100);

			if (Display.isCloseRequested()) {
				isRunning = false;
			}
			frameCount++;
		}
		Display.destroy();
	}

	private void logic(int delta) {
		for (AbstractMovableEntity e : entities) {
			// gravity
			//e.applyForce(gravity.scaleCpy(unit.getMass()));
			// air friction
			// linear
			Vector2d fricForce = e.getVel().scale(
					-airFricLinear * unit.getVelMag());
			e.applyForce(fricForce);
			
			// rotational friction
			fricForce = new Vector2d(unit.getAngVel() * -airFricRot, 0);
			Vector2d disp = new Vector2d(0, 1);
			e.applyForce(fricForce, disp);
			e.applyForce(fricForce.scale(-1));
		}
		for (RectEntity n : rope.nodes) {
			// gravity
			//n.applyForce(gravity.scaleCpy(n.getMass()));
			// air friction
			n.applyForce(n.getVel().scale(-airFricLinear * n.getVelMag()));
		}

		for (AbstractMovableEntity e : entities) {
			e.update(delta);
		}

		rope.update(delta);

		// collisions
		// TODO restructure to use quadtree
		ListIterator<AbstractMovableEntity> itrA = entities.listIterator();
		while (itrA.hasNext()) {
			AbstractMovableEntity a = itrA.next();
			Iterator<AbstractMovableEntity> itrB = entities.listIterator(itrA
					.nextIndex());
			while (itrB.hasNext()) {
				AbstractMovableEntity b = itrB.next();
				if (b.intersects(a)) {
					// collision happened
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
			/*
			 * if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) { rope.addNode();
			 * nodeChangeCooldown = nodeChangeCooldownNum; } else if
			 * (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) { rope.removeNode();
			 * nodeChangeCooldown = nodeChangeCooldownNum; }
			 */
		}
		Vector2d propulsion = new Vector2d(magX, magY);
		unit.applyForce(propulsion);

	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		// TODO need to add z-layering
		for (AbstractMovableEntity e : entities) {
			e.draw();
		}
		rope.draw();
	}

	private long lastFrame;

	private void setUpTimer() {
		lastFrame = getTime();
	}

	private void setUpEntities() {
		// TODO move entities container toward a collection of collections of
		// entities? this would better support things like the rope that need to
		// manage their elements and add/subtract.
		entities = new ArrayList<AbstractMovableEntity>();
		unit = new TestObject(WIDTH / 2, HEIGHT / 4, 40, 10, 5000, 1000000);

		FixedPlatform anchorA = new FixedPlatform(WIDTH / 2, 4 * HEIGHT / 5,
				50, 20);
		RectEntity anchorB = new RectEntity(WIDTH / 2, HEIGHT / 2, 30, 20,
				1000, 10000);
		/*
		 * rope = new Rope(10, new RectEntity(WIDTH / 2, HEIGHT / 2, 30, 30,
		 * 10000, 100000), new Vector2d(15, 0), new FixedPlatform( WIDTH / 2, 4
		 * * HEIGHT / 5, 20, 20), new Vector2d(0, -10));
		 */

		rope = new RopeV2(75, anchorA, new Vector2d(0, 0), unit, new Vector2d(10, 0));

		entities.add(anchorA);
		entities.add(unit);
		entities.add(new FixedPlatform(10, HEIGHT / 2, 20, HEIGHT));
		entities.add(new FixedPlatform(WIDTH - 10, HEIGHT / 2, 20, HEIGHT));
		entities.add(new FixedPlatform(WIDTH / 2, 10, WIDTH, 20));
		entities.add(new FixedPlatform(WIDTH / 2, HEIGHT - 10, WIDTH, 20));
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
