package nhom3.backend.examsystem.service;

import nhom3.backend.examsystem.repository.AnswerSheetRepository;
import nhom3.backend.examsystem.model.AnswerSheet;

import java.util.Optional;

import org.springframework.http.ResponseEntity;



public class AnswerSheetServices {
	private static AnswerSheetServices answerSheetServices;
	private AnswerSheetRepository answerSheetRepository;
	
	public AnswerSheetServices getInstance() {
		if(answerSheetServices == null)
			answerSheetServices = new AnswerSheetServices();
		return answerSheetServices;
	}
	
	// Get answer sheet by id
	public ResponseEntity<?> getAnswerSheetById(long id){
		Optional<AnswerSheet> optionalAnswerSheet = answerSheetRepository.findById(id);
		return ResponseEntity.ok(optionalAnswerSheet);
	}
	
	
}
