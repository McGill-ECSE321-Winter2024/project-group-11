package ca.mcgill.ecse321.SportsCenterApp.services;
    import org.springframework.beans.factory.annotation.Autowired;
    
    import ca.mcgill.ecse321.SportsCenterApp.model.*;
    import ca.mcgill.ecse321.SportsCenterApp.repository.*;
    import jakarta.transaction.Transactional;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Transactional
    public Owner createOwner(String aFirstName, String aLastName, String aEmail, String aPassword,  String aToken){
        Owner owner = new Owner(aFirstName, aLastName, aEmail, aPassword, aToken);
        ownerRepository.save(owner);
        if (aEmail == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return owner;
    }
    
    @Transactional
    public Owner getOwner(Integer id){
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No owner found with id: " + id));

        return owner;
    }


    @Transactional
    public List<Owner> getAllOwners(){
        return toList(ownerRepository.findAll());
    }

    @Transactional
    public String deleteOwner(Integer id){
        ownerRepository.deleteById(id);
        return "Owner with id  " + id + " has been deleted";
    }


    @Transactional
    public Owner updateOwnerPassword(Integer id, String newPassword){
        if (!isValidPassword(newPassword)){
            throw new IllegalArgumentException("Invalid new password");
        }
        Owner owner = getOwner(id);

        owner.setPassword(newPassword);
        ownerRepository.save(owner);

        return owner;
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable){
            resultList.add(t);
        }
        return resultList;
    }
    public static boolean isValidPassword(String password) {
        String passwordpattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(passwordpattern);
        java.util.regex.Matcher m = p.matcher(password);
        return m.matches();

    }
}
