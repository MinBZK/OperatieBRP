/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

import org.junit.Test;

public class BrpNationaliteitConverteerderTest {
    @Test
    public void testProbas() {
        testProbas("PROBAS", true);
        testProbas("PrObAstico", true);
        testProbas(" PrObAstisch", true);
        testProbas("  PrObAs Badjas", true);

        testProbas("Pro Bassie en Adriaan", false);
        testProbas(" Probe ass", false);
    }

    public void testProbas(final String beschrijvingDocument, final boolean wordtHerkend) {

        final BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud inhoud =
                new BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud(BrpBoolean.wrap(true, null), null, null);
        final BrpHistorie historie = new BrpHistorie(new BrpDatum(20121106, null), null, BrpDatumTijd.fromDatum(20121106, null), null, null);
        final BrpGroep<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> groep = new BrpGroep<>(inhoud, historie, null, null, null);
        final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> stapel = new BrpStapel<>(Collections.singletonList(groep));

        final Lo3NationaliteitInhoud lo3Inhoud = new Lo3NationaliteitInhoud();
        final Lo3Documentatie documentatie = new Lo3Documentatie(1L, null, null, null, null, new Lo3String(beschrijvingDocument, null), null, null);
        final Lo3Historie lo3Historie = new Lo3Historie(null, null, null);
        final Lo3Herkomst herkomst = new Lo3Herkomst(null, 0, 0, 0);
        final Lo3Categorie<Lo3NationaliteitInhoud> categorie = new Lo3Categorie<>(lo3Inhoud, documentatie, lo3Historie, herkomst);
        final Lo3Stapel<Lo3NationaliteitInhoud> lo3Nationaliteit = new Lo3Stapel<>(Collections.singletonList(categorie));

        final Lo3Stapel<Lo3NationaliteitInhoud> aangepast = new BrpNationaliteitConverteerder().converteerGeprivilegieerde(stapel, lo3Nationaliteit);

        if (wordtHerkend) {
            assertTrue(
                "Beschrijving PROBAS zou niet aangepast moeten zijn",
                beschrijvingDocument.equals(aangepast.get(0).getDocumentatie().getBeschrijvingDocument().getWaarde()));
        } else {
            assertFalse(
                "Beschrijving PROBAS zou aangepast moeten zijn",
                beschrijvingDocument.equals(aangepast.get(0).getDocumentatie().getBeschrijvingDocument().getWaarde()));
        }
    }
}
