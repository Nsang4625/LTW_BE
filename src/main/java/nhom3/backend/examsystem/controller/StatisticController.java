package nhom3.backend.examsystem.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import nhom3.backend.examsystem.dto.ExamDto;
import nhom3.backend.examsystem.model.AnswerSheet;
import nhom3.backend.examsystem.model.Exam;
import nhom3.backend.examsystem.service.AnswerSheetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {
    @Autowired
    private EntityManager entityManager;

    private final AnswerSheetServices answerSheetServices;
    // thống kê tất cả các bài kiểm tra, điểm trung bình mỗi bài, tỉ lệ làm bài
    private int count = 5;
    
    public StatisticController(AnswerSheetServices answerSheetServices) {
    	this.answerSheetServices = answerSheetServices;
    }

    @GetMapping
    public List<ExamDto> findAllExamsWithStats() {
        List<ExamDto> exams = new ArrayList<>();
        List<Object[]> results = entityManager.createNativeQuery(
                "SELECT e, AVG(a.result), COUNT(a) FROM exam e LEFT JOIN answer_sheet a ON e.id = a.exam_id GROUP BY e.id"
        ).getResultList();

        for (Object[] result : results) {
            Exam exam = (Exam) result[0];
            Double avgResult = (Double) result[1];
            Long totalAnswersheets = (Long) result[2];

            ExamDto examDto = new ExamDto();
            examDto.setId(exam.getExamId());
            examDto.setName(exam.getExamName());
            examDto.setType(exam.getExamType());
            examDto.setAvgResult(avgResult);
            examDto.setAnswerSheetRatio(totalAnswersheets.doubleValue() / count);

            exams.add(examDto);
        }

        return exams;
    }
    // xem danh sách kết quả tất cả bài thi của 1 sinh viên tìm bằng mã sinh viên
    @GetMapping("/{userId}")
    public List<AnswerSheet> findAllAnswerSheetsByUserId(@PathVariable("userId") long userId) {
        String query = "SELECT a FROM AnswerSheet a WHERE a.userId = :userId";
        Query q = entityManager.createQuery(query);
        q.setParameter("userId", userId);

        List<AnswerSheet> answersheets = q.getResultList();

        return answersheets;
    }
}
