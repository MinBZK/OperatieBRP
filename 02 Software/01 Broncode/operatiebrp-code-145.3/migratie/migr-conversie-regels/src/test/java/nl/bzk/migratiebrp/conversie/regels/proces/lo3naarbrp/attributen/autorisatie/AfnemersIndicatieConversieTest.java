/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.Collections;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AfnemersIndicatieConversieTest extends AbstractComponentTest {
    private static final String ADMINISTRATIENUMMER = "123456789";
    private static final long DATUMTIJD_REGISTRATIE = 19920101145000L;
    private static final int DATUM_EINDE_GELDIGHEID = 1993_01_01;
    private static final String PARTIJ_CODE = "800007";

    @Inject
    private AfnemersIndicatieConversie afnemersIndicatieConversie;

    @Before
    public void before() {
        Logging.initContext();
    }

    @Test
    public void testVulAfgeleideGegevensWelDatumEindeGeldigheid() {
        final BrpDatum datumEindeGeldigheid = new BrpDatum(DATUM_EINDE_GELDIGHEID, null);
        final BrpAfnemersindicaties brpAfnemerindicaties = maakBrpAfnemersindicaties(datumEindeGeldigheid);

        final BrpAfnemersindicaties brpAfnemerIndicatiesMetAfgeleideGegevens = afnemersIndicatieConversie.vulAfgeleideGegevens(brpAfnemerindicaties);
        Assert.assertNotNull(brpAfnemerIndicatiesMetAfgeleideGegevens);

        Assert.assertNull(
                brpAfnemerIndicatiesMetAfgeleideGegevens.getAfnemersindicaties().get(0).getAfnemersindicatieStapel().get(0).getInhoud().getDatumEindeVolgen());
    }

    @Test
    public void testVulAfgeleideGegevensGeenDatumEindeGeldigheid() {
        final BrpDatum datumEindeGeldigheid = null;
        final BrpAfnemersindicaties brpAfnemerindicaties = maakBrpAfnemersindicaties(datumEindeGeldigheid);

        final BrpAfnemersindicaties brpAfnemerIndicatiesMetAfgeleideGegevens = afnemersIndicatieConversie.vulAfgeleideGegevens(brpAfnemerindicaties);
        Assert.assertNotNull(brpAfnemerIndicatiesMetAfgeleideGegevens);

        Assert.assertNull(
                brpAfnemerIndicatiesMetAfgeleideGegevens.getAfnemersindicaties().get(0).getAfnemersindicatieStapel().get(0).getInhoud().getDatumEindeVolgen());
    }

    private BrpAfnemersindicaties maakBrpAfnemersindicaties(final BrpDatum datumEindeGeldigheid) {
        final BrpAfnemersindicatieInhoud inhoud = new BrpAfnemersindicatieInhoud(null, null, false);
        final BrpHistorie historie = new BrpHistorie(null, datumEindeGeldigheid, BrpDatumTijd.fromDatumTijd(DATUMTIJD_REGISTRATIE, null), null, null);
        final BrpActie actieInhoud =
                new BrpActie(
                        1L,
                        BrpSoortActieCode.CONVERSIE_GBA,
                        BrpPartijCode.MIGRATIEVOORZIENING,
                        BrpDatumTijd.fromDatumTijd(DATUMTIJD_REGISTRATIE, null),
                        null,
                        null,
                        1,
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));

        final BrpGroep<BrpAfnemersindicatieInhoud> categorie = new BrpGroep<>(inhoud, historie, actieInhoud, null, null);

        final BrpStapel<BrpAfnemersindicatieInhoud> stapel = new BrpStapel<>(Collections.singletonList(categorie));

        return new BrpAfnemersindicaties(
                ADMINISTRATIENUMMER,
                Collections.singletonList(new BrpAfnemersindicatie(new BrpPartijCode(PARTIJ_CODE), stapel, null)));
    }

}
