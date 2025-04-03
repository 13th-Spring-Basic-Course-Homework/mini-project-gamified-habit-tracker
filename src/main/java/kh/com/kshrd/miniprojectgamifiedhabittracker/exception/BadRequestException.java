package kh.com.kshrd.miniprojectgamifiedhabittracker.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
