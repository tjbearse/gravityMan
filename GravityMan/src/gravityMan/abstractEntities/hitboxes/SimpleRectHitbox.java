package gravityMan.abstractEntities.hitboxes;

import gravityMan.abstractEntities.hitboxes.SAT.Range;
import gravityMan.util.Matrix;
import gravityMan.util.Vector2d;

public class SimpleRectHitbox extends Hitbox {
	double width, height;

	public SimpleRectHitbox(double x, double y, double width, double height) {
		pos = new Vector2d(x, y);
		this.width = width;
		this.height = height;
	}

	@Override
	public Vector2d[] getFaces() {
		Vector2d[] faces = new Vector2d[2];
		faces[0] = new Vector2d(0, 1);
		faces[1] = new Vector2d(1, 0);
		return faces;
	}

	@Override
	public Range getProjRange(Matrix proj) {
		// top left
		Vector2d vec = new Vector2d(pos.getX() - width / 2, pos.getY() + height
				/ 2);
		Vector2d projVec = proj.multiply(vec);
		Range r = new Range(projVec.getMag(), projVec.getMag());

		// top right
		vec.setX(pos.getX() + width / 2);
		projVec = proj.multiply(vec);
		r.add(projVec.getMag());

		// bottom right
		vec.setY(pos.getY() - height / 2);
		projVec = proj.multiply(vec);
		r.add(projVec.getMag());

		// bottom left
		vec.setX(pos.getX() - width / 2);
		projVec = proj.multiply(vec);
		r.add(projVec.getMag());
		
		return r;
	}
}
