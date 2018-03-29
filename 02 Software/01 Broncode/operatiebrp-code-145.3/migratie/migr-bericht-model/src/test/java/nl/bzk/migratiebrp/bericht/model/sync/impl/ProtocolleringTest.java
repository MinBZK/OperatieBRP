/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.time.LocalDateTime;
import java.time.Month;
import org.junit.Assert;
import org.junit.Test;

public class ProtocolleringTest {

    @Test
    public void testMilliseconden() {
        Protocollering subject = new Protocollering(12322L);
        Assert.assertNull(subject.getStartTijdstip());

        LocalDateTime startTijdstip = LocalDateTime.of(2016, Month.AUGUST, 4, 12, 30, 7, 123456789);
        System.out.println("LocalDateTime: " + startTijdstip);
        subject.setStartTijdstip(startTijdstip);
        System.out.println("XML: " + subject.getProtocolleringType().getStartTijdstip());
        LocalDateTime check = subject.getStartTijdstip();
        System.out.println("Check: " + check);

        Assert.assertEquals(startTijdstip, check);
    }
}
