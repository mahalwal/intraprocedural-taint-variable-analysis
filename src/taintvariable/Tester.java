package taintvariable;

import java.util.Scanner;

public class Tester {
	
	
	//TEST CASE 5
	static int foo(int z)
	{
		//{z}
		int x = 22;
		//{x, z}
		int y = 12;
		//{x, y, z}
		if(x<10)
		{
			while(x<20)
			{
				//{x, y, z}
				x = x + z;
				//{x, y, z}
				x++;
				//{x, y, z}
			}
		}
		else {
			while(x<10)
			{
				//{x, y}
				y++;
				//{x, y}
			}
		}
		//{x, y}
		System.out.println(x + " " + y);
		//{x}
		return x;
		//{ }
	}
	public static void main(String [] args) {
		//{val}
		int val = foo(5);
		//{val}
		System.out.println(val);
		//{ }
	}
	
	//TEST CASE 4
//	static int foo(int z) {
//		int a;
//		int b;
//		//{z, a, b}
//		int c = 11;
//		//{z, a, b, c}
//		int d = 5;
//		//{z, a, b, c, d}
//		System.out.println(z);
//		//{z, a, b, c, d}
//		a = z * d;
//		//{z, a, b, c}
//		b = z + c;
//		//{z, a, b}
//		z = a + b;
//		//{z}
//		return z;
//		//{ }
//	}
//	public static void main(String [] args) {
//		//{val}
//		int val = foo(5);
//		//{val}
//		System.out.println(val);
//		//{ }
//	}

	//TEST CASE 3
//	static int foo() {
//		Scanner sc = new Scanner(System.in);
//		int z;
//		int a = sc.nextInt();
//		int b = sc.nextInt();
//		if (a > b) {
//			a = 10;
//		} 
//		else {
//			b = 11;
//		}
//		int c = 10;
//		while(a>5)
//		{
//			a--;
//			c = 10 + c;
//		}
//		z = a + b + c;
//		System.out.println(z);
//		return c;
//	}
//	public static void main(String[] args) {
//		int a = foo();
//	}
	
	//TEST CASE 2
//	static int foo()
//	{
//		Scanner sc = new Scanner(System.in);
//		int z;
//		int a = sc.nextInt();
//		int b = sc.nextInt();
//		int c = 10;
//		z = a+b+c;
//		return z;
//	}
	
	//TEST CASE 1
//	int bar1(int x) {
//		int product, a;
//		a = 10;
//		product = x * a;
//		System.out.println(product);
//		return product;
//	}
}