package springboot.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String helloWorld() {
        return "Greetings from Spring Boot! ";
    }

    @DeleteMapping("/people/{person_id}")
    public ResponseEntity<String> deletePerson(@RequestHeader Map<String, String> headers, @PathVariable("person_id") int personId) {
        // header key loses capitalization!?!
        if(headers.containsKey("authorization")) {
            if(!headers.get("authorization").equals("I am a session token")) {
                return new ResponseEntity<>("Hey, I don't know you", HttpStatus.valueOf(401));
            }
        } else {
            return new ResponseEntity<>("x Hey, I don't know you", HttpStatus.valueOf(401));
        }

        // return "Deleting person: " + personId;
        return new ResponseEntity<>("Deleting person: " + personId, HttpStatus.valueOf(200));
    }

//    @GetMapping("/hello")
//    @ResponseBody
//    public String helloWorld2(@RequestParam String name, @RequestParam int age) {
//        if(name.length() > 0)
//            return "Hello " + name;
//        else
//            return "Greetings from Spring Boot! ";
//    }


}