package ca.mcgill.ecse321.SportsCenterApp.dto;

import ca.mcgill.ecse321.SportsCenterApp.model.*;
import java.sql.Date;
import java.sql.Time;


public class RegistrationDto {
    private Date aDate;
    private Time aTime;
    private Customer aCustomer;
    private Session aSession;
    private Integer aId;


    public RegistrationDto(){
        super();
    }

    public RegistrationDto(Date aDate, Time aTime, Customer aCustomer, Session aSession){
        this.aDate = aDate;
        this.aTime = aTime;
        this.aCustomer = aCustomer;
        this.aSession = aSession;

    }

    public Date getDate()
    {
      return aDate;
    }
  
    public Time getTime()
    {
      return aTime;
    }
    public void setDate(Date aDate)
    {
        this.aDate = aDate;
    }
  
    public void setTime(Time aTime)
    {
        this.aTime = aTime;
    }

    //should setters return void?

    public Customer getCustomer(){
        return aCustomer;
    }

    public Session getSession(){
        return aSession;
    }

    public Integer getId(){
        return aId;
    }

    public void SetCustomer(Customer aCustomer){
        this.aCustomer = aCustomer;
    }

    public void setSession(Session aSession){
        this.aSession = aSession;
    }

    public void setId(Integer aId){
        this.aId = aId;
    }









}
