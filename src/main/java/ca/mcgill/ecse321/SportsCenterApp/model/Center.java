package ca.mcgill.ecse321.SportsCenterApp.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String weekSchedule;
    private String weekendSchedule;
    private String adress;

    // Constructors, getters, and setters

    public Center() {
        
    }

    public Center(String weekSchedule, String weekendSchedule, String adress) {
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

    public String getweekendSchedule() {
        return weekendSchedule;
    }

    public void setweekendSchedule(String weekendSchedule) {
        this.weekendSchedule = weekendSchedule;
    }


    public void setAdress(String adress){
        this.adress = adress;
    }

    public String getAdress(){
        return adress;

    }

}


