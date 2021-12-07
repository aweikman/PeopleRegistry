package springboot.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.model.AuditTrail;
import springboot.model.People;
import springboot.repository.AuditTrailRepository;
import springboot.repository.PeopleRepository;
import springboot.repository.SessionsRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class PeopleController {
    private static final Logger LOGGER = LogManager.getLogger();

    private PeopleRepository peopleRepository;
    private SessionsRepository sessionRepository;
    private AuditTrailRepository auditTrailRepository;

    public PeopleController(PeopleRepository peopleRepository, SessionsRepository sessionRepository, AuditTrailRepository auditTrailRepository) {
        this.peopleRepository = peopleRepository;
        this.sessionRepository = sessionRepository;
        this.auditTrailRepository = auditTrailRepository;
    }


    @GetMapping("/people")
    public ResponseEntity<Page<People>> fetchPeople(@RequestHeader HttpHeaders headers, @RequestParam(value = "pageNum", required = false) int pageNum, @RequestParam(value = "lastName", required = false) String lastName) {

        // initialize token based on header
        String sessionToken = headers.get("authorization").get(0);

        // if token is not correct or not present return 401
        if(sessionRepository.findById(sessionToken).isEmpty() || !headers.containsKey("authorization")){
           return new ResponseEntity("Session token is invalid or missing.", HttpStatus.valueOf(401));
        }
//        lastName = "";
//        pageNum = 1;
        Pageable firstTen = PageRequest.of(pageNum,10);
        // return 200 and fetch list of people from the db
//        List<People> person = peopleRepository.findAll();
        return new ResponseEntity<>(peopleRepository.findAllByLastNameStartingWith(lastName, firstTen), HttpStatus.valueOf(200));
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

        int userId = sessionRepository.findById(sessionToken).get().getUserId();

        //add audit trail based on user information
        AuditTrail auditTrail = new AuditTrail("added", userId, person.getId(), Instant.now());
        try {
            auditTrailRepository.save(auditTrail);
        }catch(Exception e){
            JSONObject j = new JSONObject();
            j.put("error",e.getMessage());
            return new ResponseEntity<>(j.toString(), HttpStatus.valueOf(400));
        }

        return new ResponseEntity<>(null, HttpStatus.valueOf(200));
    }

    @PutMapping("/people/{id}")
    @ResponseBody
    public Object updatePerson(@PathVariable("id") int personId, @RequestBody People person, @RequestHeader HttpHeaders headers) {

        // initialize token based on header
        String sessionToken = headers.get("authorization").get(0);

        //setting comparison variables for cases further down to compare old values to new
        String oldLastName = peopleRepository.findById(personId).get().getLastName();
        String oldFirstName = peopleRepository.findById(personId).get().getFirstName();
        String oldDateofBirth = peopleRepository.findById(personId).get().getDateOfBirth();
        Instant lastModified = peopleRepository.findById(personId).get().getLastModified();

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

//        if(person.getLastModified().equals(lastModified)) {
//            // update person in table based on id
//            person = peopleRepository.save(person);
//        }
//        else {
//            return new ResponseEntity<>("Another user has just made a change to this record", HttpStatus.valueOf(409));
//        }
        //case statements for audittrail messages
        String changed_msg = "";
        if(!person.getLastName().equals(oldLastName)){
            changed_msg = changed_msg.concat("last Name changed from "+oldLastName+" to "+person.getLastName()+". ");
        }
        if(!person.getFirstName().equals(oldFirstName)){
            changed_msg = changed_msg.concat("first name changed from "+oldFirstName+" to "+person.getFirstName()+". ");
        }
        if(!person.getDateOfBirth().equals(oldDateofBirth)){
            changed_msg = changed_msg.concat("dob changed from "+oldDateofBirth+" to "+person.getDateOfBirth()+". ");
        }
        if (changed_msg == null){
            return new ResponseEntity<>(null, HttpStatus.valueOf(200));
        }

        //update audit trail with information based on user and modified data
        int userId = sessionRepository.findById(sessionToken).get().getUserId();
        AuditTrail auditTrail = new AuditTrail(changed_msg, userId, person.getId(), Instant.now());
        try {
            auditTrailRepository.save(auditTrail);
        }catch(Exception e){
            JSONObject j = new JSONObject();
            j.put("error",e.getMessage());
            return new ResponseEntity<>(j.toString(), HttpStatus.valueOf(400));
        }

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

    @GetMapping("/people/{id}/audittrail")
    @ResponseBody
    public ResponseEntity<List<AuditTrail>> fetchAuditTrail(@RequestHeader HttpHeaders headers, @PathVariable int id){
        if(!headers.containsKey("authorization")){
            return new ResponseEntity<>(null, HttpStatus.valueOf(401));
        }

        // initialize token based on header
        String token = headers.get("authorization").get(0);

        // if token is not correct or not present return 401
        if (!sessionRepository.findById(token).isPresent()){
            return new ResponseEntity<>(null, HttpStatus.valueOf(401));
        }
        // if person does not exist based on id return 404
        Optional<People> person = peopleRepository.findById(id);
        if(!person.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.valueOf(404));
        }
        // if person exists fetch info and put into response body and return 200
        return new ResponseEntity<>(auditTrailRepository.findAllByPersonId(person.get().getId()), HttpStatus.valueOf(200));

    }
}
