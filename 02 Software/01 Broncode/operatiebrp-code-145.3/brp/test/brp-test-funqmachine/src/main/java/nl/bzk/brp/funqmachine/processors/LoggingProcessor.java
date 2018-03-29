/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

/**
 * Logging processor voor het testen van output in de logging.
 */
@Plugin(name = "LoggingProcessorAppender", category = "Core", elementType = "appender", printObject = true)
public final class LoggingProcessor extends AbstractAppender {

    private static final ThreadLocal<List<LogEvent>> LOG_EVENT_STORE = ThreadLocal.withInitial(ArrayList::new);

    /**
     * Initialiseert de LoggingProcessor.
     * 
     * @param name de naam
     * @param filter de filter
     * @param layout de layout
     * @param ignoreExceptions true als exceptions genegeerd moeten worden anders false
     */
    public LoggingProcessor(final String name, final Filter filter, final Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    /** Methodes van AbstractAppender. **/

    @Override
    public void append(final LogEvent event) {
        LOG_EVENT_STORE.get().add(event);
    }

    /** Static methodes. */

    /**
     * Maakt een LoggingProcessor appender.
     * 
     * @param name de naam
     * @param layout de layout
     * @param filter de filter
     * @param otherAttribute overige attributen
     * @return een LoggingProcessor appender
     */
    @PluginFactory
    public static LoggingProcessor createAppender(
        @PluginAttribute("name") final String name,
        @PluginElement("Layout") final Layout<? extends Serializable> layout,
        @PluginElement("Filter") final Filter filter,
        @PluginAttribute("otherAttribute") final String otherAttribute)
    {
        if (name == null) {
            LOGGER.error("No name provided for MyCustomAppenderImpl");
            return null;
        }
        return new LoggingProcessor(name, filter, null, true);
    }

    /**
     * Controleert of de gegeven tekst voorkomt in de logging.
     *
     * @param logTekst de tekst die moet voorkomen in de logging
     * @return true als de tekst voorkomnt, anders false
     */
    public static boolean komtTekstVoorInLogging(final String logTekst) {
        for (final LogEvent logEvent : LOG_EVENT_STORE.get()) {
            if (logEvent.getMessage().getFormattedMessage().contains(logTekst)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reset de logging events.
     */
    public static void clear() {
        LOG_EVENT_STORE.get().clear();
    }
}
