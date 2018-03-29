/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.access;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.security.auth.Subject;
import nl.bzk.algemeenbrp.util.jmx.utils.PropertiesLoader;

/**
 * Properties based access controller.
 *
 * <p>The set of username/access level pairs is passed either as a
 * filename which denotes a properties file on disk, or directly as an instance
 * of the {@link Properties} class.  In both cases, the name of each property
 * represents a username, and the value of the property is the associated access
 * level.  Thus, any given username either does not exist in the properties or
 * has exactly one access level. The same access level can be shared by several
 * usernames.</p>
 *
 * <p>The supported access level values are {@code readonly} and
 * {@code readwrite}.  The {@code readwrite} access level can be
 * qualified by one or more <i>clauses</i>, where each clause looks
 * like <code>create <i>classNamePattern</i></code> or {@code
 * unregister}.  For example:</p>
 *
 * <pre>
 * monitorRole  readonly
 * controlRole  readwrite \
 *              create javax.management.timer.*,javax.management.monitor.* \
 *              unregister
 * </pre>
 *
 * <p>(The continuation lines with {@code \} come from the parser for
 * Properties files.)</p>
 */
public final class PropertiesAccessController implements JMXAccessController {

    private static final Logger LOGGER = Logger.getLogger(PropertiesAccessController.class.getName());

    private final PropertiesLoader propertiesLoader;
    private Map<String, Access> accesses;

    /**
     * Create a new properties access controller.
     * @param properties properties to use
     */
    public PropertiesAccessController(final Properties properties) {
        this(() -> properties);
    }

    /**
     * Create a new properties access controller.
     * @param propertiesLoader properties loader to use
     */
    public PropertiesAccessController(final PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
    }

    @Override
    public void checkAccess(final Subject subject, final String methodName, final Object[] parameterValues) {
        if (accesses == null) {
            try {
                accesses = loadAccesses();
                LOGGER.log(Level.FINE, "Loaded accesses: {0}", accesses);
            } catch (final IOException e) {
                throw new SecurityException("Could not load properties", e);
            }
        }

        if (Methods.READ_METHODS.contains(methodName)) {
            LOGGER.log(Level.FINE, "Check read");
            check(subject, access -> true);
        } else if (Methods.WRITE_METHODS.contains(methodName)) {
            LOGGER.log(Level.FINE, "Check write");
            check(subject, access -> access.write);
        } else if (Methods.CREATE_METHODS.contains(methodName)) {
            LOGGER.log(Level.FINE, "Check create");
            check(subject, access -> access.mayCreate((String) parameterValues[0]));
        } else if (Methods.UNREGISTER_METHODS.contains(methodName)) {
            LOGGER.log(Level.FINE, "Check unregister");
            check(subject, access -> access.unregister);
        } else {
            throw new SecurityException("Illegal access");
        }
    }

    private Map<String, Access> loadAccesses() throws IOException {
        final Map<String, Access> result = new HashMap<>();

        final Properties properties = propertiesLoader.loadProperties();
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            result.put((String) entry.getKey(), new Access((String) entry.getValue()));
        }

        return result;
    }

    private void check(final Subject subject, final Function<Access, Boolean> accessCheck) {
        for (final Principal principal : subject.getPrincipals()) {
            final Access access = accesses.get(principal.getName());
            LOGGER.log(Level.FINE, "Check for principal: {0} -> {1}", new Object[]{principal.getName(), access});
            if (access != null && accessCheck.apply(access)) {
                return;
            }
        }

        throw new SecurityException("Illegal access");
    }

    private static final class Access {
        boolean write;
        List<String> creates = new ArrayList<>();
        boolean unregister;

        public Access(final String accessString) {
            final String normalized = accessString.replaceAll("\\s+", " ").replaceAll("\\s?,\\s?", ",").trim();
            final String[] parts = normalized.split("\\s");

            boolean addCreate = false;
            for (final String part : parts) {
                if (addCreate) {
                    Arrays.stream(part.split(",")).forEach(
                            classNamePattern -> creates.add(convertClassNamePatternToPattern(classNamePattern)));
                    addCreate = false;
                } else if ("readonly".equals(part)) {
                    // Ok, implied
                } else if ("readwrite".equals(part)) {
                    write = true;
                } else if ("unregister".equals(part)) {
                    unregister = true;
                } else if ("create".equals(part)) {
                    addCreate = true;
                } else {
                    throw new SecurityException("Invalid access configuration. Did not expect part: " + part);
                }
            }
        }

        private static String convertClassNamePatternToPattern(final String classNamePattern) {
            return classNamePattern.replace(".", "\\.").replace("*", "[^\\.]*");
        }

        boolean mayCreate(final String className) {
            for (final String createPattern : creates) {
                if (Pattern.matches(createPattern, className)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public String toString() {
            return "Access [write=" + write + ", creates=" + creates + ", unregister=" + unregister + "]";
        }

    }
}
