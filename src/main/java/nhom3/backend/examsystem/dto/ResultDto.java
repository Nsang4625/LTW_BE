package nhom3.backend.examsystem.dto;

import lombok.Getter;
import lombok.Setter;

public class ResultDto {
    @Getter
    @Setter
    private Double count;

    @Getter
    @Setter
    private Double total;

    public ResultDto(Double count, Double total){
        this.count = count;
        this.total = total;
    }
}
