package com.coursed.dto;

/**
 * Created by Hexray on 11.12.2016.
 */
public class YearDTO {

    private Long id;
    private Integer beginYear;
    private Integer endYear;

    public YearDTO() {
    }

    public YearDTO(Long id, Integer beginYear, Integer endYear) {
        this.id = id;
        this.beginYear = beginYear;
        this.endYear = endYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }
}
