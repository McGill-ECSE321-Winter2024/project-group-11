package ca.mcgill.ecse321.SportsCenterApp.services;
    import org.springframework.beans.factory.annotation.Autowired;
    
    import ca.mcgill.ecse321.SportsCenterApp.model.*;
    import ca.mcgill.ecse321.SportsCenterApp.repository.*;
    import jakarta.transaction.Transactional;

public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Transactional
    public Owner createOwner(String aFirstName, String aLastName, String aEmail, String aPassword, Integer aId){
        Owner owner = new Owner(aLastName, aLastName, aEmail, aPassword, aId);
        return owner;
    }
    
    @Transactional
    public Owner getOwner(Integer id){
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No owner found with id: " + id));
        ownerRepository.save(owner);
        return owner;
    }

    @Transactional
    public String deleteOwner(Integer id){
        ownerRepository.deleteById(id);
        return "Owner with id  " + id + " has been deleted";
    }
}
