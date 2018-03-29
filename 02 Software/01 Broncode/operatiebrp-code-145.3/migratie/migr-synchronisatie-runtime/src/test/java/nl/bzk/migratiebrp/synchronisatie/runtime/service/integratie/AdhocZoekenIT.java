/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.integratie;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortDienstType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService;
import org.junit.Assert;
import org.junit.Test;

public class AdhocZoekenIT extends AbstractSynchronisatieIT {

    @Inject
    @Named("zoekPersoonService")
    private SynchronisatieBerichtService<AdHocZoekPersoonVerzoekBericht, AdHocZoekPersoonAntwoordBericht> subject;

    @Test
    public void testUC100401C10T010stap0010() throws Exception {
        final AdHocZoekPersoonVerzoekBericht verzoek = new AdHocZoekPersoonVerzoekBericht();
        verzoek.setMessageId("BERICHT-ID-1");
        verzoek.setPartijCode("580002");
        verzoek.setPersoonIdentificerendeGegevens("00069010460110010123456789001200091234567890240006Jansen0801311600062994HA");
        verzoek.getGevraagdeRubrieken().add("010110");
        verzoek.setSoortDienst(SoortDienstType.ZOEK_PERSOON);

        final AdHocZoekPersoonAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNull(antwoord);
    }

    @Test
    public void testUC100401C10T320stap0010() throws Exception {
        final AdHocZoekPersoonVerzoekBericht verzoek = new AdHocZoekPersoonVerzoekBericht();
        verzoek.setMessageId("BERICHT-ID-2");
        verzoek.setPartijCode("580002");
        verzoek.setPersoonIdentificerendeGegevens("0003801017011001012345678900401105100040499");
        verzoek.getGevraagdeRubrieken().add("010110");
        verzoek.setSoortDienst(SoortDienstType.ZOEK_PERSOON);

        final AdHocZoekPersoonAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNull(antwoord);
    }
}
