/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;

import org.junit.Assert;
import org.junit.Test;

public class MvVerhuizingAntwoordBerichtTest {

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void test() throws Exception {
        final MvVerhuizingAntwoordBericht bericht = new MvVerhuizingAntwoordBericht();
        bericht.setStatus(StatusType.WAARSCHUWING);
        bericht.setToelichting("Bla bla bla");

        final String formatted = bericht.format();
        System.out.println(formatted);

        final BrpBericht parsed = factory.getBericht(formatted);
        parsed.setMessageId(bericht.getMessageId());
        System.out.println(parsed);

        Assert.assertEquals(bericht, parsed);
    }
}
