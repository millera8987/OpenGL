package openGLDemos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
 
public class ObeliskScene implements GLEventListener, KeyListener {
 
    static GLU glu = new GLU(); 					//Creates an object for the OpenGL Utility Library
    static GLCanvas canvas = new GLCanvas(); 		// Open GL Rendering Support
    static Frame frame = new Frame("Title"); 		//Creates a window to draw on
    static Animator animator = new Animator(canvas);//Creates our animation loop by repeatedly calling display();
    boolean[] keys = new boolean[256];    			//Storage for keys for user interface.     
    // ************** Declare and initialize variables here *******************
    private GLUquadric quadric;
    double xrot = 20, yrot = 0, zrot = 0;
    boolean quadraticView = true;
    double yspin = 0;
    double movement = 0.2;//speed of walking
    double heading = 0;//degree at which you are looking
    double xtrans = 0, ytrans = 0, ztrans = 0;
    double xpos = 0, ypos = 1, zpos = 40;
    double sceneroty = 0;
    double lookupdown = 0;
    double PI = 3.14159265;
    double PIOVER180 = PI/180;
    
    
    
    
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
        
        this.setColor(gl, Color.GREEN);
        gl.glBegin(GL2.GL_QUADS);
        	gl.glVertex3d(20,-1,20);
        	gl.glVertex3d(20,-1,-20);
        	gl.glVertex3d(-20,-1,-20);
        	gl.glVertex3d(-20,-1,20);
        gl.glEnd();
        
       obelisk(gl);
       scene(gl);
        
        // **** END DRAWINGS ****
        
        processKeys();
        
        
        
    }
    
    public void obelisk(GL2 gl){
    	//obelisk
    	
    	this.setColor(gl, Color.GRAY);
    	gl.glBegin(GL2.GL_QUAD_STRIP);
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
    	


    	


    	
    	this.setColor(gl, Color.RED);
    	//cube(gl, -15,3-1,-15,3);
    	//house base
    	gl.glTranslated(-15,-1,-15);
    	gl.glRotated(-90,1,0,0);
    	glu.gluCylinder(quadric,4.5f,4.5f,6.0f,32,32);	// Draw Our Cylinder
    	gl.glRotated(90,1,0,0);
    	gl.glTranslated(15,1,15);

    	gl.glTranslated(-15,9,-15);
    	//gl.glColor3dv(brown);	
    	//roof
    	this.setColor(gl, MyMethods.brown);
    	gl.glRotated(90,1,0,0);
    	glu.gluCylinder(quadric,0.0f,7.0f,6.0f,32,32);	// Draw Our Cylinder
    	gl.glRotated(-90,1,0,0);
    	gl.glTranslated(15,-9,15);
    	
    	
    	//one tree
    	gl.glTranslated(-15,0.5,15);    	
    	this.setColor(gl, MyMethods.brown);
    	
    	gl.glRotated(90,1,0,0);
    	glu.gluCylinder(quadric,0.2f,0.2f,1.5f,32,32);	// Draw Our Cylinder
    	gl.glRotated(-90,1,0,0);
    	

    	gl.glTranslated(0,4,0);
    	//gl.glColor3dv(dgreen);	
    	this.setColor(gl, MyMethods.darkGreen);
    	gl.glRotated(90,1,0,0);
    	glu.gluCylinder(quadric,0.0f,1.5f,4.0f,32,32);	// Draw Our Cylinder
    	gl.glRotated(-90,1,0,0);
    	//gl.glColor3dv(black);
    	this.setColor(gl, Color.BLACK);
    	gl.glBegin(GL2.GL_LINES);
    	for (double r = 0;r<6.28;r+=0.3)
    	{
    		double x = 1.5*Math.sin(r);
    		double z = 1.5*Math.cos(r);
    		gl.glVertex3d(x,-4,z);
    		gl.glVertex3d(0,0,0);
    	}
    	gl.glEnd();
    	gl.glBegin(GL2.GL_LINES);
    	for ( double r = 0;r<6.28;r+=0.3)
    	{
    		double x = 1.5*Math.sin(r);
    		double z = 1.5*Math.cos(r);
    		double x1 = 1.5*Math.sin(r-0.3);
    		double z1 = 1.5*Math.cos(r-0.3);
    		gl.glVertex3d(x1,-4,z1);
    		gl.glVertex3d(x,-4,z);
    		
    	}
    	gl.glEnd();
    	gl.glTranslated(0,-4,0);

    	gl.glTranslated(15,-0.5,-15);
    	
    	//two tree
    	
    	gl.glTranslated(-15,0.5,5);
    	//gl.glColor3dv(brown);
    	this.setColor(gl, MyMethods.brown);
    	gl.glRotated(90,1,0,0);
    	glu.gluCylinder(quadric,0.2f,0.2f,1.5f,32,32);	// Draw Our Cylinder
    	gl.glRotated(-90,1,0,0);
    	gl.glTranslated(15,-0.5,-5);

    	gl.glTranslated(-15,4.5,5);
    	this.setColor(gl, MyMethods.darkGreen);
    	gl.glRotated(90,1,0,0);
    	glu.gluCylinder(quadric,0.0f,1.5f,4.0f,32,32);	// Draw Our Cylinder
    	gl.glRotated(-90,1,0,0);
    	gl.glTranslated(15,-4.5,-5);
    	
    	//three tree
    	gl.glTranslated(-5,0.5,15);
    	//gl.glColor3dv(brown);
    	this.setColor(gl, MyMethods.brown);
    	gl.glRotated(90,1,0,0);
    	glu.gluCylinder(quadric,0.2f,0.2f,1.5f,32,32);	// Draw Our Cylinder
    	gl.glRotated(-90,1,0,0);
    	gl.glTranslated(5,-0.5,-15);

    	gl.glTranslated(-5,4.5,15);
    	this.setColor(gl, MyMethods.darkGreen);	
    	
    	gl.glRotated(90,1,0,0);
    	glu.gluCylinder(quadric,0.0f,1.5f,4.0f,32,32);	// Draw Our Cylinder
    	gl.glRotated(-90,1,0,0);
    	gl.glTranslated(5,-4.5,-15);

    	
    /*
    	glColor3dv(red);
    	glTranslated(-5,0.0,-5);
    	glRotated(90,1,0,0);
    	gluDisk(quadratic,0.5f,1.5f,32,32);		// Draw A Disc (CD Shape)
    	glRotated(-90,1,0,0);
    	glTranslated(5,-0.0,5);
    */
    	gl.glTranslated(10,0,10);
    	//gl.glColor3dv(blue);		
    	this.setColor(gl, Color.BLUE);
    	gl.glRotated(90,1,0,0);
    	glu.gluCylinder(quadric,6.0f,6.0f,1.0f,32,32);	// Draw Our Cylinder
    	gl.glRotated(-90,1,0,0);
    	gl.glTranslated(-10,0,-10);

    	//water
    	gl.glTranslated(10,-.2,10);
    	//gl.glColor3dv(lightblue);	
    	this.setColor(gl, MyMethods.lightblue);
    	gl.glRotated(90,1,0,0);
    	glu.gluDisk(quadric,0.0f,5.8f,32,32);		// Draw A Disc (CD Shape)
    	gl.glRotated(-90,1,0,0);
    	gl.glTranslated(-10,0.2,-10);

    	

    	    	//gl.glColor3dv(lightblue);
    	this.setColor(gl, MyMethods.lightblue);
    	gl.glTranslated(10,9,-10);
    	glu.gluSphere(quadric,2.9f,32,32);		// Draw A Sphere
    	gl.glTranslated(-10,-9,10);
    	
    	//make a corn silo
    	gl.glTranslated(10,9,-10);
    	//gl.glColor3dv(red);	
    	this.setColor(gl, Color.RED);
    	gl.glRotated(90,1,0,0);
    	glu.gluCylinder(quadric,3.0f,3.0f,10.0f,32,32);	// Draw Our Cylinder
    	gl.glRotated(-90,1,0,0);
    	gl.glTranslated(-10,-9,10);
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
    }
    	
    	void cube(GL2 gl, double x, double y, double z, double size)
    	{
    		gl.glBegin(GL2.GL_QUADS);
    			//back
    		gl.glVertex3d(x-size,y-size,z-size);
    		gl.glVertex3d(x+size,y-size,z-size);
    		gl.glVertex3d(x+size,y+size,z-size);
    		gl.glVertex3d(x-size,y+size,z-size);
    			//front
    		gl.glVertex3d(x-size,y-size,z+size);
    		gl.glVertex3d(x+size,y-size,z+size);
    			gl.glVertex3d(x+size,y+size,z+size);
    			gl.glVertex3d(x-size,y+size,z+size);
    			//right
    			gl.glVertex3d(x+size,y-size,z+size);
    			gl.glVertex3d(x+size,y+size,z+size);
    			gl.glVertex3d(x+size,y+size,z-size);
    			gl.glVertex3d(x+size,y-size,z-size);
    			//left
    			gl.glVertex3d(x-size,y-size,z+size);
    			gl.glVertex3d(x-size,y+size,z+size);
    			gl.glVertex3d(x-size,y+size,z-size);
    			gl.glVertex3d(x-size,y-size,z-size);
    			//top
    			gl.glVertex3d(x-size,y+size,z+size);
    			gl.glVertex3d(x-size,y+size,z-size);
    			gl.glVertex3d(x+size,y+size,z-size);
    			gl.glVertex3d(x+size,y+size,z+size);

    			//bottom
    			gl.glVertex3d(x-size,y-size,z+size);
    			gl.glVertex3d(x-size,y-size,z-size);
    			gl.glVertex3d(x+size,y-size,z-size);
    			gl.glVertex3d(x+size,y-size,z+size);
    			gl.glEnd();
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
       
        if(keys[KeyEvent.VK_LEFT]){
     	   heading+=0.3;
     	   yspin = heading;
        }
        if(keys[KeyEvent.VK_RIGHT]){
     	   heading-=0.3;
     	   yspin = heading;
        }
        if(keys[KeyEvent.VK_UP]){
     	   xpos -= Math.sin(heading*PIOVER180) * movement;
     	   zpos -= Math.cos(heading*PIOVER180) * movement;
     	   
        }
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
        //fly
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
    }
 
    public static void exit() {
        animator.stop();						//stop animation.
        frame.dispose();						//destroy the window.
        System.exit(0);							//stop the program
    }
 
    public static void main(String[] args) {
        
    	canvas.addGLEventListener(new ObeliskScene()); // <-- ADD CLASS NAME HERE!!!
       
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
}




