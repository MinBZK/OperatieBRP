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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoudTest;
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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoudTest;
import org.junit.Test;

public class BrpPersoonslijstTest {
    public static Integer PERSOONID = 123456789;

    public static BrpPersoonslijst createPL() {

        BrpPersoonslijst pl =
                new BrpPersoonslijst(
                    PERSOONID,
                    BrpNaamgebruikInhoudTest.createStapel(),
                    BrpAdresInhoudTest.createStapel(),
                    BrpPersoonAfgeleidAdministratiefInhoudTest.createStapel(),
                    BrpBehandeldAlsNederlanderIndicatieInhoudTest.createStapel(),
                    BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoudTest.createStapel(),
                    BrpBijhoudingInhoudTest.createStapel(),
                    BrpDerdeHeeftGezagIndicatieInhoudTest.createStapel(),
                    BrpDeelnameEuVerkiezingenInhoudTest.createStapel(),
                    BrpGeboorteInhoudTest.createStapel(),
                    BrpGeslachtsaanduidingInhoudTest.createStapel(),
                    BrpGeslachtsnaamcomponentInhoudTest.createList(),
                    BrpIdentificatienummersInhoudTest.createStapel(),
                    BrpMigratieInhoudTest.createStapel(),
                    BrpInschrijvingInhoudTest.createStapel(),
                    BrpNationaliteitInhoudTest.createList(true),
                    BrpNummerverwijzingInhoudTest.createStapel(),
                    BrpOnderCurateleIndicatieInhoudTest.createStapel(),
                    BrpOverlijdenInhoudTest.createStapel(),
                    BrpPersoonskaartInhoudTest.createStapel(),
                    BrpReisdocumentInhoudTest.createList(),
                    BrpRelatieTest.createLegeOuderRelatie(),
                    BrpSamengesteldeNaamInhoudTest.createStapel(),
                    BrpStaatloosIndicatieInhoudTest.createStapel(),
                    BrpUitsluitingKiesrechtInhoudTest.createStapel(),
                    BrpVastgesteldNietNederlanderIndicatieInhoudTest.createStapel(),
                    BrpVerblijfsrechtInhoudTest.createStapel(),
                    BrpVerstrekkingsbeperkingIndicatieInhoudTest.createStapel(),
                    BrpVoornaamInhoudTest.createList(),
                    BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoudTest.createStapel(),
                    BrpVerificatieInhoudTest.createList(),
                    BrpIstRelatieGroepInhoudTest.createStapel(),
                    BrpIstRelatieGroepInhoudTest.createStapel(),
                    Collections.<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>>emptyList(),
                    Collections.<BrpStapel<BrpIstRelatieGroepInhoud>>emptyList(),
                    null);

        return pl;
    }

    @Test
    public void testValidatie() {
        BrpPersoonslijst pl = createPL();
        pl.valideer();
    }

    @Test
    public void testGetActueelAdministratienummer() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(createPL());
        b.identificatienummersStapel(BrpIdentificatienummersInhoudTest.createStapelZonderActueele());
        BrpPersoonslijst p = b.build();
        assertNull(p.getActueelAdministratienummer());

    }

    @Test(expected = NullPointerException.class)
    public void testGetRelatiesExceptie() {
        BrpPersoonslijst pl = createPL();
        pl.getRelaties(null);
    }

    @Test
    public void testGetRelaties() {
        BrpPersoonslijst pl = createPL();
        List<BrpRelatie> lijst = pl.getRelaties(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING);
        assertEquals(1, lijst.size());

    }

    @Test
    public void testEquals() {
        BrpPersoonslijst pl = createPL();
        assertTrue(pl.equals(returnZelfde(pl)));
        assertFalse(pl.equals(pl.getActueelAdministratienummer()));
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(createPL());
        b.persoonId(654321);
        b.naamgebruikStapel(BrpNaamgebruikInhoudTest.createStapel("Andere naam"));
        assertFalse(pl.equals(b.build()));
    }

    private BrpPersoonslijst returnZelfde(BrpPersoonslijst pl) {
        return pl;
    }
}
