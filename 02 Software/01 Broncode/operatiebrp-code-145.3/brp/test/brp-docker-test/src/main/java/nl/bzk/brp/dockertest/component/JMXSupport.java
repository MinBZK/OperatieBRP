/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import static nl.bzk.brp.dockertest.component.Poorten.JMX_POORT;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.test.common.TestclientExceptie;

/**
 * Interface voor JMX enabled Dockers.
 */
public interface JMXSupport extends Docker {

    Logger LOGGER = LoggerFactory.getLogger();
    int MAX_AANTAL_POGINGEN = 3;


    String getJmxDomain();

    /**
     * Voert een jmxOperatie uit
     * @param jmxOperatie naam vd JMX operatie
     * @param objectName object naam
     * @return resultaat van operatie
     */
    default Object voerUit(final String jmxOperatie, final ObjectName objectName) {
        try {
            return jmxRequest(null, jmxOperatie, null, objectName);
        } catch (IOException | MBeanException | InstanceNotFoundException | ReflectionException e) {
            throw new JMXException(String.format("JXM operatie mislukt [operatie=%s]", jmxOperatie), e);
        }
    }

    /**
     * Retourneert waarden van MBean attributen.
     * @param attributen lijst met opgevraagde attributen
     * @param objectName object naam
     * @return lijst met MBean attributtwaarden
     */
    default AttributeList geefJMXAttributen(final String[] attributen, final ObjectName objectName)
            throws IOException, MBeanException, InstanceNotFoundException, ReflectionException {
        return (AttributeList) jmxRequest(null, null, attributen, objectName);
    }

    default Object jmxRequest(Integer pogingNr, final String jmxOperatie, final String[] attributen, final ObjectName objectName)
            throws IOException, MBeanException, InstanceNotFoundException, ReflectionException {
        final Integer poging = pogingNr == null ? 1 : pogingNr;
        LOGGER.info("voor {} uit: {}, poging: {}, dockernaam: {}", jmxOperatie != null ? "JMX operatie" : "JMX attr.waarden ophalen", jmxOperatie != null ?
                jmxOperatie : attributen, poging, this.getLogischeNaam());

        Object result = null;
        try {
            final JMXServiceURL serviceURL =
                    new JMXServiceURL("simple", getOmgeving().getDockerHostname(), this.getPoortMap().get(JMX_POORT));
            LOGGER.info(String.format("JMX Service URL %s", serviceURL.toString()));
            JMXConnector connect = null;
            try {
                final Map<String, Object> environment = Maps.newHashMap();
                environment.put(JMXConnector.CREDENTIALS, new String[]{"admin", "admin"});
                environment.put("jmx.remote.requesttimeout", 60);
                connect = JMXConnectorFactory.connect(serviceURL, environment);
                LOGGER.info("JMX Succesvol verbonden");
                final MBeanServerConnection mBeanServerConnection = connect.getMBeanServerConnection();
                if (jmxOperatie != null) {
                    return mBeanServerConnection.invoke(objectName, jmxOperatie, null, null);
                } else if (attributen != null) {
                    result = mBeanServerConnection.getAttributes(objectName, attributen);
                }
            } finally {
                if (connect != null) {
                    connect.close();
                }
            }
        } catch (IOException e) {
            LOGGER.warn("IO opgetreden, probeer opnieuw", e.getMessage());
            if (poging == MAX_AANTAL_POGINGEN) {
                throw new JMXException(String.format("Het is niet gelukt de operatie [%s] uit te voeren binnen aantal pogingen", jmxOperatie));
            }
            result = jmxRequest(poging + 1, jmxOperatie, attributen, objectName);
        } catch (Exception e) {
           LOGGER.error("error refreshing caches", e);
           throw new TestclientExceptie(e);
        }
        return result;
    }
}
