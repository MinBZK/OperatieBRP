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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

public class Lo3ToevalligeGebeurtenisVerbintenisPreconditiesTest extends AbstractLo3ToevalligeGebeurtenisProconditiesTest {

    private static final Lo3Documentatie DOCUMENTATIE = Lo3StapelHelper.lo3Akt(1L, "0599", "3-A1234");
    private static final Lo3Historie HISTORIE = Lo3StapelHelper.lo3His(null, 20160101, null);

    final Lo3Categorie<Lo3PersoonInhoud> PERSOON =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                    null,
                    new Lo3Historie(null, null, null),
                    Lo3StapelHelper.lo3Her(1, 0, 0));

    private void controleer(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> verbintenis, final SoortMeldingCode soortMeldingCode) {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        null,
                        Lo3StapelHelper.lo3Stapel(verbintenis),
                        null);

        controleer(toevalligeGebeurtenis, soortMeldingCode);
    }

    @Test
    public void ok() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3HuwelijkOfGp(
                                "9682621217",
                                "409946473",
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
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        controleer(huwelijk, null);
    }

    @Test
    public void geenGroep81() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
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
                        null,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        controleer(huwelijk, SoortMeldingCode.TG048);
    }

    @Test
    public void welGroep82() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
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
                        new Lo3Documentatie(
                                1,
                                DOCUMENTATIE.getGemeenteAkte(),
                                DOCUMENTATIE.getNummerAkte(),
                                new Lo3GemeenteCode("0599"),
                                null,
                                null,
                                null,
                                null),
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        controleer(huwelijk, SoortMeldingCode.TG005);
    }

    @Test
    public void welGroep83() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
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
                        new Lo3Onderzoek(new Lo3Integer(60000), null, null),
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        controleer(huwelijk, SoortMeldingCode.TG006);
    }

    @Test
    public void welGroep84() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
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
                        new Lo3Historie(Lo3IndicatieOnjuist.O, HISTORIE.getIngangsdatumGeldigheid(), null),
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        controleer(huwelijk, SoortMeldingCode.TG007);
    }

    @Test
    public void geenGroep85() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
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
                        new Lo3Historie(null, null, null),
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        controleer(huwelijk, SoortMeldingCode.TG050);
    }

    @Test
    public void welGroep86() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
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
                        new Lo3Historie(null, HISTORIE.getIngangsdatumGeldigheid(), new Lo3Datum(20160102)),
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        controleer(huwelijk, SoortMeldingCode.TG009);
    }

    @Test
    public void welGroep01() {
        final Lo3Documentatie documentatie = Lo3StapelHelper.lo3Akt(1L, "0599", "3-B1234");

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
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
                                null,
                                null,
                                null,
                                20160101,
                                "0599",
                                "6030",
                                "S",
                                "H"),
                        documentatie,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historisch =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3HuwelijkOfGp(
                                "9682621217",
                                "409946473",
                                "Klasina",
                                null,
                                null,
                                "Grutjes",
                                19470101,
                                "0599",
                                "6030",
                                null,
                                20060101,
                                "0599",
                                "6030",
                                null,
                                null,
                                null,
                                null,
                                "H"),
                        null,
                        new Lo3Historie(null, null, null),
                        Lo3StapelHelper.lo3Her(5, 0, 1));

        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        documentatie.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        documentatie.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        null,
                        Lo3StapelHelper.lo3Stapel(actueel, historisch),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG056);
    }

    @Test
    public void geenGroep02() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3HuwelijkOfGp(
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
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
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        controleer(huwelijk, SoortMeldingCode.TG019);
    }

    @Test
    public void geenGroep03() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3HuwelijkOfGp(
                                null,
                                null,
                                "Klasina",
                                null,
                                null,
                                "Grutjes",
                                null,
                                null,
                                null,
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
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        controleer(huwelijk, SoortMeldingCode.TG020);
    }

    @Test
    public void geenGroep04() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
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
                                null,
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
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        controleer(huwelijk, SoortMeldingCode.TG035);
    }

    @Test
    public void welGroep04() {
        final Lo3Documentatie documentatie = Lo3StapelHelper.lo3Akt(1L, "0599", "3-B1234");

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
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
                                null,
                                null,
                                null,
                                20160101,
                                "0599",
                                "6030",
                                "S",
                                "H"),
                        documentatie,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historisch =
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
                                20060101,
                                "0599",
                                "6030",
                                null,
                                null,
                                null,
                                null,
                                "H"),
                        null,
                        new Lo3Historie(null, null, null),
                        Lo3StapelHelper.lo3Her(5, 0, 1));

        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        documentatie.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        documentatie.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        null,
                        Lo3StapelHelper.lo3Stapel(actueel, historisch),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG055);
    }

    @Test
    public void geenGroep06() {
        final Lo3Documentatie documentatie = Lo3StapelHelper.lo3Akt(1L, "0599", "3-B1234");

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
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
                                null,
                                null,
                                null,
                                20160101,
                                "0599",
                                "6030",
                                "S",
                                "H"),
                        documentatie,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historisch =
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
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                "H"),
                        null,
                        new Lo3Historie(null, null, null),
                        Lo3StapelHelper.lo3Her(5, 0, 1));

        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        documentatie.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        documentatie.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        null,
                        Lo3StapelHelper.lo3Stapel(actueel, historisch),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG036);
    }

    @Test
    public void welGroep07() {
        final Lo3Documentatie documentatie = Lo3StapelHelper.lo3Akt(1L, "0599", "3-B1234");

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueel =
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
                                null,
                                null,
                                null,
                                20160101,
                                "0599",
                                "6030",
                                "S",
                                "H"),
                        documentatie,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> historisch =
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
                                null,
                                20160101,
                                "0599",
                                "6030",
                                20160101,
                                "0599",
                                "6030",
                                "S",
                                "H"),
                        null,
                        new Lo3Historie(null, null, null),
                        Lo3StapelHelper.lo3Her(5, 0, 1));

        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        documentatie.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        documentatie.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        null,
                        Lo3StapelHelper.lo3Stapel(actueel, historisch),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG021);
    }

    @Test
    public void geenGroep15() {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijk =
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
                                null),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(5, 0, 0));

        controleer(huwelijk, SoortMeldingCode.TG022);
    }
}
