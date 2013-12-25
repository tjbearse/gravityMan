package gravityMan.util;

public class Matrix {
	protected double a, b, c, d;

	public Matrix() {
		a = 0; // upperLeft
		b = 0; // lowerLeft
		c = 0; // upperRight
		d = 0; // lowerRight

	}

	public Matrix(double upLeft, double downLeft, double upRight,
			double downRight) {
		this.a = upLeft;
		this.b = downLeft;
		this.c = upRight;
		this.d = downRight;
	}

	public void matrixScaler(double factor) {
		a *= factor;
		b *= factor;
		c *= factor;
		d *= factor;
	}

	public Matrix copyScaledMartrix(double factor) {
		return new Matrix(a * factor, b * factor, c * factor, d * factor);
	}

	public Vector2d multiply(Vector2d vec) {
		double e = a * vec.getX() + c * vec.getY(); //top 
		double f = b * vec.getX() + d * vec.getY(); //bottom
		return new Vector2d(e, f);

	}

	public Matrix multiply(Matrix other) {
		double i = a * other.a + c * other.b; //upperLeft
		double j = b * other.a + d * other.b; //lowerLeft
		double k = a * other.c + c * other.d; //upperRight
		double l = b * other.c + d * other.d; //lowerRight
		return new Matrix(i, j, k, l);
	}
	
	public static Matrix rotationMatrix(double theta){
		double m = Math.cos(theta); //upperLeft
		double n = -Math.sin(theta); //lowerLeft
		double o = Math.sin(theta); //upperRight
		double p = Math.cos(theta); //lowerRight
		return new Matrix(m, n, o, p);
	}
	
	public static Matrix projectionMatrix(Vector2d v){
		double q = (1/(v.getMag()* v.getMag())) * (v.getX() * v.getX()); //upperLeft
		double r = (1/(v.getMag()* v.getMag())) * (v.getX() * v.getY()); //lowerLeft
		double s = (1/(v.getMag()* v.getMag())) * (v.getX() * v.getY());  //upperRight
		double t = (1/(v.getMag()* v.getMag())) * (v.getY() * v.getY()); //lowerRight
		return new Matrix(q, r, s, t);
	}

}
