/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc308;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.brp.BrpBijhoudingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.IscGemeenten;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningVerzoekBericht;
import nl.moderniseringgba.isc.migratie.service.GemeenteService;
import nl.moderniseringgba.isc.migratie.service.Stelsel;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/uc308-test-beans.xml", "classpath*:usecase-beans.xml" })
public class ControleerBijhoudingGemeenteDecisionTest {

    private static final String BRP_GEMEENTE = "1234";
    private static final String LO3_GEMEENTE = "5678";

    @Inject
    private ControleerBijhoudingsGemeenteDecision controleerBijhoudingsGemeenteDecision;

    @Inject
    private GemeenteService gemeenteService;

    @Test
    public void testLo3Gemeente() {

        // when
        Mockito.when(gemeenteService.geefStelselVoorGemeente(5678)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.BRP);

        final IscGemeenten iscGemeenten = new IscGemeenten();
        iscGemeenten.setBrpGemeente(BRP_GEMEENTE);
        iscGemeenten.setLo3Gemeente(LO3_GEMEENTE);

        final ErkenningVerzoekType erkenningVerzoekType = new ErkenningVerzoekType();
        erkenningVerzoekType.setIscGemeenten(iscGemeenten);

        final BrpBijhoudingVerzoekBericht erkenningVerzoekBericht = new ErkenningVerzoekBericht(erkenningVerzoekType);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningVerzoekBericht);

        final String result = controleerBijhoudingsGemeenteDecision.execute(parameters);

        Assert.assertNull(result);

    }

    @Test
    public void testNietLo3Gemeente() {

        // when
        Mockito.when(gemeenteService.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(5678)).thenReturn(Stelsel.BRP);

        final IscGemeenten iscGemeenten = new IscGemeenten();
        iscGemeenten.setBrpGemeente(BRP_GEMEENTE);
        iscGemeenten.setLo3Gemeente(LO3_GEMEENTE);

        final ErkenningVerzoekType erkenningVerzoekType = new ErkenningVerzoekType();
        erkenningVerzoekType.setIscGemeenten(iscGemeenten);

        final BrpBijhoudingVerzoekBericht erkenningVerzoekBericht = new ErkenningVerzoekBericht(erkenningVerzoekType);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningVerzoekBericht);

        final String result = controleerBijhoudingsGemeenteDecision.execute(parameters);

        Assert.assertEquals("Gevonden gemeente is niet LO3.", UC308Constants.CONTROLE_LO3_GEMEENTE_MISLUKT, result);

    }

    @Test
    public void testNietBrpGemeente() {

        // when
        Mockito.when(gemeenteService.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(5678)).thenReturn(Stelsel.GBA);

        final IscGemeenten iscGemeenten = new IscGemeenten();
        iscGemeenten.setBrpGemeente(BRP_GEMEENTE);
        iscGemeenten.setLo3Gemeente(LO3_GEMEENTE);

        final ErkenningVerzoekType erkenningVerzoekType = new ErkenningVerzoekType();
        erkenningVerzoekType.setIscGemeenten(iscGemeenten);

        final BrpBijhoudingVerzoekBericht erkenningVerzoekBericht = new ErkenningVerzoekBericht(erkenningVerzoekType);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningVerzoekBericht);

        final String result = controleerBijhoudingsGemeenteDecision.execute(parameters);

        Assert.assertEquals("Gevonden gemeente is niet BRP.", UC308Constants.CONTROLE_LO3_GEMEENTE_MISLUKT, result);

    }

    @Test
    public void testBrpGemeente() {

        // when
        Mockito.when(gemeenteService.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.BRP);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(5678)).thenReturn(Stelsel.BRP);

        final IscGemeenten iscGemeenten = new IscGemeenten();
        iscGemeenten.setBrpGemeente(BRP_GEMEENTE);
        iscGemeenten.setLo3Gemeente(LO3_GEMEENTE);

        final ErkenningVerzoekType erkenningVerzoekType = new ErkenningVerzoekType();
        erkenningVerzoekType.setIscGemeenten(iscGemeenten);

        final BrpBijhoudingVerzoekBericht erkenningVerzoekBericht = new ErkenningVerzoekBericht(erkenningVerzoekType);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningVerzoekBericht);

        final String result = controleerBijhoudingsGemeenteDecision.execute(parameters);

        Assert.assertEquals("Gevonden gemeente is niet BRP.", UC308Constants.CONTROLE_LO3_GEMEENTE_MISLUKT, result);

    }

}
