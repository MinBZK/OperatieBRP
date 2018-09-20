/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.logging.log4j;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.Charsets;
import org.apache.logging.log4j.core.util.Throwables;
import org.apache.logging.log4j.core.util.Transform;
import org.apache.logging.log4j.message.Message;

/**
 * De layout voor Log4J om logging naar Json formaat mogelijk te maken zodat deze opgepakt kan worden door LogStash.
 *
 * Deze code is gebaseerd op code van https://github.com/majikthys/log4j2-logstash-jsonevent-layout. Er zijn aanpassingen nodig geweest om de MDC
 * logging op correcte wijze zichtbaar te maken in deze layout.
 */

@Plugin(name = "BrpJsonLayout", category = "Core", elementType = "layout", printObject = true)
public class BrpJsonLayout extends AbstractStringLayout {

    /**
     * Logstash timestamp formaat.
     */
    public static final  String   LOG_STASH_ISO8601_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String   COMMA                              = ",";
    private static final int      DEFAULT_SIZE                       = 256;
    // We yield to \r\n for the default.
    private static final String   DEFAULT_EOL                        = "\r\n";
    private static final String   COMPACT_EOL                        = "";
    private static final String   COMPACT_INDENT                     = "";
    private static final String   CURLY_START                        = "{";
    private static final String   CURLY_END                          = "}";
    private static final String   QUOTES                             = "\"";
    private static final String   QUOTES_COMMA                       = "\",";

    private final    DateFormat                     iso8601DateFormat;
    private final    String                         eol;
    private final    String                         indent1;
    private final    String                         indent2;
    private final    String                         indent3;
    private final    String                         eventSeparator;
    private final    String                         eventEnd;

    private final    String                         subLayoutBegin;
    private final    String                         subLayoutEnd;

    private volatile boolean                        firstLayoutDone;
    private final    Layout<? extends Serializable> subLayout;

    /**
     * Instantieert een nieuwe Brp JSON layout voor Log4J2.
     */
    protected BrpJsonLayout() {
        super(Charsets.UTF_8);

        this.eol = COMPACT_EOL;
        this.indent1 = COMPACT_INDENT;
        this.indent2 = this.indent1 + this.indent1;
        this.indent3 = this.indent2 + this.indent1;
        this.eventEnd = DEFAULT_EOL;
        this.eventSeparator = "";
        this.subLayoutBegin = QUOTES;
        this.subLayoutEnd = QUOTES;
        this.iso8601DateFormat = new SimpleDateFormat(LOG_STASH_ISO8601_TIMESTAMP_FORMAT);
        this.subLayout = PatternLayout.createDefaultLayout();
    }

    /**
     * Creëert een JSON Layout.
     *
     * @return JSON layout
     */
    @PluginFactory
    public static BrpJsonLayout createLayout() {
        return new BrpJsonLayout();
    }

    /**
     * Formats a {@link org.apache.logging.log4j.core.LogEvent} in conformance with the log4j.dtd.
     *
     * @param event The LogEvent.
     * @return The XML representation of the LogEvent.
     */
    @Override
    public final String toSerializable(final LogEvent event) {
        final StringBuilder buf = new StringBuilder(DEFAULT_SIZE);
        // DC locking to avoid synchronizing the whole layout.
        final boolean check;
        if (!this.firstLayoutDone) {
            synchronized (this) {
                check = this.firstLayoutDone;
                if (!check) {
                    this.firstLayoutDone = true;
                } else {
                    buf.append(this.eventSeparator);
                }
            }
        } else {
            buf.append(this.eventSeparator);
        }
        buf.append(this.indent1);
        buf.append(CURLY_START);

        buf.append(this.eol);
        buf.append(this.indent2);

        // Versie en timestamp
        appendVersionTimeLog(buf, event);

        // Marker
        appendMarkerLog(buf, event);

        // Logger
        appendLoggerLog(buf, event);

        // Level
        appendLevelLog(buf, event);

        // Thread
        appendThreadLog(buf, event);

        // Message
        appendMessageLog(buf, event);

        // NDC
        appendNdcLog(buf, event);

        // Thrown
        appendThrownLog(buf, event);

        // LocationInfo
        appendLocationInfoLog(buf, event);

        // MDC
        appendMdcLog(buf, event);

        //Log (the sublayout)
        appendLogLog(buf, event);


        buf.append(CURLY_END);
        buf.append(this.eventEnd);

        return buf.toString();
    }

    private void appendLogLog(final StringBuilder buf, final LogEvent event) {
        buf.append(COMMA);
        buf.append(this.eol);
        buf.append(this.indent2);
        buf.append("\"log\":");
        buf.append(this.subLayoutBegin);

        final Serializable serializedLayoutProduct = subLayout.toSerializable(event);
        buf.append(Transform.escapeJsonControlCharacters(serializedLayoutProduct.toString()));

        buf.append(this.subLayoutEnd);

        buf.append(this.eol);
        buf.append(this.indent1);
    }

    private void appendMdcLog(final StringBuilder buf, final LogEvent event) {
        if (event.getContextMap().size() > 0) {
            buf.append(COMMA);
            buf.append(this.eol);
            buf.append(this.indent2);
            buf.append("\"BRP\":{");
            buf.append(this.eol);
            final Set<Entry<String, String>> entrySet = event.getContextMap().entrySet();
            int i = 1;
            for (final Entry<String, String> entry : entrySet) {
                buf.append(this.eol);
                buf.append(this.indent3);
                buf.append(QUOTES);
                buf.append(Transform.escapeJsonControlCharacters(entry.getKey()));
                buf.append("\":\"");
                buf.append(Transform.escapeJsonControlCharacters(String.valueOf(entry.getValue())));
                buf.append(QUOTES);
                if (i < entrySet.size()) {
                    buf.append(COMMA);
                }
                buf.append(this.eol);
                i++;
            }
            buf.append(this.indent2);
            buf.append(CURLY_END);
        }
    }

    private void appendLocationInfoLog(final StringBuilder buf, final LogEvent event) {
        if (null != event.getSource()) {
            final StackTraceElement element = event.getSource();
            buf.append(COMMA);
            buf.append(this.eol);
            buf.append(this.indent2);
            buf.append("\"LocationInfo\":{");
            buf.append(this.eol);
            buf.append(this.indent3);
            buf.append("\"class\":\"");
            buf.append(Transform.escapeJsonControlCharacters(element.getClassName()));
            buf.append(QUOTES_COMMA);
            buf.append(this.eol);
            buf.append(this.indent3);
            buf.append("\"method\":\"");
            buf.append(Transform.escapeJsonControlCharacters(element.getMethodName()));
            buf.append(QUOTES_COMMA);
            buf.append(this.eol);
            buf.append(this.indent3);
            buf.append("\"file\":\"");
            buf.append(Transform.escapeJsonControlCharacters(element.getFileName()));
            buf.append(QUOTES_COMMA);
            buf.append(this.eol);
            buf.append(this.indent3);
            buf.append("\"line\":\"");
            buf.append(element.getLineNumber());
            buf.append(QUOTES);
            buf.append(this.eol);
            buf.append(this.indent2);
            buf.append(CURLY_END);
        }
    }

    private void appendThrownLog(final StringBuilder buf, final LogEvent event) {
        final Throwable throwable = event.getThrown();
        if (throwable != null) {
            buf.append(COMMA);
            buf.append(this.eol);
            buf.append(this.indent2);
            buf.append("\"throwable\":\"");
            final List<String> list = Throwables.toStringList(throwable);
            for (final String str : list) {
                buf.append(Transform.escapeJsonControlCharacters(str));
                buf.append("\\\\n");
            }
            buf.append(QUOTES);
        }
    }

    private void appendNdcLog(final StringBuilder buf, final LogEvent event) {
        if (event.getContextStack().getDepth() > 0) {
            buf.append(COMMA);
            buf.append(this.eol);
            buf.append("\"ndc\":");
            buf.append(Transform.escapeJsonControlCharacters(event.getContextStack().toString()));
            buf.append(QUOTES);
        }
    }

    private void appendMessageLog(final StringBuilder buf, final LogEvent event) {
        buf.append(QUOTES_COMMA);
        buf.append(this.eol);

        final Message msg = event.getMessage();
        if (msg != null) {
            buf.append(this.indent2);
            buf.append("\"message\":\"");
            buf.append(Transform.escapeJsonControlCharacters(event.getMessage().getFormattedMessage()));
            buf.append(QUOTES);
        }
    }

    private void appendVersionTimeLog(final StringBuilder buf, final LogEvent event) {
        // Logstash versie is 1
        buf.append("\"@version\":\"");
        buf.append("1");
        buf.append(QUOTES_COMMA);
        buf.append(this.eol);
        buf.append(this.indent2);
        buf.append("\"@timestamp\":\"");
        buf.append(iso8601DateFormat.format(new Date(event.getTimeMillis())));
    }

    private void appendThreadLog(final StringBuilder buf, final LogEvent event) {
        buf.append(QUOTES_COMMA);
        buf.append(this.eol);
        buf.append(this.indent2);
        buf.append("\"thread\":\"");
        buf.append(Transform.escapeJsonControlCharacters(event.getThreadName()));
    }

    private void appendLevelLog(final StringBuilder buf, final LogEvent event) {
        buf.append(QUOTES_COMMA);
        buf.append(this.eol);
        buf.append(this.indent2);
        buf.append("\"level\":\"");
        buf.append(Transform.escapeJsonControlCharacters(String.valueOf(event.getLevel())));
    }

    private void appendLoggerLog(final StringBuilder buf, final LogEvent event) {
        buf.append(QUOTES_COMMA);
        buf.append(this.eol);
        buf.append(this.indent2);
        buf.append("\"logger\":\"");
        String name = event.getLoggerName();
        if (name.isEmpty()) {
            name = "root";
        }
        buf.append(Transform.escapeJsonControlCharacters(name));
    }

    private void appendMarkerLog(final StringBuilder buf, final LogEvent event) {
        buf.append(QUOTES_COMMA);
        buf.append(this.eol);
        buf.append(this.indent2);
        buf.append("\"marker\":\"");
        final String markerNaam;
        if (event.getMarker() != null) {
            markerNaam = event.getMarker().getName();
        } else {
            markerNaam = "GEEN";
        }
        buf.append(Transform.escapeJsonControlCharacters(markerNaam));
    }

    /**
     * Returns appropriate JSON headers.
     *
     * @return a byte array containing the header, opening the JSON array.
     */
    @Override
    public final byte[] getHeader() {
        return null;
    }

    /**
     * Returns appropriate JSON footer.
     *
     * @return a byte array containing the footer, closing the JSON array.
     */
    @Override
    public final byte[] getFooter() {
        return null;
    }

    /**
     * XMLLayout's content format is specified by:.
     * <p/>
     * Key: "dtd" Value: "log4j-events.dtd"
     * <p/>
     * Key: "version" Value: "2.0"
     *
     * @return Map of content format keys supporting XMLLayout
     */
    @Override
    public final Map<String, String> getContentFormat() {
        final Map<String, String> result = new HashMap<>();
        result.put("version", "2.0");
        return result;
    }

    /**
     * @return Het content type.
     */
    @Override
    public final String getContentType() {
        return "application/json; charset=" + this.getCharset();
    }
}
