package nhom3.backend.examsystem.model;

public class Result {
	private int questionCount;
	private int correctCount;
	private int wrongCount;
	private int missingCount;
	


	public Result() {
		
	}
	
	public Result(int questionCount, int correctCount, int wrongCount, int missingCount) {
		this.questionCount = questionCount;
		this.correctCount = correctCount;
		this.wrongCount = wrongCount;
		this.missingCount = missingCount;
	}
	
	public int getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}
	public int getCorrectCount() {
		return correctCount;
	}
	public void setCorrectCount(int correctCount) {
		this.correctCount = correctCount;
	}
	public int getWrongCount() {
		return wrongCount;
	}
	public void setWrongCount(int wrongCount) {
		this.wrongCount = wrongCount;
	}
	public int getMissingCount() {
		return missingCount;
	}

	public void setMissingCount(int missingCount) {
		this.missingCount = missingCount;
	}
	
	
}
