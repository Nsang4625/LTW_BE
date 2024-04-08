package nhom3.backend.examsystem.model;

import java.util.List;

import org.json.JSONArray;

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
	private List<String> choices;
	
	public Answer() {
			
	}
	
	public Answer(Long id, int questionId, int answerSheetId, String choices) {
		this.id = id;
		this.questionId = questionId;
		this.answerSheetId = answerSheetId;
		setChoices(choices);
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}


	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(String choices) {
		JSONArray jsa = new JSONArray(choices);
		for(int i = 0; i < jsa.length(); ++i) {
			this.choices.add(jsa.getString(i));
		}
	}

	public int getAnswerSheetId() {
		return answerSheetId;
	}

	public void setAnswerSheetId(int answerSheetId) {
		this.answerSheetId = answerSheetId;
	}
	
}
