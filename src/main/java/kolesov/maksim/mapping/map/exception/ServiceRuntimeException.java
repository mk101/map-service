package kolesov.maksim.mapping.map.exception;

public class ServiceRuntimeException extends RuntimeException {

    public ServiceRuntimeException(String message) {
        super(message);
    }

    public ServiceRuntimeException(Throwable cause) {
        super(cause);
    }

    public ServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
