package base.arch.component.exception;

public class ServiceException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3379844841794094610L;

    /**
     * 异常类型
     */
    private String exceptionType;

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * 异常描述
     */
    private String description = "";

    private String businessModule = "";

    private String errorCode = "";

    public ServiceException(String exceptionType, String description, String businessModule, String errorCode, Throwable cause) {
        super(description, cause);
        this.exceptionType = exceptionType;
        this.description = description;
        this.businessModule = businessModule;
        this.errorCode = errorCode;
    }

    public ServiceException(String description, String errorCode, Throwable cause) {
        super(cause);
        this.description = description;
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public String getBusinessModule() {
        return businessModule;
    }

    public void setBusinessModule(String businessModule) {
        this.businessModule = businessModule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
