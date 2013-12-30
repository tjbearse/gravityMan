package gravityMan.abstractEntities.hitboxes;

import java.util.ArrayList;

import gravityMan.entities.AbstractEntity;
import gravityMan.util.Matrix;
import gravityMan.util.Vector2d;

public final class SAT {

	static public boolean intersects(Hitbox a, Hitbox b) {
		// get faces in form of vectors
		Vector2d[] facesA = a.getFaces();
		Vector2d[] facesB = b.getFaces();
		// eliminate redundant faces (assume there are non inside each array)
		ArrayList<Vector2d> faces = new ArrayList<Vector2d>();
		for (Vector2d faceA : facesA) {
			boolean redundant = false;
			for (Vector2d faceB : facesB) {
				if (redundant(faceA, faceB)) { // FIXME redundant is broken?
					//redundant = true;
				}
			}
			if (!redundant) {
				faces.add(faceA);
			}
		}
		for (Vector2d faceB : facesB) {
			faces.add(faceB);
		}

		// for each face:
		for (Vector2d face : faces) {
			Matrix proj = Matrix.projectionMatrix(face);
			// get projected range for each shape
			Range rangeA = a.getProjRange(proj);
			Range rangeB = b.getProjRange(proj);
			// compare ranges if any gap return false;
			if (!Range.intersects(rangeA, rangeB)) {
				return false;
			}
		}

		return true;
	}

	static private boolean redundant(Vector2d a, Vector2d b) {
		// TODO: double check this method
		// % by PI to account for opposite direction redundancies
		return a.getAngleRad() % Math.PI != b.getAngleDeg() % Math.PI;
	}

	static class Range {
		private double start, end;

		public Range(double a, double b) {
			if (a < b) {
				start = a;
				end = b;
			} else {
				start = b;
				end = a;
			}
		}

		public void add(double val) {
			if (val < start) {
				start = val;
			} else if (val > end) {
				end = val;
			}
		}
		// if a, b, A or b, a, B
		public static boolean intersects(Range a, Range b) {
			return (a.start <= b.start && b.start <= a.end)
					|| (a.start >= b.start && a.start <= b.end);
		}
	}
}
