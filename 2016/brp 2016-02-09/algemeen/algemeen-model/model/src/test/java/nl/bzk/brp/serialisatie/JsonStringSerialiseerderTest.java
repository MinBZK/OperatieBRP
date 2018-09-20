/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import static org.junit.Assert.assertEquals;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.internbericht.AfnemerindicatieOnderhoudOpdracht;
import org.junit.Test;


public class JsonStringSerialiseerderTest {

    private JsonStringSerializer<AfnemerindicatieOnderhoudOpdracht> jsonStringSerialiseerder =
        new JsonStringSerializer<>(
            AfnemerindicatieOnderhoudOpdracht.class);

    @Test
    public void testObjectNaarJson() {
        AfnemerindicatieOnderhoudOpdracht afnemerindicatieOnderhoudOpdracht = new AfnemerindicatieOnderhoudOpdracht();
        afnemerindicatieOnderhoudOpdracht.setAbonnementNaam(new NaamEnumeratiewaardeAttribuut("abonnement x"));
        afnemerindicatieOnderhoudOpdracht.setPersoonId(123456);
        afnemerindicatieOnderhoudOpdracht.setReferentienummer(new ReferentienummerAttribuut("12abc"));
        afnemerindicatieOnderhoudOpdracht.setPartijCode(new PartijCodeAttribuut(12345678));

        String jsonBericht = jsonStringSerialiseerder.serialiseerNaarString(afnemerindicatieOnderhoudOpdracht);

        assertEquals("{\"abonnementNaam\":\"abonnement x\",\"partijCode\":12345678,\"persoonId\":123456,"
                + "\"referentienummer\":\"12abc\"}", jsonBericht);
    }

    @Test
    public void testObjectVanuitJson() {
        String testJsonString =
                "{\"abonnementNaam\":\"abonnement y\",\"partijCode\":12345678,\"persoonId\":65431,"
                        + "\"referentienummer\":\"12abc\"}";

        AfnemerindicatieOnderhoudOpdracht afnemerindicatieOnderhoudOpdracht =
                jsonStringSerialiseerder.deserialiseerVanuitString(testJsonString);

        assertEquals("abonnement y", afnemerindicatieOnderhoudOpdracht.getAbonnementNaam().getWaarde());
        assertEquals(Integer.valueOf(65431), afnemerindicatieOnderhoudOpdracht.getPersoonId());
        assertEquals("12abc", afnemerindicatieOnderhoudOpdracht.getReferentienummer().getWaarde());
        assertEquals(Integer.valueOf(12345678), afnemerindicatieOnderhoudOpdracht.getPartijCode().getWaarde());
    }

    @Test
    public void testVanJsonNaarObjectNaarJson() {
        String testJsonString =
                "{\"abonnementNaam\":\"abonnement y\",\"partijCode\":12345678,\"persoonId\":65431,"
                        + "\"referentienummer\":\"12abc\"}";

        AfnemerindicatieOnderhoudOpdracht afnemerindicatieOnderhoudOpdracht =
                jsonStringSerialiseerder.deserialiseerVanuitString(testJsonString);
        String resultaat = jsonStringSerialiseerder.serialiseerNaarString(afnemerindicatieOnderhoudOpdracht);

        assertEquals(testJsonString, resultaat);
    }

}
