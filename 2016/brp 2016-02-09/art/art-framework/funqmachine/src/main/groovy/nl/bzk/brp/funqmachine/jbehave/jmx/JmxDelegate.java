/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.jmx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import nl.bzk.brp.funqmachine.configuratie.JmxCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by operatiebrp on 04/12/15.
 */
public abstract class JmxDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(JmxDelegate.class);

    public Object perform(JmxCommand jmxCommand) {
        final JMXServiceURL serviceURL;
        Object result = null;

        try {
            serviceURL = new JMXServiceURL(jmxCommand.getJmxURL());
            JMXConnector connect = null;
            try {

                String[] signatureArray = null;
                if (jmxCommand.getParams() != null) {
                    final List<String> signatureList = new ArrayList<>();
                    for (Object o : jmxCommand.getParams()) {
                        signatureList.add(o.getClass().getName());
                    }
                    signatureArray = signatureList.toArray(new String[signatureList.size()]);
                }

                connect = JMXConnectorFactory.connect(serviceURL);
                final MBeanServerConnection mBeanServerConnection = connect.getMBeanServerConnection();
                result = execute(mBeanServerConnection, jmxCommand, signatureArray);

            } finally {
                if (connect != null) {
                    connect.close();
                }
            }

        } catch (MalformedObjectNameException | MBeanException | IOException | ReflectionException | AttributeNotFoundException | InstanceNotFoundException e) {
            LOGGER.error("Het is niet gelukt de jmx operatie uit te voeren: " + jmxCommand + ": "
                + " omdat: " + e.getMessage() + " - " + e.getCause());
        }

        return result;
    }

    abstract protected Object execute(MBeanServerConnection mBeanServerConnection, JmxCommand jmxCommand, String[] signatureArray)
        throws MalformedObjectNameException, MBeanException, IOException, ReflectionException, InstanceNotFoundException, AttributeNotFoundException;
}
