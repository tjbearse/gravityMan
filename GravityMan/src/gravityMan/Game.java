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

	private TestObject unit;
	private Rope rope;
	
	private double unitProp = .3;
	private double unitTurnProp = .01;
	private int unitCollision = 0;

	private double airFriction = .01;
	private Vector2d gravity = new Vector2d(0, -.098);
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
			Display.sync(60);

			if (Display.isCloseRequested()) {
				isRunning = false;
			}
		}
		Display.destroy();
	}

	private void logic(int delta) {
		unit.update(delta);
		rope.update(delta);
		for(RopeNode n : rope.nodes){
			n.applyForce(gravity);
			n.applyForce(n.getVel().scaleCpy(-airFriction));
		}
	}
	

	private void input() {
		double magX;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			magX = unitProp;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			magX = -unitProp;
		} else {
			magX = 0;
		}
		
		double magY;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			magY = unitProp;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			magY = -unitProp;
		} else {
			magY = 0;
		}
		Vector2d propulsion = new Vector2d(magY, magX);
		
		rope.anchor.setVel(propulsion);

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
		unit = new TestObject(WIDTH / 4, HEIGHT / 4, 15, 10, 10);
		rope = new Rope(WIDTH/2, 3*HEIGHT/4, 6);
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
