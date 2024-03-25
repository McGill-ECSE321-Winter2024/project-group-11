package ca.mcgill.ecse321.SportsCenterApp.dto;

import ca.mcgill.ecse321.SportsCenterApp.model.*;
import java.sql.Date;
import java.sql.Time;


public class RegistrationDto {
    private Date date;
    private Time time;
    private CustomerDto customer;
    private SessionDto session;
    private Integer id;


    public RegistrationDto(){
        super();
    }

    public RegistrationDto(Date date, Time time, CustomerDto customer, SessionDto session) {
        this.date = date;
        this.time = time;
        this.customer = customer;
        this.session = session;
    }

    public RegistrationDto(Date date, Time time, CustomerDto customer, SessionDto session, Integer id) {
        this.date = date;
        this.time = time;
        this.customer = customer;
        this.session = session;
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public void setSession(SessionDto session) {
        this.session = session;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public SessionDto getSession() {
        return session;
    }

    public Integer getId() {
        return id;
    }
}
