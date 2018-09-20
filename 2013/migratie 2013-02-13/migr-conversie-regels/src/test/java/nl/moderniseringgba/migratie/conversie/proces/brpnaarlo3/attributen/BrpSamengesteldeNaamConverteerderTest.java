/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;

import org.junit.Test;

@Requirement(Requirements.CEL0230_BL02)
public class BrpSamengesteldeNaamConverteerderTest extends AbstractComponentTest {

    @Inject
    private BrpPersoonConverteerder converteerder;

    @Test
    public void testScheidingstekenSpatie() {
        final BrpGroepInhoud inhoud = maakInhoud(' ', "van");
        final Lo3PersoonInhoud lo3Inhoud = converteer(inhoud);
        assertEquals("van", lo3Inhoud.getVoorvoegselGeslachtsnaam());
    }

    @Test
    public void testScheidingstekenGeenSpatie() {
        final BrpGroepInhoud inhoud = maakInhoud('\'', "d");
        final Lo3PersoonInhoud lo3Inhoud = converteer(inhoud);
        assertEquals("d'", lo3Inhoud.getVoorvoegselGeslachtsnaam());
    }

    @Test
    public void testScheidingstekenLeeg() {
        final BrpGroepInhoud inhoud = maakInhoud(null, "niet overnemen");
        final Lo3PersoonInhoud lo3Inhoud = converteer(inhoud);
        assertNull(lo3Inhoud.getVoorvoegselGeslachtsnaam());
    }

    @SuppressWarnings("unchecked")
    private Lo3PersoonInhoud converteer(final BrpGroepInhoud inhoud) {
        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20010101), null, BrpDatumTijd.fromDatum(20010101), null);
        final BrpActie actieInhoud =
                new BrpActie(1L, BrpSoortActieCode.CONVERSIE_GBA, BrpPartijCode.MIGRATIEVOORZIENING, null, null,
                        BrpDatumTijd.fromDatum(20010101), null, 0, null);
        final BrpGroep<BrpGroepInhoud> groep =
                new BrpGroep<BrpGroepInhoud>(inhoud, historie, actieInhoud, null, null);
        final List<BrpGroep<BrpGroepInhoud>> groepen = Arrays.asList(groep);
        final BrpStapel<? extends BrpGroepInhoud> brpStapels = new BrpStapel<BrpGroepInhoud>(groepen);
        return converteerder.converteer(brpStapels).getMeestRecenteElement().getInhoud();
    }

    private BrpGroepInhoud maakInhoud(final Character scheidingsteken, final String voorvoegsel) {
        return new BrpSamengesteldeNaamInhoud(null, "Voornaam", voorvoegsel, scheidingsteken, null, "Geslachtsnaam",
                false, false);
    }
}
