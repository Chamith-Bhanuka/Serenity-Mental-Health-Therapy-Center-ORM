package lk.ijse.mentalHealthTherapyCenter.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super("Login Error: " + message);
    }
}
