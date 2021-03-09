package vectorfield;

import processing.core.*;

public class Main extends PApplet{
	public Vec2D[][] field;
	public int res = 25;
	public int cols, rows;
	public int currI = 0, currJ = 0;
	
	
	public static void main(String[] args) {
		PApplet.main(new String[] {"vectorfield.Main"});
	}
	
	public void settings() {
		size(800, 800);
		cols = width/res+1;
		rows = height/res+1;
	}

	public void setup() {
		field = new Vec2D[cols][rows];
		for(int i=0;i<cols;i++) {
			for(int j=0;j<rows;j++) {
			  field[i][j] = new Vec2D(0, 0);
		  }
		}
	}
	
	public void mousePressed() {
		float record = Float.POSITIVE_INFINITY;
		for(int i=0;i<cols;i++) {
			for(int j=0;j<rows;j++) {
				float d = dist(mouseX, mouseY, i*res, j*res);
				if(d<record) {
					record = d;
					currI = i;
					currJ = j;
				}
			}
    }
	}
	
	public void draw() {
		background(0);
		Vec2D[][] nextField = new Vec2D[cols][rows];
		//update vectorgrid
		for(int i=0;i<cols;i++) {
			for(int j=0;j<rows;j++) {
				Vec2D avgVec = new Vec2D(0, 0);
				int count = 0;
			  for(int i_=-1;i_<=1;i_++) {//loop through all neighbors
			  	for(int j_=-1;j_<=1;j_++) {
			  		int nI = i+i_;//neighbor i
			  		int nJ = j+j_;//neighbor j
				  	if(nI>=0&&nI<=cols-1) {//if i is in bounds of field
				  		if(nJ>=0&&nJ<=rows-1) {//if j is in bounds of field
				  			if(field[i][j]!=field[nI][nJ]) {
				  			  avgVec.add(field[nI][nJ]);
				  			  count++;
				  			}
					  	}
				  	}
				  }
			  }
			  avgVec.div(count+0.005f);
			  nextField[i][j] = avgVec;
			}
		}
		for(int i=0;i<cols;i++) {
			for(int j=0;j<rows;j++) {
		    field[i][j] = nextField[i][j];
			}
		}
		
		Vec2D sum = new Vec2D(0, 0);
		for(int i=0;i<cols;i++) {
			for(int j=0;j<rows;j++) {
				Vec2D curr = field[i][j];
				push();
			  translate(i*res, j*res);
			  stroke(255, map(curr.mag(), 0, res*2, 50, 255));
			  strokeWeight(2);
			  line(0, 0, constrain(curr.x, -res, res), constrain(curr.y, -res, res));
			  pop();
			  sum.add(curr);
		  }
		}
		surface.setTitle("VectorField fps: "+round(frameRate));
		if(mousePressed) {
			float scl = 10;
			float xchange = (mouseX-currI*res)*scl;
			float ychange = (mouseY-currJ*res)*scl;
			field[currI][currJ] = new Vec2D(xchange, ychange);
		}
	}
}
