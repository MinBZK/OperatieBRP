/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.zoeken;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZoekPersoonOpActueleGegevensServiceTest {

    @Mock
    private BrpDalService brpDalService;

    @Mock
    private ZoekPersoonFilter filter;

    @InjectMocks
    private ZoekPersoonOpActueleGegevensService subject;

    @Test
    public void testPreoperties() {
        Assert.assertEquals(ZoekPersoonOpActueleGegevensVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertEquals("ZoekPersoonOpActueleGegevensService", subject.getServiceNaam());
    }

    @Test
    public void test() throws BerichtSyntaxException {
        final ZoekPersoonOpActueleGegevensVerzoekBericht verzoek = new ZoekPersoonOpActueleGegevensVerzoekBericht();
        verzoek.setANummer("1234567890");
        verzoek.setBsn("123456789");
        verzoek.setGeslachtsnaam("Petersen");
        verzoek.setPostcode("4321WW");
        verzoek.setAanvullendeZoekcriteria("00000");

        final List<Persoon> personen = new ArrayList<>();
        personen.add(new Persoon(SoortPersoon.INGESCHREVENE));

        final List<GevondenPersoon> gevondenPersonen = new ArrayList<>();
        gevondenPersonen.add(new GevondenPersoon(1, 1234567890L, "1900"));

        Mockito.when(brpDalService.zoekPersonenOpActueleGegevens(1234567890L, 123456789, "Petersen", "4321WW")).thenReturn(personen);
        Mockito.when(filter.filter(personen, "00000")).thenReturn(gevondenPersonen);

        // Execute
        final ZoekPersoonAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());
        Assert.assertEquals(ZoekPersoonResultaatType.GEVONDEN, antwoord.getResultaat());
        Assert.assertEquals(Integer.valueOf(1), antwoord.getPersoonId());
        Assert.assertEquals("1234567890", antwoord.getAnummer());
        Assert.assertEquals("1900", antwoord.getGemeente());
    }
}
