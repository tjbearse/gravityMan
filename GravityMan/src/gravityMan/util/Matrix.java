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
		double e = a * vec.getX() + c * vec.getY();
		double f = b * vec.getX() + d * vec.getY();
		return new Vector2d(e, f);

	}

	public Matrix multiply(Matrix other) {
		double i = a * other.a + c * other.b;
		double j = b * other.a + d * other.b;
		double k = a * other.c + c * other.d;
		double l = b * other.c + d * other.d;
		return new Matrix(i, j, k, l);
	}

}
