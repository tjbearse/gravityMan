package gravityMan;

import gravityMan.entities.Entity;

import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;

public class Game {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 480;

	private boolean isRunning = true;

	private int unitProp = 1;
	private double unitTurnProp = .01;
	private Entity unit;
	private int unitCollision = 0;

	private double AngFric = .5;

	
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
		
	}

	private void input() {
		int mag;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			mag = unitProp;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			mag = -unitProp;
		} else {
			mag = 0;
		}
		double rotMag;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			rotMag = -unitTurnProp;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			rotMag = unitTurnProp;
		} else {
			rotMag = 0;
		}

	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		unit.draw();
	}

	private long lastFrame;

	private void setUpTimer() {
		lastFrame = getTime();
	}

	private void setUpEntities() {
		//unit = new Entity(WIDTH / 2, HEIGHT / 2, 15, 1);
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
			Display.setTitle("PONG");
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

