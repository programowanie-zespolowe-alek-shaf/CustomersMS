package pl.agh.customers.common.exception;

public abstract class CustomException extends Exception {
    private static final long serialVersionUID = -7806029002430564887L;

    private String message;

    public CustomException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}