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
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

/**
 * Test voor {@link Lo3ToevalligeGebeurtenisAkteVerbintenisOmzettingPrecondities}.
 */
public class Lo3ToevalligeGebeurtenisAkteVerbintenisOmzettingPreconditiesTest extends AbstractLo3ToevalligeGebeurtenisProconditiesTest {

    private static final Lo3Documentatie DOCUMENTATIE = Lo3StapelHelper.lo3Akt(1L, "0599", "3-H1234");
    private static final Lo3Historie HISTORIE = Lo3StapelHelper.lo3His(null, 20160101, null);

    private static final Lo3Categorie<Lo3PersoonInhoud> PERSOON =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                    null,
                    new Lo3Historie(null, null, null),
                    Lo3StapelHelper.lo3Her(1, 0, 0));

    final Lo3Categorie<Lo3HuwelijkOfGpInhoud> OMZETTING =
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
                            20170101,
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

    final Lo3Categorie<Lo3HuwelijkOfGpInhoud> SLUITING =
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
                            "P"),
                    null,
                    new Lo3Historie(null, null, null),
                    Lo3StapelHelper.lo3Her(5, 0, 1));

    @Test
    public void ok() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        null,
                        Lo3StapelHelper.lo3Stapel(OMZETTING, SLUITING),
                        null);

        controleer(toevalligeGebeurtenis, null);
    }

    @Test
    public void partnerschapOk() {
        final Lo3Documentatie documentatiePartnerschap = Lo3StapelHelper.lo3Akt(1L, "0599", "5-H1234");
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        documentatiePartnerschap.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        documentatiePartnerschap.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
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
                                                20170101,
                                                "0599",
                                                "6030",
                                                null,
                                                null,
                                                null,
                                                null,
                                                "P"),
                                        documentatiePartnerschap,
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(5, 0, 0)),
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
                                        null,
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(5, 0, 1))),
                        null);

        controleer(toevalligeGebeurtenis, null);
    }

    @Test
    public void welCategorie51() {
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
                                        DOCUMENTATIE,
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(1, 0, 0)),
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper
                                                .lo3Persoon(null, null, "Klaas", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                                        null,
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(1, 0, 1))),
                        null,
                        null,
                        Lo3StapelHelper.lo3Stapel(OMZETTING, SLUITING),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG027);
    }

    @Test
    public void welCategorie02() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        Lo3StapelHelper.lo3Stapel(
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                                        DOCUMENTATIE,
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(2, 0, 0))),
                        null,
                        Lo3StapelHelper.lo3Stapel(OMZETTING, SLUITING),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG028);
    }

    @Test
    public void welCategorie03() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        Lo3StapelHelper.lo3Stapel(
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                                        DOCUMENTATIE,
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(3, 0, 0))),
                        Lo3StapelHelper.lo3Stapel(OMZETTING, SLUITING),
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
                        null,
                        null,
                        Lo3StapelHelper.lo3Stapel(OMZETTING, SLUITING),
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Overlijden(20160101, "0599", "6030"),
                                DOCUMENTATIE,
                                HISTORIE,
                                Lo3StapelHelper.lo3Her(6, 0, 0)));

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG028);
    }

    @Test
    public void geenCategorie05() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        null,
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG031);
    }

    @Test
    public void geenGroep06() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
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
                                                null,
                                                null,
                                                null,
                                                null,
                                                null,
                                                null,
                                                null,
                                                "H"),
                                        DOCUMENTATIE,
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(5, 0, 0)),
                                SLUITING),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG032);
    }

    @Test
    public void welGroep07() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
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
                                                20170101,
                                                "0599",
                                                "6030",
                                                20170101,
                                                "0599",
                                                "6030",
                                                "S",
                                                "H"),
                                        DOCUMENTATIE,
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(5, 0, 0)),
                                SLUITING),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG033);
    }

    @Test
    public void verkeerde051510() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
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
                                                20170101,
                                                "0599",
                                                "6030",
                                                null,
                                                null,
                                                null,
                                                null,
                                                "P"),
                                        DOCUMENTATIE,
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(5, 0, 0)),
                                SLUITING),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG034);
    }

    @Test
    public void geenCategorie55() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        null,
                        Lo3StapelHelper.lo3Stapel(OMZETTING),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG037);
    }

    @Test
    public void groep02Ongelijk() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        null,
                        Lo3StapelHelper.lo3Stapel(
                                Lo3StapelHelper.lo3Cat(
                                        Lo3StapelHelper.lo3HuwelijkOfGp(
                                                null,
                                                null,
                                                "Betsy",
                                                null,
                                                null,
                                                "Grutjes",
                                                19470101,
                                                "0599",
                                                "6030",
                                                "V",
                                                20170101,
                                                "0599",
                                                "6030",
                                                null,
                                                null,
                                                null,
                                                null,
                                                "H"),
                                        DOCUMENTATIE,
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(5, 0, 0)),
                                SLUITING),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG040);
    }

    @Test
    public void groep03Ongelijk() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
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
                                                19570101,
                                                "0599",
                                                "6030",
                                                "V",
                                                20170101,
                                                "0599",
                                                "6030",
                                                null,
                                                null,
                                                null,
                                                null,
                                                "H"),
                                        DOCUMENTATIE,
                                        HISTORIE,
                                        Lo3StapelHelper.lo3Her(5, 0, 0)),
                                SLUITING),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG041);
    }

    @Test
    public void verkeerde551510() {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        null,
                        Lo3StapelHelper.lo3Stapel(
                                OMZETTING,
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
                                        null,
                                        new Lo3Historie(null, null, null),
                                        Lo3StapelHelper.lo3Her(5, 0, 1))),
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG034);
    }

}
