package nhom3.backend.examsystem.dto;

public class StatisticStudentDto {
    private Long id;
    private String examName;
    private Double mark;

    public StatisticStudentDto() {
    }

    public StatisticStudentDto(Long id, String examName, Double mark) {
        this.id = id;
        this.examName = examName;
        this.mark = mark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }
}
