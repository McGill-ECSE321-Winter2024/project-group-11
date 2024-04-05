package ca.mcgill.ecse321.SportsCenterApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse321.SportsCenterApp.repository.CenterRepository;
import ca.mcgill.ecse321.SportsCenterApp.model.Center;


import java.util.*;


@Service
public class CenterService {


    @Autowired
    CenterRepository centerRepository;



    @Transactional
    public Center createCenter(String weekSchedule, String weekendSchedule, String adress) {
        if (weekSchedule == null || weekSchedule.isEmpty()) {
            throw new IllegalArgumentException("Week schedule can't be empty!");
        }

        if (weekendSchedule == null || weekendSchedule.isEmpty()) {
            throw new IllegalArgumentException("Weekend schedule can't be empty!");
        }

        if (adress == null || adress.isEmpty()) {
            throw new IllegalArgumentException("Adress can't be empty!");
        }

        Center center = new Center(weekSchedule, weekendSchedule, adress);
        centerRepository.save(center);
        return center;
        
    }


    @Transactional
    public Center getCenter(Integer id){

        Optional<Center> center = centerRepository.findById(id);

        if (center.isPresent()){
            return center.get();
        }
        else {
            throw new IllegalArgumentException("Center not found for id: " + id);
        }
    }


    @Transactional
    public Center updateCenter(Integer id, String newWeekSchedule, String newWeekendSchedule, String newAdress){

        Optional<Center> centerOptional = centerRepository.findById(id);

        if (centerOptional.isPresent()){
            Center center =  centerOptional.get();

            if (newWeekSchedule != null) {
                if(newWeekSchedule.isEmpty()) {
                    throw new IllegalArgumentException("Week schedule cannot be empty!");
                }
                center.setWeekSchedule(newWeekSchedule);
            }
            if (newWeekendSchedule != null) {
                if (newWeekendSchedule.isEmpty()) {
                    throw new IllegalArgumentException("Weekend schedule cannot be empty!");
                }
                center.setweekendSchedule(newWeekendSchedule);
            }

            if (newAdress != null) {
                if (newAdress.isEmpty()) {
                    throw new IllegalArgumentException("Adress cannot be empty!");
                }
                center.setAdress(newAdress);
            }

            centerRepository.save(center);
            return center;
            
        }
        else {
            throw new IllegalArgumentException("Center not found for id: " + id);
        }

    }

    @Transactional
    public List<Center> getAllCenters(){
        return toList(centerRepository.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable){
            resultList.add(t);
        }
        return resultList;
    }


    @Transactional
    public void deleteCenter(Integer id){
        centerRepository.deleteById(id);
    }


}
    

