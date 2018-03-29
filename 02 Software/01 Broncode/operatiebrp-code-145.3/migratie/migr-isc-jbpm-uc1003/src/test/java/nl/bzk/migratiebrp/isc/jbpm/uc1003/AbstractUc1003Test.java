/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.PlaatsAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkAfnemersindicatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwijderAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractUcTest;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen.PlaatsenAfnIndTestUtil;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration({"classpath:/uc1003-test-beans.xml"})
public abstract class AbstractUc1003Test extends AbstractUcTest {

    private static final String BIJHOUDINGS_GEMEENTE = "0518";

    protected AbstractUc1003Test(final String processDefinitionXml) {
        super(processDefinitionXml);
    }

    @Override
    protected String generateMessageId() {
        return UUID.randomUUID().toString();
    }

    @Before
    public void setupPartijRegister() {
        // Partij register
        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("059901", "0599", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("042901", "0429", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("051801", "0518", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("199902", "1999", intToDate(19900101), Collections.emptyList()));
        partijen.add(new Partij("071701", "0717", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        setPartijRegister(new PartijRegisterImpl(partijen));
    }

    /* *** ZOEK PERSOON ***************************************************************************************** */

    protected AdHocZoekPersoonAntwoordBericht maakAdHocZoekPersoonAntwoordBericht(final SyncBericht zoekPersoonVerzoek, final int aantalGevonden) {
        final AdHocZoekPersoonAntwoordBericht antwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        if (aantalGevonden == 0) {
            antwoordBericht.setFoutreden(AdHocZoekAntwoordFoutReden.G);
        } else if (aantalGevonden == 1) {
            antwoordBericht.setInhoud("00000000Ha01A000000000003801031011001012345678900120009123456789");
        } else {
            antwoordBericht.setFoutreden(AdHocZoekAntwoordFoutReden.U);
        }
        antwoordBericht.setMessageId(generateMessageId());
        antwoordBericht.setCorrelationId(zoekPersoonVerzoek.getMessageId());
        return antwoordBericht;
    }

    /* *** BLOKKERING INFO ***************************************************************************************** */

    protected BlokkeringInfoAntwoordBericht maakBlokkeringInfoAntwoordBericht(
            final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek,
            final PersoonsaanduidingType persoonsaanduiding) {
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
            final String foutCode) {
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
            final String foutCode) {
        final VerwerkAfnemersindicatieAntwoordBericht antwoordBericht = PlaatsenAfnIndTestUtil.maakVerwerkAfnemersindicatieAntwoordBericht(foutCode);
        antwoordBericht.setMessageId(generateMessageId());
        antwoordBericht.setCorrelationId(verwijderVerzoekBericht.getMessageId());
        return antwoordBericht;
    }

}
