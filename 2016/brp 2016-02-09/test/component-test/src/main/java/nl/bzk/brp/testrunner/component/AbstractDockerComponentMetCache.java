/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.omgeving.CacheHouder;
import nl.bzk.brp.testrunner.omgeving.ComponentException;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import nl.bzk.brp.testrunner.component.util.PoortManager;

/**
 * Helper class om caches te verversen.
 *
 * Let op, de poortmap voor de JMX poort moet intern hetzelfde zijn als
 * extern, dus niet -p 1055:1099, maar 1099:1099
 *
 * docker run -d -p 10880:10880 -p 60314:8080 -e CATALINA_OPTS="-Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=localhost
 * -Dcom.sun.management.jmxremote.port=10880 -Dcom.sun.management.jmxremote.rmi.port=10880 -Dcom.sun.management.jmxremote.ssl=false
 * -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false"
 * -e ROUTERINGCENTRALE_PORT_61616_TCP_PORT=39912 -e JMX_PORT=10880 --add-host gba-levering-routering-centrale:172.17.42.1
 * brp/brp-levering-routering-centrale
 */
public abstract class AbstractDockerComponentMetCache extends AbstractDockerComponent implements CacheHouder {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String JMX_OPERATIE      = "herlaadViaJmx";
    private static final String OBJECT_NAME_BASIS = "-nl.bzk.brp.levering.business.toegang.abonnement.cache:name=AutAutCache";


    // de interne poort kan om de één of andere reden niet 1099 zijn....!!!
    private final int jmxPoort = PoortManager.get().geefVrijePoort();
    private final ObjectName objectName;

    protected AbstractDockerComponentMetCache(final Omgeving omgeving, final String jmxObjectNaamPrefix) {
        super(omgeving);

        try {
            objectName = new ObjectName(jmxObjectNaamPrefix + OBJECT_NAME_BASIS);
        } catch (MalformedObjectNameException e) {
            throw new ComponentException(e);
        }
    }

    @Override
    protected Map<String, String> geefOmgevingsVariabelen() {
        final Map<String, String> map = super.geefOmgevingsVariabelen();
        map.put("JMX_PORT", String.valueOf(jmxPoort));
        map.put("DOCKER_IP", getOmgeving().geefOmgevingHost());
        return map;
    }

    @Override
    protected int geefExternePoort(final int internePoort) {
        //intern en extern moet voor JMX gelijk zijn!
        if (internePoort == jmxPoort) {
            return internePoort;
        }
        return super.geefExternePoort(internePoort);
    }

    @Override
    protected List<Integer> geefInternePoorten() {
        final List<Integer> internePoorten = super.geefInternePoorten();
        internePoorten.add(jmxPoort);
        return internePoorten;
    }

    @Override
    public final void updateCache() {

        LOGGER.info("Ververs cache: " + objectName);
        final String jmxHost = getOmgeving().geefOmgevingHost();
        final String jmxServiceURL = String.format("service:jmx:rmi://%s/jndi/rmi://%s:%d/jmxrmi",
            jmxHost, jmxHost, jmxPoort);

        LOGGER.info(String.format("JMX Service URL %s", jmxServiceURL));
        try {
            final JMXServiceURL serviceURL = new JMXServiceURL(jmxServiceURL);
            JMXConnector connect = null;
            try {
                connect = JMXConnectorFactory.connect(serviceURL);
                LOGGER.info("JMX Succesvol verbonden");
                final MBeanServerConnection mBeanServerConnection = connect.getMBeanServerConnection();;
                mBeanServerConnection.invoke(objectName, JMX_OPERATIE, null, null);
            }  finally {
                if (connect != null) {
                    connect.close();
                }
            }
        } catch (MBeanException | IOException | ReflectionException | InstanceNotFoundException e) {
            throw new ComponentException("Het is niet gelukt de cache te verversen: " + objectName.toString(), e);
        }
    }
}
