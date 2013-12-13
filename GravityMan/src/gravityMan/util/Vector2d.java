package gravityMan.util;

public class Vector2d  {
	private double x, y;

	
	public Vector2d(Vector2d v){
		x = v.x;
		y = v.y;
	}
	public Vector2d() {
		// TODO Auto-generated constructor stub
	}
	
	Vector2d(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	//in radians
	Vector2d(double theta){
		x = Math.cos(theta);
		y = Math.sin(theta);
	}
	
	
	public void scale(double factor) {
		double theta = getAngleRad();
		double mag = getMag();
		x = Math.cos(theta) * mag * factor;
		y = Math.sin(theta) * mag * factor;
	}
	
	public Vector2d cpScale(double factor) {
		double theta = getAngleRad();
		double mag = getMag();
		return new Vector2d(Math.cos(theta) * mag * factor, Math.sin(theta) * mag * factor);
	}
	

	public void add(Vector2d v) {
		x += v.x;
		y += v.y;
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
	public void setX(double val){
		x = val;
	}
	
	public void setY(double val){
		y = val;
	}
	
	
	public void zero(){
		x = 0;
		y = 0;
	}
}

