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

public class ObeliskScene {
	int grass;
	public ObeliskScene(GL2 gl){
		
	}
	 
	public void draw(GL2 gl, GLU glu,GLUquadric shape){
		//Make a collection of methods that draws each individual piece of the scene
		ground(gl);
		//obelisk(gl);
		
		gl.glTranslated(10, 0, 10);
		pool(gl, glu, shape);
		gl.glTranslated(10, 0, -10);
		
		
	}
	
	public void pool(GL2 gl, GLU glu,GLUquadric shape){
		
		gl.glRotated(-90, 1, 0, 0);
		
		MyAPI.setColor(gl, Color.LIGHT_GRAY);
		gl.glTranslated(0,0,.9);
		glu.gluDisk(shape, 0, 3.9, 32, 32);
		gl.glTranslated(0,0,-.9);
		
		MyAPI.setColor(gl, Color.BLUE);
		glu.gluCylinder(shape, 4, 4, 1, 32, 32);
		gl.glRotated(90, 1, 0, 0);
		
		
	}
	
	
	
	
	public void obelisk(GL2 gl){
		
	}
	
	public void ground(GL2 gl){
		 MyAPI.setColor(gl, Color.GREEN);
	        gl.glBegin(GL2.GL_QUADS);
	        	gl.glVertex3d(20,0,20);
	        	gl.glVertex3d(20,0,-20);
	        	gl.glVertex3d(-20,0,-20);
	        	gl.glVertex3d(-20,0,20);
	        gl.glEnd();
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
