package nhom3.backend.examsystem.model;

public class Answer {
	private int questionId;
	private int choice;
	
	public Answer() {
			
	}
	
	public Answer(int questionId, int choice) {
		this.questionId = questionId;
		this.choice = choice;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}


	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}
	
}
