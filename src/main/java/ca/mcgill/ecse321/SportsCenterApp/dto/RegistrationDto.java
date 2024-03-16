package ca.mcgill.ecse321.SportsCenterApp.dto;

import java.sql.Date;
import java.sql.Time;

public class RegistrationDto {

    private Date date;
    private Time time;

    public Date getDate {
        return this.date;
    }

    public Date getTime {
        return this.time;
    }

    public RegistrationDto(Date date, Time time) {
        this.date = date;
        this.time = time;
    }

    public RegistrationDto() {
        this.date = Date(System.currentTimeMillis());
        this.time = Time(System.currentTimeMillis());
    }
}