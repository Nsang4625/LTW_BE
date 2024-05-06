package nhom3.backend.examsystem.dto;

import java.util.ArrayList;
import java.util.List;

public class StatisticDto {
    private Double tyLeHoanThanh;
    private Double diemtb;
    private List<String> phanPhoiDiem;

    public StatisticDto()
    {

    }

    public StatisticDto(Double tyLeHoanThanh, Double diemtb, List<String> phanPhoiDiem) {
        this.tyLeHoanThanh = tyLeHoanThanh;
        this.diemtb = diemtb;
        this.phanPhoiDiem = phanPhoiDiem;
    }

    public Double getTyLeHoanThanh() {
        return tyLeHoanThanh;
    }

    public void setTyLeHoanThanh(Double tyLeHoanThanh) {
        this.tyLeHoanThanh = tyLeHoanThanh;
    }

    public Double getDiemtb() {
        return diemtb;
    }

    public void setDiemtb(Double diemtb) {
        this.diemtb = diemtb;
    }

    public List<String> getPhanPhoiDiem() {
        return phanPhoiDiem;
    }

    public void setPhanPhoiDiem(List<String> phanPhoiDiem) {
        this.phanPhoiDiem = phanPhoiDiem;
    }

}
