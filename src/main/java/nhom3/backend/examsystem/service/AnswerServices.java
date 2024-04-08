package nhom3.backend.examsystem.service;

import java.util.List;

import nhom3.backend.examsystem.model.Answer;
import nhom3.backend.examsystem.model.AnswerSheet;

public class AnswerServices {

	private static AnswerServices answerServices;
	
	public AnswerServices() {
		
	}
	
	public static AnswerServices getInstance() {
		if(answerServices == null)
			answerServices = new AnswerServices();
		return answerServices;	
			
	}
	public void addAnswerSheet(List<Answer> answers) {
		
	}
}
