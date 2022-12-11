package fr.carbon.maptotreasures.exception;

/**
 * The Class ConfigException.
 */
public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConfigException(Throwable cause) {
		super("Problem during getting config", cause);
	}
}
