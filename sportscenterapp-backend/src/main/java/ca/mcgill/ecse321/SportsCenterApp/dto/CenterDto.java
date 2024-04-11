package ca.mcgill.ecse321.SportsCenterApp.dto;

public class CenterDto {

    private Integer id;
    private String weekSchedule;
    private String weekendSchedule;
    private String adress;

    
    public CenterDto(){

    }

    public CenterDto(Integer id, String weekSchedule, String weekendSchedule, String adress) {
        this.id = id;
        this.weekSchedule = weekSchedule;
        this.weekendSchedule = weekendSchedule;
        this.adress = adress;
    }

    public Integer getId() {
        return id;
    }

   
    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getWeekSchedule() {
        return weekSchedule;
    }

    
    public void setWeekSchedule(String weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    
    public String getWeekendSchedule() {
        return weekendSchedule;
    }

   
    public void setWeekendSchedule(String weekendSchedule) {
        this.weekendSchedule = weekendSchedule;
    }

    public void setAdress(String adress){
        this.adress = adress;
    }

    public String getAdress(){
        return adress;

    }
}
