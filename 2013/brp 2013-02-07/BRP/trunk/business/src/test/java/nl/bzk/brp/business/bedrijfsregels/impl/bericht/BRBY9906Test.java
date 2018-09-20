/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;


import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.model.administratie.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class BRBY9906Test {

    private BRBY9906 brby9906;

    private InschrijvingGeboorteBericht bericht;
    private CommunicatieIdMap communicatieIds;


    @Before
    public void setup() {
        communicatieIds = new CommunicatieIdMap();
        bericht = new InschrijvingGeboorteBericht();
        ReflectionTestUtils.setField(bericht, "identificeerbaarObjectIndex", communicatieIds);

        brby9906 = new BRBY9906();
    }

    @Test
    public void testGelijkeEntiteitInReferentie() {
        PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setCommunicatieID("p1");
        persoon1.setReferentieID("p1");

        PersoonBericht persoon2 = new PersoonBericht();
        persoon2.setCommunicatieID("p2");

        communicatieIds.put(persoon1);
        communicatieIds.put(persoon2);

        List<Melding> meldingen = brby9906.executeer(bericht);

        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testNietGelijkeEntiteitInReferentie() {
        PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setCommunicatieID("p1");
        persoon1.setReferentieID("a1");

        PersoonAdresBericht adres1 = new PersoonAdresBericht();
        adres1.setCommunicatieID("a1");

        communicatieIds.put(persoon1);
        communicatieIds.put(adres1);

        List<Melding> meldingen = brby9906.executeer(bericht);

        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY9906, meldingen.get(0).getCode());
        Assert.assertEquals("p1", meldingen.get(0).getCommunicatieID());
    }

    @Test
    public void testVerwezenObjectBestaatNiet() {
        PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setCommunicatieID("p1");
        persoon1.setReferentieID("bestaatniet");

        communicatieIds.put(persoon1);

        List<Melding> meldingen = brby9906.executeer(bericht);

        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testVerwijzingVanUitMeldingen() {
        PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setCommunicatieID("p1");

        AdministratieveHandelingGedeblokkeerdeMeldingBericht deblokkeerMelding =
                new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        deblokkeerMelding.setCommunicatieID("d1");
        deblokkeerMelding.setReferentieID("p1");

        communicatieIds.put(persoon1);
        communicatieIds.put(deblokkeerMelding);

        List<Melding> meldingen = brby9906.executeer(bericht);

        Assert.assertEquals(0, meldingen.size());
    }
}
