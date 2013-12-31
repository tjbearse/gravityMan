package gravityMan.entities.abstractEntities;


public interface Entity {
		public void draw();
		public void update(int delta);
		
		public void setLocation(double x, double y);
		public void setX(double x);
		public void setY(double y);
		
		public double getX();
		public double getY();
		//TODO needs updating / is needed?
}
