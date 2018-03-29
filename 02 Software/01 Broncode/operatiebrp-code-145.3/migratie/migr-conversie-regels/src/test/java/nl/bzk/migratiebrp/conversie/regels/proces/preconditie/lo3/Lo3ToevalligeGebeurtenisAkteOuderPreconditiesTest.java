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
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

/**
 * Test voor {@link Lo3ToevalligeGebeurtenisAkteOudersPrecondities}.
 */
public class Lo3ToevalligeGebeurtenisAkteOuderPreconditiesTest extends AbstractLo3ToevalligeGebeurtenisProconditiesTest {

    private static final Lo3Documentatie DOCUMENTATIE = Lo3StapelHelper.lo3Akt(1L, "0599", "1-Q1234");
    private static final Lo3Historie HISTORIE = Lo3StapelHelper.lo3His(null, 20160101, null);

    private static final Lo3Categorie<Lo3PersoonInhoud> PERSOON =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                    null,
                    new Lo3Historie(null, null, null),
                    Lo3StapelHelper.lo3Her(1, 0, 0));

    final Lo3Categorie<Lo3OuderInhoud> OUDER =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                    DOCUMENTATIE,
                    HISTORIE,
                    Lo3StapelHelper.lo3Her(2, 0, 0));

    @Test
    public void ok() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, null);
    }

    @Test
    public void okMetNaamswijziging() {
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
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(1, 0, 0)),
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper
                                                .lo3Persoon(null, null, "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                                        null,
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(1, 0, 1))),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, null);
    }

    @Test
    public void geenCategorie01() {
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
    public void welCategorie05() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        Lo3StapelHelper.lo3Stapel(
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper.lo3HuwelijkOfGp(
                                                null,
                                                null,
                                                "Klasina",
                                                null,
                                                null,
                                                "Grutjes",
                                                19470101,
                                                "0599",
                                                "6030",
                                                "V",
                                                20160101,
                                                "0599",
                                                "6030",
                                                null,
                                                null,
                                                null,
                                                null,
                                                "H"),
                                        DOCUMENTATIE,
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(5, 0, 0))),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG028);
    }

    @Test
    public void welCategorie06() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Overlijden(20160101, "0599", "6030"),
                                DOCUMENTATIE,
                                HISTORIE,
                                Lo3StapelHelper.lo3Her(6, 0, 0)));

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG028);
    }

    @Test
    public void naamswijzigingMetVoornaamGewijzigd() {
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
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(1, 0, 0)),
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper
                                                .lo3Persoon(null, null, "Peter", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                                        null,
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(1, 0, 1))),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG061);
    }

    @Test
    public void naamswijzigingMetGroep03Gewijzigd() {
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
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(1, 0, 0)),
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper
                                                .lo3Persoon(null, null, "Jaap", "G", "van de", "Romeinen", 19770101, "0626", "6030", "M", null, null, null),
                                        null,
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(1, 0, 1))),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG061);
    }

    @Test
    public void naamswijzigingMetGroep04Gewijzigd() {
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
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(1, 0, 0)),
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper
                                                .lo3Persoon(null, null, "Jaap", null, "van de", "Romeinen", 19770101, "0599", "6030", "V", null, null, null),
                                        null,
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(1, 0, 1))),
                        Lo3StapelHelper.lo3Stapel(OUDER),
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG061);
    }
}
