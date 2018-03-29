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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.junit.Test;

/**
 * Test voor {@link Lo3ToevalligeGebeurtenisPersoonPrecondities}.
 */
public class Lo3ToevalligeGebeurtenisPersoonPreconditiesTest extends AbstractLo3ToevalligeGebeurtenisProconditiesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final Lo3Documentatie DOCUMENTATIE = Lo3StapelHelper.lo3Akt(1L, "0599", "1-M1234");
    private static final Lo3Historie HISTORIE = Lo3StapelHelper.lo3His(null, 20160101, null);

    final Lo3Categorie<Lo3PersoonInhoud> PERSOON =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                    DOCUMENTATIE,
                    HISTORIE,
                    Lo3StapelHelper.lo3Her(1, 0, 0));

    final Lo3Categorie<Lo3PersoonInhoud> VOORNAAM_WIJZIGING =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Persoon(null, null, "Klaas", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                    null,
                    new Lo3Historie(null, null, null),
                    Lo3StapelHelper.lo3Her(51, 0, 1));

    final Lo3Categorie<Lo3PersoonInhoud> VOORNAAM_WIJZIGING_NULL_HISTORIE =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Persoon(null, null, "Klaas", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                    null,
                    new Lo3Historie(null, null, null),
                    Lo3StapelHelper.lo3Her(51, 0, 1));

    final Lo3Categorie<Lo3PersoonInhoud> VOORNAAM_WIJZIGING_NULL_HISTORIE_NULL_DOCUMENT =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Persoon(null, null, "Klaas", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                    new Lo3Documentatie(1l, null, null, null, null, null, null, null),
                    new Lo3Historie(null, null, null),
                    Lo3StapelHelper.lo3Her(51, 0, 1));

    private void controleer(
            final Lo3Categorie<Lo3PersoonInhoud> persoonActueel,
            final Lo3Categorie<Lo3PersoonInhoud> persoonHistorisch,
            final SoortMeldingCode soortMeldingCode) {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(persoonActueel, persoonHistorisch),
                        null,
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, soortMeldingCode);
    }

    @Test
    public void ok() {
        controleer(PERSOON, VOORNAAM_WIJZIGING, null);
    }

    @Test
    public void okMetNullHistorie() {
        controleer(PERSOON, VOORNAAM_WIJZIGING_NULL_HISTORIE, null);
    }

    @Test
    public void okMetNullHistorieEnNullDocumentatie() {
        controleer(PERSOON, VOORNAAM_WIJZIGING_NULL_HISTORIE_NULL_DOCUMENT, null);
    }

    @Test
    public void foutMetOmgedraaideCategorieen() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(VOORNAAM_WIJZIGING_NULL_HISTORIE_NULL_DOCUMENT, PERSOON),
                        null,
                        null,
                        null,
                        null);

        subject.controleerToevalligeGebeurtenis(toevalligeGebeurtenis);

        if (!Logging.getLogging().getRegels().isEmpty()) {
            LOGGER.info("Gevonden regels: ");
            for (final LogRegel regel : Logging.getLogging().getRegels()) {
                LOGGER.info(" - {}", regel);
            }
        }

        assertAantalInfos(0);
        assertAantalWarnings(0);
        assertAantalErrors(2);
        assertSoortMeldingCode(SoortMeldingCode.TG048, 1);
        assertSoortMeldingCode(SoortMeldingCode.TG050, 1);
    }

    @Test
    public void geenGroep81() {
        final Lo3Categorie<Lo3PersoonInhoud> persoon =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                        null,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(1, 0, 0));

        controleer(persoon, VOORNAAM_WIJZIGING, SoortMeldingCode.TG048);
    }

    @Test
    public void welGroep81() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0626"),
                        new Lo3String("2-A1234"),
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
                                        Lo3StapelHelper.lo3Akt(1L, "0599", "2-A1234"),
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(1, 0, 0))),
                        null,
                        null,
                        null,
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Overlijden(20160101, "0599", "6030"),
                                Lo3StapelHelper.lo3Akt(1L, "0599", "2-A1234"),
                                HISTORIE,
                                Lo3StapelHelper.lo3Her(6, 0, 0)));

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG047);
    }

    @Test
    public void welGroep82() {
        final Lo3Categorie<Lo3PersoonInhoud> persoon =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
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
                        Lo3StapelHelper.lo3Her(1, 0, 0));

        controleer(persoon, VOORNAAM_WIJZIGING, SoortMeldingCode.TG005);
    }

    @Test
    public void welGroep83() {
        final Lo3Categorie<Lo3PersoonInhoud> persoon =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                        DOCUMENTATIE,
                        new Lo3Onderzoek(new Lo3Integer(60000), null, null),
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(1, 0, 0));

        controleer(persoon, VOORNAAM_WIJZIGING, SoortMeldingCode.TG006);
    }

    @Test
    public void welGroep84() {
        final Lo3Categorie<Lo3PersoonInhoud> persoon =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                        DOCUMENTATIE,
                        new Lo3Historie(Lo3IndicatieOnjuist.O, HISTORIE.getIngangsdatumGeldigheid(), null),
                        Lo3StapelHelper.lo3Her(1, 0, 0));

        controleer(persoon, VOORNAAM_WIJZIGING, SoortMeldingCode.TG007);
    }

    @Test
    public void geenGroep85() {
        final Lo3Categorie<Lo3PersoonInhoud> persoon =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                        DOCUMENTATIE,
                        new Lo3Historie(null, null, null),
                        Lo3StapelHelper.lo3Her(1, 0, 0));

        controleer(persoon, VOORNAAM_WIJZIGING, SoortMeldingCode.TG050);
    }

    @Test
    public void welGroep85() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0626"),
                        new Lo3String("2-A1234"),
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
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(1, 0, 0))),
                        null,
                        null,
                        null,
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Overlijden(20160101, "0599", "6030"),
                                Lo3StapelHelper.lo3Akt(1L, "0599", "2-A1234"),
                                HISTORIE,
                                Lo3StapelHelper.lo3Her(6, 0, 0)));

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG049);
    }

    @Test
    public void welGroep86() {
        final Lo3Categorie<Lo3PersoonInhoud> persoon =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                        DOCUMENTATIE,
                        new Lo3Historie(null, HISTORIE.getIngangsdatumGeldigheid(), new Lo3Datum(20160102)),
                        Lo3StapelHelper.lo3Her(1, 0, 0));

        controleer(persoon, VOORNAAM_WIJZIGING, SoortMeldingCode.TG009);
    }

    @Test
    public void welGroep88() {
        final Lo3Categorie<Lo3PersoonInhoud> persoon =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                        new Lo3Documentatie(
                                1,
                                DOCUMENTATIE.getGemeenteAkte(),
                                DOCUMENTATIE.getNummerAkte(),
                                null,
                                null,
                                null,
                                new Lo3RNIDeelnemerCode("0000"),
                                null),
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(1, 0, 0));

        controleer(persoon, VOORNAAM_WIJZIGING, SoortMeldingCode.TG003);
    }

    @Test
    public void geenGroep01() {
        final Lo3Categorie<Lo3PersoonInhoud> persoon =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Persoon(null, null, "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(1, 0, 0));

        controleer(persoon, VOORNAAM_WIJZIGING, SoortMeldingCode.TG051);
    }

    @Test
    public void geenGroep02() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0626"),
                        new Lo3String("2-A1234"),
                        Lo3StapelHelper.lo3Stapel(
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper
                                                .lo3Persoon("4892503058", "474529769", null, null, null, null, 19770101, "0599", "6030", "M", null, null, null),
                                        null,
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(1, 0, 0))),
                        null,
                        null,
                        null,
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Overlijden(20160101, "0599", "6030"),
                                Lo3StapelHelper.lo3Akt(1L, "0599", "2-A1234"),
                                HISTORIE,
                                Lo3StapelHelper.lo3Her(6, 0, 0)));

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG052);
    }

    @Test
    public void geenGroep03() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0626"),
                        new Lo3String("2-A1234"),
                        Lo3StapelHelper.lo3Stapel(
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper
                                                .lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", null, null, null, "M", null, null, null),
                                        null,
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(1, 0, 0))),
                        null,
                        null,
                        null,
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Overlijden(20160101, "0599", "6030"),
                                Lo3StapelHelper.lo3Akt(1L, "0599", "2-A1234"),
                                HISTORIE,
                                Lo3StapelHelper.lo3Her(6, 0, 0)));

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG053);
    }

    @Test
    public void geenGroep04() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0626"),
                        new Lo3String("2-A1234"),
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
                                                null,
                                                null,
                                                null,
                                                null),
                                        null,
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(1, 0, 0))),
                        null,
                        null,
                        null,
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Overlijden(20160101, "0599", "6030"),
                                Lo3StapelHelper.lo3Akt(1L, "0599", "2-A1234"),
                                HISTORIE,
                                Lo3StapelHelper.lo3Her(6, 0, 0)));

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG054);
    }

    @Test
    public void welGroep20() {
        final Lo3Categorie<Lo3PersoonInhoud> persoon =
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
                                "4892503058",
                                null,
                                null),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(1, 0, 0));

        controleer(persoon, VOORNAAM_WIJZIGING, SoortMeldingCode.TG001);
    }

    @Test
    public void welGroep61() {
        final Lo3Categorie<Lo3PersoonInhoud> persoon =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, "E"),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(1, 0, 0));

        controleer(persoon, VOORNAAM_WIJZIGING, SoortMeldingCode.TG002);
    }

}
