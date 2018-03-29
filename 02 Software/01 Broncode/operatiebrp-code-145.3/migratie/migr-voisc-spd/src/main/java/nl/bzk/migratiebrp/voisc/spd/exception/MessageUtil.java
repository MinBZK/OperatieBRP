/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.exception;

import static java.lang.Math.toIntExact;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * MessageUtil for constructing/composing (exception)messages for logging to Log4J and/or Gebeurtenissen purposes
 *
 * Remarks: While logging numbers (doubles,longs & integers) the default message formatting is used. This will be
 * overruled by the pattern placed between the placeholders of texts in the messages.propperties file: Example: Dit is
 * getal {0,number,#). Produces: Dit is getal 1000000. Instead of the default formatting: Dit is getal 1,000,000
 */
public final class MessageUtil {
    private static final String NEW_LINE = "\n";

    private static final String MESSAGE_BUNDLE_REPLACEMENT = "???";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final ResourceBundle MESSAGES_BUNDLE = ResourceBundle.getBundle("voisc-spd-messages");
    private static final String MELDING = "MELDING-";

    private MessageUtil() {

    }

    /**
     * @param code String the key used for finding the corresponding exception message
     * @param parameters Object[];
     * @return String composed error message
     */
    public static String composeMessage(final String code, final Object[] parameters) {
        return composeMessage(code, parameters, null);
    }

    /**
     * An assumption is that the key exists in the file. If not, an exception will be thrown and the message will make
     * clear what's wroong but we do not provide handling of this exception.
     * @param code String the key used for finding the corresponding exception message
     * @param parameters Object[]
     * @param cause Throwable
     * @return string
     */
    public static String composeMessage(final String code, final Object[] parameters, final Throwable cause) {
        final StringBuilder messageBuffer = new StringBuilder();

        String messageFromBundle;
        try {
            messageFromBundle = MESSAGES_BUNDLE.getString(code);
        } catch (final MissingResourceException mrex) {
            LOG.error("Missing message for: " + code, mrex);
            messageFromBundle = MESSAGE_BUNDLE_REPLACEMENT + code + "??? {0}; {1}; {2}; {3}; {4}; {5}; {6}; {7}.";
        }
        final String message = MessageFormat.format(messageFromBundle, parameters);
        messageBuffer.append("[").append(MELDING).append(code).append("]:  ");
        messageBuffer.append(message);
        appendExtraParametersIfPresent(messageBuffer, messageFromBundle, parameters);
        if (cause != null) {
            messageBuffer.append(NEW_LINE);
            messageBuffer.append(getExceptionDetails(cause));
        }
        return messageBuffer.toString();
    }

    /**
     * util method to append extra information given in the Object[] parameters. If a message from the
     * message.properties only expects 3 parameters and the Object[] contains 4 parameters, we need to append the fourth
     * parameter. to be used as part of the message but only as substition indication for parameters
     * @param messageBuffer StringBuffer
     * @param messageFromBundle String
     * @param parameters Object[]
     */
    private static void appendExtraParametersIfPresent(final StringBuilder messageBuffer, final String messageFromBundle, final Object[] parameters) {
        if (parameters != null && parameters.length > 0) {
            final int nrOfParameters = parameters.length;
            int nrOfParametersNeeded = toIntExact(messageFromBundle.chars().filter(c -> c == '{').count());
            if (nrOfParameters > nrOfParametersNeeded) {
                int aantalExtra = nrOfParameters - nrOfParametersNeeded;
                while (aantalExtra > 0) {
                    messageBuffer.append(parameters[parameters.length - aantalExtra]).append(", ");
                    aantalExtra--;
                }
            }
        }
    }

    /**
     * @param e De opgetreden exceptie.
     * @return String errorMessage
     */
    private static String getExceptionDetails(final Throwable e) {
        final StringWriter buffer = new StringWriter();
        final PrintWriter errorMessage = new PrintWriter(buffer);
        Throwable throwable = e;
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        if (throwable.getMessage() != null && !"".equals(throwable.getMessage())) {
            errorMessage.write(throwable.getMessage() + NEW_LINE);
        }

        while (throwable instanceof java.sql.BatchUpdateException) {
            final java.sql.BatchUpdateException bue = (java.sql.BatchUpdateException) throwable;
            if (bue.getNextException() != null) {
                errorMessage.write(bue.getNextException().getMessage() + NEW_LINE);
                throwable = bue.getNextException();
            }
        }

        if (!(throwable instanceof VoaRuntimeException)) {
            // append the stacktrace to support defect resolution
            throwable.printStackTrace(errorMessage);
        }
        return buffer.toString();
    } // getExceptionDetails
}
