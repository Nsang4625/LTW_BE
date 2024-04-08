package nhom3.backend.examsystem.controller;

import org.springframework.web.bind.annotation.RestController;

import nhom3.backend.examsystem.model.Answer;
import nhom3.backend.examsystem.model.AnswerSheet;
import nhom3.backend.examsystem.response.ResponseHandler;
import nhom3.backend.examsystem.service.AnswerServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/submission")

public class Submit {
	
	@PostMapping("/submit")	
	public ResponseEntity<Object> postAnswerSheet(@RequestBody Map<String, Object> answerSheet) {
		try {
			
//			System.out.printf("Exam id: %d\n", answerSheet.get("examId"));
//			System.out.printf("User id: %d\n", answerSheet.get("userId"));
			
			// Calculate the result
			List<Map<String, Object>> answers = (ArrayList<Map<String, Object>>) answerSheet.get("answers");
			
			for(int i = 0; i < answers.size(); ++i) {
				Map<String, Object> answer = answers.get(i);
//				ArrayList<String> choices = (ArrayList<String>) answer.get("choices");
//				System.out.println(answer.get("choices").getClass().getName());
				JSONArray jsa = new JSONArray(answer.get("choices"));
//				for(int j = 0; j < choices.size(); ++j) {
//					System.out.println(choices.get(j));
//				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
			return ResponseHandler.getResponse("Lỗi nộp bài", HttpStatus.BAD_REQUEST, new AnswerSheet());
		}
		
		return ResponseHandler.getResponse("Nộp bài thành công", HttpStatus.OK, answerSheet);
	}
}
