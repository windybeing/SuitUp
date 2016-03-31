package chpoi.suitup.exception;

/**
 * Specific Exception for SuitUp error situation
 * 
 * @author Dong Zhiyuan
 *
 */
public  class SuitUpException extends Exception {

	private static final long serialVersionUID = 1L;

	public SuitUpException(String message){
		super(message);
	}
}
