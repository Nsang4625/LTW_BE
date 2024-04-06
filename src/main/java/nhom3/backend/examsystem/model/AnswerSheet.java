package nhom3.backend.examsystem.model;

import java.util.List;

public class AnswerSheet {
	List<Answer> answers;
	
	public AnswerSheet() {
		
	}
	
	public AnswerSheet(List<Answer> answers) {
		this.answers = answers;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	
}
