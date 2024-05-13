package nhom3.backend.examsystem.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import nhom3.backend.examsystem.dto.ResultDto;
import nhom3.backend.examsystem.model.Exam;
import nhom3.backend.examsystem.model.User;
import nhom3.backend.examsystem.repository.UserRepository;
import nhom3.backend.examsystem.service.ExamService;
import lombok.RequiredArgsConstructor;
import nhom3.backend.examsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/exam")
@CrossOrigin(origins = "*")
public class ExamController {
    private final ExamService examService;

    @Autowired
    private EntityManager entityManager;
    
    public ExamController(ExamService examService) {
    	this.examService = examService;
    }


    // Get exam by id
    @GetMapping("/{examId}")
    public ResponseEntity<?> getExamById(@PathVariable("examId") Long examId){
        return examService.getExamById(examId);
    }

    // Get all exams
    @GetMapping("/get-all-exams")
    public ResponseEntity<?> getAllExams(){
        return examService.getAllExams();
    }

    // Create new exam
    @PostMapping("/create-exam")
    public ResponseEntity<?> createExam(@RequestBody Exam exam){
        return examService.createExam(exam);
    }

    // Delete exam by id
    @DeleteMapping("/delete-exam/{examId}")
    public ResponseEntity<?> deleteExamById(@PathVariable("examId") Long examId){
        return examService.deleteExamById(examId);
    }

    // Edit exam by id
    @PutMapping("/edit/{examId}")
    public ResponseEntity<?> editExam(@PathVariable("examId") Long examId, @RequestBody Exam newExam){
        return examService.editExamById(examId, newExam);
    }

    // Search exam by name
    @GetMapping("/exam-name/{examName}")
    public ResponseEntity<?> getExamByExamName(@PathVariable("examName") String examName){
        return examService.getExamByExamName(examName);
    }

    // Search exam by type
    @GetMapping("/exam-type/{examType}")
    public ResponseEntity<?> getExamByExamType(@PathVariable("examType") String examType){
        return examService.getExamByExamType(examType);
    }
    @GetMapping("/result/{examId}")
    public ResultDto getExamResult(@PathVariable("examId") long examId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  authentication.getName();
        Query q = entityManager.createNativeQuery("SELECT user_id FROM quiz.user WHERE user.username = ?");
        q.setParameter(1, username);
        String userId =  q.getSingleResult().toString();


        Object result = entityManager.createNativeQuery("SELECT result FROM quiz.answer_sheet WHERE answer_sheet.exam_id = " + examId + " AND answer_sheet.user_id = " + userId).getSingleResult();
        Double count = Double.valueOf(result.toString());
        Object total = entityManager.createNativeQuery("SELECT count(*) FROM question WHERE question.exam_id = " + examId).getSingleResult();
        Double total2 = Double.valueOf(total.toString());
        return new ResultDto(count, total2);
    }
}
