package server;

import java.util.Random;

public class QuestionList {

	private taskings tasks[];

	public QuestionList () {
		tasks = new taskings[8];
		for (int i = 0; i < 8; i++) {
			tasks[i] = new taskings();
		}
		tasks[0].t = "What is 9 + 10 (meme)";
		tasks[0].ans = "21";
		tasks[1].t = "Opposite of white";
		tasks[1].ans = "black";
		tasks[2].t = "What came first, the chicken or the egg";
		tasks[2].ans = "egg";
		tasks[3].t = "What are people from mars called";
		tasks[3].ans = "martians";
		tasks[4].t = "What is the fastest bird on earth";
		tasks[4].ans = "falcon";
		tasks[5].t = "What is racecar backwards ";
		tasks[5].ans = "racecar";
		tasks[6].t = "How old is Betty White";
		tasks[6].ans = "99";
		tasks[7].t = "What is Mr. Krabs first name";
		tasks[7].ans = "eugene";
	}

	public taskings getTask () {
		Random rand = new Random();
		int i = rand.nextInt(8);
		return tasks[i];
	}

}

class taskings {
	String t;
	String ans;
}