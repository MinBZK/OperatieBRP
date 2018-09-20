/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.logging;

import java.util.Map;

import nl.bzk.brp.logging.MDC.MDCCloser;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;


/**
 * Logger implementatie; huidige implementatie is wrapper om SLF4J heen.
 */
final class LoggerImpl implements Logger {

    /** De Marker die de functionele log markeert. */
    private static final Marker    FUNCTIONEEL = MarkerFactory.getMarker("FUNCTIONELE_LOG");

    private final org.slf4j.Logger delegate;

    /**
     * Constructor.
     * 
     * @param delegate De delegate.
     */
    LoggerImpl(final org.slf4j.Logger delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** debug ******************************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    @Override
    public void debug(final String message) {
        this.delegate.debug(message);
    }

    @Override
    public void debug(final String format, final Object... args) {
        this.delegate.debug(format, args);
    }

    @Override
    public void debug(final Map<String, String> data, final String message) {
        try (MDCCloser dataCloser = MDC.putData(data)) {
            this.delegate.debug(message);
        }
    }

    @Override
    public void debug(final Map<String, String> data, final String format, final Object... args) {
        try (MDCCloser dataCloser = MDC.putData(data)) {
            this.delegate.debug(format, args);
        }
    }

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** info ******************************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    @Override
    public void info(final String message) {
        this.delegate.info(message);
    }

    @Override
    public void info(final String format, final Object... args) {
        this.delegate.info(format, args);
    }

    @Override
    public void info(final Map<String, String> data, final String message) {
        try (MDCCloser dataCloser = MDC.putData(data)) {
            this.delegate.info(message);
        }
    }

    @Override
    public void info(final Map<String, String> data, final String format, final Object... args) {
        try (MDCCloser dataCloser = MDC.putData(data)) {
            this.delegate.info(format, args);
        }
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding)) {
            this.delegate.info(FUNCTIONEEL, functioneleMelding.getOmschrijving());
        }
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding, final String message) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding)) {
            this.delegate.info(FUNCTIONEEL, message);
        }
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding, final String format, final Object... args) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding)) {
            this.delegate.info(FUNCTIONEEL, format, args);
        }
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding, final Map<String, String> data) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding);
                MDCCloser dataCloser = MDC.putData(data))
        {
            this.delegate.info(FUNCTIONEEL, functioneleMelding.getOmschrijving());
        }
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String message)
    {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding);
                MDCCloser dataCloser = MDC.putData(data))
        {
            this.delegate.info(FUNCTIONEEL, message);
        }
    }

    @Override
    public void info(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String format,
            final Object... args)
    {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding);
                MDCCloser dataCloser = MDC.putData(data))
        {
            this.delegate.info(FUNCTIONEEL, format, args);
        }
    }

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** warn ******************************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    @Override
    public void warn(final String message) {
        this.delegate.warn(message);
    }

    @Override
    public void warn(final String format, final Object... args) {
        this.delegate.warn(format, args);
    }

    @Override
    public void warn(final Map<String, String> data, final String message) {
        try (MDCCloser dataCloser = MDC.putData(data)) {
            this.delegate.warn(message);
        }
    }

    @Override
    public void warn(final Map<String, String> data, final String format, final Object... args) {
        try (MDCCloser dataCloser = MDC.putData(data)) {
            this.delegate.warn(format, args);
        }
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding)) {
            this.delegate.warn(FUNCTIONEEL, functioneleMelding.getOmschrijving());
        }
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding, final String message) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding)) {
            this.delegate.warn(FUNCTIONEEL, message);
        }
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding, final String format, final Object... args) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding)) {
            this.delegate.warn(FUNCTIONEEL, format, args);
        }
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding, final Map<String, String> data) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding);
                MDCCloser dataCloser = MDC.putData(data))
        {
            this.delegate.warn(FUNCTIONEEL, functioneleMelding.getOmschrijving());
        }
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String message)
    {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding);
                MDCCloser dataCloser = MDC.putData(data))
        {
            this.delegate.warn(FUNCTIONEEL, message);
        }
    }

    @Override
    public void warn(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String format,
            final Object... args)
    {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding);
                MDCCloser dataCloser = MDC.putData(data))
        {
            this.delegate.warn(FUNCTIONEEL, format, args);
        }
    }

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** error ******************************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    @Override
    public void error(final String message) {
        this.delegate.error(message);
    }

    @Override
    public void error(final String format, final Object... args) {
        this.delegate.error(format, args);
    }

    @Override
    public void error(final Map<String, String> data, final String message) {
        try (MDCCloser dataCloser = MDC.putData(data)) {
            this.delegate.error(message);
        }
    }

    @Override
    public void error(final Map<String, String> data, final String format, final Object... args) {
        try (MDCCloser dataCloser = MDC.putData(data)) {
            this.delegate.error(format, args);
        }
    }

    @Override
    public void error(final FunctioneleMelding functioneleMelding) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding)) {
            this.delegate.error(FUNCTIONEEL, functioneleMelding.getOmschrijving());
        }
    }

    @Override
    public void error(final FunctioneleMelding functioneleMelding, final String message) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding)) {
            this.delegate.error(FUNCTIONEEL, message);
        }
    }

    @Override
    public void error(final FunctioneleMelding functioneleMelding, final String format, final Object... args) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding)) {
            this.delegate.error(FUNCTIONEEL, format, args);
        }
    }

    @Override
    public void error(final FunctioneleMelding functioneleMelding, final Map<String, String> data) {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding);
                MDCCloser dataCloser = MDC.putData(data))
        {
            this.delegate.error(FUNCTIONEEL, functioneleMelding.getOmschrijving());
        }
    }

    @Override
    public void
            error(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String message)
    {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding);
                MDCCloser dataCloser = MDC.putData(data))
        {
            this.delegate.error(FUNCTIONEEL, message);
        }
    }

    @Override
    public void error(final FunctioneleMelding functioneleMelding, final Map<String, String> data, final String format,
            final Object... args)
    {
        try (MDCCloser meldingCloser = MDC.putMelding(functioneleMelding);
                MDCCloser dataCloser = MDC.putData(data))
        {
            this.delegate.error(FUNCTIONEEL, format, args);
        }
    }

    // ******************************************************************************* //
    // ******************************************************************************* //
    // ***** enabledCheckers ********************************************************* //
    // ******************************************************************************* //
    // ******************************************************************************* //

    @Override
    public boolean isWarnEnabled() {
        return this.delegate.isWarnEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return this.delegate.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return this.delegate.isInfoEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return this.delegate.isErrorEnabled();
    }

}
