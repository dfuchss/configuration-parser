package home.fox.configuration;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Handle all {@link String Strings}.
 *
 * @author Dominik Fuchss
 *
 */
public final class Messages {
	/**
	 * The {@link ResourceBundle}.
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");

	/**
	 * Prevent instantiation.
	 */
	private Messages() {
	}

	/**
	 * Get the saved String by key.
	 *
	 * @param key
	 *            the key
	 * @return the string or {@code !'key'!} if none found
	 */
	public static String getString(String key) {
		try {
			return Messages.RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
