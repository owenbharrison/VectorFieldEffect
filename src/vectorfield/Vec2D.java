package vectorfield;

class Vec2D {
  public float x;
  public float y;
  
  public Vec2D(float x_, float y_) {
  	x = x_;
  	y = y_;
  }
  
  public void add(Vec2D v) {
  	x += v.x;
  	y += v.y;
  }
  
  public void sub(Vec2D v) {
  	x += v.x;
  	y += v.y;
  }
  
  public void mult(float v) {
  	x *= v;
  	y *= v;
  }
  
  public void div(float v) {
  	x /= v;
  	y /= v;
  }
  
  public float mag() {
  	return (float) processing.core.PApplet.sqrt(x*x+y*y);
  }
  
  Vec2D copy() {
  	return new Vec2D(x, y);
  }
  
  public String toString() {
  	return "["+x+", "+y+"]";
  }
  
  public void normalize() {
    float m = mag();
    if(m>0)div(m);
  }
  
  public void limit(float f) {
  	if(mag()>f) {
  		normalize();
  		mult(f);
  	}
  }
}
