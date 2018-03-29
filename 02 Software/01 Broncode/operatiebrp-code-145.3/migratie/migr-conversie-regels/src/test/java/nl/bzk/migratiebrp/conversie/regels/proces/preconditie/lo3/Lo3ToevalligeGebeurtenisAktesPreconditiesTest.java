/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

/**
 * Test voor {@link Lo3ToevalligeGebeurtenisAktesPrecondities}.
 */
public class Lo3ToevalligeGebeurtenisAktesPreconditiesTest extends AbstractLo3ToevalligeGebeurtenisProconditiesTest {

    private void controleer(final String aktenummer) {

        final Lo3Documentatie documentatie = Lo3StapelHelper.lo3Akt(1L, "0599", aktenummer);

        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        documentatie.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        documentatie.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper.lo3Persoon(
                                                "4892503058",
                                                "474529769",
                                                "Jaap",
                                                "G",
                                                "van de",
                                                "Romeinen",
                                                19770101,
                                                "0599",
                                                "6030",
                                                "M",
                                                null,
                                                null,
                                                null),
                                        null,
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(1, 0, 0))),
                        null,
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG012);
    }

    @Test
    public void onbekendeSoortAkte() {
        controleer("9-X1234");
    }

    @Test
    public void ongeldigeLengte() {
        controleer("9-X1234567");
    }

    @Test
    public void leeg() {
        controleer(null);
    }
}
