/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3SplitsenVerbintenis;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test voor het splitsen van verbintenissen.
 */
public class Lo3SplitsenVerbintenisTest extends AbstractLoggingTest {

    /**
     * Reproduceren van het geval dat een lege rij niet gekoppeld kan worden aan een juist gevulde rij.
     */
    @Test
    @Ignore
    public void testLegeHuwelijksrijNietTeKoppelen() {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen = new ArrayList<>();

        final Lo3Documentatie doc1 = createDocumentatie(1);
        final Lo3Documentatie doc2 = createDocumentatie(2);

        final Lo3HuwelijkOfGpInhoud inhoud1 =
                createHuwelijkInhoud(new Lo3GemeenteCode("1234"), new Lo3Datum(0), new Lo3RedenOntbindingHuwelijkOfGpCode("S"), null);

        final Lo3HuwelijkOfGpInhoud inhoud2 = createHuwelijkInhoud(null, null, null, new Lo3SoortVerbintenis("H"));

        categorieen.add(new Lo3Categorie<>(inhoud1, doc1, new Lo3Historie(null, new Lo3Datum(0), new Lo3Datum(19950102)), new Lo3Herkomst(
            Lo3CategorieEnum.CATEGORIE_04,
            0,
            1)));
        categorieen.add(new Lo3Categorie<>(inhoud2, doc2, new Lo3Historie(null, new Lo3Datum(19900101), new Lo3Datum(19900102)), new Lo3Herkomst(
            Lo3CategorieEnum.CATEGORIE_04,
            0,
            0)));

        Lo3SplitsenVerbintenis.splitsVerbintenissen(new Lo3Stapel<>(categorieen));

        assertAantalErrors(0);
    }

    private Lo3HuwelijkOfGpInhoud createHuwelijkInhoud(
        final Lo3GemeenteCode gemeenteCode,
        final Lo3Datum datumSluitingHuwelijkOfAangaanGp,
        final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbinding,
        final Lo3SoortVerbintenis soortVerbintenis)
    {
        return new Lo3HuwelijkOfGpInhoud(
            Lo3Long.wrap(1234567890L),
            Lo3Integer.wrap(123456789),
            Lo3String.wrap("Ben"),
            null,
            null,
            Lo3String.wrap("Getrouwd"),
            new Lo3Datum(19800101),
            new Lo3GemeenteCode("1234"),
            new Lo3LandCode("6030"),
            Lo3GeslachtsaanduidingEnum.MAN.asElement(),
            null,
            gemeenteCode,
            null,
            datumSluitingHuwelijkOfAangaanGp,
            new Lo3GemeenteCode("1234"),
            null,
            redenOntbinding,
            soortVerbintenis);
    }

    private Lo3Documentatie createDocumentatie(final long id) {
        return new Lo3Documentatie(id, new Lo3GemeenteCode("0518"), Lo3String.wrap("A" + id), null, null, null, null, null);
    }
}
