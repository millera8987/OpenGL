package openGL;

import java.awt.Color;
import java.nio.DoubleBuffer;

import javax.media.opengl.GL2;

import myAPI.MyAPI;

public class WireCube {
	double phi = (1 + Math.sqrt(5))/2.0;
	double oneOverPhi = 1 / phi;
	double angle = 0;
	
	public WireCube(){
		
	}
	
	public void increaseAngle(){
		angle+=0.01;
	}
	public void draw(GL2 gl){
		MyAPI.setColor(gl, Color.WHITE);
		side1(gl);
		
		MyAPI.setColor(gl, Color.RED);
		side2(gl);
		
		


	}
	
	public void side2(GL2 gl){
		
		
		double x1 = 0;
		double y1 = -this.oneOverPhi;
		double z1 = this.phi;
		
		double x2 = 0;
		double y2 = this.oneOverPhi;
		double z2 = this.phi;
		
		double x3 = 1;
		double y3 = 1;
		double z3 = 1;
		
		double x4 = this.phi;
		double y4 = 0;
		double z4 = this.oneOverPhi;
		
		double x5 = 1;
		double y5 = -1;
		double z5 = 1;
		
				
		
		gl.glBegin(GL2.GL_POLYGON);
			double[] vector = {1,1,1};
			gl.glVertex3dv(vector,0);
			gl.glVertex3d(x1,y1,z1);
			gl.glVertex3d(x2,y2,z2);
			gl.glVertex3d(x3,y3,z3);
			gl.glVertex3d(x4,y4,z4);
			gl.glVertex3d(x5,y5,z5);
			
		
		gl.glEnd();
	}
	public void side1(GL2 gl){
		double x1 = 0;
		double y1 = -this.oneOverPhi;
		double z1 = this.phi;
		
		double x2 = 0;
		double y2 = this.oneOverPhi;
		double z2 = this.phi;
		
		double x3 = 1;
		double y3 = 1;
		double z3 = 1;
		
		double x4 = this.phi;
		double y4 = 0;
		double z4 = this.oneOverPhi;
		
		double x5 = 1;
		double y5 = -1;
		double z5 = 1;
		
				
		
		gl.glBegin(GL2.GL_POLYGON);
		
			gl.glVertex3d(x1,y1,z1);
			gl.glVertex3d(x2,y2,z2);
			gl.glVertex3d(x3,y3,z3);
			gl.glVertex3d(x4,y4,z4);
			gl.glVertex3d(x5,y5,z5);
			
		
		gl.glEnd();
	}
	public void cube(GL2 gl){
		MyAPI.setColor(gl, Color.WHITE);
		
		gl.glBegin(GL2.GL_LINES);
		//front face
		
			//top line
			gl.glVertex3d(-1,1,1);
			gl.glVertex3d(1,1,1);
			
			//bottom line
			gl.glVertex3d(-1,-1,1);
			gl.glVertex3d(1,-1,1);
			
			//left line
			gl.glVertex3d(-1,1,1);
			gl.glVertex3d(-1,-1,1);
			
			//right line
			gl.glVertex3d(1,1,1);
			gl.glVertex3d(1,-1,1);
		
		//back face
			//top line
			gl.glVertex3d(-1,1,-1);
			gl.glVertex3d(1,1,-1);
			
			//bottom line
			gl.glVertex3d(-1,-1,-1);
			gl.glVertex3d(1,-1,-1);
			
			//left line
			gl.glVertex3d(-1,1,-1);
			gl.glVertex3d(-1,-1,-1);
			
			//right line
			gl.glVertex3d(1,1,-1);
			gl.glVertex3d(1,-1,-1);
		
		//4 connecting lines
			//upper right line
			gl.glVertex3d(1,1,1);
			gl.glVertex3d(1,1,-1);
			
			//upper left line
			gl.glVertex3d(-1,1,1);
			gl.glVertex3d(-1,1,-1);
			
			//lower right line
			gl.glVertex3d(1,-1,1);
			gl.glVertex3d(1,-1,-1);
			
			//lower left line
			gl.glVertex3d(-1,-1,1);
			gl.glVertex3d(-1,-1,-1);
		
		gl.glEnd();
		
		
		//triangle pyramid
		this.pyramid(gl);
		this.quadBase(gl);
		
		
	}
	
	 public void pyramid(GL2 gl){
	    	//front
		 MyAPI.setColor(gl, Color.MAGENTA);
	    	gl.glBegin(GL2.GL_TRIANGLES);
	    		gl.glVertex3d(0.5,-0.5,0.5);
	    		gl.glVertex3d(0.0,0.5,0.0);
	    		gl.glVertex3d(-0.5,-0.5,0.5);
	    	gl.glEnd();
	    	MyAPI.setColor(gl, Color.LIGHT_GRAY);
	    	gl.glBegin(GL2.GL_TRIANGLES);
	    		gl.glVertex3d(0.5,-0.5,-0.5);
	    		gl.glVertex3d(0.0,0.5,0.0);
	    		gl.glVertex3d(-0.5,-0.5,-0.5);
	    	gl.glEnd();
	    	MyAPI.setColor(gl, Color.YELLOW);
	    	gl.glBegin(GL2.GL_TRIANGLES);
	    		gl.glVertex3d(0.5,-0.5,-0.5);
	    		gl.glVertex3d(0.0,0.5,0.0);
	    		gl.glVertex3d(0.5,-0.5,0.5);
	    	gl.glEnd();
	    	MyAPI.setColor(gl, Color.ORANGE);
	    	gl.glBegin(GL2.GL_TRIANGLES);
	    		gl.glVertex3d(-0.5,-0.5,-0.5);
	    		gl.glVertex3d(-0.0,0.5,0.0);
	    		gl.glVertex3d(-0.5,-0.5,0.5);
	    	gl.glEnd();
	    	
	    }
	    
	    public void quadBase(GL2 gl){
	    	
	    	//front
	    	MyAPI.setColor(gl, Color.RED);
	    	gl.glBegin(GL2.GL_QUADS);
	    		gl.glVertex3d(0.5,-1,0.5);
	    		gl.glVertex3d(0.5,-0.5,0.5);
	    		gl.glVertex3d(-0.5,-0.5,0.5);
	    		gl.glVertex3d(-0.5,-1,0.5);
	    	gl.glEnd();
	    	
	    	MyAPI.setColor(gl, Color.CYAN);
	    	gl.glBegin(GL2.GL_QUADS);
	    		gl.glVertex3d(0.5,-1,-0.5);
	    		gl.glVertex3d(0.5,-0.5,-0.5);
	    		gl.glVertex3d(-0.5,-0.5,-0.5);
	    		gl.glVertex3d(-0.5,-1,-0.5);
	    	gl.glEnd();
	    	
	    	MyAPI.setColor(gl, Color.BLUE);
	    	gl.glBegin(GL2.GL_QUADS);
	    		gl.glVertex3d(0.5,-1,0.5);
	    		gl.glVertex3d(0.5,-1,-0.5);
	    		gl.glVertex3d(0.5,-0.5,-0.5);
	    		gl.glVertex3d(0.5,-0.5,0.5);    		
	    	gl.glEnd();
	    	MyAPI.setColor(gl, Color.GREEN);
	    	gl.glBegin(GL2.GL_QUADS);
	    		gl.glVertex3d(-0.5,-1,0.5);
	    		gl.glVertex3d(-0.5,-1,-0.5);
	    		gl.glVertex3d(-0.5,-0.5,-0.5);
	    		gl.glVertex3d(-0.5,-0.5,0.5);    		
	    	gl.glEnd();
	    }
	

}
