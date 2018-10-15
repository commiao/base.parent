package basic.arch.tools.encrypt.aes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DES exception
 *
*/
public class AESException extends Exception {

	private final static Logger logger = LoggerFactory
			.getLogger(AESException.class);

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public AESException(Exception e) {
		super(e);
		logger.error("AESException-Error",e);
	}

	public AESException(String message) {
		super(message);
		logger.error(message);
	}
}
