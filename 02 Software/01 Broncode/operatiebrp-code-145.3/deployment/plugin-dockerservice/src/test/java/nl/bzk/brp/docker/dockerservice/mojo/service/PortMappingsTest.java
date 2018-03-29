/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.docker.dockerservice.mojo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;

/**
 * Controle voor poort mappings.
 */
public class PortMappingsTest {

    private static final Map<Integer, String> KNOWN_PORTS = new HashMap<>();

    static {
        KNOWN_PORTS.put(1212, "mailbox");
        KNOWN_PORTS.put(5432, "postgres");
        KNOWN_PORTS.put(3481, "jmx");
        KNOWN_PORTS.put(5445, "hornetq");
        KNOWN_PORTS.put(5601, "kibana");
        KNOWN_PORTS.put(8000, "debug");
        KNOWN_PORTS.put(8080, "http");
        KNOWN_PORTS.put(61616, "activemq");
    }


    @Test
    public void controle() throws Exception {
        final Map<Integer, Map<String, List<Integer>>> mappings = bepaalPoortMappings();

        boolean okay = true;
        final Map<Integer, List<String>> externalMapping = new TreeMap<>();
        for (final Map.Entry<Integer, Map<String, List<Integer>>> mappingsEntry : mappings.entrySet()) {
            final Integer internalPort = mappingsEntry.getKey();
            final SortedSet<Integer> usedPorts = new TreeSet<>();
            final Map<String, List<Integer>> mapping = mappingsEntry.getValue();

            for (final Map.Entry<String, List<Integer>> mappingEntry : mapping.entrySet()) {
                final String service = mappingEntry.getKey();
                final List<Integer> externalPorts = mappingEntry.getValue();
                for (final Integer externalPort : externalPorts) {
                    externalMapping.computeIfAbsent(externalPort, (key) -> new ArrayList<>());
                    externalMapping.get(externalPort).add(service);

                    usedPorts.add(externalPort);
                }

                if (externalPorts.size() > 1) {
                    okay = false;
                    System.err.println(String.format("Service '%s' has multiple mappings for internal port %d", service, internalPort));
                }
            }

            KNOWN_PORTS.computeIfAbsent(internalPort, (key) -> "unknown");
            final String description = KNOWN_PORTS.get(internalPort);
            System.out.println(String.format("Mappings for internal port %d (%s) -> %s", internalPort, description, usedPorts));
        }

        for (final Map.Entry<Integer, List<String>> externalMappingEntry : externalMapping.entrySet()) {
            final Integer internalPort = externalMappingEntry.getKey();
            final List<String> usedByServices = externalMappingEntry.getValue();

            if (usedByServices.size() > 1) {
                okay = false;
                System.err.println(String.format("External port '%d' is used by multiple services -> %s", internalPort, usedByServices));
            }
        }

        Assert.assertTrue(okay);
    }

    private Map<Integer, Map<String, List<Integer>>> bepaalPoortMappings() throws Exception {
        final Pattern pattern = Pattern.compile("(\\d+)\\:(\\d+)");

        final Map<Integer, Map<String, List<Integer>>> mappings = new TreeMap<>();
        final List<String> services = ServicesUtil.bepaalAlleServices();
        services.remove("brp-database");
        for (final String service : services) {
            final Properties properties = ServiceUtil.getServiceProperties(service);
            final String ports = properties.getProperty("create.ports", "");
            final Matcher matcher = pattern.matcher(ports);
            while (matcher.find()) {
                final int port = Integer.parseInt(matcher.group(2));

                mappings.computeIfAbsent(port, (key) -> new TreeMap<>());
                final Map<String, List<Integer>> mapping = mappings.get(port);
                mapping.computeIfAbsent(service, (key) -> new ArrayList<>());
                final List<Integer> mappedPorts = mapping.get(service);
                mappedPorts.add(Integer.valueOf(matcher.group(1)));
            }
        }

        return mappings;
    }


}
