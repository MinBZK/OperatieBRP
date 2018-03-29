/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.zoeken;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.integratie.AbstractSynchronisatieIT;
import org.junit.Assert;
import org.junit.Test;

public class ZoekPersoonOpActueleGegevensServiceIT extends AbstractSynchronisatieIT {

    @Inject
    @Named("zoekPersoonOpActueleGegevensService")
    private SynchronisatieBerichtService<ZoekPersoonOpActueleGegevensVerzoekBericht, ZoekPersoonAntwoordBericht> subject;

    @Test
    public void testNietsGevonden() throws Exception {
        final ZoekPersoonOpActueleGegevensVerzoekBericht verzoek = new ZoekPersoonOpActueleGegevensVerzoekBericht();
        verzoek.setMessageId("BERICHT-ID-1");
        verzoek.setANummer("5567534653");
        Assert.assertFalse("Testdatabase bevat onverwacht het a-nummer wat wij hier gebruiken!?", komtAnummerActueelVoor(verzoek.getANummer()));

        final ZoekPersoonAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord);
        Assert.assertNotNull(antwoord.getMessageId());
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());
        Assert.assertEquals(ZoekPersoonResultaatType.GEEN, antwoord.getResultaat());
        Assert.assertNull(antwoord.getPersoonId());
        Assert.assertNull(antwoord.getAnummer());
        Assert.assertNull(antwoord.getGemeente());
    }
}
