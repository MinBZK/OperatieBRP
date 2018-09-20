/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import org.junit.Assert;
import org.junit.Test;

public class ZoekPersoonOpActueleGegevensVerzoekBerichtTest extends AbstractSyncBerichtTest {

    @Test
    public void test() throws Exception {
        final ZoekPersoonOpActueleGegevensVerzoekBericht subject = new ZoekPersoonOpActueleGegevensVerzoekBericht();
        subject.setANummer("1234567890");
        subject.setBsn("123456789");
        subject.setGeslachtsnaam("Jansen");
        subject.setPostcode("1234RE");
        subject.setAanvullendeZoekcriteria("BLAPR");

        Assert.assertEquals("1234567890", subject.getANummer());
        Assert.assertEquals("123456789", subject.getBsn());
        Assert.assertEquals("Jansen", subject.getGeslachtsnaam());
        Assert.assertEquals("1234RE", subject.getPostcode());
        Assert.assertEquals("BLAPR", subject.getAanvullendeZoekcriteria());

        controleerFormatParse(subject);
        controleerSerialization(subject);
    }

}
