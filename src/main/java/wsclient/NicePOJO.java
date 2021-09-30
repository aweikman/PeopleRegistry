package wsclient;

import model.Dog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class NicePOJO {
    private static final String WS_URL = "http://localhost:8080";
    private static final Logger logger = LogManager.getLogger();

    public static void main(String [] args) {
        try {
            // get the dogs
            ArrayList<Dog> dogs = new DogGateway(WS_URL + "/dogs", "i am a session token")
                    .getDogs();
            // do something with the dogs
            System.out.println(dogs);


        } catch(DogException e) {
            logger.error(e.getMessage());
        }

    }
}
