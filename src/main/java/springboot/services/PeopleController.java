package springboot.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pw_hash.HashUtils;
import springboot.model.People;
import springboot.model.Users;
import springboot.repository.PeopleRepository;
import springboot.repository.SessionsRepository;
import springboot.repository.UsersRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PeopleController {
    private static final Logger logger = LogManager.getLogger();

    private PeopleRepository peopleRepository;
    private SessionsRepository sessionRepository;

    public PeopleController(PeopleRepository peopleRepository, SessionsRepository sessionRepository) {
        this.peopleRepository = peopleRepository;
        this.sessionRepository = sessionRepository;
    }


    @GetMapping("/people")
    public ResponseEntity<List<People>> fetchPeople(@RequestHeader HttpHeaders headers) {

        // initialize token based on header
        String sessionToken = headers.get("authorization").get(0);

        // if token is not correct or not present return 401
        if(sessionRepository.findById(sessionToken).isEmpty() || !headers.containsKey("authorization")){
            return new ResponseEntity("Session token is invalid or missing.", HttpStatus.valueOf(401));
        }

        // return 200 and fetch list of people from the db
        List<People> person = peopleRepository.findAll();
        return new ResponseEntity<>(person, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable("id") int personId, @RequestHeader HttpHeaders headers) {

        // initialize token based on header
        String sessionToken = headers.get("authorization").get(0);

        // if token is not correct or not present return 401
        if(sessionRepository.findById(sessionToken).isEmpty() || !headers.containsKey("authorization")){
            return new ResponseEntity("Session token is invalid or missing.", HttpStatus.valueOf(401));
        }

        // check to see if id does not exist and return 404
        if(peopleRepository.findById(personId).isEmpty())
        {
            return new ResponseEntity<>(null, HttpStatus.valueOf(404));
        }

        // if id does exist -> delete person from table
        Optional<People> person = peopleRepository.findById(personId);
        peopleRepository.delete(person.get());
        return new ResponseEntity<>("Deleting person: " + personId, HttpStatus.valueOf(200));
    }

    @PostMapping("/people")
    @ResponseBody
    public ResponseEntity<String> insertPerson(@RequestBody People person, @RequestHeader HttpHeaders headers){

        // initialize token based on header
        String sessionToken = headers.get("authorization").get(0);

        // if token is not correct or not present return 401
        if(sessionRepository.findById(sessionToken).isEmpty() || !headers.containsKey("authorization")){
            return new ResponseEntity("Session token is invalid or missing.", HttpStatus.valueOf(401));
        }


        // code for checking if entered date is after current date
        Date date = null;
        try {
            // set format for entered date
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            // instantiate date entered
            date = sdf.parse(person.getDateOfBirth());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // instantiate current date to compare entered date
        Date currentDate = new Date();

        // cases for invalid request body response
        if(person.getFirstName().length() < 1 || person.getFirstName().length() > 100 ||
                person.getLastName().length() < 1 || person.getLastName().length() > 100 || date.after(currentDate))
        {
            return new ResponseEntity<>("Request body is incorrect (e.g., bad field name, validation error, please enter date of birth MM-dd-yyyy)", HttpStatus.valueOf(400));
        }

        // else we are returning 200 and adding person to table
        peopleRepository.save(person);
        return new ResponseEntity<>(null, HttpStatus.valueOf(200));
    }

    @PutMapping("/people/{id}")
    @ResponseBody
    public ResponseEntity<String> updatePerson(@PathVariable("id") int personId, @RequestBody People person, @RequestHeader HttpHeaders headers) {

        // initialize token based on header
        String sessionToken = headers.get("authorization").get(0);

        // if token is not correct or not present return 401
        if(sessionRepository.findById(sessionToken).isEmpty() || !headers.containsKey("authorization")){
            return new ResponseEntity("Session token is invalid or missing.", HttpStatus.valueOf(401));
        }

        // code for checking if entered date is after current date
        Date date = null;
        try {
            // set format for entered date
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            // instantiate date entered
            date = sdf.parse(person.getDateOfBirth());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // instantiate current date to compare entered date
        Date currentDate = new Date();

        // cases for invalid request body response
        if(person.getFirstName().length() < 1 || person.getFirstName().length() > 100 ||
                person.getLastName().length() < 1 || person.getLastName().length() > 100 || date.after(currentDate))
        {
            return new ResponseEntity<>("Request body is incorrect (e.g., bad field name, validation error, please enter date of birth MM-dd-yyyy)", HttpStatus.valueOf(400));
        }

        // check to see if id does not exist and return 404
        else if(peopleRepository.findById(personId).isEmpty())
        {
            return new ResponseEntity<>(null, HttpStatus.valueOf(404));
        }

        // else we are returning 200 and updating table

        // we don't expect the incoming json to have the widget's id, so we set it
        person.setId(personId);

        // update person in table based on id
        person = peopleRepository.save(person);

        // return 200 response
        return new ResponseEntity<>(null, HttpStatus.valueOf(200));
    }

    @GetMapping("/people/{id}")
    @ResponseBody
    public ResponseEntity <String> fetchPerson(@RequestHeader HttpHeaders headers, @PathVariable("id") int personId) {
        Optional <People> person = peopleRepository.findById(personId);

        // initialize token based on header
        String sessionToken = headers.get("authorization").get(0);

        // if person does not exist based on id return 404
        if (peopleRepository.findById(personId).isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.valueOf(404));
        }
        // if token is not correct or not present return 401
        else if(sessionRepository.findById(sessionToken).isEmpty() || !headers.containsKey("authorization")){
            return new ResponseEntity("Session token is invalid or missing.", HttpStatus.valueOf(401));
        }

        // if person exists fetch info and put into response body and return 200
        return new ResponseEntity<>("Fetching Person with ID: " + personId + " " + peopleRepository.findById(personId),  HttpStatus.valueOf(200));
    }
}
