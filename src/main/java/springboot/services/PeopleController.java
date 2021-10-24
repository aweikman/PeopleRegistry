package springboot.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.model.People;
import springboot.repository.PeopleRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PeopleController {
    private static final Logger logger = LogManager.getLogger();

    private PeopleRepository peopleRepository;

    public PeopleController(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @GetMapping("/people")
    public ResponseEntity<List<People>> fetchPeople(@RequestHeader Map<String, String> headers) {
        // get the list of people models from the repository
        List<People> person = peopleRepository.findAll();
        return new ResponseEntity<>(person, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deletePerson(@PathVariable("id") int personId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<String> deletePerson(@RequestHeader Map<String, String> headers, @PathVariable("id") Long personId) {
        // header key loses capitalization!?!
//        if(headers.containsKey("authorization")) {
//            if(!headers.get("authorization").equals("I am a session token")) {
//                return new ResponseEntity<>("Hey, I don't know you", HttpStatus.valueOf(401));
//            }
//        } else {
//            return new ResponseEntity<>("x Hey, I don't know you", HttpStatus.valueOf(401));
//        }
//
//        // return "Deleting person: " + personId;
        Optional<People> person = peopleRepository.findById(personId);
        if(person.isPresent())
            return new ResponseEntity<>(person.dele, HttpStatus.valueOf(200));
        else
            return new ResponseEntity<>(new Cat(), HttpStatus.valueOf(404));
        return new ResponseEntity<>("Deleting person: " + personId, HttpStatus.valueOf(200));
    }

//    @GetMapping("/people/{id}")
//    public ResponseEntity<People> fetchPersonById(@PathVariable long id, @RequestHeader Map<String, String> headers) {
//        Optional<People> people = peopleRepository.findById(id);
//        if(people.isPresent())
//            return new ResponseEntity<>(people.get(), HttpStatus.valueOf(200));
//        else
//            return new ResponseEntity<>(new People(), HttpStatus.valueOf(404));
//    }

    // TODO: add post, put, delete
}
