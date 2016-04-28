package myAPI;

import java.awt.Color;
import java.util.Scanner;

public class MyMethods {
	
	public static Color brown = new Color(102,51,0);
	public static Color darkGreen = new Color(0,85,0);
	
	public static Color pink = new Color(255,204,255);
	public static Color purple= new Color(204,0,255);

	public static Color lightblue = new Color(204,255,255); 
	
	
	public static int getInt(String prompt){
		Scanner input = new Scanner(System.in);
		System.out.print(prompt);
		int anyNum = input.nextInt();		
		return anyNum;
	}
	
	public static double getDouble(String prompt){
		Scanner input = new Scanner(System.in);
		System.out.print(prompt);
		double anyNum = input.nextDouble();		
		return anyNum;
	}
	
	public static char getLetterGrade(double anyGrade){
		if (anyGrade>=90)
			return 'A';
		else if(anyGrade>=80)
			return 'B';
		else if(anyGrade>=70)
			return 'C';
		else if(anyGrade>=60)
			return 'D';
		else 
			return 'F';
	}
	
	public static boolean isPrime(int anyInteger){
		
		for(int i=2;i<anyInteger;i++){
			if(anyInteger%i==0){
				return false;
			}
		}
		
		return true;
	}
	
	public static int getGCD(int anyNum1, int anyNum2){
		int gcd = 2; //initial gcd
		int i = 2; //smallest possible gcd
		while(i<=anyNum1 && i <= anyNum2){
			if(anyNum1 % i == 0 && anyNum2 % i == 0){
				gcd = i; //update gcd
			}
			i++;//increment to next possible gcd
		}
		
		return gcd;
	}
	
	public static int getRandomInt(int start, int stop){		
		int randomNum = 1;
		int range = stop - start;				
		randomNum = (int)(Math.random()*(range+1))+start;		
		return randomNum;		
	}
	
	public static Color getRandomColor(){
		int num = getRandomInt(1,7);
		switch(num){
		case 1:
			return Color.BLUE;
		case 2:
			return Color.CYAN;
		case 3:
			return Color.DARK_GRAY;
		case 4:
			return Color.GRAY;
		case 5:
			return Color.GREEN;
		case 6:
			return Color.LIGHT_GRAY;
		default:
			return Color.YELLOW;
		}
	}
	
	public static String getOneString(String prompt){
		Scanner input = new Scanner(System.in);
		System.out.print(prompt);
		String anyString = input.next();
		return anyString;
	}
	
	public static String getMultipleStrings(String prompt){
		Scanner input = new Scanner(System.in);
		System.out.print(prompt);
		String anyString = input.nextLine();
		return anyString;
	}
}

