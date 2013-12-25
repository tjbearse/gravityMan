package gravityMan;

import gravityMan.abstractEntities.entities.TestObject;
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

	private double unitProp = .3;
	private double unitTurnProp = .01;
	private int unitCollision = 0;

	private double airFriction = .4;
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
		unit.update(delta);
		unit.applyForce(gravity.scaleCpy(unit.getMass()));
		unit.applyForce(unit.getVel().scale(-airFriction * unit.getVelMag()));

		rope.update(delta);
		for (RopeNode n : rope.nodes) {
			n.applyForce(gravity.scaleCpy(n.getMass()));
			n.applyForce(n.getVel().scale(-airFriction * n.getVelMag()));
			n.applyForce(n.getVel().scaleCpy(-airFriction));
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
		// unit.applyForce(propulsion);
		rope.anchorA.setVel(propulsion);

	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		unit.draw();
		rope.draw();
	}

	private long lastFrame;

	private void setUpTimer() {
		lastFrame = getTime();
	}

	private void setUpEntities() {
		unit = new TestObject(WIDTH / 2, 3 * HEIGHT / 4, 15, 10, 5000);
		rope = new Rope(WIDTH / 2, 3 * HEIGHT / 4, 10);
		rope.attachB(unit);
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
