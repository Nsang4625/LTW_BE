package nhom3.backend.examsystem.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private int questionId;
	private int answerSheetId;
	private String choices;
	
	public Answer() {
			
	}
	
	public Answer(Long id, int questionId, int answerSheetId, String choices) {
		this.id = id;
		this.questionId = questionId;
		this.answerSheetId = answerSheetId;
		this.choices = choices;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}


	public String getChoices() {
		return choices;
	}

	public void setChoices(String choices) {
		this.choices = choices;
	}

	public int getAnswerSheetId() {
		return answerSheetId;
	}

	public void setAnswerSheetId(int answerSheetId) {
		this.answerSheetId = answerSheetId;
	}
	
}
