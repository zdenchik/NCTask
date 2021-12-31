package ua.edu.sumdu.j2se.zozulia.tasks;

import java.time.LocalDateTime;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello");
		AbstractTaskList test = new ArrayTaskList();
		test.setType(ListTypes.types.ARRAY);
		test.add(new Task("fe",LocalDateTime.now()));
		for(Task i : test){
			System.out.println(i.getTitle());
		};
		//AbstractTaskList test2 = test.incoming(LocalDateTime.now(),LocalDateTime.MAX);
		Task temp = new Task("fe",LocalDateTime.now().plusSeconds(10),LocalDateTime.now().plusSeconds(100),20);

		temp.setActive(true);
		System.out.println(temp.nextTimeAfter(LocalDateTime.now().plusSeconds(5)));
	}
}

