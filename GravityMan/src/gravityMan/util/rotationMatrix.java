package gravityMan.util;

public class rotationMatrix {
	protected double a, b, c, d, t;
	public rotationMatrix() {
		a = 0;  //upper left
		b = 0;	//lower left
		c = 0;	//upper right
		d = 0;	//lower right
		t = 0;	//theta
		// TODO Auto-generated constructor stub
	}
	
	public rotationMatrix(Matrix original, double theta){
		this.a = a * Math.cos(t);
		this.b = b * Math.sin(t);
		this.c = c * -Math.sin(t);
		this.d = d * Math.cos(t);
		
	}

}
