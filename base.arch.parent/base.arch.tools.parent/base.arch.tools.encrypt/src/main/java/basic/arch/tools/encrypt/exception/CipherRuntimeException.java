package basic.arch.tools.encrypt.exception;

public class CipherRuntimeException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CipherRuntimeException() {
        super();
    }

    public CipherRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CipherRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CipherRuntimeException(String message) {
        super(message);
    }

    public CipherRuntimeException(Throwable cause) {
        super(cause);
    }

    
}
