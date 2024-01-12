/**
 * The Logger class supports tracing events to STDOUT.
 *
 * This class provides a Java system property with 
 * name "logger.Logger.tracing" and a boolean value 
 * (false means OFF and true means ON).
 */
public class Logger 
{
    public static final String LOGGER_PROPERTY_NAME = "Logger.tracing";

    /**
     * Whether tracing is ON (true) or OFF (false)
     */
    private static boolean tracing;

    static {
	// Allows the user to turn ON and OFF tracing
	tracing = new Boolean(System.getProperty(LOGGER_PROPERTY_NAME, "" + Boolean.FALSE));
    }

    /**
     * Logs the given event to STDOUT if tracing is ON.
     * 
     * @param event The event to be logged if tracing is ON.
     */
    public static void log(Object event) {
	if (Logger.tracing) {
	    System.out.println(event);
	}
    }
}
