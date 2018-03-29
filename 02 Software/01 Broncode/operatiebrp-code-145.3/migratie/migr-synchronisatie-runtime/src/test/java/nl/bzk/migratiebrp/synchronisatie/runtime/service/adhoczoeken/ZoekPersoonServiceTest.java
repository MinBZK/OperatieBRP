/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.adhoczoeken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import nl.bzk.migratiebrp.bericht.model.lo3.syntax.SyntaxControle;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersoonVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortDienstType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonVerzoekBericht;
import nl.bzk.migratiebrp.conversie.vragen.ConversieLo3NaarBrpVragen;
import nl.bzk.migratiebrp.conversie.vragen.ZoekCriterium;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor de adhoc zoeken service.
 */
@RunWith(MockitoJUnitRunner.class)
public class ZoekPersoonServiceTest {

    @Mock
    private SyntaxControle syntaxControle;

    @Mock
    private ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen;

    @Mock
    private AdHocZoekenNaarBrpVerzender adHocZoekenNaarBrpVerzender;

    @InjectMocks
    private ZoekPersoonService adHocZoekenService;

    @Test
    public void testGetVerzoekType() {
        Assert.assertEquals(AdHocZoekPersoonVerzoekBericht.class, adHocZoekenService.getVerzoekType());
    }

    @Test
    public void testGoedeZoekOpdracht() throws Exception {
        Mockito.doNothing().when(syntaxControle).controleerInhoud(Mockito.anyList());
        final List<ZoekCriterium> criteria = new ArrayList<>();
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", "123456789"));
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Burgerservicenummer", "98765432"));
        Mockito.when(conversieLo3NaarBrpVragen.converteer(Mockito.anyList())).thenReturn(criteria);
        final AdHocZoekPersoonVerzoekType verzoek = new AdHocZoekPersoonVerzoekType();
        verzoek.setPartijCode("200003");
        verzoek.getGevraagdeRubrieken().addAll(Arrays.asList("010110", "010120", "010240", "081160"));
        verzoek.setPersoonIdentificerendeGegevens("00069010460110010123456789001200091234567890240006Jansen0801311600062994HA");
        verzoek.setSoortDienst(SoortDienstType.ZOEK_PERSOON);
        Mockito.doNothing().when(adHocZoekenNaarBrpVerzender)
                .verstuurAdHocZoekenVraag(Mockito.any(Persoonsvraag.class), Mockito.anyString(), Mockito.anyString());

        adHocZoekenService.verwerkBericht(new AdHocZoekPersoonVerzoekBericht(verzoek));

        Mockito.verify(adHocZoekenNaarBrpVerzender, Mockito.atLeastOnce()).verstuurAdHocZoekenVraag(Mockito.any(), Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGoedeZoekOpdrachtGenesteZoekCriteria() throws Exception {
        Mockito.doNothing().when(syntaxControle).controleerInhoud(Mockito.anyList());
        final List<ZoekCriterium> criteria = new ArrayList<>();
        final ZoekCriterium genest = new ZoekCriterium("Persoon.Identificatienummers.Burgerservicenummer", "98765432");
        genest.setOf(new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", "123456789"));
        criteria.add(genest);
        Mockito.when(conversieLo3NaarBrpVragen.converteer(Mockito.anyList())).thenReturn(criteria);
        final AdHocZoekPersoonVerzoekType verzoek = new AdHocZoekPersoonVerzoekType();
        verzoek.setPartijCode("200003");
        verzoek.getGevraagdeRubrieken().addAll(Arrays.asList("010110", "010120", "010240", "081160"));
        verzoek.setPersoonIdentificerendeGegevens("00069010460110010123456789001200091234567890240006Jansen0801311600062994HA");
        verzoek.setSoortDienst(SoortDienstType.ZOEK_PERSOON);
        Mockito.doNothing().when(adHocZoekenNaarBrpVerzender)
                .verstuurAdHocZoekenVraag(Mockito.any(Persoonsvraag.class), Mockito.anyString(), Mockito.anyString());

        adHocZoekenService.verwerkBericht(new AdHocZoekPersoonVerzoekBericht(verzoek));

        Mockito.verify(adHocZoekenNaarBrpVerzender, Mockito.atLeastOnce()).verstuurAdHocZoekenVraag(Mockito.any(), Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testZoekOpdrachtMetNietToegestaneRubrieken() throws Exception {
        Mockito.doNothing().when(syntaxControle).controleerInhoud(Mockito.anyList());
        final List<ZoekCriterium> criteria = new ArrayList<>();
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", "123456789"));
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Burgerservicenummer", "98765432"));
        Mockito.when(conversieLo3NaarBrpVragen.converteer(Mockito.anyList())).thenReturn(criteria);
        final AdHocZoekPersoonVerzoekType verzoek = new AdHocZoekPersoonVerzoekType();
        verzoek.setPartijCode("200003");
        verzoek.getGevraagdeRubrieken().addAll(Arrays.asList("010110", "010120", "010240", "080920"));
        verzoek.setPersoonIdentificerendeGegevens("00071010460110010123456789001200091234567890240006Jansen08015092000820160101");
        verzoek.setSoortDienst(SoortDienstType.ZOEK_PERSOON);

        Mockito.doNothing().when(adHocZoekenNaarBrpVerzender)
                .verstuurAdHocZoekenVraag(Mockito.any(Persoonsvraag.class), Mockito.anyString(), Mockito.anyString());

        final AdHocZoekPersoonVerzoekBericht adHocZoekPersoonVerzoekBericht = new AdHocZoekPersoonVerzoekBericht(verzoek);
        adHocZoekPersoonVerzoekBericht.setMessageId("id-1");
        final AdHocZoekPersoonAntwoordBericht adHocZoekPersoonAntwoordBericht = adHocZoekenService.verwerkBericht(adHocZoekPersoonVerzoekBericht);
        Assert.assertEquals(AdHocZoekAntwoordFoutReden.X, adHocZoekPersoonAntwoordBericht.getFoutreden());
        Assert.assertEquals("id-1", adHocZoekPersoonAntwoordBericht.getCorrelationId());
        Mockito.verify(adHocZoekenNaarBrpVerzender, Mockito.never()).verstuurAdHocZoekenVraag(Mockito.any(), Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testZoekOpdrachtMetNietToegestaneRubriekenDienstPlaatsenAfnemerindicatie() throws Exception {
        Mockito.doNothing().when(syntaxControle).controleerInhoud(Mockito.anyList());
        final List<ZoekCriterium> criteria = new ArrayList<>();
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", "123456789"));
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Burgerservicenummer", "98765432"));
        Mockito.when(conversieLo3NaarBrpVragen.converteer(Mockito.anyList())).thenReturn(criteria);
        final AdHocZoekPersoonVerzoekType verzoek = new AdHocZoekPersoonVerzoekType();
        verzoek.setPartijCode("200003");
        verzoek.setSoortDienst(SoortDienstType.PLAATSING_AFNEMERINDICATIE);
        verzoek.getGevraagdeRubrieken().addAll(Arrays.asList("010110", "010120", "010240", "080920"));
        verzoek.setPersoonIdentificerendeGegevens("00071010460110010123456789001200091234567890240006Jansen08015092000820160101");
        Mockito.doNothing().when(adHocZoekenNaarBrpVerzender)
                .verstuurAdHocZoekenVraag(Mockito.any(Persoonsvraag.class), Mockito.anyString(), Mockito.anyString());

        final AdHocZoekPersoonVerzoekBericht adHocZoekPersoonVerzoekBericht = new AdHocZoekPersoonVerzoekBericht(verzoek);
        adHocZoekPersoonVerzoekBericht.setMessageId("id-1");
        final AdHocZoekPersoonAntwoordBericht adHocZoekPersoonAntwoordBericht = adHocZoekenService.verwerkBericht(adHocZoekPersoonVerzoekBericht);
        Assert.assertEquals(AdHocZoekAntwoordFoutReden.X, adHocZoekPersoonAntwoordBericht.getFoutreden());
        Assert.assertEquals("id-1", adHocZoekPersoonAntwoordBericht.getCorrelationId());
        Mockito.verify(adHocZoekenNaarBrpVerzender, Mockito.never()).verstuurAdHocZoekenVraag(Mockito.any(), Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testZoekOpdrachtMetNietToegestaneRubriekenDienstVerwijderenAfnemerindicatie() throws Exception {
        Mockito.doNothing().when(syntaxControle).controleerInhoud(Mockito.anyList());
        final List<ZoekCriterium> criteria = new ArrayList<>();
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", "123456789"));
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Burgerservicenummer", "98765432"));
        Mockito.when(conversieLo3NaarBrpVragen.converteer(Mockito.anyList())).thenReturn(criteria);
        final AdHocZoekPersoonVerzoekType verzoek = new AdHocZoekPersoonVerzoekType();
        verzoek.setPartijCode("200003");
        verzoek.setSoortDienst(SoortDienstType.PLAATSING_AFNEMERINDICATIE);
        verzoek.getGevraagdeRubrieken().addAll(Arrays.asList("010110", "010120", "010240", "080920"));
        verzoek.setPersoonIdentificerendeGegevens("00071010460110010123456789001200091234567890240006Jansen08015092000820160101");
        Mockito.doNothing().when(adHocZoekenNaarBrpVerzender)
                .verstuurAdHocZoekenVraag(Mockito.any(Persoonsvraag.class), Mockito.anyString(), Mockito.anyString());

        final AdHocZoekPersoonVerzoekBericht adHocZoekPersoonVerzoekBericht = new AdHocZoekPersoonVerzoekBericht(verzoek);
        adHocZoekPersoonVerzoekBericht.setMessageId("id-1");
        final AdHocZoekPersoonAntwoordBericht adHocZoekPersoonAntwoordBericht = adHocZoekenService.verwerkBericht(adHocZoekPersoonVerzoekBericht);
        Assert.assertEquals(AdHocZoekAntwoordFoutReden.X, adHocZoekPersoonAntwoordBericht.getFoutreden());
        Assert.assertEquals("id-1", adHocZoekPersoonAntwoordBericht.getCorrelationId());
        Mockito.verify(adHocZoekenNaarBrpVerzender, Mockito.never()).verstuurAdHocZoekenVraag(Mockito.any(), Mockito.anyString(), Mockito.any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerkeerdeSyntaxOpdracht() throws Exception {
        final List<ZoekCriterium> criteria = new ArrayList<>();
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", "123456789"));
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Burgerservicenummer", "98765432"));
        Mockito.when(conversieLo3NaarBrpVragen.converteer(Mockito.anyList())).thenReturn(criteria);
        final AdHocZoekPersoonVerzoekType verzoek = new AdHocZoekPersoonVerzoekType();
        verzoek.setPartijCode("200003");
        verzoek.getGevraagdeRubrieken().addAll(Arrays.asList("010110", "010120", "010240", "081160"));
        verzoek.setPersoonIdentificerendeGegevens("x");
        verzoek.setSoortDienst(SoortDienstType.ZOEK_PERSOON);
        Mockito.doNothing().when(adHocZoekenNaarBrpVerzender)
                .verstuurAdHocZoekenVraag(Mockito.any(Persoonsvraag.class), Mockito.anyString(), Mockito.anyString());

        adHocZoekenService.verwerkBericht(new AdHocZoekPersoonVerzoekBericht(verzoek));
    }

    @Test
    public void testServiceNaam() {
        Assert.assertEquals("ZoekPersoonService", adHocZoekenService.getServiceNaam());
    }
}
