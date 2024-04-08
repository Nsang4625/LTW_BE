package nhom3.backend.examsystem.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AnswerSheet {	
	@Id
	private long id;
	private long userId;
	private int result;
	public AnswerSheet() {
		
	}

	public AnswerSheet(long id, long userId, int result) {
		super();
		this.id = id;
		this.userId = userId;
		this.result = result;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
}
