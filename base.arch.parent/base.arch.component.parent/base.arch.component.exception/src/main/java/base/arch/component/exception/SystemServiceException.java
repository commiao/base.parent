package base.arch.component.exception;

import base.arch.component.exception.api.ExceptionConfiguration;

public class SystemServiceException extends RuntimeException {

	public static final String SYS_ERROR_CODING = "GL99999";

	/**
	 * 异常描述
	 */
	private String description = "";

	/**
	 * 业务模块
	 */
	private String businessModule = "";

	private String exceptionType = "";

	private String errorCode = SYS_ERROR_CODING;

	public SystemServiceException(String description, String businessModule,
			Throwable cause) {
		super(description, cause);
		this.description = description;
		this.businessModule = businessModule;
		this.setExceptionType(ExceptionConfiguration.EXCEPTION_TYPE_SYSTEM);
	}

	public SystemServiceException(String description, Throwable cause) {

		this.setExceptionType(ExceptionConfiguration.EXCEPTION_TYPE_SYSTEM);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5136083023958834605L;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBusinessModule() {
		return businessModule;
	}

	public void setBusinessModule(String businessModule) {
		this.businessModule = businessModule;
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
