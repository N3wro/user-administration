package htl.stp.at.userverwaltung.domain.advice;

public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException()
    {
        super("404 User not found");
    }
}
