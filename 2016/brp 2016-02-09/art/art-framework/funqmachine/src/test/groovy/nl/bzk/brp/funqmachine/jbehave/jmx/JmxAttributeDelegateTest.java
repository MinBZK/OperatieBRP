/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.jmx;


import javax.management.ObjectName;
import nl.bzk.brp.funqmachine.configuratie.JmxCommand;
import org.junit.Ignore;
import org.junit.Test;

/**
 */
public class JmxAttributeDelegateTest {

    @Ignore
    @Test
    public void getQueueNames() {
        JmxCommand jmxCommand = new JmxCommand("service:jmx:rmi://oap10.modernodam.nl/jndi/rmi://oap10.modernodam.nl:1099/jmxrmi",
            "", "nl.bzk.brp:type=Broker,brokerName=BrpMessageBroker", null);
        jmxCommand.setAttributeName("Queues");
        Object result = new JmxAttributeDelegate().perform(jmxCommand);

        ObjectName[] objectNames = (ObjectName[]) result;
    }

}
