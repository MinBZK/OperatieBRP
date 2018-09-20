/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.berichten;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;

import nl.moderniseringgba.isc.test.PropertyTestUtil;

import org.junit.Test;

public class BerichtTest {
    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Bericht bericht = new Bericht();

        PropertyTestUtil.testMutableProperty(bericht, "id", null, 1L);
        PropertyTestUtil.testMutableProperty(bericht, "tijdstip", null, new Timestamp(System.currentTimeMillis()));
        PropertyTestUtil.testMutableProperty(bericht, "kanaal", null, "BRP");
        PropertyTestUtil.testMutableProperty(bericht, "richting", null, Direction.INKOMEND);
        PropertyTestUtil.testMutableProperty(bericht, "messageId", null, "234234234");
        PropertyTestUtil.testMutableProperty(bericht, "correlationId", null, "4643563456546");
        PropertyTestUtil.testMutableProperty(bericht, "bericht", null, "bla bal blabla blabla");
        PropertyTestUtil.testMutableProperty(bericht, "naam", null, "testbericht");
        PropertyTestUtil.testMutableProperty(bericht, "processInstanceId", null, 324523543L);
        PropertyTestUtil.testMutableProperty(bericht, "herhaling", null, 13);
        PropertyTestUtil.testMutableProperty(bericht, "bronGemeente", null, "3455");
        PropertyTestUtil.testMutableProperty(bericht, "doelGemeente", null, "0512");
    }

}
