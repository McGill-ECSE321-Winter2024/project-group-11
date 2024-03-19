package ca.mcgill.ecse321.SportsCenterApp.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;
import java.sql.Time;

@SuppressWarnings("unused")
public class SessionDto {
    private Date date;
    private Time startTime;
    private Time endTime;
    private float price;
    private Integer remainingCapacity;
    private Integer roomNumber;
    private Integer id;
    private ClassTypeDto classType;
    private InstructorDto instructor;

    public SessionDto(Date date, Time startTime, Time endTime, float price, Integer remainingCapacity, Integer roomNumber, InstructorDto instructorDto, ClassTypeDto classTypeDto, Integer id) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.remainingCapacity = remainingCapacity;
        this.roomNumber = roomNumber;
        this.id = id;
        this.instructor = instructorDto;
        this.classType = classTypeDto;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public float getPrice() {
        return price;
    }

    public ClassTypeDto getClassType() {
        return classType;
    }

    public InstructorDto getInstructor() {
        return instructor;
    }

    public Integer getRemainingCapacity() {
        return remainingCapacity;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public Integer getId() {
        return id;
    }
}
