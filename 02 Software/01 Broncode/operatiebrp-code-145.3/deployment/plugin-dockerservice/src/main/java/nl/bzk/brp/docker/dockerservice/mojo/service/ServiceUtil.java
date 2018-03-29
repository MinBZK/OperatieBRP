package nl.bzk.brp.docker.dockerservice.mojo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Action;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Mode;

/**
 * Service configuratie utility.
 */
public final class ServiceUtil {

    private static final Map<String, Properties> PROPERTIES = new HashMap<>();

    private static final String KEY_NAAM = "name";

    private static final String SUFFIX_PORTMAPPING = "ports";
    private static final String SUFFIX_IMAGE = "image";
    private static final String SUFFIX_COMMAND = "command";

    private ServiceUtil() {
        // Niet instantieerbaar
    }

    static Properties getServiceProperties(final String service) throws ServiceConfigurationException {
        if (PROPERTIES.containsKey(service)) {
            return PROPERTIES.get(service);
        }

        final String propertyBestand = "/serviceconf/" + service + ".properties";
        try (InputStream propertyInputStream = ServiceUtil.class.getResourceAsStream(propertyBestand)) {
            if (propertyInputStream == null) {
                throw new ServiceConfigurationException("Kan " + propertyBestand + " niet vinden.");
            }

            final Properties serviceDefaults = new Properties();
            serviceDefaults.setProperty(KEY_NAAM, service);

            final Properties serviceProperties = new Properties(serviceDefaults);
            serviceProperties.load(propertyInputStream);

            PROPERTIES.put(service, serviceProperties);

            return serviceProperties;
        } catch (final IOException e) {
            throw new ServiceConfigurationException("Kan " + propertyBestand + " niet lezen.", e);
        }
    }

    /**
     * Bepaal de docker container naam.
     *
     * @param service service
     * @return docker container naam
     */
    public static String bepaalContainerNaam(final String service) throws ServiceConfigurationException {
        return getServiceProperties(service).getProperty(KEY_NAAM);
    }


    /**
     * Zoek de juiste property bestand op voor de service (naam propertybestand is
     * &lt;action&gt;_&lt;service&gt;.properties) en bepaal de argumenten voor de actie.
     * @param service service
     * @param actie gedefinieerde actie van service (create, rm, scale, update)
     * @param mode mode waarin de actie draait
     * @param imageRegistry registry (optioneel, mag null zijn)
     * @param imageVersion version (optioneel, mag null zijn)
     * @param extraServiceArguments extra service argumenten (optioneel, mag null zijn)
     * @param skipPortMapping als true, dan wordt geen port mapping toegevoegd als service argument
     * @param extraCommandArguments extra command argumenten (optioneel, mag null zijn)
     * @return lijst van argumenten voor de actie
     * @throws ServiceConfigurationException bij configuratie fouten
     */
    public static List<String> bepaalActieArgumenten(final String service, final Action actie, final Mode mode, final String imageRegistry,
                                                     final String imageVersion, final String extraServiceArguments, final boolean skipPortMapping,
                                                     final String extraCommandArguments) throws ServiceConfigurationException {
        final Properties serviceProperties = getServiceProperties(service);

        final String argumenten = bepaalArgumenten(serviceProperties, actie, mode, extraServiceArguments);
        if (argumenten == null) {
            throw new ServiceConfigurationException(actie + " (property=" + actie.getPropertyKey() + ") wordt voor service " + service + " niet ondersteund.");
        }
        final String portMapping = bepaalPortMapping(serviceProperties, actie, skipPortMapping);
        final String image = bepaalImage(serviceProperties, actie, imageRegistry, imageVersion);
        final String command = bepaalCommand(serviceProperties, actie, extraCommandArguments);

        final List<String> result = new ArrayList<>();
        if (!argumenten.trim().isEmpty()) {
            result.addAll(Arrays.asList(argumenten.trim().split("\\s+")));
        }
        if (!portMapping.trim().isEmpty()) {
            result.addAll(Arrays.asList(portMapping.trim().split("\\s+")));
        }
        if ((image != null) && !image.trim().isEmpty()) {
            result.add(image.trim());
        }
        if ((command != null) && !command.trim().isEmpty()) {
            result.addAll(Arrays.asList(command.trim().split("\\s+")));
        }

        return result;
    }

    private static String bepaalArgumenten(final Properties serviceProperties, final Action actie, final Mode mode, final String extraServiceArguments) {
        String argumenten = serviceProperties.getProperty(actie.getPropertyKey());
        if (argumenten == null) {
            return null;
        }

        argumenten += " " + serviceProperties.getProperty(actie.getPropertyKey() + "." + mode.getPropertySuffix(), "");

        if (extraServiceArguments != null) {
            argumenten += " " + extraServiceArguments;
        }

        return argumenten;
    }


    private static String bepaalPortMapping(final Properties serviceProperties, final Action actie, boolean skipPortMapping) {
        return skipPortMapping ? "" : serviceProperties.getProperty(actie.getPropertyKey() + "." + SUFFIX_PORTMAPPING, "");
    }

    /**
     * Bepaal de naam van de image
     * @param service naam van de service
     * @param actie actie welke wordt uitgevoerd
     * @param imageRegistry registry van de image
     * @param imageVersion versie van de image
     * @return volledige naam van de image
     */
    public static String bepaalImage(final String service, final Action actie, final String imageRegistry, final String imageVersion)
            throws ServiceConfigurationException {
        final Properties serviceProperties = getServiceProperties(service);
        return bepaalImage(serviceProperties, actie, imageRegistry, imageVersion);
    }

    private static String bepaalImage(final Properties serviceProperties, final Action actie, final String imageRegistry, final String imageVersion) {
        final String image = serviceProperties.getProperty(actie.getPropertyKey() + "." + SUFFIX_IMAGE);
        if (image == null) {
            return "";
        } else {
            return (imageRegistry == null ? "" : imageRegistry) + image + ":" + (imageVersion == null ? "latest" : imageVersion);
        }
    }

    private static String bepaalCommand(final Properties serviceProperties, final Action actie, final String extraCommandArguments) {
        String command = serviceProperties.getProperty(actie.getPropertyKey() + "." + SUFFIX_COMMAND);
        if (command == null) {
            command = " ";
        } else if (extraCommandArguments != null) {
            command += " " + extraCommandArguments;
        }
        return command;
    }

    /**
     * Service configuration exception.
     */
    public static final class ServiceConfigurationException extends Exception {
        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         * @param message message
         */
        public ServiceConfigurationException(final String message) {
            super(message);
        }

        /**
         * Constructor.
         * @param message message
         * @param cause cause
         */
        public ServiceConfigurationException(final String message, final IOException cause) {
            super(message, cause);
        }
    }
}
