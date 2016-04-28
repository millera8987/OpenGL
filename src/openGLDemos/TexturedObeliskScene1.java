package openGLDemos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.swing.JFrame;

import myAPI.MyMethods;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
 
public class TexturedObeliskScene1 implements GLEventListener, KeyListener {
 
    static GLU glu = new GLU(); 					//Creates an object for the OpenGL Utility Library
    static GLCanvas canvas = new GLCanvas(); 		// Open GL Rendering Support
    static Frame frame = new Frame("Title"); 		//Creates a window to draw on
    static Animator animator = new Animator(canvas);//Creates our animation loop by repeatedly calling display();
    boolean[] keys = new boolean[256];    			//Storage for keys for user interface.     
    // ************** Declare and initialize variables here *******************
    private GLUquadric quadric;
    double xrot = 0, yrot = 0, zrot = 0;
   
    boolean quadraticView = true;
    
    double yspin = 0;
    double movement = 0.2;//speed of walking
    double heading = 0;//degree at which you are looking
    double xtrans = 0, ytrans = 0, ztrans = 0;
    double xpos = 0, ypos = 1, zpos = 15;
    double sceneroty = 0;
    double lookupdown = 0;
    double PI = 3.14159265;
    double PIOVER180 = PI/180;
    
    
    
    //Texture variables
    final int MAX_TEXTURES_USED = 16;
    int [] texture = new int[MAX_TEXTURES_USED];
    
    int grass;
    
    boolean showExtras = true;
    
    Ball ball;
    double tractorAngle = 0;
    double mill_angle = 0;
    
    
   // *************** End variable declarations and initializations ***********
    public void init(GLAutoDrawable gLDrawable) {
        GL2 gl = gLDrawable.getGL().getGL2();		// Gets an OpenGL drawable area called gl from glDrawable
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);	// Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);	// Black Background
        gl.glClearDepth(1.0f);						// Depth Buffer Setup
        gl.glEnable(GL.GL_DEPTH_TEST);				// Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);				// The Type Of Depth Testing To Do
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // Really Nice Perspective Calculations
        ((Component) gLDrawable).addKeyListener(this); //Adds a listener for the keyboard for user interface
        quadric = glu.gluNewQuadric();
        glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH);
        glu.gluQuadricTexture(quadric, true);
        
        // **** initialize any variables here ****

        
        gl.glEnable(GL.GL_TEXTURE_2D);//enable texture mapping
        
        //Load textures here!
        grass = getMyTextures(gl, "../upImages/upGrass.bmp");
        //texture[0] = getMyTextures(gl, "../upImages/upGrass.bmp");
        //texture[0] = getMyTextures(gl, "images/grass.bmp");
        texture[1] = getMyTextures(gl, "images/trees.bmp");
        texture[2] = getMyTextures(gl, "images/water.bmp");
        texture[3] = getMyTextures(gl, "images/bark.bmp");
        texture[4] = getMyTextures(gl, "images/stone.bmp");
        texture[5] = getMyTextures(gl, "images/bricks.bmp");
        texture[6] = getMyTextures(gl, "images/grid.bmp");
        texture[7] = getMyTextures(gl, "images/pool.bmp");
        texture[8] = getMyTextures(gl, "images/siding.bmp");
        texture[9] = getMyTextures(gl, "images/roof.bmp");
        texture[10] = getMyTextures(gl, "images/sky.bmp");
        /*
        ball.x=-7;
    	ball.y=0;
    	ball.z=11;
    	ball.xadd=0.1;
    	ball.yadd=0.1;
    	ball.zadd=0.1;
    	*/
        ball = new Ball(-7,0,11,0.1,0.1,0.1);
        // **** end initialization of variables ****
       
    }
   
    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2(); // Gets an OpenGL drawable area called gl from glDrawable
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);	// Clear Screen
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);	// Depth Buffer
        gl.glLoadIdentity();				// Reset The Current Modelview Matrix
        xtrans = -xpos;
        ytrans = -ypos;
        ztrans = -zpos;
        sceneroty = 360 - yspin;
        gl.glRotated(lookupdown, 1,0,0);
        gl.glRotated(sceneroty, 0, 1, 0);        
        gl.glTranslated(xtrans,ytrans,ztrans);
        
        gl.glRotated(xrot, 1,0,0);
        gl.glRotated(yrot, 0,1,0);
        gl.glRotated(zrot, 0,0,1);
        coordinatePlane(gl);
        // **** BEGIN DRAWINGS HERE ****
        
        
        
       obelisk(gl);
       scene(gl);
       extras(gl);
        
        // **** END DRAWINGS ****
        
        processKeys();
        
        
        
    }
    
    public void extras(GL2 gl){
    	if(showExtras){
    		ball_bounce(gl);
    		fence(gl);
    		moving_tractor(gl);    		    	
    		wind_mill(gl);
    		
    	}
    }
    
    void fan(GL2 gl)
    {
    	double mscale = 1;
    	//glColor3dv(red);
    	this.setColor(gl, Color.RED);
    	gl.glBegin(GL2.GL_LINES);
    	gl.glVertex3d(2*mscale,0,0);
    	gl.glVertex3d(0,0,0);		
    	gl.glEnd();
    	//glColor3dv(dgreen);
    	this.setColor(gl, Color.DARK_GRAY);
    	gl.glBegin(GL2.GL_QUADS);
    	gl.glVertex3d(2*mscale,0,0);
    	gl.glVertex3d(2*mscale,0.3*mscale,0);
    	gl.glVertex3d(.2*mscale,0.3*mscale,0);
    	gl.glVertex3d(0.2*mscale,0,0);
    	gl.glEnd();

    }

    void wind_mill(GL2 gl)
    {
    	  		

		
    	gl.glTranslated(0, 12, 1);
    	gl.glRotated(mill_angle, 0, 0, 1);  
    	fan(gl);
    	gl.glRotated(90,0,0,1);
    		fan(gl);
    		gl.glRotated(-90,0,0,1);
    		gl.glRotated(180,0,0,1);
    		fan(gl);
    		gl.glRotated(-180,0,0,1);
    		gl.glRotated(-90,0,0,1);
    		fan(gl);
    		gl.glRotated(90,0,0,1);
    		gl.glRotated(-mill_angle, 0, 0, 1);
    		mill_angle+=5;
    		gl.glTranslated(0, -12, -1);
    }

    void tractor(GL2 gl)
    {
    	//glColor3dv(green);
    	this.setColor(gl, Color.GREEN);
    	glu.gluCylinder(quadric, 0.5,0.5,2,32,32);
    	this.setColor(gl, Color.BLACK);
    	gl.glTranslated(0.5,-0.3,0);
    	gl.glRotated(90,0,1,0);
    	glu.gluDisk(quadric, 0,0.8,32,32);
    	gl.glRotated(-90,0,1,0);
    	gl.glTranslated(-0.5,0.3,0);
    	
    	gl.glTranslated(-0.5,-0.3,0);
    	gl.glRotated(90,0,1,0);
    	glu.gluDisk(quadric, 0,0.8,32,32);
    	gl.glRotated(-90,0,1,0);
    	gl.glTranslated(0.5,0.3,0);

    	gl.glTranslated(0.3,-0.7,2);
    	gl.glRotated(90,0,1,0);
    	glu.gluDisk(quadric, 0,0.4,32,32);
    	gl.glRotated(-90,0,1,0);
    	gl.glTranslated(-0.3,0.7,-2);

    	gl.glTranslated(-0.3,-0.7,2);
    	gl.glRotated(90,0,1,0);
    	glu.gluDisk(quadric, 0,0.4,32,32);
    	gl.glRotated(-90,0,1,0);
    	gl.glTranslated(0.3,0.7,-2);
    }

    void fence_piece(GL2 gl)
    {
    	//length = 4
//    	glTranslated(0,0.15,0);

    	gl.glTranslated(0,0.4,0);
    	glu.gluCylinder(quadric,0.1,0.1,4,32,32);
    	gl.glTranslated(0,-0.4,0);

    	gl.glTranslated(0,.8,0);
    	glu.gluCylinder(quadric,0.1,0.1,4,32,32);
    	gl.glTranslated(0,-0.8,0);
    	
    	gl.glTranslated(0,1.2,0);
    	glu.gluCylinder(quadric,0.1,0.1,4,32,32);
    	gl.glTranslated(0,-1.2,0);

    	gl.glRotated(-90,1,0,0);
    	glu.gluCylinder(quadric,0.1,0.1,1.5,32,32);
    	gl.glRotated(90,1,0,0);

    	//glTranslated(0,-0.15,0);
    }

    void fence(GL2 gl)
    {
    	this.setColor(gl, Color.WHITE);
    	//glColor3dv(white);
    		for (int xx = -4;xx>=-16;xx-=12)
    	{
    		for (int zz = 6;zz<=10;zz+=4)
    		{
    			gl.glTranslated(xx,-1,zz);
    			fence_piece(gl);
    			gl.glTranslated(-xx,1,-zz);
    		}//next zz
    	}//next xx
    	for(int zz = 6;zz<=14;zz+=8)
    	{
    	for (int xx=-16;xx<=-8;xx+=4)
    	{
    		gl.glTranslated(xx,-1,zz);
    		gl.glRotated(90,0,1,0);
    		fence_piece(gl);
    		gl.glRotated(-90,0,1,0);
    		gl.glTranslated(-xx,1,-zz);
    	}
    	}
    }

    void ball_bounce(GL2 gl)
    {
    	this.setColor(gl, Color.RED);
    	gl.glTranslated(ball.x,ball.y,ball.z);
    	glu.gluSphere(quadric,0.2,32,32);
    	gl.glTranslated(-ball.x,-ball.y,-ball.z);
    	ball.x+=ball.xadd;
    	//ball.y+=ball.yadd;
    	ball.z+=ball.zadd;

    	if (ball.x + ball.xadd > -4 || ball.x + ball.xadd < -16) 
    	{
    		ball.xadd = -ball.xadd;
    	}
    	if (ball.z + ball.zadd < 6 || ball.z + ball.zadd > 14) 
    	{
    		ball.zadd = -ball.zadd;
    	}
    }

    void moving_tractor(GL2 gl)
    {
    	gl.glRotated(tractorAngle,0,1,0);
    	gl.glTranslated(40,0,0);
    	tractor(gl);
    	gl.glTranslated(-40,0,0);
    	gl.glRotated(-tractorAngle,0,1,0);
    	tractorAngle-=1;
    }
    
    public void obelisk(GL2 gl){
    	//obelisk
    	
    	this.setColor(gl, Color.WHITE);
    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[4]);//obelisk
    	int xtile = 5;
    	int ytile = 5;
    	gl.glBegin(GL2.GL_QUAD_STRIP);
    	gl.glTexCoord2d(0,0);gl.glVertex3d(2,-1,2);//bottom right front
    	gl.glTexCoord2d(0,ytile);gl.glVertex3d(1,10,1);//top right front
    	gl.glTexCoord2d(xtile,0);gl.glVertex3d(2,-1,-2);//botton right back
    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(1,10,-1);//top right back

    	gl.glTexCoord2d(0,0);gl.glVertex3d(-2,-1,-2);
    	gl.glTexCoord2d(0,ytile);gl.glVertex3d(-1,10,-1);

    	gl.glTexCoord2d(xtile,0);gl.glVertex3d(-2,-1,2);
    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(-1,10,1);

    	gl.glTexCoord2d(0,0);gl.glVertex3d(2,-1,2);
    	gl.glTexCoord2d(0,ytile);gl.glVertex3d(1,10,1);
    	gl.glEnd();
    	
    	gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//grass
    	
    	this.setColor(gl, Color.GRAY);
    	gl.glBegin(GL2.GL_TRIANGLE_FAN);
	    	gl.glVertex3d(0,12,0);	
	    	gl.glVertex3d(1,10,1);
	    	gl.glVertex3d(1,10,-1);
	    	gl.glVertex3d(-1,10,-1);
	    	gl.glVertex3d(-1,10,1);
	    	gl.glVertex3d(1,10,1);
	    gl.glEnd();
	    
	    this.setColor(gl, Color.BLACK);
    	gl.glBegin(GL2.GL_LINES);
    	gl.glVertex3d(2,-1,2);
    		gl.glVertex3d(1,10,1);
    		gl.glVertex3d(2,-1,-2);
    		gl.glVertex3d(1,10,-1);

    		gl.glVertex3d(-2,-1,-2);
    		gl.glVertex3d(-1,10,-1);

    		gl.glVertex3d(-2,-1,2);
    		gl.glVertex3d(-1,10,1);

    		gl.glVertex3d(2,-1,2);
    		gl.glVertex3d(1,10,1);
    	gl.glEnd();    
    	gl.glBegin(GL2.GL_LINES);
	    	gl.glVertex3d(0,12,0);	
	    	gl.glVertex3d(1,10,1);
	    	gl.glVertex3d(0,12,0);
	    	gl.glVertex3d(1,10,-1);
	    	gl.glVertex3d(0,12,0);
	    	gl.glVertex3d(-1,10,-1);
	    	gl.glVertex3d(0,12,0);
	    	gl.glVertex3d(-1,10,1);
	    gl.glEnd();
    	gl.glBegin(GL2.GL_LINE_LOOP);
	    	gl.glVertex3d(1,10,1);
	    	gl.glVertex3d(1,10,-1);
	    	gl.glVertex3d(-1,10,-1);
	    	gl.glVertex3d(-1,10,1);
	    	gl.glVertex3d(1,10,1);
	    gl.glEnd();
	    
	    
    }
    
    public void scene(GL2 gl){
    	if(quadraticView)
    	{
    	


    	
    		//glColor3dv(white);
    		this.setColor(gl, Color.WHITE);

    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[10]);//grass
    		
    		gl.glRotated(-90,1,0,0);
    		glu.gluSphere(quadric,50,32,32);
    		gl.glRotated(90,1,0,0);
    		
    		int ground_size = 50;
    		this.setColor(gl, Color.WHITE);
    		//gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[0]);//grass
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, grass);//grass
    		gl.glBegin(GL2.GL_QUADS);
    		gl.glTexCoord2d(ground_size,0);gl.glVertex3d(ground_size,-1,ground_size);
    		gl.glTexCoord2d(ground_size,ground_size);gl.glVertex3d(ground_size,-1,-ground_size);
    		gl.glTexCoord2d(0,ground_size);gl.glVertex3d(-ground_size,-1,-ground_size);
    		gl.glTexCoord2d(0,0);gl.glVertex3d(-ground_size,-1,ground_size);
    		gl.glEnd();
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//no map
    /*		
    		this.setColor(gl, Color.WHITE);
    		cube(gl,-15,3-1,-15,3);

    		gl.glTranslated(-15,9,-15);
    		this.setColor(gl, Color.WHITE);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[9]);//roof
    		gl.glRotated(90,1,0,0);
    		glu.gluCylinder(quadric,0.0f,7.0f,6.0f,32,32);	// Draw Our Cylinder
    		gl.glRotated(-90,1,0,0);
    		gl.glTranslated(15,-9,15);
*/
        	this.setColor(gl, Color.WHITE);
        	//cube(gl, -15,3-1,-15,3);
        	//house base
        	gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[8]);
        	gl.glTranslated(-15,-1,-15);
        	gl.glRotated(-90,1,0,0);
        	glu.gluCylinder(quadric,4.5f,4.5f,6.0f,32,32);	// Draw Our Cylinder
        	gl.glRotated(90,1,0,0);
        	gl.glTranslated(15,1,15);
        	gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
        	
        	
        	gl.glTranslated(-15,9,-15);
        	//gl.glColor3dv(brown);	
        	//roof
        	this.setColor(gl, Color.WHITE);
        	gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[9]);//roof
        	gl.glRotated(90,1,0,0);
        	glu.gluCylinder(quadric,0.0f,7.0f,6.0f,32,32);	// Draw Our Cylinder
        	gl.glRotated(-90,1,0,0);
        	gl.glTranslated(15,-9,15);
        	gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//roof
    		this.setColor(gl, Color.WHITE);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[4]);//obelisk
    		int xtile = 5;
    		int ytile = 5;
    		gl.glBegin(GL2.GL_QUAD_STRIP);
    		gl.glTexCoord2d(0,0);gl.glVertex3d(2,-1,2);//bottom right front
    		gl.glTexCoord2d(0,ytile);gl.glVertex3d(1,10,1);//top right front
    		gl.glTexCoord2d(xtile,0);gl.glVertex3d(2,-1,-2);//botton right back
    		gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(1,10,-1);//top right back

    		gl.glTexCoord2d(0,0);gl.glVertex3d(-2,-1,-2);
    		gl.glTexCoord2d(0,ytile);gl.glVertex3d(-1,10,-1);

    		gl.glTexCoord2d(xtile,0);gl.glVertex3d(-2,-1,2);
    		gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(-1,10,1);

    		gl.glTexCoord2d(0,0);gl.glVertex3d(2,-1,2);
    		gl.glTexCoord2d(0,ytile);gl.glVertex3d(1,10,1);
    		gl.glEnd();
    		this.setColor(gl, Color.DARK_GRAY);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//grass
    		gl.glBegin(GL2.GL_TRIANGLE_FAN);
    		gl.glVertex3d(0,12,0);	
    		gl.glVertex3d(1,10,1);
    		gl.glVertex3d(1,10,-1);
    		gl.glVertex3d(-1,10,-1);
    		gl.glVertex3d(-1,10,1);
    		gl.glVertex3d(1,10,1);
    		gl.glEnd();
    		
    		/*
    		gl.glTranslated(0,12,0);
    			gluCylinder(quadratic, 0.1,0.1,1,32,32);
    			gl.glTranslated(0,-12,0);
    		
    			gl.glTranslated(0,12,1);
    			gl.glRotated(mill_angle, 0,0,1);
    			wind_mill();
    			gl.glRotated(-mill_angle,0,0,1);
    			gl.glTranslated(0,-12,-1);
    		mill_angle+=1;

*/
    		
    		
    		//one tree
    		gl.glTranslated(-15,0.5,15);
    		this.setColor(gl, Color.WHITE);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[3]);//bark
    		gl.glRotated(90,1,0,0);
    		glu.gluCylinder(quadric,0.2f,0.2f,1.5f,32,32);	// Draw Our Cylinder
    		gl.glRotated(-90,1,0,0);
    		

    		gl.glTranslated(0,4,0);
    		this.setColor(gl, Color.WHITE);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[1]);//tree
    		gl.glRotated(90,1,0,0);
    		glu.gluCylinder(quadric,0.0f,1.5f,4.0f,32,32);	// Draw Our Cylinder
    		gl.glRotated(-90,1,0,0);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//no  map
    		
    		gl.glTranslated(0,-4,0);

    		gl.glTranslated(15,-0.5,-15);
    		
    		//two tree
    		
    		gl.glTranslated(-15,0.5,5);
    		this.setColor(gl, Color.WHITE);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[3]);//bark
    		gl.glRotated(90,1,0,0);
    		glu.gluCylinder(quadric,0.2f,0.2f,1.5f,32,32);	// Draw Our Cylinder
    		gl.glRotated(-90,1,0,0);
    		gl.glTranslated(15,-0.5,-5);

    		gl.glTranslated(-15,4.5,5);
    		this.setColor(gl, Color.WHITE);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[1]);//tree
    		gl.glRotated(90,1,0,0);
    		glu.gluCylinder(quadric,0.0f,1.5f,4.0f,32,32);	// Draw Our Cylinder
    		gl.glRotated(-90,1,0,0);
    		gl.glTranslated(15,-4.5,-5);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//no  map
    		
    		//three tree
    		gl.glTranslated(-5,0.5,15);
    		this.setColor(gl, Color.WHITE);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[3]);//bark
    		gl.glRotated(90,1,0,0);
    		glu.gluCylinder(quadric,0.2f,0.2f,1.5f,32,32);	// Draw Our Cylinder
    		gl.glRotated(-90,1,0,0);
    		gl.glTranslated(5,-0.5,-15);

    		gl.glTranslated(-5,4.5,15);
    		this.setColor(gl, Color.WHITE);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[1]);//tree
    		gl.glRotated(90,1,0,0);
    		glu.gluCylinder(quadric,0.0f,1.5f,4.0f,32,32);	// Draw Our Cylinder
    		gl.glRotated(-90,1,0,0);
    		gl.glTranslated(5,-4.5,-15);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//no  map

    		
    	/*
    		glColor3dv(red);
    		glTranslated(-5,0.0,-5);
    		glRotated(90,1,0,0);
    		gluDisk(quadratic,0.5f,1.5f,32,32);		// Draw A Disc (CD Shape)
    		glRotated(-90,1,0,0);
    		glTranslated(5,-0.0,5);
    	*/
    		gl.glTranslated(10,0,10);
    		this.setColor(gl, Color.WHITE);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[7]);//grass	
    		gl.glRotated(90,1,0,0);
    		glu.gluCylinder(quadric,6.0f,6.0f,1.0f,32,32);	// Draw Our Cylinder
    		gl.glRotated(-90,1,0,0);
    		gl.glTranslated(-10,0,-10);

    		//water
    		gl.glTranslated(10,-.2,10);
    		this.setColor(gl, Color.WHITE);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[2]);//water
    		gl.glRotated(90,1,0,0);
    		glu.gluDisk(quadric,0.0f,5.8f,32,32);		// Draw A Disc (CD Shape)
    		gl.glRotated(-90,1,0,0);
    		gl.glTranslated(-10,0.2,-10);
    		
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//no map

    		
    		this.setColor(gl, Color.WHITE);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[6]);//grid
    		gl.glTranslated(10,9,-10);
    		gl.glRotated(90,1,0,0);
    		glu.gluSphere(quadric,3.0f,32,32);		// Draw A Sphere
    		gl.glRotated(-90,1,0,0);
    		gl.glTranslated(-10,-9,10);
    		
    		//make a corn silo
    		gl.glTranslated(10,9,-10);
    		this.setColor(gl, Color.WHITE);
    			gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[5]);//bricks
    			gl.glRotated(90,1,0,0);
    			glu.gluCylinder(quadric,3.0f,3.0f,10.0f,32,32);	// Draw Our Cylinder
    		gl.glRotated(-90,1,0,0);
    		gl.glTranslated(-10,-9,10);
    		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//no map
    	
    	
    }
    }
    	
    void cube(GL2 gl, double x, double y, double z, double size)
    {
    	int xtile = 1;
    	int ytile = 1;
    	//glColor3dv(white);
    	this.setColor(gl, Color.WHITE);
    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[8]);//grass
    	gl.glBegin(GL2.GL_QUADS);
    		//back
	    	gl.glTexCoord2d(0,0);gl.glVertex3d(x-size,y-size,z-size);
	    	gl.glTexCoord2d(xtile,0);gl.glVertex3d(x+size,y-size,z-size);
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x+size,y+size,z-size);
	    	gl.glTexCoord2d(0,ytile);gl.glVertex3d(x-size,y+size,z-size);
	    		//front
	    	gl.glTexCoord2d(0,0);gl.glVertex3d(x-size,y-size,z+size);
	    	gl.glTexCoord2d(xtile,0);gl.glVertex3d(x+size,y-size,z+size);
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x+size,y+size,z+size);
	    	gl.glTexCoord2d(0,ytile);gl.glVertex3d(x-size,y+size,z+size);
	    		//right
	    	gl.glTexCoord2d(0,0);gl.glVertex3d(x+size,y-size,z+size);
	    	gl.glTexCoord2d(0,ytile);gl.glVertex3d(x+size,y+size,z+size);
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x+size,y+size,z-size);
	    	gl.glTexCoord2d(xtile,0);gl.glVertex3d(x+size,y-size,z-size);
	    		//left
	    	gl.glTexCoord2d(0,0);gl.glVertex3d(x-size,y-size,z+size);
	    	gl.glTexCoord2d(0,ytile);gl.glVertex3d(x-size,y+size,z+size);
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x-size,y+size,z-size);
	    	gl.glTexCoord2d(xtile,0);gl.glVertex3d(x-size,y-size,z-size);
	    		//top
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x-size,y+size,z+size);
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x-size,y+size,z-size);
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x+size,y+size,z-size);
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x+size,y+size,z+size);
	
	    		//bottom
	    		
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x-size,y-size,z+size);
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x-size,y-size,z-size);
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x+size,y-size,z-size);
	    	gl.glTexCoord2d(xtile,ytile);gl.glVertex3d(x+size,y-size,z+size);
    	gl.glEnd();
    	gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//grass
    }
        
    public void processKeys(){
    	 // **** MAKE DECISIONS ABOUT WHAT KEYS ARE PRESSED ****
    	
    	

    	if(keys['Q']){
    		xrot+=0.1;
    	}       
    	if(keys['W']){
    		xrot-=0.1;
    	}
    	//if(keys['A']){
    		yrot+=0.1;
    	//}       
    	if(keys['S']){
    		yrot-=0.1;
    	}
    	if(keys['Z']){
    		zrot+=0.1;
    	}       
    	if(keys['X']){
    		zrot-=0.1;
    	}
    	
    	//first person movement
        //looking left       
        if(keys[KeyEvent.VK_LEFT]){
     	   heading+=0.3;
     	   yspin = heading;
        }
        //looking right
        if(keys[KeyEvent.VK_RIGHT]){
     	   heading-=0.3;
     	   yspin = heading;
        }
        //walking forward
        if(keys[KeyEvent.VK_UP]){
     	   xpos -= Math.sin(heading*PIOVER180) * movement;
     	   zpos -= Math.cos(heading*PIOVER180) * movement;     	   
        }
        //walking backward
        if(keys[KeyEvent.VK_DOWN]){
     	   xpos += Math.sin(heading*PIOVER180) * movement;
     	   zpos += Math.cos(heading*PIOVER180) * movement;
     	   
        }
        //strafing
        if(keys['.']){
     	   xpos -= Math.sin((heading-90)*PIOVER180) * movement;
     	   zpos -= Math.cos((heading-90)*PIOVER180) * movement;
     	   
        }
        if(keys[',']){
     	   xpos += Math.sin((heading-90)*PIOVER180) * movement;
     	   zpos += Math.cos((heading-90)*PIOVER180) * movement;
     	   
        }
        //look up/down
        if(keys[KeyEvent.VK_SHIFT]){
     	   lookupdown -=.4;
        }
        if(keys[KeyEvent.VK_CONTROL]){
     	   lookupdown += .4;
        }
        //flying
        if(keys[KeyEvent.VK_PAGE_UP]){
     	   ypos +=.2;
        }
        if(keys[KeyEvent.VK_PAGE_DOWN]){
     	   ypos -=.2;
        }
    	
    	// **** END USER INTERFACE DECISIONS *******
    	if (keys[KeyEvent.VK_ESCAPE]) {		//if the escape key is pressed quit
            //exit();
    		System.exit(0);
        }
    }
    
   

    public void keyPressed(KeyEvent e) {
       int key = e.getKeyCode(); 			//read key code
       keys[key] = true;					//if a key is pressed, set that key to true
    	
      
    }
 
    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    }
 
    public void coordinatePlane(GL2 gl){
    	gl.glBegin(GL2.GL_LINES);
    	//red x axis
    	gl.glColor3d(1, 0, 0);
    	gl.glVertex3d(-1, 0, 0);
    	gl.glVertex3d(1, 0, 0);
    	
    	//green y axis
    	gl.glColor3d(0, 1, 0);
    	gl.glVertex3d(0, -1, 0);
    	gl.glVertex3d(0, 1, 0);
    	
    	//blue z axis
    	gl.glColor3d(0, 0, 1);
    	gl.glVertex3d(0, 0, -1);
    	gl.glVertex3d(0, 0, 1);
    	gl.glEnd();
    	
    }
    public void setColor(GL2 gl, Color c){
    	   double r = c.getRed()/255.0;
    	   double g = c.getGreen()/255.0;
    	   double b = c.getBlue()/255.0;
    	   gl.glColor3d(r, g, b);    	
    	}

 
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
        GL2 gl = gLDrawable.getGL().getGL2();	// Gets an OpenGL drawable area called gl from glDrawable
        if (height <= 0) {						//make sure there is a height of the window
            height = 1;
        }
        float h = (float) width / (float) height;
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION); //sets the current matrix
        gl.glLoadIdentity();						 // Reset The Current Modelview Matrix
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);	// Calculate The Aspect Ratio Of The Window
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();						// Reset The Current Modelview Matrix
    }
 
 
 
    public void keyReleased(KeyEvent e) {
    	int key = e.getKeyCode();				//get key code
    	keys[key] = false;						//if a key is released set to false.
    }
 
    public void keyTyped(KeyEvent e) {
    	if(keys['E']){
    		if(showExtras){
    			showExtras = false;
    		}
    		else{
    			showExtras = true;
    		}
    	}
    }
 
    public static void exit() {
        animator.stop();						//stop animation.
        frame.dispose();						//destroy the window.
        System.exit(0);							//stop the program
    }
 
    public static void main(String[] args) {
        
    	canvas.addGLEventListener(new TexturedObeliskScene1()); // <-- ADD CLASS NAME HERE!!!
       
        frame.add(canvas);									//adds our drawing canvas to the window
        //frame.setSize(640, 480);
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH); //maximizes the window
        //frame.setUndecorated(true);							//removes menu bar
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.addWindowListener(new WindowAdapter() {		//adds user interface to the window
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        frame.setVisible(true);								//show the window
        animator.start();									//start the animation
        canvas.requestFocus();								//give user interface to the drawing area
    }	
 
    public void dispose(GLAutoDrawable gLDrawable) {
        // do nothing
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




