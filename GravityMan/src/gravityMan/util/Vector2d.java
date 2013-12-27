package gravityMan.util;

public class Vector2d {
	private double x, y;

	public Vector2d(Vector2d v) {
		x = v.x;
		y = v.y;
	}

	public Vector2d() {
		x = 0;
		y = 0;
	}

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// in radians
	public Vector2d(double theta) {
		x = Math.cos(theta);
		y = Math.sin(theta);
	}

	// returns self (for nesting operations)
	// TODO replace unneeded copies with edit same thing, espec. rope + updates
	public Vector2d scale(double factor) {
		double theta = getAngleRad();
		double mag = getMag();
		x = Math.cos(theta) * mag * factor;
		y = Math.sin(theta) * mag * factor;
		return this;
	}

	public Vector2d scaleCpy(double factor) {
		Vector2d vec = new Vector2d(this);
		vec.scale(factor);
		return vec;
	}

	public Vector2d add(Vector2d v) {
		x += v.x;
		y += v.y;
		return this;
	}

	public Vector2d sub(Vector2d v) {
		x -= v.x;
		y -= v.y;
		return this;
	}

	public Vector2d addCpy(Vector2d v) {
		Vector2d vec = new Vector2d(this);
		return vec.add(v);
	}

	public Vector2d subCpy(Vector2d v) {
		Vector2d vec = new Vector2d(this);
		return vec.sub(v);
	}

	public void setAngleRad(double theta) {
		double mag = getMag();
		x = Math.cos(theta) * mag;
		y = Math.sin(theta) * mag;
	}

	private double cleanRad(double theta) {
		theta %= 2 * Math.PI;
		if (theta < 0) {
			theta += 2 * Math.PI;
		}
		return theta;
	}

	public double getAngleDeg() {
		return Math.toDegrees(getAngleRad());
	}

	public double getAngleRad() {
		double val;
		if (x == 0) {
			if (y < 0) {
				val = 3 * Math.PI / 2;
			} else {
				val = Math.PI / 2;
			}
		} else {
			val = Math.atan(y / x);
			if (x < 0) {
				val += Math.PI;
			}
		}
		return cleanRad(val);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getMag() {
		return Math.sqrt(x * x + y * y);
	}

	public void setX(double val) {
		x = val;
	}

	public void setY(double val) {
		y = val;
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void zero() {
		x = 0;
		y = 0;
	}

	public double getComponent(double theta) {
		return this.getMag() * Math.cos(theta);
	}

	public double cross(Vector2d b) {
		double theta = b.getAngleDeg() - this.getAngleDeg();
		return Math.sin(theta) * this.getMag() * b.getMag();
	}

}
