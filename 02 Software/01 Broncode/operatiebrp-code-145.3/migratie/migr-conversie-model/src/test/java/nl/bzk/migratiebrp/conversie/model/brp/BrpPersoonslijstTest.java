/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoudTestUtil;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnverwerktDocumentAanwezigIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoudTestUtil;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoudTestUtil;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoudTestUtil;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoudTestUtil;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoudTestUtil;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoudTestUtil;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoudTest;
import org.junit.Test;

public class BrpPersoonslijstTest {
    public static Long PERSOONID = 123456789L;

    public static BrpPersoonslijst createPL() {

        final BrpPersoonslijst pl =
                new BrpPersoonslijst(
                        PERSOONID,
                        1L,
                        2L,
                        BrpNaamgebruikInhoudTest.createStapel(),
                        BrpAdresInhoudTest.createStapel(),
                        BrpPersoonAfgeleidAdministratiefInhoudTestUtil.createStapel(),
                        BrpBehandeldAlsNederlanderIndicatieInhoudTest.createStapel(),
                        BrpOnverwerktDocumentAanwezigIndicatieInhoudTest.createStapel(),
                        BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoudTestUtil.createStapel(),
                        BrpBijhoudingInhoudTestUtil.createStapel(),
                        BrpDerdeHeeftGezagIndicatieInhoudTest.createStapel(),
                        BrpDeelnameEuVerkiezingenInhoudTest.createStapel(),
                        BrpGeboorteInhoudTest.createStapel(),
                        BrpGeslachtsaanduidingInhoudTest.createStapel(),
                        BrpGeslachtsnaamcomponentInhoudTest.createList(),
                        BrpIdentificatienummersInhoudTest.createStapel(),
                        BrpMigratieInhoudTest.createStapel(),
                        BrpInschrijvingInhoudTest.createStapel(),
                        BrpNationaliteitInhoudTest.createList(true),
                        BrpBuitenlandsPersoonsnummerInhoudTest.createList(true),
                        BrpNummerverwijzingInhoudTest.createStapel(),
                        BrpOnderCurateleIndicatieInhoudTest.createStapel(),
                        BrpOverlijdenInhoudTest.createStapel(),
                        BrpPersoonskaartInhoudTest.createStapel(),
                        BrpReisdocumentInhoudTest.createList(),
                        BrpRelatieTest.createLegeOuderRelatie(),
                        BrpSamengesteldeNaamInhoudTest.createStapel(),
                        BrpStaatloosIndicatieInhoudTestUtil.createStapel(),
                        BrpUitsluitingKiesrechtInhoudTestUtil.createStapel(),
                        BrpVastgesteldNietNederlanderIndicatieInhoudTest.createStapel(),
                        BrpVerblijfsrechtInhoudTest.createStapel(),
                        BrpVerstrekkingsbeperkingIndicatieInhoudTestUtil.createStapel(),
                        BrpVoornaamInhoudTest.createList(),
                        BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoudTest.createStapel(),
                        BrpVerificatieInhoudTestUtil.createList(),
                        BrpIstRelatieGroepInhoudTest.createStapel(),
                        BrpIstRelatieGroepInhoudTest.createStapel(),
                        Collections.<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>>emptyList(),
                        Collections.<BrpStapel<BrpIstRelatieGroepInhoud>>emptyList(),
                        null);

        return pl;
    }

    @Test
    public void testValidatie() {
        final BrpPersoonslijst pl = createPL();
        pl.valideer();
    }

    @Test
    public void testGetActueelAdministratienummer() {
        final BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(createPL());
        b.identificatienummersStapel(BrpIdentificatienummersInhoudTest.createStapelZonderActueele());
        final BrpPersoonslijst p = b.build();
        assertNull(p.getActueelAdministratienummer());

    }

    @Test(expected = NullPointerException.class)
    public void testGetRelatiesExceptie() {
        final BrpPersoonslijst pl = createPL();
        pl.getRelaties(null);
    }

    @Test
    public void testGetRelaties() {
        final BrpPersoonslijst pl = createPL();
        final List<BrpRelatie> lijst = pl.getRelaties(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING);
        assertEquals(1, lijst.size());

    }

    @Test
    public void testEquals() {
        final BrpPersoonslijst pl = createPL();
        assertTrue(pl.equals(returnZelfde(pl)));
        assertFalse(pl.equals(pl.getActueelAdministratienummer()));
        final BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(createPL());
        b.persoonId(654321L);
        b.naamgebruikStapel(BrpNaamgebruikInhoudTest.createStapel("Andere naam"));
        assertFalse(pl.equals(b.build()));
    }

    private BrpPersoonslijst returnZelfde(final BrpPersoonslijst pl) {
        return pl;
    }
}
