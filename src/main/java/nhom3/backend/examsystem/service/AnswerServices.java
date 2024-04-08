package nhom3.backend.examsystem.service;

import nhom3.backend.examsystem.repository.AnswerRepository;

public class AnswerServices {

	private static AnswerServices answerServices;
	private AnswerRepository answerRepository;
	
	public static AnswerServices getInstance() {
		if(answerServices == null)
			answerServices = new AnswerServices();
		return answerServices;	
			
	}

}
