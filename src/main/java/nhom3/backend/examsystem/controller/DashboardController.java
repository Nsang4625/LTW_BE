package nhom3.backend.examsystem.controller;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import nhom3.backend.examsystem.dto.StatisticDto;
import nhom3.backend.examsystem.model.Exam;
import nhom3.backend.examsystem.service.AnswerSheetServices;
import nhom3.backend.examsystem.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("admin/dashboard_admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {
    @Autowired
    private EntityManager entityManager;
    private final ExamService examService;
    // thống kê tất cả các bài kiểm tra, điểm trung bình mỗi bài, tỉ lệ làm bài
    private int count = 5;
    @GetMapping("/Exam/List")
    public ResponseEntity<?> getAllExams(){
        return examService.getAllExams();
    }

    @GetMapping("/User/List")
    public List<String> getAllUser(){
        List<String> userList = new ArrayList<>();
        List<Object[]> results = entityManager.createNativeQuery(
                "SELECT u.user_id, u.username, r.authority FROM user u left join user_role ur on u.user_id = ur.user_id left join role r on ur.role_id = r.role_id;"
        ).getResultList();
        for (Object[] result : results) {
            Long id = (Long) result[0];
            String name = (String) result[1];
            String authority = (String) result[2];
            String tmp = String.valueOf(id) + ":" + name + ":" + authority;
            userList.add(tmp);
        }
        return userList;
    }

//   Dang phat trien...
    @GetMapping("/Exam/Fix/{examId}")
    public ResponseEntity<?> editExam(@PathVariable("examId") Long examId, @RequestBody Exam newExam){
        return examService.editExamById(examId, newExam);
    }

    @GetMapping("/Exam/Create")
    public ResponseEntity<?> createExam(@RequestBody Exam exam){
        return examService.createExam(exam);
    }

    @GetMapping("/Exam/Delete/{examId}")
    public ResponseEntity<?> deleteExamById(@PathVariable("examId") Long examId){
        return examService.deleteExamById(examId);
    }

}
