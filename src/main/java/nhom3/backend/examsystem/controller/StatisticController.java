package nhom3.backend.examsystem.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import nhom3.backend.examsystem.dto.ExamDto;
import nhom3.backend.examsystem.dto.StatisticDto;
import nhom3.backend.examsystem.dto.StatisticStudentDto;
import nhom3.backend.examsystem.model.AnswerSheet;
import nhom3.backend.examsystem.model.Exam;
import nhom3.backend.examsystem.service.AnswerSheetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.DoubleBuffer;
import java.util.*;

@RestController
@RequestMapping("/admin/statistic")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StatisticController {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    };
    @Autowired
    private EntityManager entityManager;

    private final AnswerSheetServices answerSheetServices;
    

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
            Integer numOfCorr = (Integer) result[2];

            Integer numOfQues = ((Number)entityManager.createNativeQuery("SELECT count(id) FROM question where exam_id = " + id)
                    .getSingleResult()).intValue();
            if (numOfCorr != null && numOfQues != null)
            {
                Double mark = (Double.valueOf(numOfCorr) / Double.valueOf(numOfQues))*10;
                mark = round(mark, 1);
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

        }
        List <Double> sortedKeys = new ArrayList(mp.keySet());
        Collections.sort(sortedKeys);
        List<String> phanPhoiDiem = new ArrayList<>();
        for (Double key : sortedKeys)
        {
            String tmp = String.valueOf(key) + ":" + String.valueOf(mp.get(key));
            phanPhoiDiem.add(tmp);
        }
        diemTB = diemTB / count;
        diemTB = round(diemTB, 1);


        int numOfStudent = ((Number)entityManager.createNativeQuery("SELECT count(u.username) From user u left join user_role ur ON u.user_id = ur.user_id left join role r ON ur.role_id = r.role_id WHERE r.authority = 'USER'")
                .getSingleResult()).intValue();
        Integer numOfExamDone =  count;
        Integer numOfExam = ((Number)entityManager.createNativeQuery("SELECT count(id) From exam")
                .getSingleResult()).intValue();
        Double tiLeHoanThanh = numOfExamDone*1.0/(numOfStudent*1.0 * numOfExam);
        tiLeHoanThanh = round(tiLeHoanThanh, 1);
        tiLeHoanThanh = tiLeHoanThanh*100;
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
        try {
            List<Object[]> results = entityManager.createNativeQuery(
                    "SELECT e.id, e.name, a.result FROM exam e LEFT JOIN answer_sheet a ON e.id = a.exam_id where e.id = " + examId
            ).getResultList();
            for (Object[] result : results) {
                Long id = (Long) result[0];
                String name = (String) result[1];
                Integer numOfCorr = (Integer) result[2];

                Integer numOfQues = ((Number)entityManager.createNativeQuery("SELECT count(id) FROM question where exam_id = " + id)
                        .getSingleResult()).intValue();
                if (numOfCorr != null && numOfQues != null)
                {
                    Double mark = (Double.valueOf(numOfCorr) / Double.valueOf(numOfQues)) * 10;
                    mark = round(mark, 1);
                    if (mark == null) {
                        continue;
                    } else {
                        diemTB += mark;
                        count++;
                        if (!mp.containsKey(mark)) {
                            mp.put(mark, 1);
                        } else {
                            mp.put(mark, mp.get(mark) + 1);
                        }
                    }
                }
            }
            List <Double> sortedKeys = new ArrayList(mp.keySet());
            Collections.sort(sortedKeys);
            List<String> phanPhoiDiem = new ArrayList<>();
            for (Double key : sortedKeys)
            {
                String tmp = String.valueOf(key) + ":" + String.valueOf(mp.get(key));
                phanPhoiDiem.add(tmp);
            }
            diemTB = diemTB / count;
            diemTB = round(diemTB, 1);


            int numOfStudent = ((Number)entityManager.createNativeQuery("SELECT count(u.username) From user u left join user_role ur ON u.user_id = ur.user_id left join role r ON ur.role_id = r.role_id WHERE r.authority = 'USER'")
                    .getSingleResult()).intValue();
            Integer numOfExamDone =  count;
            Double tiLeHoanThanh = numOfExamDone*1.0/numOfStudent;
            tiLeHoanThanh = round(tiLeHoanThanh, 1);
            tiLeHoanThanh = tiLeHoanThanh*100;

            Integer tongSolanThamGia = count;
            StatisticDto st = new StatisticDto(tongSolanThamGia, tiLeHoanThanh, diemTB, phanPhoiDiem);
            return st;
        }
        catch (Exception e) {
            List<String> phanPhoiDiem = new ArrayList<>();
            StatisticDto st = new StatisticDto(0, 0.0, diemTB, phanPhoiDiem);
            return st;
        }

    }

    // xem danh sách kết quả tất cả bài thi của 1 sinh viên tìm bằng mã sinh viên
    @GetMapping("/student/{userId}")
    public List<Object> findAllAnswerSheetsByUserId(@PathVariable("userId") long userId) {
        List<Object> ls = new ArrayList<>();
        try {
        String name = this.entityManager.createNativeQuery("SELECT u.username From user u WHERE u.user_id = "+ String.valueOf(userId)).getSingleResult().toString();
        ls.add(name);
        String query = "SELECT a.exam_id, e.name, a.result FROM Answer_Sheet a left join exam e ON a.exam_id = e.id WHERE a.user_id =" + String.valueOf(userId);
        Query q = entityManager.createNativeQuery(query);
        List<Object[]> results = q.getResultList();
        for (Object[] result : results) {
            Long examId = (Long) result[0];
            String examName = (String) result[1];
            Integer numOfCorr = (Integer) result[2];

            Integer numOfQues = ((Number)entityManager.createNativeQuery("SELECT count(id) FROM question where exam_id = " + examId)
                    .getSingleResult()).intValue();
            Double mark = (Double.valueOf(numOfCorr) / Double.valueOf(numOfQues))*10;
            mark = round(mark, 1);
            StatisticStudentDto ssd = new StatisticStudentDto(examId, examName, mark);
            ls.add(ssd);
        }
        }
        catch (Exception e) {
            ls.add(e);
        }

        return ls;
    }



    @GetMapping("/exam-result/{examId}")
    public Double getExamResult(@PathVariable("examId") long examId){
        int userId = 2;
        String query = "select result from answer_sheet where answer_sheet.exam_id = " + examId + " and answer_sheet.user_id = " + userId;
        Object result = entityManager.createNativeQuery("SELECT result FROM quiz.answer_sheet WHERE answer_sheet.exam_id = " + examId + " AND answer_sheet.user_id = " + userId).getSingleResult();
        Double count = Double.valueOf(result.toString());
        Object total = entityManager.createNativeQuery("SELECT count(*) FROM question WHERE question.exam_id = " + examId);
        Double total2 = Double.valueOf(total.toString());
        return count / total2;
    }

//    @GetMapping("/exam-result/{examId}/details")
//    public List<Object> getExamResultDetals(@PathVariable("examId") long examId) {
//        int userId = 2;
//        Object answerSheet = entityManager.createNativeQuery("SELECT id FROM answer_sheet WHERE answer_sheet.exam_id = " + examId + " AND answer_sheet.user_id = " + userId).getSingleResult();
//        Double answerSheetId = (Double) answerSheet;
//        List<Object> results = entityManager.createNativeQuery("SELECT * FROM answer WHERE answer.answer_sheet_id = " + answerSheetId + "").getResultList();
//    }
}
