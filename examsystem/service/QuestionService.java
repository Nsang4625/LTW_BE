package nhom3.backend.examsystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import nhom3.backend.examsystem.model.Exam;
import nhom3.backend.examsystem.model.Question;
import nhom3.backend.examsystem.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import nhom3.backend.examsystem.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    // Create
    public ResponseEntity<?> createQuestion(Question question, Long examId) throws JsonProcessingException {
        Exam exam = examRepository.findById(examId).orElse(null);
        if(exam==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found the exam by id");
        }
        Question q = new Question();

        q.setExamId(examId);
        q.setQuestionText(question.getQuestionText());
        q.setChoices(question.getChoiceList());
        q.setCorrectAnswer(question.getCorrectAnswerList());

        questionRepository.save(q);

        return ResponseEntity.ok(q);
    }

    // Get question by id
    public ResponseEntity<?> getQuestionById(Long questionId){
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        return ResponseEntity.ok(optionalQuestion);
    }

    // Get all questions
    public ResponseEntity<?> getAllQuestions(){
        List<Question> questionList = questionRepository.findAll();
        return ResponseEntity.ok(questionList);
    }

    // Get question by exam's id
    public ResponseEntity<?> getQuestionsByExamId(Long examId) {
        List<Question> questionList = questionRepository.findByExamId(examId);
        return ResponseEntity.ok(questionList);
    }

    // Get question by questionType's id
    public ResponseEntity<?> getQuestionsByQuestionTypeId(Long questionTypeId) {
        List<Question> questionList = questionRepository.findByQuestionTypeId(questionTypeId);
        return ResponseEntity.ok(questionList);
    }


    // Delete
    public ResponseEntity<?> deleteQuestionById(Long questionId){
        Question q = questionRepository.findById(questionId).orElseThrow();
        questionRepository.deleteById(questionId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted question by id");
    }

    // Edit
    public ResponseEntity<?> editQuestionById(Long questionId, Question newQuestion) throws JsonProcessingException {
        Question q = questionRepository.findById(questionId).orElseThrow();

        q.setExamId(newQuestion.getExamId());
        q.setQuestionTypeId(newQuestion.getQuestionTypeId());
        q.setChoices(newQuestion.getChoiceList());
        q.setCorrectAnswer(newQuestion.getCorrectAnswerList());

        questionRepository.save(q);

        return ResponseEntity.ok(q);
    }
}
