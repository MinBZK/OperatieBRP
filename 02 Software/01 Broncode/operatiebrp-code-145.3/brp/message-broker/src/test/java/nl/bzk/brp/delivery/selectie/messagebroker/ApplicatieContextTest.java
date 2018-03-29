/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.messagebroker;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 */
public class ApplicatieContextTest {

    @Test
    public void test() {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("brp-broker-context.xml");
    }
}
