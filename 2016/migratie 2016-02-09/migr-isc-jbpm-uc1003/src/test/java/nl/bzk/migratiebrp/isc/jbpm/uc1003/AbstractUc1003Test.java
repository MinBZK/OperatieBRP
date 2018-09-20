/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003;

import java.util.UUID;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.PlaatsAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkAfnemersindicatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwijderAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractUcTest;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen.PlaatsenAfnIndTestUtil;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:/uc1003-test-beans.xml")
public abstract class AbstractUc1003Test extends AbstractUcTest {

    private static final String BIJHOUDINGS_GEMEENTE = "0518";
    private static final String A_NUMMER_PERSOON_1 = "1234567890";

    protected AbstractUc1003Test(final String processDefinitionXml) {
        super(processDefinitionXml);
    }

    @Override
    protected String generateMessageId() {
        return UUID.randomUUID().toString();
    }

    /* *** ZOEK PERSOON ***************************************************************************************** */

    protected ZoekPersoonAntwoordBericht maakZoekPersoonAntwoordBericht(final SyncBericht zoekPersoonVerzoek, final int aantalGevonden) {
        final ZoekPersoonAntwoordBericht antwoordBericht = new ZoekPersoonAntwoordBericht();
        antwoordBericht.setStatus(StatusType.OK);
        if (aantalGevonden == 0) {
            antwoordBericht.setResultaat(ZoekPersoonResultaatType.GEEN);
        } else if (aantalGevonden == 1) {
            antwoordBericht.setResultaat(ZoekPersoonResultaatType.GEVONDEN);
            antwoordBericht.setAnummer(A_NUMMER_PERSOON_1);
            antwoordBericht.setPersoonId(1);
            antwoordBericht.setGemeente(BIJHOUDINGS_GEMEENTE);
        } else {
            antwoordBericht.setResultaat(ZoekPersoonResultaatType.MEERDERE);
        }
        antwoordBericht.setMessageId(generateMessageId());
        antwoordBericht.setCorrelationId(zoekPersoonVerzoek.getMessageId());
        return antwoordBericht;
    }

    /* *** BLOKKERING INFO ***************************************************************************************** */

    protected BlokkeringInfoAntwoordBericht maakBlokkeringInfoAntwoordBericht(
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek,
        final PersoonsaanduidingType persoonsaanduiding)
    {
        final BlokkeringInfoAntwoordBericht result = new BlokkeringInfoAntwoordBericht();
        result.setStatus(StatusType.OK);
        result.setPersoonsaanduiding(persoonsaanduiding);
        if (persoonsaanduiding != null) {
            result.setProcessId("proc-123123");
            result.setGemeenteNaar(BIJHOUDINGS_GEMEENTE);
        }
        result.setMessageId(generateMessageId());
        result.setCorrelationId(blokkeringInfoVerzoek.getMessageId());

        return result;
    }

    /*
     * *** PLAATSEN AFNEMERSINDICATIE
     * *****************************************************************************************
     */

    protected VerwerkAfnemersindicatieAntwoordBericht maakVerwerkAfnemersindicatieAntwoordBericht(
        final PlaatsAfnemersindicatieVerzoekBericht plaatsVerzoekBericht,
        final String foutCode)
    {
        final VerwerkAfnemersindicatieAntwoordBericht antwoordBericht = PlaatsenAfnIndTestUtil.maakVerwerkAfnemersindicatieAntwoordBericht(foutCode);
        antwoordBericht.setMessageId(generateMessageId());
        antwoordBericht.setCorrelationId(plaatsVerzoekBericht.getMessageId());
        return antwoordBericht;
    }

    /*
     * *** VERWIJDEREN AFNEMERSINDICATIE
     * *****************************************************************************************
     */

    protected VerwerkAfnemersindicatieAntwoordBericht maakVerwerkAfnemersindicatieAntwoordBericht(
        final VerwijderAfnemersindicatieVerzoekBericht verwijderVerzoekBericht,
        final String foutCode)
    {
        final VerwerkAfnemersindicatieAntwoordBericht antwoordBericht = PlaatsenAfnIndTestUtil.maakVerwerkAfnemersindicatieAntwoordBericht(foutCode);
        antwoordBericht.setMessageId(generateMessageId());
        antwoordBericht.setCorrelationId(verwijderVerzoekBericht.getMessageId());
        return antwoordBericht;
    }

}
