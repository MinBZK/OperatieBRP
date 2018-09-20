/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.jmx;

import nl.bzk.brp.funqmachine.configuratie.JmxCommand;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by operatiebrp on 04/12/15.
 */
public class JmxMethodDelegateTest {

    @Ignore
    @Test
    public void purgeSingleQueue() {
        JmxCommand jmxCommand = new JmxCommand("service:jmx:rmi://oap10.modernodam.nl/jndi/rmi://oap10.modernodam.nl:1099/jmxrmi", "",
            "nl.bzk.brp:type=Broker,brokerName=BrpMessageBroker,destinationType=Queue,destinationName=AdministratieveHandelingen", "purge");
        Object result = new JmxMethodDelegate().perform(jmxCommand);
    }
}
