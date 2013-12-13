package gravityMan.entities;

public interface MovableEntity extends Entity {
	double getVelX();
	double getVelY();
	double getVelMag();
	double getVelAngleRad();
	
	void setVelX(double val);
	void setVelY(double val);
	void setVelAngleRad(double theta);
	void scaleVel(double factor);
	
	void setTheta(double theta);
	double getTheta();
}
