package edu.uwm.cs351;

public class Student {
	public String name;
	public double score;
	
	public static Student method(Student[] a) {
		double tmp = 0.0;
		for (int i=0; i < 10; i++)
			if (a[i].score > tmp) 
				tmp = a[i].score;
			for (int i=0; i < 10; i++) if (a[i].score == tmp) return a[i];
System.out.println("No one is best");
		return null;
	}
}
