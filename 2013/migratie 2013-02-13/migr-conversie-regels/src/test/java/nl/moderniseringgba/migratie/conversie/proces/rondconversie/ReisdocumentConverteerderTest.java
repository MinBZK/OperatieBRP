/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.rondconversie;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.proces.AbstractConversieServiceTest;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Test;

/**
 * Test het contract van ReisdocumentConverteerder.
 * 
 */
public class ReisdocumentConverteerderTest extends AbstractConversieServiceTest {

    @Test
    public void testRondConverteerReisdocument() throws InputValidationException {
        final Lo3PersoonslijstBuilder builder = maakLo3PersoonslijstBuilder();
        voegReisdocumentToe(builder);
        final Lo3Persoonslijst lo3Persoonslijst = builder.build();

        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);

        final Lo3Persoonslijst lo3Terug = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);

        assertEquals(lo3Persoonslijst.getReisdocumentStapels(), lo3Terug.getReisdocumentStapels());
    }

    @SuppressWarnings("unchecked")
    private Lo3PersoonslijstBuilder maakLo3PersoonslijstBuilder() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen =
                new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

        persoonCategorieen.add(Lo3Builder.createLo3Persoon(1234567890L, 987654321L, "Piet Jan", "P", "van",
                "HorenZeggen", VerplichteStapel.GEBOORTE_DATUM, "0000", "6030", "M", "E", null, null, null, 19800101,
                19800202));

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = new Lo3Stapel<Lo3PersoonInhoud>(persoonCategorieen);
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(new Lo3Categorie<Lo3InschrijvingInhoud>(
                        new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(19800101), null, null, 1,
                                new Lo3Datumtijdstempel(20070401000000000L), null), null, Lo3Historie.NULL_HISTORIE,
                        null)));
        final Lo3Stapel<Lo3OuderInhoud> ouder = VerplichteStapel.createOuderStapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(persoonStapel).ouder1Stapel(ouder).ouder2Stapel(ouder)
                .inschrijvingStapel(lo3InschrijvingStapel).verblijfplaatsStapel(verblijfplaats);
    }

    private void voegReisdocumentToe(final Lo3PersoonslijstBuilder builder) {
        final List<Lo3Categorie<Lo3ReisdocumentInhoud>> reisdocumentCategorieen =
                new ArrayList<Lo3Categorie<Lo3ReisdocumentInhoud>>();
        reisdocumentCategorieen.add(new Lo3Categorie<Lo3ReisdocumentInhoud>(new Lo3ReisdocumentInhoud(
                new Lo3SoortNederlandsReisdocument("P"), "P12345678", new Lo3Datum(20120101),
                new Lo3AutoriteitVanAfgifteNederlandsReisdocument("123456"), new Lo3Datum(20170101), new Lo3Datum(
                        20130101), null, new Lo3LengteHouder(180), null, null), null, new Lo3Historie(null,
                new Lo3Datum(20120102), new Lo3Datum(20010102)), null));

        builder.reisdocumentStapel(new Lo3Stapel<Lo3ReisdocumentInhoud>(reisdocumentCategorieen));
    }
}
