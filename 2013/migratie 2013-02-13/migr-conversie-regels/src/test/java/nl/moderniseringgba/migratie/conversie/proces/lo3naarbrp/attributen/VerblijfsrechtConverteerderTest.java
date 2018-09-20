/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.proces.AbstractConversieServiceTest;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Test;

/**
 * Test het contract van VerblijfsrechtConverteerder.
 * 
 */
public class VerblijfsrechtConverteerderTest extends AbstractConversieServiceTest {

    @Test
    public void testConverteer() throws InputValidationException {
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(maakLo3Persoonslijst());
        assertNotNull(brpPersoonslijst.getVerblijfsrechtStapel());
        assertEquals(1, brpPersoonslijst.getVerblijfsrechtStapel().size());
        final BrpStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel = brpPersoonslijst.getVerblijfsrechtStapel();
        assertEquals(new BrpDatum(20000101), verblijfsrechtStapel.get(0).getInhoud().getAanvangVerblijfsrecht());
    }

    @SuppressWarnings("unchecked")
    private Lo3Persoonslijst maakLo3Persoonslijst() throws InputValidationException {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen =
                new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

        persoonCategorieen.add(Lo3Builder.createLo3Persoon(1234567890L, 987654321L, "Piet Jan", "PS", "van",
                "HorenZeggen", VerplichteStapel.GEBOORTE_DATUM, "0000", "6030", "M", "E", null, null, null,
                VerplichteStapel.GEBOORTE_DATUM, VerplichteStapel.GEBOORTE_DATUM + 1));

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = new Lo3Stapel<Lo3PersoonInhoud>(persoonCategorieen);

        final List<Lo3Categorie<Lo3VerblijfstitelInhoud>> verblijfstitelCategorieen =
                new ArrayList<Lo3Categorie<Lo3VerblijfstitelInhoud>>();
        verblijfstitelCategorieen.add(new Lo3Categorie<Lo3VerblijfstitelInhoud>(new Lo3VerblijfstitelInhoud(
                new Lo3AanduidingVerblijfstitelCode("12"), null, new Lo3Datum(20000101)), null, new Lo3Historie(null,
                new Lo3Datum(20000101), new Lo3Datum(20000102)), null));
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(new Lo3Categorie<Lo3InschrijvingInhoud>(
                        new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(19800101), null, null, 1,
                                new Lo3Datumtijdstempel(20070401000000000L), null), null, Lo3Historie.NULL_HISTORIE,
                        null)));
        final Lo3Stapel<Lo3OuderInhoud> ouder = VerplichteStapel.createOuderStapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(persoonStapel).ouder1Stapel(ouder).ouder2Stapel(ouder)
                .verblijfplaatsStapel(verblijfplaats)
                .verblijfstitelStapel(new Lo3Stapel<Lo3VerblijfstitelInhoud>(verblijfstitelCategorieen))
                .inschrijvingStapel(lo3InschrijvingStapel).build();
    }
}
