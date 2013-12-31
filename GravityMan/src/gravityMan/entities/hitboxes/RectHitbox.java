package gravityMan.entities.hitboxes;

import gravityMan.entities.hitboxes.SAT.Range;
import gravityMan.util.Matrix;
import gravityMan.util.Vector2d;

public class RectHitbox extends Hitbox {
	double width, height;

	public RectHitbox(double x, double y, double width, double height) {
		pos = new Vector2d(x, y);
		this.width = width;
		this.height = height;
	}

	@Override
	public Vector2d[] getFaces() {
		// TODO can save on reproducing multiple times each cycle?
		Matrix m = Matrix.rotationMatrix(theta);
		Vector2d[] faces = new Vector2d[2];
		faces[0] = m.multiply(new Vector2d(0, 1));
		faces[1] = m.multiply(new Vector2d(1, 0));
		return faces;
	}

	@Override
	public Range getProjRange(Matrix proj) {
		// TODO make matrix to combine rotate, trans, proj (affine?)
		Matrix rot = Matrix.rotationMatrix(theta);
		
		// top left
		Vector2d vec = rot.multiply(new Vector2d(-width / 2, height / 2));
		vec.add(pos);
		vec = proj.multiply(vec);
		int sign = vec.getAngleRad() > Math.PI ? 1 : -1;
		Range r = new Range(vec.getMag() * sign, vec.getMag() * sign);

		// top right
		vec = rot.multiply(new Vector2d(width / 2, height / 2));
		vec.add(pos);
		vec = proj.multiply(vec);
		sign = vec.getAngleRad() > Math.PI ? 1 : -1;
		r.add(vec.getMag() * sign);

		// bottom right
		vec = rot.multiply(new Vector2d(width / 2, -height / 2));
		vec.add(pos);
		vec = proj.multiply(vec);
		sign = vec.getAngleRad() > Math.PI ? 1 : -1;
		r.add(vec.getMag() * sign);

		// bottom left
		vec = rot.multiply(new Vector2d(-width / 2, -height / 2));
		vec.add(pos);
		vec = proj.multiply(vec);
		sign = vec.getAngleRad() > Math.PI ? 1 : -1;
		r.add(vec.getMag() * sign);
		return r;
	}
}
