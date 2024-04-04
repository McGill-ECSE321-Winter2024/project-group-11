package ca.mcgill.ecse321.SportsCenterApp.controllers;

import ca.mcgill.ecse321.SportsCenterApp.dto.SessionDto;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import ca.mcgill.ecse321.SportsCenterApp.services.SessionService;
import ca.mcgill.ecse321.SportsCenterApp.utilities.DtoConverter;
import org.springframework.http.HttpStatus;
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
            List<SessionDto> sessions = sessionService.getAllSessions().stream().map(DtoConverter::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(sessions, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSession(@PathVariable("id") Integer id) {
        try {
            SessionDto session = DtoConverter.convertToDto(sessionService.getSession(id));
            return new ResponseEntity<>(session, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    //TODO maybe dont allow instructor to be updated. (instructor update handled by dropInstructor, addInstructor)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSession(@PathVariable("id") Integer id, @RequestBody SessionDto sessionDto) {
        try {
            @SuppressWarnings("unused")
            int updateCount = sessionService.updateSession(sessionDto.getDate(), sessionDto.getStartTime(), sessionDto.getEndTime(), sessionDto.getPrice(),
                            sessionDto.getRemainingCapacity(), sessionDto.getRoomNumber(), sessionDto.getClassType().getId(), sessionDto.getId());
            return new ResponseEntity<>(updateCount, HttpStatus.OK);
        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/instructor/{instructorId}")
    public ResponseEntity<?> addInstructorToSession(@PathVariable("id") Integer id, @PathVariable("instructorId") Integer instructorId) {
        try {
            Session success = sessionService.addInstructorToSession(id, instructorId);

            return new ResponseEntity<>(DtoConverter.convertToDto(success), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}/instructor")
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
            List<Session> session = sessionService.getAllSessionsByInstructorId(id);
            List<SessionDto> sessions = session.stream().map(DtoConverter::convertToDto).toList();
            return new ResponseEntity<>(sessionService.getAllSessionsByInstructorId(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/")
    public ResponseEntity<?> createSession(@RequestBody SessionDto sessionDto) {
        try {
            Session session = sessionService.createSession(sessionDto.getDate(), sessionDto.getStartTime(), sessionDto.getEndTime(), sessionDto.getPrice(),
                    sessionDto.getRemainingCapacity(), sessionDto.getRoomNumber(), sessionDto.getInstructor().getId() ,sessionDto.getClassType().getId());
            return new ResponseEntity<>(session, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable("id") Integer id) {
        try {
            sessionService.deleteSession(id);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting", HttpStatus.BAD_REQUEST);
        }
    }

}
