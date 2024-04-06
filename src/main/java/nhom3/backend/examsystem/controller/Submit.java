package nhom3.backend.examsystem.controller;

import org.springframework.web.bind.annotation.RestController;

import nhom3.backend.examsystem.model.AnswerSheet;
import nhom3.backend.examsystem.response.ResponseHandler;
import nhom3.backend.examsystem.service.AnswerServices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/submission")

public class Submit {
	
	@PostMapping("/submit")	
	public ResponseEntity<Object> postAnswerSheet(@RequestBody AnswerSheet answerSheet) {
		try {
			AnswerServices.getInstance().addAnswerSheet(answerSheet);
		}
		catch(Exception e) {
			return ResponseHandler.getResponse("Lỗi nộp bài", HttpStatus.BAD_REQUEST, new AnswerSheet());
		}
		return ResponseHandler.getResponse("Nộp bài thành công", HttpStatus.OK, answerSheet);
	}
}
