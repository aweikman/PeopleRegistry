package springboot.services;

import pw_hash.HashUtils;
import springboot.model.Sessions;
import springboot.model.Users;
import springboot.repository.SessionsRepository;
import springboot.repository.UsersRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.repository.SessionsRepository;
import springboot.repository.UsersRepository;

import java.util.List;

@RestController
public class LoginController {

    // Checks the user table in db
    // IF user/pass is correct, CREATES a new token id (generate)
    // If response is correct, generates the token, stores the new token alongside username inside session table
    // Progress through program

    // Initializing our logger
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private UsersRepository usersRepository;
    private SessionsRepository sessionRepository;

    public LoginController(UsersRepository usersRepo, SessionsRepository sessionRepository) {
        this.usersRepository = usersRepo;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody Users user) throws JSONException {
        // Calls our search functions from UsersRepos (SQL Queries)
        List<Users> loginAttempt = usersRepository.findUserByUsernameAndPassword(user.getUsername(), HashUtils.getCryptoHash(user.getPassword(), "SHA-256"));
        List<Integer> newUserID = usersRepository.findIDByUsername(user.getUsername());

        // Checking if the login attempt is empty (IE No request)
        if (loginAttempt.isEmpty()) {
            return new ResponseEntity<>("User credentials are invalid or null. Please try again.", HttpStatus.valueOf(401));
        } else {

            // Creating a session object
            String newUserToken = HashUtils.getCryptoHash(user.getPassword() + "" + System.currentTimeMillis(), "SHA-256");
            Sessions session = new Sessions();
            session.setToken(newUserToken);
            session.setUserId(newUserID.get(0));
            sessionRepository.save(session);

            //Retrieve JObject with Token (maybe user?)
            JSONObject newToken = new JSONObject();
            newToken.put("token", session.getToken());

            return new ResponseEntity<>( user.getUsername() +"'s Token ID:  " + newToken.toString(), HttpStatus.valueOf(200));
        }
    }
}
