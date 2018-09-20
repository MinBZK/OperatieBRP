/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

// CHECKSTYLE:OFF
/**
 * MessageUtil for constructing/composing (exception)messages for logging to Log4J and/or Gebeurtenissen purposes
 * 
 * Remarks: While logging numbers (doubles,longs & integers) the default message formatting is used. This will be
 * overruled by the pattern placed between the placeholders of texts in the messages.propperties file: Example: Dit is
 * getal {0,number,#). Produces: Dit is getal 1000000. Instead of the default formatting: Dit is getal 1,000,000
 * 
 * 
 * 
 */
public final class MessageUtil {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final ResourceBundle MESSAGES_BUNDLE = ResourceBundle.getBundle("messages");
    private static final String MELDING = "MELDING-";

    private MessageUtil() {

    }

    /**
     * 
     * @param code
     *            String the key used for finding the corresponding exception message
     * @param parameters
     *            Object[];
     * @return String composed error message
     */
    public static String composeMessage(final String code, final Object[] parameters) {
        return composeMessage(code, parameters, null);
    }

    /**
     * An assumption is that the key exists in the file. If not, an exception will be thrown and the message will make
     * clear what's wroong but we do not provide handling of this exception.
     * 
     * @param code
     *            String the key used for finding the corresponding exception message
     * @param parameters
     *            Object[]
     * @param cause
     *            Throwable
     * @return string
     */
    public static String composeMessage(final String code, final Object[] parameters, final Throwable cause) {
        final StringBuilder messageBuffer = new StringBuilder();

        String messageFromBundle = null;
        try {
            messageFromBundle = MESSAGES_BUNDLE.getString(code);
        } catch (final MissingResourceException mrex) {
            LOG.error("Missing message for: " + code, mrex);
            messageFromBundle = "???" + code + "??? {0}; {1}; {2}; {3}; {4}; {5}; {6}; {7}.";
        }
        final String message = MessageFormat.format(messageFromBundle, parameters);
        messageBuffer.append("[").append(MELDING).append(code).append("]: ");
        messageBuffer.append(message);
        appendExtraParametersIfPresent(messageBuffer, messageFromBundle, parameters);
        if (cause != null) {
            messageBuffer.append("\n");
            messageBuffer.append(getExceptionDetails(cause));
        }
        return messageBuffer.toString();
    }

    /**
     * Method to be used.
     * 
     * @param resourceBundle
     *            ResourceBundle to find messages in.
     * @param key
     *            The key used for finding the corresponding message. If none is found the key is repeated with
     *            embracing "???".
     * @param parameters
     *            Parameters to be substitued into the message string found.
     * @param cause
     *            Causing exception (if any).
     * @return Human readable message.
     */
    public static String composeMessage(
            final ResourceBundle resourceBundle,
            final String key,
            final Object[] parameters,
            final Throwable cause) {
        String messageFromBundle;
        try {
            messageFromBundle = resourceBundle.getString(key);
        } catch (final MissingResourceException mrex) {
            messageFromBundle = "???" + key + "???: {0}; {1}; {2}; {3}; {4}; {5}; {6}; {7}.";
        }

        Object[] params = null;
        if (parameters != null) {
            params = new String[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                params[i] = parameters[i] == null ? "null" : String.valueOf(parameters[i]);
            }
        }

        String message = "[MELDING-" + key + "]: " + MessageFormat.format(messageFromBundle, params);
        if (cause != null) {
            message += "\n" + getExceptionDetails(cause);
        }
        return message;
    }

    /**
     * util method to append extra information given in the Object[] parameters. If a message from the
     * message.properties only expects 3 parameters and the Object[] contains 4 parameters, we need to append the fourth
     * parameter. to be used as part of the message but only as substition indication for parameters
     * 
     * @param messageBuffer
     *            StringBuffer
     * @param messageFromBundle
     *            String
     * @param parameters
     *            Object[]
     */
    private static void appendExtraParametersIfPresent(
            final StringBuilder messageBuffer,
            final String messageFromBundle,
            final Object[] parameters) {
        final char[] characters = messageFromBundle.toCharArray();
        final int nrOfParameters = parameters != null ? parameters.length : 0;
        int nrOfParametersNeeded = 0;
        if (nrOfParameters > 0) {
            for (int i = 0; i < characters.length; i++) {
                if (characters[i] == '{') {
                    nrOfParametersNeeded++;
                }
            }
            if (nrOfParameters > nrOfParametersNeeded) {
                int aantalExtra = nrOfParameters - nrOfParametersNeeded;
                while (aantalExtra > 0) {
                    messageBuffer.append(parameters[parameters.length - aantalExtra] + ", ");
                    aantalExtra--;
                }
            }
        }
    }

    /**
     * 
     * @param code
     *            String
     * @param parameters
     *            Object[]
     * @return String
     */
    public static String composeLogMessage(final String code, final Object[] parameters) {
        return composeLogMessage(code, parameters, null);
    }

    /**
     * 
     * @param code
     *            String
     * @return String
     */
    public static String composeLogMessage(final String code) {
        return composeLogMessage(code, null, null);
    }

    /**
     * 
     * @param code
     *            String
     * @param cause
     * @return String
     */
    public static String composeLogMessage(final String code, final Throwable cause) {
        return composeLogMessage(code, null, cause);
    }

    /**
     * 
     * @param code
     *            String
     * @param parameters
     *            Object[]
     * @param cause
     *            Throwable
     * @return String
     */
    public static String composeLogMessage(final String code, final Object[] parameters, final Throwable cause) {
        return composeMessage(code, parameters, cause);
    }

    /**
     * 
     * @param e
     *            Throwable
     * @return String errorMessage
     */
    private static String getExceptionDetails(Throwable e) {
        final StringWriter buffer = new StringWriter();
        final PrintWriter errorMessage = new PrintWriter(buffer);
        while (e != null && e.getCause() != null) {
            e = e.getCause();
        }

        if (e != null && e.getMessage() != null && !e.getMessage().equals("")) {
            errorMessage.write(e.getMessage() + "\n");
        }

        while (e instanceof java.sql.BatchUpdateException) {
            final java.sql.BatchUpdateException bue = (java.sql.BatchUpdateException) e;
            if (bue.getNextException() != null) {
                errorMessage.write(bue.getNextException().getMessage() + "\n");
                e = bue.getNextException();
            }
        }

        if (e != null && !(e instanceof VoaRuntimeException)) {
            // append the stacktrace to support defect resolution
            e.printStackTrace(errorMessage);
        }
        return buffer.toString();
    }// getExceptionDetails
}
