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
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersonenOpAdresVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdresFunctieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.IdentificatieType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresVerzoekBericht;
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
public class ZoekPersonenOpAdresServiceTest {

    @Mock
    private SyntaxControle syntaxControle;

    @Mock
    private ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen;

    @Mock
    private AdHocZoekenNaarBrpVerzender adHocZoekenNaarBrpVerzender;

    @InjectMocks
    private ZoekPersonenOpAdresService adHocZoekenService;

    @Test
    public void testGetVerzoekType() {
        Assert.assertEquals(AdHocZoekPersonenOpAdresVerzoekBericht.class, adHocZoekenService.getVerzoekType());
    }

    @Test
    public void testGoedeZoekOpdracht() throws Exception {
        Mockito.doNothing().when(syntaxControle).controleerInhoud(Mockito.anyList());
        final List<ZoekCriterium> criteria = new ArrayList<>();
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", "123456789"));
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Burgerservicenummer", "98765432"));
        Mockito.when(conversieLo3NaarBrpVragen.converteer(Mockito.anyList())).thenReturn(criteria);
        final AdHocZoekPersonenOpAdresVerzoekType verzoek = new AdHocZoekPersonenOpAdresVerzoekType();
        verzoek.setPartijCode("200003");
        verzoek.getGevraagdeRubrieken().addAll(Arrays.asList("010110", "010120", "010240", "081160"));
        verzoek.setIdentificerendeGegevens("00069010460110010123456789001200091234567890240006Jansen0801311600062994HA");
        verzoek.setAdresfunctie(AdresFunctieType.W);
        verzoek.setIdentificatie(IdentificatieType.P);
        Mockito.doNothing().when(adHocZoekenNaarBrpVerzender)
                .verstuurAdHocZoekenVraag(Mockito.any(Persoonsvraag.class), Mockito.anyString(), Mockito.anyString());

        adHocZoekenService.verwerkBericht(new AdHocZoekPersonenOpAdresVerzoekBericht(verzoek));

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
        final AdHocZoekPersonenOpAdresVerzoekType verzoek = new AdHocZoekPersonenOpAdresVerzoekType();
        verzoek.setPartijCode("200003");
        verzoek.getGevraagdeRubrieken().addAll(Arrays.asList("010110", "010120", "010240", "081160"));
        verzoek.setIdentificerendeGegevens("00069010460110010123456789001200091234567890240006Jansen0801311600062994HA");
        verzoek.setAdresfunctie(AdresFunctieType.W);
        verzoek.setIdentificatie(IdentificatieType.P);
        Mockito.doNothing().when(adHocZoekenNaarBrpVerzender)
                .verstuurAdHocZoekenVraag(Mockito.any(Persoonsvraag.class), Mockito.anyString(), Mockito.anyString());

        adHocZoekenService.verwerkBericht(new AdHocZoekPersonenOpAdresVerzoekBericht(verzoek));

        Mockito.verify(adHocZoekenNaarBrpVerzender, Mockito.atLeastOnce()).verstuurAdHocZoekenVraag(Mockito.any(), Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testZoekOpdrachtMetNietToegestaneRubrieken() throws Exception {
        Mockito.doNothing().when(syntaxControle).controleerInhoud(Mockito.anyList());
        final List<ZoekCriterium> criteria = new ArrayList<>();
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", "123456789"));
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Burgerservicenummer", "98765432"));
        Mockito.when(conversieLo3NaarBrpVragen.converteer(Mockito.anyList())).thenReturn(criteria);
        final AdHocZoekPersonenOpAdresVerzoekType verzoek = new AdHocZoekPersonenOpAdresVerzoekType();
        verzoek.setPartijCode("200003");
        verzoek.getGevraagdeRubrieken().addAll(Arrays.asList("010110", "010120", "010240", "080920"));
        verzoek.setIdentificerendeGegevens("00071010460110010123456789001200091234567890240006Jansen08015092000820160101");
        verzoek.setAdresfunctie(AdresFunctieType.W);
        verzoek.setIdentificatie(IdentificatieType.P);

        Mockito.doNothing().when(adHocZoekenNaarBrpVerzender)
                .verstuurAdHocZoekenVraag(Mockito.any(Persoonsvraag.class), Mockito.anyString(), Mockito.anyString());

        final AdHocZoekPersonenOpAdresVerzoekBericht adHocZoekPersonenOpAdresVerzoekBericht = new AdHocZoekPersonenOpAdresVerzoekBericht(verzoek);
        adHocZoekPersonenOpAdresVerzoekBericht.setMessageId("id-1");
        final AdHocZoekPersonenOpAdresAntwoordBericht
                adHocZoekPersonenOpAdresAntwoordBericht =
                adHocZoekenService.verwerkBericht(adHocZoekPersonenOpAdresVerzoekBericht);
        Assert.assertEquals(AdHocZoekAntwoordFoutReden.X, adHocZoekPersonenOpAdresAntwoordBericht.getFoutreden());
        Assert.assertEquals("id-1", adHocZoekPersonenOpAdresAntwoordBericht.getCorrelationId());
        Mockito.verify(adHocZoekenNaarBrpVerzender, Mockito.never()).verstuurAdHocZoekenVraag(Mockito.any(), Mockito.anyString(), Mockito.any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerkeerdeSyntaxOpdracht() throws Exception {
        final List<ZoekCriterium> criteria = new ArrayList<>();
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Administratienummer", "123456789"));
        criteria.add(new ZoekCriterium("Persoon.Identificatienummers.Burgerservicenummer", "98765432"));
        Mockito.when(conversieLo3NaarBrpVragen.converteer(Mockito.anyList())).thenReturn(criteria);
        final AdHocZoekPersonenOpAdresVerzoekType verzoek = new AdHocZoekPersonenOpAdresVerzoekType();
        verzoek.setPartijCode("200003");
        verzoek.getGevraagdeRubrieken().addAll(Arrays.asList("010110", "010120", "010240", "081160"));
        verzoek.setIdentificerendeGegevens("x");
        verzoek.setAdresfunctie(AdresFunctieType.W);
        verzoek.setIdentificatie(IdentificatieType.P);
        Mockito.doNothing().when(adHocZoekenNaarBrpVerzender)
                .verstuurAdHocZoekenVraag(Mockito.any(Persoonsvraag.class), Mockito.anyString(), Mockito.anyString());

        adHocZoekenService.verwerkBericht(new AdHocZoekPersonenOpAdresVerzoekBericht(verzoek));
    }

    @Test
    public void testServiceNaam() {
        Assert.assertEquals("ZoekPersonenOpAdresService", adHocZoekenService.getServiceNaam());
    }
}
