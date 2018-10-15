package basic.arch.tools.encrypt.des;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DESException extends Exception{
	private final static Logger logger = LoggerFactory
			.getLogger(DESException.class);
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public DESException(Exception e) {
		super(e);
		logger.error("AESException-Error",e);
	}
	public DESException(String message) {
		super(message);
		logger.error(message);
	}
}
