package openGL;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import myAPI.MyAPI;

public class ExampleQuadrics {
	
	int grass;
	int sky;
	
	public ExampleQuadrics(GL2 gl){
		grass = getMyTextures(gl,"images/grass.jpg");
		sky = getMyTextures(gl,"images/sky.png");
	}
	
	public void draw(GL2 gl, GLU glu,GLUquadric shape){
		ground(gl);
		sampleSphere(gl,glu,shape);
		sampleCylinder(gl,glu,shape);
	}
	
	public void ground(GL2 gl){
		 MyAPI.setColor(gl, Color.WHITE);
		 gl.glBindTexture(GL2.GL_TEXTURE_2D, grass);   
		  gl.glBegin(GL2.GL_QUADS);
		        gl.glTexCoord2d(10,0);gl.glVertex3d(20,0,20);
		        gl.glTexCoord2d(10,10);gl.glVertex3d(20,0,-20);
		        gl.glTexCoord2d(0,10);gl.glVertex3d(-20,0,-20);
		        gl.glTexCoord2d(0,0);gl.glVertex3d(-20,0,20);
	        gl.glEnd();
	        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//NO MAPPING  
	        
	}

	
	
	public void sampleCylinder(GL2 gl, GLU glu,GLUquadric shape){
		MyAPI.setColor(gl, Color.YELLOW);
		gl.glTranslated(-8,0,-10);
		gl.glRotated(-90,1,0,0);
		glu.gluCylinder(shape, 0.5, 0.5, 5, 32, 32);
		gl.glRotated(90,1,0,0);
		gl.glTranslated(8,0,10);
	}
	
	public void sampleSphere(GL2 gl, GLU glu,GLUquadric shape){
		MyAPI.setColor(gl, Color.WHITE);
		//warning: not x,y,z position, by default it draws at the origin
		gl.glTranslated(5, 2, -7);
		 gl.glBindTexture(GL2.GL_TEXTURE_2D, sky);   
		glu.gluSphere(shape, 50, 32, 32);
		 gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);   
		//undo translate
		gl.glTranslated(-5, -2, 7);
	}
	public int getMyTextures(GL2 gl, String filename) {
		Texture t = null;
		try {
			URL textureURL;
			textureURL = getClass().getResource(filename);
			BufferedImage img = ImageIO.read(textureURL);
			ImageUtil.flipImageVertically(img);
			t = AWTTextureIO.newTexture(gl.getGLProfile(), img, true);
			t.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
			t.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
			t.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
			t.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		} catch (IOException exc) {
			exc.printStackTrace();
			System.exit(1); // program will exit when your file not found
		}
		int anyInt = t.getTextureObject(gl);
		return anyInt;
	}
}
