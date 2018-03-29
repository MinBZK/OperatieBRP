/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.util;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.springframework.util.Assert;

/**
 * Log4J Appender voor het checken van {@link Regel regels} in logging.
 *
 * NB. Niet geschikt gemaakt voor multithreading!
 */
// note: class name need not match the @Plugin name.
@Plugin(name = "BrpLoggingAppender", category = "Core", elementType = "appender", printObject = true)
public class BrpLoggingAppender extends AbstractAppender {

    private static final Pattern REGEL_PATROON = Pattern.compile("R[0-9]{4}");

    private final Set<Regel> regelSet = Sets.newHashSet();
    private final Map<Level, List<String>> logRegels = Maps.newHashMap();

    protected BrpLoggingAppender(String name, Filter filter,
                                 Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @PluginFactory
    public static BrpLoggingAppender createAppender(@PluginElement("Layout") Layout<? extends Serializable> layout,
                                                    @PluginElement("Filter") final Filter filter,
                                                    @PluginAttribute("name") final String name,
                                                    @PluginAttribute(value = "ignoreExceptions", defaultBoolean = true) final boolean ignoreExceptions) {
        if (name == null) {
            LOGGER.error("No name provided for BrpLoggingAppender");
            return null;
        }
        return new BrpLoggingAppender(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(final LogEvent logEvent) {
        final String message = logEvent.getMessage().getFormattedMessage();
        final Matcher matcher = REGEL_PATROON.matcher(message);
        if (matcher.find()) {
            final String regelCode = matcher.group();
            regelSet.add(Regel.parseCode(regelCode));
        }
        logRegels.putIfAbsent(logEvent.getLevel(), Lists.newArrayList());
        logRegels.get(logEvent.getLevel()).add(message);
    }

    public boolean heeftLogEventOntvangenMetCode(final String code) {
        final Regel regel = Regel.parseCode(code);
        return regelSet.contains(regel);
    }

    public boolean heeftMessageTextOntvangen(final String logLevel, final String messageTextRegex) {
        final Level level = Level.getLevel(logLevel);
        for (final String logRegel : logRegels.get(level)) {
            if (logRegel.matches(messageTextRegex)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        regelSet.clear();
        logRegels.clear();
    }
}
