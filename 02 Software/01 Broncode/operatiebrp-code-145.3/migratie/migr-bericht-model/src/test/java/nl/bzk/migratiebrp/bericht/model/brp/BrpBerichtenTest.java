/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.brp;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.brp.factory.BrpBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.xml.BrpXml;
import org.junit.Assert;
import org.junit.Test;

public class BrpBerichtenTest {

    @Test(expected = RuntimeException.class)
    public void testElementToStringExceptie() throws BerichtInhoudException {
        BrpXml.SINGLETON.elementToString(null);
    }

    @Test
    public void test() {
        final String bericht =
                "<?xml version=\"1.0\"?><brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" "
                        + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                        + "><brp:stuurgegevens><brp:zendendePartij>199903</brp:zendendePartij><brp:zendendeSysteem>BRP</brp:zendendeSysteem><brp"
                        + ":referentienummer>ce025753-493a-4983-afdf-6632585d3b76</b"
                        + "rp:referentienummer><brp:crossReferentienummer>65e5f78d-32a9-40ed-a620-828000000023</brp:crossReferentienummer><brp"
                        + ":tijdstipVerzending>2015-12-07T08:54:11.680+00:00</brp:tijd"
                        + "stipVerzending></brp:stuurgegevens><brp:resultaat><brp:verwerking>Geslaagd</brp:verwerking><brp:bijhouding>Verwerkt</brp"
                        + ":bijhouding><brp:hoogsteMeldingsniveau>Geen</brp:hoogs"
                        + "teMeldingsniveau></brp:resultaat><brp:gBAVoltrekkingHuwelijkInNederland brp:objecttype=\"AdministratieveHandeling\">"
                        + "<brp:partijCode>059901</brp"
                        + ":partijCode><brp:tijdstipRegistratie>2015-12-07T08:54:09"
                        + ".222+00:00</brp:tijdstipRegistratie></brp:gBAVoltrekkingHuwelijkInNederland></brp"
                        + ":isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R>";

        final BrpBericht brpBericht = BrpBerichtFactory.SINGLETON.getBericht(bericht);
        Assert.assertTrue(brpBericht instanceof RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingResultaat);
    }

    @Test
    public void testOngeldigBericht() {
        final String bericht =
                "<?xml version=\"1.0\"?><TEST-BERICHT/>";

        final BrpBericht brpBericht = BrpBerichtFactory.SINGLETON.getBericht(bericht);
        Assert.assertEquals("OngeldigBericht", brpBericht.getBerichtType());
    }

}
