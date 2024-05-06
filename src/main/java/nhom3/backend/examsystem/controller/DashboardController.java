package nhom3.backend.examsystem.controller;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import nhom3.backend.examsystem.dto.StatisticDto;
import nhom3.backend.examsystem.service.AnswerSheetServices;
import nhom3.backend.examsystem.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("admin/dashboard_admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class DashboardController {
    @Autowired
    private EntityManager entityManager;
    private final ExamService examService;
    // thống kê tất cả các bài kiểm tra, điểm trung bình mỗi bài, tỉ lệ làm bài
    private int count = 5;
    @GetMapping("/ListExam")
    public ResponseEntity<?> getAllExams(){
        return examService.getAllExams();
    }
}
