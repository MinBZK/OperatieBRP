/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.schedular;

import java.io.IOException;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * De Class JmxClient.
 */
@Service
public class JmxClient {

    /** De Constante LOGGER. */
    private static final Logger   LOGGER        = LoggerFactory.getLogger(DataVerzamelaar.class);

    /** De Constante MICROSECONDEN. */
    private static final int      MICROSECONDEN = 1000;

    /** De mbsc. */
    private MBeanServerConnection mbsc;

    /** De jmxc. */
    private JMXConnector          jmxc;

    /** De jmx service url. */
    @Value("${monitor.jmx.serviceurl}")
    private String                jmxServiceUrl;

    // @Value("${monitor.jmx.mbean.berichten}")
    // private String mbeanBerichten;
    // @Value("${monitor.jmx.mbean.berichten.inkomendeberichten}")
    // private String inkomendeBerichten;
    // @Value("${monitor.jmx.mbean.berichten.uitgaandeberichten}")
    // private String uitgaandeBerichten;
    // @Value("${monitor.jmx.mbean.berichten.soapfault}")
    // private String soapfault;
    // @Value("${monitor.jmx.mbean.berichten.fouteninfo}")
    // private String foutenInfo;
    // @Value("${monitor.jmx.mbean.berichten.foutenwaarschuwing}")
    // private String foutenWaarschuwing;
    // @Value("${monitor.jmx.mbean.berichten.foutenfout}")
    // private String foutenFout;
    // @Value("${monitor.jmx.mbean.berichten.foutensysteem}")
    // private String foutenSysteem;
    // @Value("${monitor.jmx.mbean.berichten.gemiddelderesponsetijd}")
    // private String gemiddeldeResponseTijd;
    // @Value("${monitor.jmx.mbean.berichten.gemiddeldeResponseTijdPerTijdsEenheid}")
    // private String gemiddeldeResponseTijdPerTijdsEenheid;

    /** De mbean catalina thread pool. */
    @Value("${monitor.jmx.mbean.catalina.threadpool}")
    private String                mbeanCatalinaThreadPool;

    /** De current threads busy. */
    @Value("${monitor.jmx.mbean.catalina.threadpool.busythreads}")
    private String                currentThreadsBusy;

    /** De max threads. */
    @Value("${monitor.jmx.mbean.catalina.threadpool.maxthreads}")
    private String                maxThreads;

    // @Value("${monitor.jmx.mbean.catalina.datasource}")
    // private String mbeanCatalinaDataSource;
    // @Value("${monitor.jmx.mbean.catalina.datasource.numactive}")
    // private String numActive;

    /** De mbean memory. */
    @Value("${monitor.jmx.mbean.memory}")
    private String                mbeanMemory;

    /** De heap memory usage. */
    @Value("${monitor.jmx.mbean.memory.heapmemoryusage}")
    private String                heapMemoryUsage;

    /** De non heap memory usage. */
    @Value("${monitor.jmx.mbean.memory.nonheapmemoryusage}")
    private String                nonHeapMemoryUsage;

    /** De mbean cxf. */
    @Value("${monitor.jmx.mbean.cxf}")
    private String                mbeanCxf;

    /** De avg response time. */
    @Value("${monitor.jmx.mbean.cxf.avgresponsetime}")
    private String                avgResponseTime;

    /** De connected. */
    private boolean               connected;

    /**
     * Connect.
     *
     * @return true, als succesvol
     * @throws MalformedObjectNameException de malformed object name exception
     * @throws NullPointerException de null pointer exception
     * @throws InstanceNotFoundException de instance not found exception
     * @throws IntrospectionException de introspection exception
     * @throws ReflectionException de reflection exception
     * @throws AttributeNotFoundException de attribute not found exception
     */
    public boolean connect() throws MalformedObjectNameException, NullPointerException, InstanceNotFoundException,
            IntrospectionException, ReflectionException, AttributeNotFoundException
    {
        // Create an RMI connector client and
        // connect it to the RMI connector server
        LOGGER.info("\nCreate an RMI connector client and connect it to the RMI connector server");
        // service:jmx:rmi:///jndi/rmi://:9998/jmxrmi
        JMXServiceURL url;
        try {
            url = new JMXServiceURL(jmxServiceUrl);
            jmxc = JMXConnectorFactory.connect(url, null);
            LOGGER.info("\nGet an MBeanServerConnection");
            mbsc = jmxc.getMBeanServerConnection();

            connected = true;
        } catch (IOException e) {
            connected = false;
            LOGGER.error("Kan niet verbonden worden met de JMX service: " + jmxServiceUrl, e);
        }

        return connected;
    }

    // /**
    // * Haalt het aantal inkomende berichten op.
    // *
    // * @return als jmx client niet verbonden is 0
    // * @throws Exception bij problemen met de jmx verbinding
    // */
    // public int getInkomendeBerichten() throws Exception {
    // return getWaarde(mbeanBerichten, inkomendeBerichten);
    // }

    /**
     * Haalt het aantal inkomende berichten op.
     *
     * @return als jmx client niet verbonden is 0
     * @throws Exception bij problemen met de jmx verbinding
     */
    public int getUitgaandeBerichten() throws Exception {
        int uitgaandeBerichten = 0;
        int responseTijd = 0;
        ObjectName cxfMBeanName = new ObjectName(mbeanCxf);
        Set<ObjectInstance> set = mbsc.queryMBeans(cxfMBeanName, null);

        if (set.size() > 0) {
            for (Object o : set.toArray()) {
                ObjectName port = ((ObjectInstance) o).getObjectName();
//                MBeanInfo portInfo = mbsc.getMBeanInfo(port);
//                MBeanAttributeInfo[] portAttributes = portInfo.getAttributes();
//                for (MBeanAttributeInfo attr : portAttributes) {
//                    try {
//                        LOGGER.debug(" -> " + attr.getName() + ": " + mbsc.getAttribute(port, attr.getName()));
//                    } catch (MBeanException e) {
//                        LOGGER.error("FOUT: ", e);
//                    }
//                }
                uitgaandeBerichten += (Integer) mbsc.getAttribute(port, "NumInvocations");
            }
        }

        return uitgaandeBerichten;
    }

    /**
     * Haalt het aantal database connecties op.
     *
     * @return als jmx client niet verbonden is 0
     * @throws Exception bij problemen met de jmx verbinding
     */
    // public int getSqlDataSourceActiveConnections() throws Exception {
    // return getWaarde(mbeanCatalinaDataSource, numActive);
    // }

    /**
     * Haalt het aantal actieve threads op.
     *
     * @return als jmx client niet verbonden is 0
     * @throws Exception bij problemen met de jmx verbinding
     */
    public int getActiveThreads() throws Exception {
        return getWaarde(mbeanCatalinaThreadPool, currentThreadsBusy);
    }

    /**
     * Haalt maximaal te gebruiken threads op.
     *
     * @return als jmx client niet verbonden is 0
     * @throws Exception bij problemen met de jmx verbinding
     */
    public int getMaxThreads() throws Exception {
        return getWaarde(mbeanCatalinaThreadPool, maxThreads);
    }

    /**
     * Haalt de heap memory gebruik op.
     *
     * @return als jmx client niet verbonden is 0
     * @throws Exception bij problemen met de jmx verbinding
     */
    public Long getHeapMemoryUsage() throws Exception {
        if (connected) {
            ObjectName mbeanName = new ObjectName(mbeanMemory);
            return (Long) ((CompositeDataSupport) mbsc.getAttribute(mbeanName, heapMemoryUsage)).get("used");
        }

        return 0L;
    }

    /**
     * Haalt NonHeap memory gebruik op.
     *
     * @return als jmx client niet verbonden is 0
     * @throws Exception bij problemen met de jmx verbinding
     */
    public Long getNonHeapMemoryUsage() throws Exception {
        if (connected) {
            ObjectName mbeanName = new ObjectName(mbeanMemory);
            return (Long) ((CompositeDataSupport) mbsc.getAttribute(mbeanName, nonHeapMemoryUsage)).get("used");
        }

        return 0L;
    }

    // /**
    // * Haalt het aantal opgetreden fouten op.
    // *
    // * @return als jmx client niet verbonden is 0
    // * @throws Exception bij problemen met de jmx verbinding
    // */
    // public Fouten getFoutenTelling() throws Exception {
    // if (connected) {
    // ObjectName mbeanName = new ObjectName(mbeanBerichten);
    //
    // return new Fouten((Integer) mbsc.getAttribute(mbeanName, soapfault), (Integer) mbsc.getAttribute(mbeanName,
    // foutenInfo), (Integer) mbsc.getAttribute(mbeanName, foutenWaarschuwing),
    // (Integer) mbsc.getAttribute(mbeanName, foutenFout), (Integer) mbsc.getAttribute(mbeanName,
    // foutenSysteem));
    // }
    //
    // return null;
    // }

     /**
     * Haalt de response tijden op.
     *
     * @return ResponseTijd object met daarin de response tijd van een servlet en de ingebouwde cxf AvgResponseTime
     * @throws Exception bij problemen met de jmx verbinding
     */
     public ResponseTijd getResponseTijden() throws Exception {
         ResponseTijd responseTijd = new ResponseTijd(0,0,0);

         int gemiddeldeResponseTijd = 0;
         ObjectName cxfMBeanName = new ObjectName(mbeanCxf);
         Set<ObjectInstance> set = mbsc.queryMBeans(cxfMBeanName, null);

         if (set.size() > 0) {
             for (Object o : set.toArray()) {
                 ObjectName port = ((ObjectInstance) o).getObjectName();
                 if ((Long) mbsc.getAttribute(port, "MaxResponseTime") > 0) {
                 gemiddeldeResponseTijd += (Integer) mbsc.getAttribute(port, "AvgResponseTime");
                 }
             }
         }
         gemiddeldeResponseTijd = (gemiddeldeResponseTijd / set.size()) / 1000;
         responseTijd.setGemiddeldeResponseTijdPerTijdsEenheid(gemiddeldeResponseTijd);
         return responseTijd;
     }

    /**
     * Disconnect.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void disconnect() throws IOException {
        // Close MBeanServer connection
        LOGGER.info("\nSluit verbinding met server");
        jmxc.close();
        connected = false;
    }

    public boolean isConnected() {
        return connected;
    }

     /**
     * Zet alle tellers op 0.
     *
     * @throws Exception wanneer iets misgegaan is bij het resetten.
     */
     public void resetTellers() throws Exception {
     ObjectName cxfMBeanName = new ObjectName(mbeanCxf);
     Set<ObjectInstance> set = mbsc.queryMBeans(cxfMBeanName, null);
     if (set.size() > 0) {
         for (Object o : set.toArray()) {
             ObjectName port = ((ObjectInstance) o).getObjectName();
             mbsc.invoke(port, "reset", null, null);
         }
     }
     }

    /**
     * Haalt een waarde op.
     *
     * @param mbean de mbean
     * @param attribuut de attribuut
     * @return waarde
     * @throws Exception de exception
     */
    private int getWaarde(final String mbean, final String attribuut) throws Exception {
        if (connected) {
            ObjectName mbeanName = new ObjectName(mbean);
            return (Integer) mbsc.getAttribute(mbeanName, attribuut);
        }

        return 0;
    }
}
