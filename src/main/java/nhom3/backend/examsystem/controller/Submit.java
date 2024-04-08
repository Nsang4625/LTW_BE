package nhom3.backend.examsystem.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import nhom3.backend.examsystem.model.Answer;
import nhom3.backend.examsystem.model.AnswerSheet;
import nhom3.backend.examsystem.model.Question;
import nhom3.backend.examsystem.repository.QuestionRepository;
import nhom3.backend.examsystem.response.ResponseHandler;
import nhom3.backend.examsystem.service.AnswerServices;
import nhom3.backend.examsystem.service.AnswerSheetServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
	
public class Submit {

	@Autowired
	private final QuestionRepository questionRepository;
	private final AnswerSheetServices answerSheetServices;
	private final AnswerServices answerServices;
	
	public Submit(QuestionRepository questionRepository, AnswerSheetServices answerSheetServices, AnswerServices answerServices) {
		this.questionRepository = questionRepository;
		this.answerSheetServices = answerSheetServices;
		this.answerServices = answerServices;
	}
	@PostMapping("/submit")	
	public ResponseEntity<Object> postAnswerSheet(@RequestBody Map<String, Object> answerSheet) {
		try {
			
//			System.out.printf("Exam id: %d\n", answerSheet.get("examId"));
//			System.out.printf("User id: %d\n", answerSheet.get("userId"));
			
			// Calculate the result
			List<Map<String, Object>> answers = (ArrayList<Map<String, Object>>) answerSheet.get("answers");
			Integer examId = (Integer) answerSheet.get("examId");
			Integer userId = (Integer) answerSheet.get("userId");
			
			int result = 0;
			for(int i = 0; i < answers.size(); ++i) {
				Map<String, Object> answer = answers.get(i);
				Integer questionId = (Integer) answer.get("questionId");
				String choicesJson = (String) answer.get("choices");
				System.out.println(choicesJson);
				JSONArray jsa = new JSONArray(choicesJson);
				List<String> choices = new ArrayList<String>();
				for(int j = 0; j < jsa.length(); ++j) {
					choices.add(jsa.getString(j));
				}
				answerServices.createAnswer(questionId, 2, choices);
				
				
//				Optional<Question> optionalQuestion = questionRepository.findById(questionId);
//				Question question = optionalQuestion.get();
//				List<String> correctAnswers = question.getCorrectAnswerList();
//				Collections.sort(choices);
//				Collections.sort(correctAnswers);
//				int flag = 1;
//				if(choices.size() != correctAnswers.size())
//					flag = 0;
//				for(int j = 0; j < choices.size(); ++j) {
//					if(choices.get(i).equals(answers.get(i)) == false) {
//						flag = 0;
//					}
//				}
//				result += flag;
			}
			answerSheet.put("result", result);
			ResponseEntity re = answerSheetServices.createAnswerSheet(examId, userId, result);
			System.out.println(re.getBody());
		}
		catch(Exception e) {
			System.out.println(e);
			return ResponseHandler.getResponse("Lỗi nộp bài", HttpStatus.BAD_REQUEST, new AnswerSheet());
		}
		
		return ResponseHandler.getResponse("Nộp bài thành công", HttpStatus.OK, answerSheet);
	}
}
