package ca.mcgill.ecse321.SportsCenterApp.dto;

import ca.mcgill.ecse321.SportsCenterApp.model.ClassType.DifficultyLevel;

public class ClassTypeDto {

    private String name;
    private String description;
    private boolean approved;
    private DifficultyLevel difficultyLevel;


    public ClassTypeDto(){

    }

    public ClassTypeDto(String name, String description, boolean approved, DifficultyLevel difficultyLevel){
        this.name = name;
        this.description = description;
        this.approved = approved;
        this.difficultyLevel = difficultyLevel;
    }
    

     
     public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getDescription() {
        return description;
    }

    
    public void setDescription(String description) {
        this.description = description;
    }

  
    public boolean isApproved() {
        return approved;
    }


    public void setApproved(boolean approved) {
        this.approved = approved;
    }

 
    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

  
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

}
