package nhom3.backend.examsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Question {
	@Id
	private int examId;
	private int questionTypeId;

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(int questionTypeId) {
		this.questionTypeId = questionTypeId;
	}
}
