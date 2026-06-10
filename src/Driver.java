import edu.uwm.cs351.Student;

public class Driver {
	public static void main(String[] args) {
		Student[] array = new Student[10];
		for (int i=0; i < 10; ++i) {
			array[i] = new Student();
			array[i].name = "Student #" + i;
			array[i].score = (10 - i) * i * 4;
			System.out.println(array[i].name + " has " + array[i].score);
		}
		System.out.println("Best student is " + Student.method(array).name);
	}
}
