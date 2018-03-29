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
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

/**
 * Test voor {@link Lo3ToevalligeGebeurtenisOuderPrecondities}.
 */
public class Lo3ToevalligeGebeurtenisOuderPreconditiesTest extends AbstractLo3ToevalligeGebeurtenisProconditiesTest {

    private static final Lo3Documentatie DOCUMENTATIE = Lo3StapelHelper.lo3Akt(1L, "0599", "1-Q1234");
    private static final Lo3Historie HISTORIE = Lo3StapelHelper.lo3His(null, 20160101, null);

    private static final Lo3Categorie<Lo3PersoonInhoud> PERSOON =
            Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Persoon("4892503058", "474529769", "Jaap", "G", "van de", "Romeinen", 19770101, "0599", "6030", "M", null, null, null),
                    null,
                    new Lo3Historie(null, null, null),
                    Lo3StapelHelper.lo3Her(1, 0, 0));

    private void controleer(final Lo3Categorie<Lo3OuderInhoud> ouder, final SoortMeldingCode soortMeldingCode) {
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        Lo3StapelHelper.lo3Stapel(ouder),
                        null,
                        null);

        controleer(toevalligeGebeurtenis, soortMeldingCode);
    }

    @Test
    public void ok() {
        final Lo3Categorie<Lo3OuderInhoud> ouder =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(2, 0, 0));

        controleer(ouder, null);
    }

    @Test
    public void okMetHistorie() {
        final Lo3Categorie<Lo3OuderInhoud> actueel =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder("9787101985", "336344041", null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(2, 0, 0));
        final Lo3Categorie<Lo3OuderInhoud> historisch =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", null, 19470101),
                        null,
                        new Lo3Historie(null, null, null),
                        Lo3StapelHelper.lo3Her(2, 0, 1));

        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        Lo3StapelHelper.lo3Stapel(actueel, historisch),
                        null,
                        null);

        controleer(toevalligeGebeurtenis, null);
    }

    @Test
    public void geenGroep81() {
        final Lo3Categorie<Lo3OuderInhoud> ouder =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                        null,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(2, 0, 0));

        controleer(ouder, SoortMeldingCode.TG048);
    }

    @Test
    public void welGroep82() {
        final Lo3Categorie<Lo3OuderInhoud> ouder =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
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
                        Lo3StapelHelper.lo3Her(2, 0, 0));

        controleer(ouder, SoortMeldingCode.TG005);
    }

    @Test
    public void welGroep83() {
        final Lo3Categorie<Lo3OuderInhoud> ouder =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                        DOCUMENTATIE,
                        new Lo3Onderzoek(new Lo3Integer(60000), null, null),
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(2, 0, 0));

        controleer(ouder, SoortMeldingCode.TG006);
    }

    @Test
    public void welGroep84() {
        final Lo3Categorie<Lo3OuderInhoud> ouder =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                        DOCUMENTATIE,
                        new Lo3Historie(Lo3IndicatieOnjuist.O, HISTORIE.getIngangsdatumGeldigheid(), null),
                        Lo3StapelHelper.lo3Her(2, 0, 0));

        controleer(ouder, SoortMeldingCode.TG007);
    }

    @Test
    public void geenGroep85() {
        final Lo3Categorie<Lo3OuderInhoud> ouder =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                        DOCUMENTATIE,
                        new Lo3Historie(null, null, null),
                        Lo3StapelHelper.lo3Her(2, 0, 0));

        controleer(ouder, SoortMeldingCode.TG050);
    }

    @Test
    public void welGroep86() {
        final Lo3Categorie<Lo3OuderInhoud> ouder =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                        DOCUMENTATIE,
                        new Lo3Historie(null, HISTORIE.getIngangsdatumGeldigheid(), new Lo3Datum(20160102)),
                        Lo3StapelHelper.lo3Her(2, 0, 0));

        controleer(ouder, SoortMeldingCode.TG009);
    }

    @Test
    public void welGroep01() {
        final Lo3Categorie<Lo3OuderInhoud> actueel =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(2, 0, 0));
        final Lo3Categorie<Lo3OuderInhoud> historisch =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder("113407245", null, null, null, null, "Ouder", 19470101, "0599", "06030", null, 19470101),
                        null,
                        new Lo3Historie(null, null, null),
                        Lo3StapelHelper.lo3Her(2, 0, 1));

        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        Lo3StapelHelper.lo3Stapel(actueel, historisch),
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG059);
    }

    @Test
    public void geenGroep02() {
        final Lo3Categorie<Lo3OuderInhoud> ouder =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, null, 19470101, "0599", "06030", "V", 19470101),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(2, 0, 0));

        controleer(ouder, SoortMeldingCode.TG014);
    }

    @Test
    public void geenGroep03() {
        final Lo3Categorie<Lo3OuderInhoud> ouder =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", null, null, null, "V", 19470101),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(2, 0, 0));

        controleer(ouder, SoortMeldingCode.TG015);
    }

    @Test
    public void geenGroep04() {
        final Lo3Categorie<Lo3OuderInhoud> ouder =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", null, 19470101),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(2, 0, 0));

        controleer(ouder, SoortMeldingCode.TG016);
    }

    @Test
    public void welGroep04() {
        final Lo3Categorie<Lo3OuderInhoud> actueel =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(2, 0, 0));
        final Lo3Categorie<Lo3OuderInhoud> historisch =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", 19470101),
                        null,
                        new Lo3Historie(null, null, null),
                        Lo3StapelHelper.lo3Her(2, 0, 1));

        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        DOCUMENTATIE.getGemeenteAkte(),
                        new Lo3GemeenteCode("0626"),
                        DOCUMENTATIE.getNummerAkte(),
                        Lo3StapelHelper.lo3Stapel(PERSOON),
                        null,
                        Lo3StapelHelper.lo3Stapel(actueel, historisch),
                        null,
                        null);

        controleer(toevalligeGebeurtenis, SoortMeldingCode.TG060);
    }

    @Test
    public void geenGroep62() {
        final Lo3Categorie<Lo3OuderInhoud> ouder =
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(null, null, null, null, null, "Ouder", 19470101, "0599", "06030", "V", null),
                        DOCUMENTATIE,
                        HISTORIE,
                        Lo3StapelHelper.lo3Her(2, 0, 0));

        controleer(ouder, SoortMeldingCode.TG017);
    }

}
