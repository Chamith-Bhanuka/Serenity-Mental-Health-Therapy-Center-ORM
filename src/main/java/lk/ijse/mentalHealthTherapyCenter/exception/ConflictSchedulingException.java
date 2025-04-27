package lk.ijse.mentalHealthTherapyCenter.exception;

public class ConflictSchedulingException extends RuntimeException {
  public ConflictSchedulingException(String message) {
    super("Scheduling Conflict: " + message);
  }
}
