/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.omgeving;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

public class StandaardOmgeving implements Omgeving {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String                         DOCKER_IP_BEGIN = "172.17.";
    private final        Map<String, Component> naamMap         = new LinkedHashMap<>();

    private static final String HOST_IP = bepaalHostIP();

    enum OmgevingStatus {
        INITIEEL,
        DEFINITIEF,
        GESTART,
        GESTOPT
    }

    private OmgevingStatus status = OmgevingStatus.INITIEEL;

    {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (status != OmgevingStatus.GESTOPT) {
                    LOGGER.info("JVM gestopt, probeer omgeving alsnog te stoppen");
                    stopAlleComponenten();
                }
            }
        });
    }

    public void add(final Component component) {
        if (status != OmgevingStatus.INITIEEL) {
            throw new IllegalStateException();
        }
        naamMap.put(component.getLogischeNaam(), component);
    }

    @Override
    public boolean bevat(final String logischeNaam) {
        return naamMap.containsKey(logischeNaam);
    }

    @Override
    public void start() {
        if (status != OmgevingStatus.DEFINITIEF) {
            throw new IllegalStateException();
        }
        LOGGER.info("Omgeving wordt gestart");
        try {
            for (Component dockerComponent : naamMap.values()) {
                dockerComponent.preStart();
            }
            for (Component dockerComponent : naamMap.values()) {
                dockerComponent.start();
            }
        } catch (ComponentException e) {
            throw new OmgevingException(e);
        }
        status = OmgevingStatus.GESTART;
    }

    @Override
    public void stop() {
        if (status != OmgevingStatus.GESTART) {
            throw new IllegalStateException();
        }
        stopAlleComponenten();
        status = OmgevingStatus.GESTOPT;
    }

    @Override
    public Component geefComponent(final String logischeNaam) {
        for (final Component dockerComponent : naamMap.values()) {
            if (logischeNaam.equals(dockerComponent.getLogischeNaam())) {
                return dockerComponent;
            }
        }
        throw new OmgevingException(String.format("Component met naam %s niet gevonden", logischeNaam));
    }

    @Override
    public Iterable<Component> geefComponenten() {
        return naamMap.values();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> geefComponenten(final Class<T> type) {
        final List<T> componenten = new LinkedList<>();
        for (final Component component : naamMap.values()) {
            if (type.isAssignableFrom(component.getClass())) {
                componenten.add((T) component);
            }
        }
        return componenten;
    }

    @Override
    public <T> T geefComponent(final Class<T> type) {
        final List<T> componenten = geefComponenten(type);
        if (componenten.size() > 1) {
            throw new OmgevingException("Er bestaan meerdere componenten van het gegeven type");
        }
        if (componenten.size() == 0) {
            throw new OmgevingException("Component zit niet in de omgeving: " + type.getSimpleName()) ;
        }
        return componenten.iterator().next();
    }

    @Override
    public String geefOmgevingHost() {
        return HOST_IP;
    }


    @Override
    public boolean isGestart() {
        return status == OmgevingStatus.GESTART;
    }

    @Override
    public boolean isGestopt() {
        return status == OmgevingStatus.GESTOPT;
    }


    public final Omgeving maak() {
        if (status != OmgevingStatus.INITIEEL) {
            throw new IllegalStateException();
        }
        status = OmgevingStatus.DEFINITIEF;
        LOGGER.info("Omgeving definief gemaakt " + naamMap.keySet());
        return this;
    }

    /**
     * Deze methode geeft het host IP-adres van de omgeving.
     */
    private static String bepaalHostIP() {

        OperatingSystem os = new OperatingSystem();
        if (os.getOperatingSystemType() == OperatingSystem.OSType.MacOS) {
            return "192.168.99.100";
        }

        final Enumeration<NetworkInterface> netwerkInterfaces;
        try {
            netwerkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        while (netwerkInterfaces.hasMoreElements()) {
            final NetworkInterface netwerkInterface = netwerkInterfaces.nextElement();
            for (InterfaceAddress interfaceAdres : netwerkInterface.getInterfaceAddresses()) {
                final InetAddress inetAddress = interfaceAdres.getAddress();
                final String ipAdres = inetAddress.getHostAddress();
                if (ipAdres.startsWith(DOCKER_IP_BEGIN)) {
                    return ipAdres;
                }
            }

        }
        throw new RuntimeException("IP-adres niet gevonden");
    }

    private void stopAlleComponenten() {
        LOGGER.info("Start stoppen omgeving");
        final LinkedList<Component> list = new LinkedList<>(naamMap.values());
        Collections.reverse(list);

            for (final Component component : list) {
                if (component.isGestart()) {
                    try {
                        component.stop();
                    } catch (Exception e) {
                        LOGGER.info("Stoppen mislukt: " + component.getLogischeNaam());
                    }
                }
            }
        LOGGER.info("Omgeving gestopt");
    }

}
