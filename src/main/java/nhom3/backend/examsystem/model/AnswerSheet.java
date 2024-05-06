package nhom3.backend.examsystem.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Answer_sheet")
public class AnswerSheet {	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private long id;
	
	@Column(name="User_id")
	private long userId;
	
	@Column(name="Exam_id")
	private long examId;


	@Column(name="Res")
	private double result;
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

	public double getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	public long getExamId() {
		return examId;
	}

	public void setExamId(long examId) {
		this.examId = examId;
	}
}
