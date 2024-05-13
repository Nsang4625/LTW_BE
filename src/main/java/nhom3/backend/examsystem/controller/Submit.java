package nhom3.backend.examsystem.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import nhom3.backend.examsystem.model.Answer;
import nhom3.backend.examsystem.model.AnswerSheet;
import nhom3.backend.examsystem.model.Question;
import nhom3.backend.examsystem.model.User;
import nhom3.backend.examsystem.repository.QuestionRepository;
import nhom3.backend.examsystem.repository.UserRepository;
import nhom3.backend.examsystem.response.ResponseHandler;
import nhom3.backend.examsystem.service.AnswerServices;
import nhom3.backend.examsystem.service.AnswerSheetServices;
import nhom3.backend.examsystem.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
	
public class Submit {

//	@Autowired
	private final QuestionRepository questionRepository;
	private final UserRepository userRepository;
	private final AnswerSheetServices answerSheetServices;

	public Submit(QuestionRepository questionRepository, AnswerSheetServices answerSheetServices,
			 UserRepository userRepository) {
		this.questionRepository = questionRepository;
		this.answerSheetServices = answerSheetServices;
		this.userRepository = userRepository;
	}
	@PostMapping("/submit")	
	public ResponseEntity<Object> postAnswerSheet(@RequestBody Map<String, Object> answerSheet) {
		try {
			
//			System.out.printf("Exam id: %d\n", answerSheet.get("examId"));
//			System.out.printf("User id: %d\n", answerSheet.get("userId"));
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
					
			User user = userRepository.findByUsername(userName).get();
			Integer userId = (Integer) user.getUserId().intValue();
			
			System.out.printf("userId: %d", userId);
			
			// Calculate the result
			List<Map<String, Object>> answers = (ArrayList<Map<String, Object>>) answerSheet.get("answers");
			Integer examId = (Integer) answerSheet.get("examId");
			
			int result = 0;
			for(int i = 0; i < answers.size(); ++i) {
				Map<String, Object> answer = answers.get(i);
				Integer questionIdInt = (Integer) answer.get("questionId");
				Long questionId = Long.valueOf(questionIdInt.longValue());
				String choicesJson = (String) answer.get("choices");
	
				JSONArray jsa = new JSONArray(choicesJson);
				List<String> choices = new ArrayList<String>();
				for(int j = 0; j < jsa.length(); ++j) {
					choices.add(jsa.getString(j));
				}
				
				Optional<Question> optionalQuestion = questionRepository.findById(questionId);
				Question question = optionalQuestion.get();
				String p = question.getCorrectAnswer();

				List<String> correctAnswers = new ArrayList<String>();
				jsa = new JSONArray(p);
				for(int j = 0; j < jsa.length(); ++j) {
					correctAnswers.add(jsa.getString(j));
				}

				Collections.sort(choices);
				Collections.sort(correctAnswers);
				int flag = 1;
				if(choices.size() != correctAnswers.size()) {
					flag = 0;
					continue;
				}
				for(int j = 0; j < choices.size(); ++j) {
					if(choices.get(j).equals(correctAnswers.get(j)) == false) {
//						System.out.printf("Choice: %s, answers: %s\n", choices.get(j), answers.get(j));
						flag = 0;
					}
				}
				result += flag;
			}
			answerSheet.put("result", result);
			answerSheet.put("userId", userId);
			ResponseEntity re = answerSheetServices.createAnswerSheet(examId, userId, result, answers);
//			System.out.println(re.get);
		}
		catch(Exception e) {
			System.out.println(e);
			return ResponseHandler.getResponse("Lỗi nộp bài", HttpStatus.BAD_REQUEST, new AnswerSheet());
		}
		
		return ResponseHandler.getResponse("Nộp bài thành công", HttpStatus.OK, answerSheet);
	}
}
