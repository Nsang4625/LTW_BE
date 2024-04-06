package nhom3.backend.examsystem.controller;

import org.springframework.web.bind.annotation.RestController;

import nhom3.backend.examsystem.model.Answer;
import nhom3.backend.examsystem.model.AnswerSheet;
import nhom3.backend.examsystem.model.Result;
import nhom3.backend.examsystem.service.AnswerServices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/submission")

public class Submit {
	
	AnswerServices answerService;
	
	@PostMapping("/submit")	
	public AnswerSheet postAnswerSheet(@RequestBody AnswerSheet answerSheet) {
		answerService.getInstance().addAnswerSheet(answerSheet);
		return answerSheet;
	}
}
