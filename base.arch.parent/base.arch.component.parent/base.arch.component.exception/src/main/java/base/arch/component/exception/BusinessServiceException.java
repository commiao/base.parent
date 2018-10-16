package base.arch.component.exception;

import base.arch.component.exception.api.ExceptionConfiguration;

public class BusinessServiceException extends UnifiedException {

    public BusinessServiceException(){}
    
    public BusinessServiceException(String description, String errorCode, Object dataModel, Throwable cause) {
        super(description, errorCode, dataModel, cause);
        this.setExceptionType(ExceptionConfiguration.EXCEPTION_TYPE_BUSINESS);
    }

    public BusinessServiceException(String description, String errorCode, Throwable cause) {
        super(description, errorCode, cause);
        this.setExceptionType(ExceptionConfiguration.EXCEPTION_TYPE_BUSINESS);
    }
    

    public BusinessServiceException(String description, String businessModule, String errorCode, Throwable cause) {
        super(description, businessModule, errorCode, cause);
    }



    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6215120336436899869L;

}
