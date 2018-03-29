/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

/**
 * Test voor {@link Lo3ToevalligeGebeurtenisOuderPrecondities}.
 */
public class Lo3ToevalligeGebeurtenisPreconditiesTest extends AbstractLo3ToevalligeGebeurtenisProconditiesTest {

    private static final Lo3Documentatie DOCUMENTATIE = Lo3StapelHelper.lo3Akt(1L, "0599", "1-Q1234");
    private static final Lo3Historie HISTORIE = Lo3StapelHelper.lo3His(null, 20160101, null);

    private static final Lo3Categorie<Lo3PersoonInhoud> NAAMSWIJZIGING =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", null, "der", "Grieken", 19770101, "0599", "6030", "M", null, null, null),
                    DOCUMENTATIE,
                    HISTORIE,
                    Lo3StapelHelper.lo3Her(1, 0, 0));

    private static final Lo3Categorie<Lo3PersoonInhoud> PERSOON =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Persoon(null, null, "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                    null,
                    new Lo3Historie(null, null, null),
                    Lo3StapelHelper.lo3Her(1, 0, 1));

    private final Lo3Categorie<Lo3OuderInhoud> OUDER =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                    DOCUMENTATIE,
                    HISTORIE,
                    Lo3StapelHelper.lo3Her(2, 0, 0));

    @Test
    public void niets() {
        controleer(null, null);
    }

    @Test
    public void ok() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(NAAMSWIJZIGING, PERSOON),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, null);
    }

    @Test
    public void geenOntvangendeGemeente() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        null,
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(NAAMSWIJZIGING, PERSOON),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG010);
    }

    @Test
    public void ongeldigeOntvangendeGemeente() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("Klasinaveen"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(NAAMSWIJZIGING, PERSOON),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG010);
    }

    @Test
    public void geenVerzendendeGemeente() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        null,
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(NAAMSWIJZIGING, PERSOON),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        subject.controleerToevalligeGebeurtenis(toevalligeGebeurtenis);
        assertAantalInfos(0);
        assertAantalWarnings(0);
        assertAantalErrors(3);
        assertSoortMeldingCode(SoortMeldingCode.TG011, 1);
        assertSoortMeldingCode(SoortMeldingCode.TG023, 2);
    }

    @Test
    public void ongeldigeVerzendendeGemeente() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("Achterhoek"),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(NAAMSWIJZIGING, PERSOON),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        subject.controleerToevalligeGebeurtenis(toevalligeGebeurtenis);
        assertAantalInfos(0);
        assertAantalWarnings(0);
        assertAantalErrors(3);
        assertSoortMeldingCode(SoortMeldingCode.TG011, 1);
        assertSoortMeldingCode(SoortMeldingCode.TG023, 2);
    }

    @Test
    public void geenAktenummer() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        null,
                        Lo3StapelHelper.lo3Stapel(NAAMSWIJZIGING, PERSOON),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        subject.controleerToevalligeGebeurtenis(toevalligeGebeurtenis);
        assertAantalInfos(0);
        assertAantalWarnings(0);
        assertAantalErrors(3);
        assertSoortMeldingCode(SoortMeldingCode.TG012, 1);
        assertSoortMeldingCode(SoortMeldingCode.TG024, 2);
    }

    @Test
    public void geenPersoon() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        null,
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG026);
    }

    @Test
    public void aktenummerOngelijk() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(NAAMSWIJZIGING, PERSOON),
                        Lo3StapelHelper.lo3Stapel(
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                                        new Lo3Documentatie(1, DOCUMENTATIE.getGemeenteAkte(), new Lo3String("1-Q4321"), null, null, null, null, null),
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(2, 0, 0))),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG024);
    }

    @Test
    public void registergemeenteOngelijk() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(NAAMSWIJZIGING, PERSOON),
                        Lo3StapelHelper.lo3Stapel(
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                                        new Lo3Documentatie(1, new Lo3GemeenteCode("0212"), DOCUMENTATIE.getNummerAkte(), null, null, null, null, null),
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(2, 0, 0))),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG023);
    }

    @Test
    public void ingangsdatumOngelijk() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper.lo3Persoon(
                                                "4892503058",
                                                "474529769",
                                                "Jaap",
                                                null,
                                                "der",
                                                "Grieken",
                                                19770101,
                                                "0599",
                                                "6030",
                                                "M",
                                                null,
                                                null,
                                                null),
                                        DOCUMENTATIE,
                                        new Lo3Historie(null, new Lo3Datum(20160202), null),
                                        Lo3StapelHelper.lo3Her(1, 0, 0)),
                                PERSOON),
                        Lo3StapelHelper.lo3Stapel(
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                                        DOCUMENTATIE,
                                        new Lo3Historie(null, new Lo3Datum(20160303), null),
                                        Lo3StapelHelper.lo3Her(2, 0, 0))),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG025);
    }
}
