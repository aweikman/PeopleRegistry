package wsclient;

public class DogException extends RuntimeException {

    public DogException(Exception e) {
        super(e);
    }

    public DogException(String msg) {
        super(msg);
    }
}
