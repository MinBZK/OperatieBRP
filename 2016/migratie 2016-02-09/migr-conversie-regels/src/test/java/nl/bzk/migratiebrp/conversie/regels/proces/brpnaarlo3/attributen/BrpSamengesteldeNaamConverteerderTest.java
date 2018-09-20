/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import org.junit.Test;

@Requirement(Requirements.CEL0230_BL02)
public class BrpSamengesteldeNaamConverteerderTest extends AbstractComponentTest {

    @Inject
    private BrpPersoonConverteerder converteerder;

    @Test
    public void testScheidingstekenSpatie() {
        final BrpGroepInhoud inhoud = maakInhoud(' ', "van");
        final Lo3PersoonInhoud lo3Inhoud = converteer(inhoud);
        assertEquals(Lo3String.wrap("van"), lo3Inhoud.getVoorvoegselGeslachtsnaam());
    }

    @Test
    public void testScheidingstekenGeenSpatie() {
        final BrpGroepInhoud inhoud = maakInhoud('\'', "d");
        final Lo3PersoonInhoud lo3Inhoud = converteer(inhoud);
        assertEquals(Lo3String.wrap("d'"), lo3Inhoud.getVoorvoegselGeslachtsnaam());
    }

    @Test
    public void testScheidingstekenLeeg() {
        final BrpGroepInhoud inhoud = maakInhoud(null, "niet overnemen");
        final Lo3PersoonInhoud lo3Inhoud = converteer(inhoud);
        assertNull(lo3Inhoud.getVoorvoegselGeslachtsnaam());
    }

    private Lo3PersoonInhoud converteer(final BrpGroepInhoud inhoud) {
        final BrpHistorie historie = new BrpHistorie(new BrpDatum(20010101, null), null, BrpDatumTijd.fromDatum(20010101, null), null, null);
        final BrpActie actieInhoud =
                new BrpActie(
                    1L,
                    BrpSoortActieCode.CONVERSIE_GBA,
                    BrpPartijCode.MIGRATIEVOORZIENING,
                        BrpDatumTijd.fromDatum(20010101, null),
                    null,
                    null,
                    0,
                    null);
        final BrpGroep<BrpGroepInhoud> groep = new BrpGroep<>(inhoud, historie, actieInhoud, null, null);
        final List<BrpGroep<BrpGroepInhoud>> groepen = Arrays.asList(groep);
        final BrpStapel<? extends BrpGroepInhoud> brpStapels = new BrpStapel<>(groepen);
        return converteerder.converteer(brpStapels).getLaatsteElement().getInhoud();
    }

    private BrpGroepInhoud maakInhoud(final Character scheidingsteken, final String voorvoegsel) {
        return new BrpSamengesteldeNaamInhoud(null, new BrpString("Voornaam", null), BrpString.wrap(voorvoegsel, null), BrpCharacter.wrap(
            scheidingsteken,
            null), null, new BrpString("Geslachtsnaam", null), new BrpBoolean(false, null), new BrpBoolean(false, null));
    }
}
