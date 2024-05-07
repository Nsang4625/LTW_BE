package nhom3.backend.examsystem.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import nhom3.backend.examsystem.dto.ExamDto;
import nhom3.backend.examsystem.dto.StatisticDto;
import nhom3.backend.examsystem.dto.StatisticStudentDto;
import nhom3.backend.examsystem.model.AnswerSheet;
import nhom3.backend.examsystem.service.AnswerSheetServices;
import nhom3.backend.examsystem.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("admin/statistic")


@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StatisticController {
    @Autowired
    private EntityManager entityManager;
    private final ExamService examService;

    private final AnswerSheetServices answerSheetServices;
    
    public StatisticController(AnswerSheetServices answerSheetServices) {
    	this.answerSheetServices = answerSheetServices;
    }
    // thống kê tất cả các bài kiểm tra, điểm trung bình mỗi bài, tỉ lệ làm bài

    private int count = 5;
    @GetMapping("/")
    public StatisticDto  findAllExamsWithStats() {
        Double diemTB = 0.0;
        int count = 0;
        Map<Double, Integer> mp = new HashMap<>();
        List<Object[]> results = entityManager.createNativeQuery(
                "SELECT e.id, e.name, a.result FROM exam e LEFT JOIN answer_sheet a ON e.id = a.exam_id"
        ).getResultList();
        for (Object[] result : results) {
            Long id = (Long) result[0];
            String name = (String) result[1];
            Double mark = (Double) result[2];
            if (mark == null)
            {
                continue;
            }
            else
            {
                diemTB += mark;
                count++;
                if(!mp.containsKey(mark))
                {
                    mp.put(mark, 1);
                }
                else
                {
                    mp.put(mark, mp.get(mark) + 1);
                }
            }
        }
        List <Double> sortedKeys = new ArrayList(mp.keySet());
        Collections.sort(sortedKeys);
        List<String> phanPhoiDiem = new ArrayList<>();
        for (Double key : sortedKeys)
        {
            String tmp = String.valueOf(key) + " điểm: " + String.valueOf(mp.get(key));
            phanPhoiDiem.add(tmp);
        }
        diemTB = diemTB / count;



        int numOfStudent = ((Number)entityManager.createNativeQuery("SELECT count(u.username) From user u left join user_role ur ON u.user_id = ur.user_id left join role r ON ur.role_id = r.role_id WHERE r.authority = 'USER'")
                .getSingleResult()).intValue();
        Integer numOfExamDone =  count;
        Double tiLeHoanThanh = numOfExamDone*1.0/numOfStudent;

        Integer tongSolanThamGia = count;
        StatisticDto st = new StatisticDto(tongSolanThamGia, tiLeHoanThanh, diemTB, phanPhoiDiem);
        return st;
    }

    @GetMapping("/getAllNameExam")
    public List<String> getAllNameExam(){
        List<String> nameExam = new ArrayList<>();
        List<Object[]> results = entityManager.createNativeQuery(
                "SELECT id, name FROM exam"

        ).getResultList();
        for (Object[] result : results) {
            Long id = (Long) result[0];
            String name = (String) result[1];
            String tmp = String.valueOf(id) + ":" + name;
            nameExam.add(tmp);
        }
        return nameExam;
    }

    @GetMapping("/{examId}")
    public StatisticDto findAllExamsIDWithStats(@PathVariable("examId") Long examId) {
        Double diemTB = 0.0;
        int count = 0;
        Map<Double, Integer> mp = new HashMap<>();
        List<Object[]> results = entityManager.createNativeQuery(
                "SELECT e.id, e.name, a.result FROM exam e LEFT JOIN answer_sheet a ON e.id = a.exam_id where e.id = " + examId
        ).getResultList();
        for (Object[] result : results) {
            Long id = (Long) result[0];
            String name = (String) result[1];
            Double mark = (Double) result[2];
            if (mark == null)
            {
                continue;
            }
            else
            {
                diemTB += mark;
                count++;
                if(!mp.containsKey(mark))
                {
                    mp.put(mark, 1);
                }
                else
                {
                    mp.put(mark, mp.get(mark) + 1);
                }
            }
        }
        List <Double> sortedKeys = new ArrayList(mp.keySet());
        Collections.sort(sortedKeys);
        List<String> phanPhoiDiem = new ArrayList<>();
        for (Double key : sortedKeys)
        {
            String tmp = String.valueOf(key) + " điểm: " + String.valueOf(mp.get(key));
            phanPhoiDiem.add(tmp);
        }
        diemTB = diemTB / count;


        int numOfStudent = ((Number)entityManager.createNativeQuery("SELECT count(u.username) From user u left join user_role ur ON u.user_id = ur.user_id left join role r ON ur.role_id = r.role_id WHERE r.authority = 'USER'")
                .getSingleResult()).intValue();
        Integer numOfExamDone =  count;
        Double tiLeHoanThanh = numOfExamDone*1.0/numOfStudent;

        Integer tongSolanThamGia = count;
        StatisticDto st = new StatisticDto(tongSolanThamGia, tiLeHoanThanh, diemTB, phanPhoiDiem);
        return st;
    }

    // xem danh sách kết quả tất cả bài thi của 1 sinh viên tìm bằng mã sinh viên
    @GetMapping("/student/{userId}")
    public List<Object> findAllAnswerSheetsByUserId(@PathVariable("userId") long userId) {
        List<Object> ls = new ArrayList<>();
        String name = this.entityManager.createNativeQuery("SELECT u.username From user u WHERE u.user_id = "+ String.valueOf(userId)).getSingleResult().toString();
        ls.add(name);
        String query = "SELECT a.exam_id, e.name, a.result FROM Answer_Sheet a left join exam e ON a.exam_id = e.id WHERE a.user_id =" + String.valueOf(userId);
        Query q = entityManager.createNativeQuery(query);
        List<Object[]> results = q.getResultList();
        for (Object[] result : results) {
            Long examId = (Long) result[0];
            String examName = (String) result[1];
            Double mark = (Double) result[2];
            StatisticStudentDto ssd = new StatisticStudentDto(examId, examName, mark);
            ls.add(ssd);
        }
        return ls;
    }
}
