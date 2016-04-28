package openGL;

import java.awt.Color;

import javax.media.opengl.GL2;

import myAPI.MyAPI;

public class CoordinatePlane {
	
	
	
	public CoordinatePlane(){
		
	}
	
	
	public void draw(GL2 gl){
		
	
		gl.glBegin(GL2.GL_LINES);
		//xaxis
		MyAPI.setColor(gl, Color.GREEN);
		gl.glVertex3d(1, 0, 0);
		gl.glVertex3d(-1, 0, 0);
		
		//yaxis
		MyAPI.setColor(gl, Color.RED);
		gl.glVertex3d(0, 1, 0);
		gl.glVertex3d(0, -1, 0);
		
		//zaxis
		MyAPI.setColor(gl, Color.BLUE);
		gl.glVertex3d(0, 0, 1);
		gl.glVertex3d(0, 0, -1);
		
		
		
		gl.glEnd();
	}

}
