package ca.mcgill.ecse321.SportsCenterApp.controllers;

import ca.mcgill.ecse321.SportsCenterApp.dto.ClassTypeDto;
import ca.mcgill.ecse321.SportsCenterApp.dto.InstructorDto;
import ca.mcgill.ecse321.SportsCenterApp.dto.SessionDto;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import ca.mcgill.ecse321.SportsCenterApp.services.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/session")
public class SessionController {


    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public ResponseEntity<List<SessionDto>> getSessions() {
        try {
            List<SessionDto> sessions = sessionService.getAllSessions().stream().map(s -> convertToDto(s)).collect(Collectors.toList());
            System.out.println("HELO");
            return new ResponseEntity<>(sessions, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> getSession(@PathVariable("id") Integer id) {
        try {
            SessionDto session = convertToDto(sessionService.getSession(id));
            return new ResponseEntity<>(session, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    //TODO maybe dont allow instructor to be updated. (instructor update handled by dropInstructor, addInstructor)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSession(@PathVariable("id") Integer id, @RequestBody SessionDto sessionDto) {
        try {
            @SuppressWarnings("unused")
            int session = sessionService.updateSession(sessionDto.getDate(), sessionDto.getStartTime(), sessionDto.getEndTime(), sessionDto.getPrice(),
                            sessionDto.getRemainingCapacity(), sessionDto.getRoomNumber(), sessionDto.getClassType().getId(), sessionDto.getId());
            return new ResponseEntity<>(session, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/instructor/{instructorId}")
    public ResponseEntity<?> addInstructorToSession(@PathVariable("id") Integer id, @PathVariable("instructorId") Integer instructorId) {
        try {
            Session success = sessionService.addInstructorToSession(id, instructorId);

            return new ResponseEntity<>(convertToDto(success), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}/instructor/")
    public ResponseEntity<?> dropInstructorFromSession(@PathVariable("id") Integer id) {
        try {
            Integer status = sessionService.dropInstructorFromSession(id);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        }
    }

    //TODO findallsession by instructorID (for insturcot to see what sessions hes teaching)
    @GetMapping("/instructor/{id}")
    public ResponseEntity<?> getAllSessionsByInstructorId(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity<>(sessionService.getAllSessionsByInstructorId(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/")
    public ResponseEntity<?> createSession(@RequestBody SessionDto sessionDto) {
        try {
            System.out.println("trying");
            Session session = sessionService.createSession(sessionDto.getDate(), sessionDto.getStartTime(), sessionDto.getEndTime(), sessionDto.getPrice(),
                    sessionDto.getRemainingCapacity(), sessionDto.getRoomNumber(), sessionDto.getClassType().getId());
            return new ResponseEntity<>(session, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private SessionDto convertToDto(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("There is no session.");
        }
        ClassTypeDto classTypeDto = convertToDto(session.getClassType());
        InstructorDto instructorDto = convertToDto(session.getInstructor());


        return new SessionDto(session.getDate(), session.getStartTime(), session.getEndTime(), session.getPrice(), session.getRemainingCapacity(), session.getRoomNumber(), instructorDto, classTypeDto, session.getId());
    }

    private ClassTypeDto convertToDto(ClassType ClassType){
        if (ClassType == null) {
            throw new IllegalArgumentException("There is no such ClassType");
        }
        ClassTypeDto ClassTypeDto = new ClassTypeDto(ClassType.getName(), ClassType.getDescription(), ClassType.getApproved(), ClassType.getDifficultyLevel(), ClassType.getId());
        return ClassTypeDto;
    }

    private InstructorDto convertToDto(Instructor instructor){
        if (instructor == null) {
            throw new IllegalArgumentException("There is no such instructor");
        }
        InstructorDto instructorDto = new InstructorDto(instructor.getFirstName(), instructor.getLastName(),instructor.getEmail(), instructor.getPassword(), instructor.getId(), instructor.getYearsOfExperience(), instructor.getBiography());
        return instructorDto;
    }


}
