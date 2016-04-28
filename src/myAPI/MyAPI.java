package myAPI;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class MyAPI {
	
	
	
	public static void setColor(GL2 gl, Color c){
		double r = c.getRed()/255.0;
		double g = c.getGreen()/255.0;
		double b = c.getBlue()/255.0;
		
		gl.glColor3d(r, g, b);    
	}
	
	
	
	
	
	
	public static int getRandomInt(int start, int stop){
		int randomNum = 1;
		int range = stop - start;
		randomNum = (int)(Math.random()*(range+1)) + start;
		return randomNum;
	}
	
	//getGCD of any two numbers
	public static int getGCD(int anyNum1, int anyNum2){
		
		int i = 1;
		int gcd = 1;
		while(i<=anyNum1 && i<=anyNum2){
			if(anyNum1 % i == 0 && anyNum2 % i == 0){
				gcd = i;
			}
			i++;
		}
		
		return gcd;
	}
	
	
	//check for primes
	public static boolean isPrime(int anyNumber){
		for(int i=2;i<=anyNumber-1;i++){
			if(anyNumber % i == 0){
				return false;
			}
		}
		
		return true;
	}
	

	//getLetterGrade given a percent
	public static String getLetterGrade(double p){
		if(p>=90){
			return "A";
		}
		else if(p >= 80){
			return "B";
		}
		else if(p >= 70){
			return "C";
		}
		else if(p >= 60){
			return "D";
		}
		else{
			return "F";
		}
	}
	
	//getDouble
	public static double getDouble(String prompt){
		Scanner input = new Scanner(System.in);
		System.out.print(prompt);
		double answer = input.nextDouble();
		return answer;
	}
	
	//getInt
		public static int getInt(String prompt){
			Scanner input = new Scanner(System.in);
			System.out.print(prompt);
			int answer = input.nextInt();
			return answer;
		}
		
		

				public static String getString(String prompt) {
					Scanner input = new Scanner(System.in);
					System.out.print(prompt);
					String answer = input.next();
					return answer;
				}
				
				public static String getMultipleStrings(String prompt) {
					Scanner input = new Scanner(System.in);
					System.out.print(prompt);
					String answer = input.nextLine();
					return answer;
				}
	
	
	
}
