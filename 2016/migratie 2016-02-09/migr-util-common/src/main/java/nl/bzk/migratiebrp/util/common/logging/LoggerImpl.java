/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.logging;

import java.util.Map;
import nl.bzk.migratiebrp.util.common.logging.MDC.MDCCloser;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Logger implementatie; huidige implementatie is wrapper om SLF4J heen.
 */
final class LoggerImpl implements Logger {

    /** De Marker die de functionele log markeert. */
    private static final Marker FUNCTIONEEL = MarkerFactory.getMarker("FUNCTIONELE_LOG");
    private static final String INFO = "info";
    private static final String DEBUG = "debug";
    private static final String WARN = "warn";
    private static final String ERROR = "error";

    private final org.slf4j.Logger delegate;

    /**
     * Constructor.
     *
     * @param delegate
     *            De delegate.
     */
    LoggerImpl(final org.slf4j.Logger delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** debug ******************************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    @Override
    public void debug(final String message) {
        delegate.debug(message);
    }

    @Override
    public void debug(final String format, final Object... args) {
        delegate.debug(format, args);
    }

    @Override
    public void debug(final Map<String, String> data, final String message) {
        doLogging(data, null, message, DEBUG);
    }

    @Override
    public void debug(final Map<String, String> data, final String format, final Object... args) {
        doLogging(data, null, format, DEBUG, args);
    }

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** info ******************************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    @Override
    public void info(final String message) {
        doLogging(null, null, message, INFO);
    }

    @Override
    public void info(final String format, final Object... args) {
        doLogging(null, null, format, INFO, args);
    }

    @Override
    public void info(final Map<String, String> data, final String message) {
        doLogging(data, null, message, INFO);
    }

    @Override
    public void info(final Map<String, String> data, final String format, final Object... args) {
        doLogging(data, null, format, INFO, args);
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding) {
        doLogging(null, functioneleMelding, functioneleMelding.getOmschrijving(), INFO);
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding, final String message) {
        doLogging(null, functioneleMelding, message, INFO);
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding, final String format, final Object... args) {
        doLogging(null, functioneleMelding, format, INFO, args);
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding, final Map<String, String> data) {
        doLogging(data, functioneleMelding, functioneleMelding.getOmschrijving(), INFO);
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String message) {
        doLogging(data, functioneleMelding, message, INFO);
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String format, final Object... args) {
        doLogging(data, functioneleMelding, format, INFO, args);
    }

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** warn ******************************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    @Override
    public void warn(final String message) {
        doLogging(null, null, message, WARN);
    }

    @Override
    public void warn(final String format, final Object... args) {
        doLogging(null, null, format, WARN, args);
    }

    @Override
    public void warn(final Map<String, String> data, final String message) {
        doLogging(data, null, message, WARN);
    }

    @Override
    public void warn(final Map<String, String> data, final String format, final Object... args) {
        doLogging(data, null, format, WARN, args);
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding) {
        doLogging(null, functioneleMelding, functioneleMelding.getOmschrijving(), WARN);
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding, final String message) {
        doLogging(null, functioneleMelding, message, WARN);
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding, final String format, final Object... args) {
        doLogging(null, functioneleMelding, format, WARN, args);
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding, final Map<String, String> data) {
        doLogging(data, functioneleMelding, functioneleMelding.getOmschrijving(), WARN);
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String message) {
        doLogging(data, functioneleMelding, message, WARN);
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String format, final Object... args) {
        doLogging(data, functioneleMelding, format, WARN, args);
    }

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** error ******************************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    @Override
    public void error(final String message) {
        delegate.error(message);
    }

    @Override
    public void error(final String format, final Object... args) {
        delegate.error(format, args);
    }

    @Override
    public void error(final Map<String, String> data, final String message) {
        doLogging(data, null, message, ERROR);
    }

    @Override
    public void error(final Map<String, String> data, final String format, final Object... args) {
        doLogging(data, null, format, ERROR, args);
    }

    @Override
    public void error(final FunctioneleMelding functioneleMelding) {
        doLogging(null, functioneleMelding, functioneleMelding.getOmschrijving(), ERROR);
    }

    @Override
    public void error(final FunctioneleMelding functioneleMelding, final String message) {
        doLogging(null, functioneleMelding, message, ERROR);
    }

    @Override
    public void error(final FunctioneleMelding functioneleMelding, final String format, final Object... args) {
        doLogging(null, functioneleMelding, format, ERROR, args);
    }

    @Override
    public void error(final FunctioneleMelding functioneleMelding, final Map<String, String> data) {
        doLogging(data, functioneleMelding, functioneleMelding.getOmschrijving(), ERROR);
    }

    @Override
    public void error(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String message) {
        doLogging(data, functioneleMelding, message, ERROR);
    }

    @Override
    public void error(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String format, final Object... args) {
        doLogging(data, functioneleMelding, format, ERROR, args);
    }

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** enabledCheckers ********************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    @Override
    public boolean isWarnEnabled() {
        return delegate.isWarnEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return delegate.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return delegate.isInfoEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return delegate.isErrorEnabled();
    }

    private void doLogging(
        final Map<String, String> data,
        final FunctioneleMelding melding,
        final String message,
        final String loggingType,
        final Object... args)
    {
        MDCCloser closerData = null;
        MDCCloser closerMelding = null;
        try {
            if (data != null) {
                closerData = MDC.putData(data);
            }
            switch (loggingType) {
                case INFO:
                    closerMelding = doInfoLogging(melding, message, args);
                    break;
                case DEBUG:
                    doDebugLogging(message, args);
                    break;
                case WARN:
                    closerMelding = doWarnLogging(melding, message, args);
                    break;
                default:
                    closerMelding = doErrorLogging(melding, message, args);

            }
        } finally {
            if (closerData != null) {
                closerData.close();
            }
            if (closerMelding != null) {
                closerMelding.close();
            }
        }
    }

    private MDCCloser doInfoLogging(final FunctioneleMelding melding, final String message, final Object[] args) {
        MDCCloser closerMelding = null;
        if (melding != null) {
            closerMelding = MDC.putMelding(melding);
            if (args.length == 0) {
                delegate.info(FUNCTIONEEL, message);
            } else {
                delegate.info(FUNCTIONEEL, message, args);
            }
        } else {
            if (args.length == 0) {
                delegate.info(message);
            } else {
                delegate.info(message, args);
            }
        }
        return closerMelding;
    }

    private void doDebugLogging(final String message, final Object[] args) {
        if (message != null) {
            if (args.length == 0) {
                delegate.debug(message);
            } else {
                delegate.debug(message, args);
            }
        }
    }

    private MDCCloser doWarnLogging(final FunctioneleMelding melding, final String message, final Object[] args) {
        MDCCloser closerMelding = null;
        if (melding != null) {
            closerMelding = MDC.putMelding(melding);
            if (args.length == 0) {
                delegate.warn(FUNCTIONEEL, message);
            } else {
                delegate.warn(FUNCTIONEEL, message, args);
            }
        } else {
            if (args.length == 0) {
                delegate.warn(message);
            } else {
                delegate.warn(message, args);
            }
        }
        return closerMelding;
    }

    private MDCCloser doErrorLogging(final FunctioneleMelding melding, final String message, final Object[] args) {
        MDCCloser closerMelding = null;
        if (melding != null) {
            closerMelding = MDC.putMelding(melding);
            if (args.length == 0) {
                delegate.error(FUNCTIONEEL, message);
            } else {
                delegate.error(FUNCTIONEEL, message, args);
            }
        } else {
            if (args.length == 0) {
                delegate.error(message);
            } else {
                delegate.error(message, args);
            }
        }
        return closerMelding;
    }
}
