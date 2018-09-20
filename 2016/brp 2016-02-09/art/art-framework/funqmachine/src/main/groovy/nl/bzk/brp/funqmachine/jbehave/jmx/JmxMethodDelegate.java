/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.jmx;

import java.io.IOException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import nl.bzk.brp.funqmachine.configuratie.JmxCommand;

/**
 * Created by operatiebrp on 04/12/15.
 */
public class JmxMethodDelegate extends JmxDelegate {

    @Override
    protected Object execute(final MBeanServerConnection mBeanServerConnection,
        final JmxCommand jmxCommand, final String[] signatureArray)
        throws MalformedObjectNameException, MBeanException, IOException, ReflectionException, InstanceNotFoundException {

        return mBeanServerConnection.invoke(new ObjectName(jmxCommand.getJMXObjectName()),
            jmxCommand.getMethodName(), jmxCommand.getParams(), signatureArray);
    }
}
