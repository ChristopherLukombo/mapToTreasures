package fr.carbon.maptotreasures.exception;

/**
 * The Class InitException.
 */
public class InitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InitException() {
		super("Problem during init config");
	}
}
