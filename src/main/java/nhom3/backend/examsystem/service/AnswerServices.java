package nhom3.backend.examsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import nhom3.backend.examsystem.model.Answer;
import nhom3.backend.examsystem.repository.AnswerRepository;

@Service
public class AnswerServices {

	@Autowired
	private final AnswerRepository answerRepository;
	public AnswerServices(AnswerRepository answerRespository) {
		this.answerRepository = answerRespository;
	}

	public ResponseEntity<?> createAnswer(long questionId, long answerSheetId, List<String> choices){
		Answer answer = new Answer();
		answer.setQuestionId(questionId);
		answer.setAnswerSheetId(answerSheetId);
		answer.setChoices(choices);
		answerRepository.save(answer);
		return null;
	}
}
