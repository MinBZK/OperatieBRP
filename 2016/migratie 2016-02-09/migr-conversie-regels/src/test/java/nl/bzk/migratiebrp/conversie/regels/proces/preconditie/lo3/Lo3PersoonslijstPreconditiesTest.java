/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

public class Lo3PersoonslijstPreconditiesTest extends AbstractPreconditieTest {

    protected static final Lo3Historie GEBOORTE_HISTORIE = Lo3StapelHelper.lo3His(19770101);
    protected static final Lo3Documentatie GEBOORTE_AKTE = Lo3StapelHelper.lo3Akt(1);
    private static final String GEM_CODE = "0518";

    @Inject
    private Lo3PersoonslijstPrecondities precondities;

    @Test
    public void testHappy() {

        precondities.controleerPersoonslijst(builder().build());
        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr001() {
        final Lo3Persoonslijst pl = builder().persoonStapel(null).build();

        precondities.controleerPersoonslijst(pl);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE047, 1);
    }

    @Test
    public void testContr004() {
        final Lo3Persoonslijst pl = builder().inschrijvingStapel(null).build();

        precondities.controleerPersoonslijst(pl);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE032, 1);
    }

    @Test
    public void testContr005() {
        final Lo3Persoonslijst pl = builder().verblijfplaatsStapel(null).build();

        precondities.controleerPersoonslijst(pl);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE033, 1);
    }

    @Test
    public void testPre065MetRNIEnRNIGemeente() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Inschrijving(null, 20120101, "R", 19770202, GEM_CODE, 0, 1, 20120101123014000L, true),
                    null,
                    Lo3Historie.NULL_HISTORIE,
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder1Stapel(null);
        builder.inschrijvingStapel(stapel);
        builder.verblijfplaatsStapel(buildRNIVerblijfplaatsStapel());
        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(0);
    }

    @Test
    public void testPre065MetFoutEnRNIGemeente() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Inschrijving(null, 20120101, "F", 19770202, GEM_CODE, 0, 1, 20120101123014000L, true),
                    null,
                    Lo3Historie.NULL_HISTORIE,
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder1Stapel(null);
        builder.inschrijvingStapel(stapel);
        builder.verblijfplaatsStapel(buildRNIVerblijfplaatsStapel());
        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(0);
    }

    @Test
    public void testPre065MetOverledenEnRNIGemeente() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Inschrijving(null, 20120101, "O", 19770202, GEM_CODE, 0, 1, 20120101123014000L, true),
                    null,
                    Lo3Historie.NULL_HISTORIE,
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder1Stapel(null);
        builder.inschrijvingStapel(stapel);
        builder.verblijfplaatsStapel(buildRNIVerblijfplaatsStapel());
        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(0);
    }

    @Test
    public void testPre065MetMinistrieelEnRNIGemeente() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Inschrijving(null, 20120101, "M", 19770202, GEM_CODE, 0, 1, 20120101123014000L, true),
                    null,
                    Lo3Historie.NULL_HISTORIE,
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder1Stapel(null);
        builder.inschrijvingStapel(stapel);
        builder.verblijfplaatsStapel(buildRNIVerblijfplaatsStapel());
        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE065, 1);
    }

    @Test
    public void testPre065MetRNIZonderRNIGemeente() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Inschrijving(null, 20120101, "R", 19770202, GEM_CODE, 0, 1, 20120101123014000L, true),
                    null,
                    Lo3Historie.NULL_HISTORIE,
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder1Stapel(null);
        builder.inschrijvingStapel(stapel);

        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE065, 1);
    }

    @Test
    public void testPre065ZonderRNI() {
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder1Stapel(null);

        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE065, 1);
    }

    @Test
    public void testPre065RNIGemeente() {
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder1Stapel(null);
        builder.verblijfplaatsStapel(buildRNIVerblijfplaatsStapel());

        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE065, 1);
    }

    @Test
    public void testPre066MetRNIEnRNIGemeente() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Inschrijving(null, 20120101, "R", 19770202, GEM_CODE, 0, 1, 20120101123014000L, true),
                    null,
                    Lo3Historie.NULL_HISTORIE,
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder2Stapel(null);
        builder.inschrijvingStapel(stapel);
        builder.verblijfplaatsStapel(buildRNIVerblijfplaatsStapel());
        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(0);
    }

    @Test
    public void testPre066MetFoutEnRNIGemeente() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Inschrijving(null, 20120101, "F", 19770202, GEM_CODE, 0, 1, 20120101123014000L, true),
                    null,
                    Lo3Historie.NULL_HISTORIE,
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder2Stapel(null);
        builder.inschrijvingStapel(stapel);
        builder.verblijfplaatsStapel(buildRNIVerblijfplaatsStapel());
        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(0);
    }

    @Test
    public void testPre066MetOverledenEnRNIGemeente() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Inschrijving(null, 20120101, "O", 19770202, GEM_CODE, 0, 1, 20120101123014000L, true),
                    null,
                    Lo3Historie.NULL_HISTORIE,
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder2Stapel(null);
        builder.inschrijvingStapel(stapel);
        builder.verblijfplaatsStapel(buildRNIVerblijfplaatsStapel());
        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(0);
    }

    @Test
    public void testPre066MetMinistrieelEnRNIGemeente() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Inschrijving(null, 20120101, "M", 19770202, GEM_CODE, 0, 1, 20120101123014000L, true),
                    null,
                    Lo3Historie.NULL_HISTORIE,
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder2Stapel(null);
        builder.inschrijvingStapel(stapel);
        builder.verblijfplaatsStapel(buildRNIVerblijfplaatsStapel());
        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE066, 1);
    }

    @Test
    public void testPre066MetRNIZonderRNIGemeente() {
        final Lo3Stapel<Lo3InschrijvingInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Inschrijving(null, 20120101, "R", 19770202, GEM_CODE, 0, 1, 20120101123014000L, true),
                    null,
                    Lo3Historie.NULL_HISTORIE,
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder2Stapel(null);
        builder.inschrijvingStapel(stapel);

        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE066, 1);
    }

    @Test
    public void testPre066ZonderRNI() {
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder2Stapel(null);

        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE066, 1);
    }

    @Test
    public void testPre066RNIGemeente() {
        final Lo3PersoonslijstBuilder builder = builder();
        builder.ouder2Stapel(null);
        builder.verblijfplaatsStapel(buildRNIVerblijfplaatsStapel());

        precondities.controleerPersoonslijst(builder.build());
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE066, 1);
    }

    public Lo3PersoonslijstBuilder builder() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(buildPersoonstapel());
        builder.ouder1Stapel(buildOuder1Stapel());
        builder.ouder2Stapel(buildOuder2Stapel());
        builder.inschrijvingStapel(buildInschrijvingStapel());
        builder.verblijfplaatsStapel(buildVerblijfplaatsStapel());

        return builder;
    }

    // @formatter:off - Geen formatting voor builders.

    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonstapel() {
        return buildPersoonstapel("6030");
    }

    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonstapel(final String landcode) {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(2450924321L,
                                                                                           "Scarlet",
                                                                                           "Simpspoon",
                                                                                           19770101,
                                                                                           GEM_CODE,
                                                                                           landcode,
                                                                                           "V"),
                                                                GEBOORTE_AKTE,
                                                                GEBOORTE_HISTORIE,
                                                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
    }

    private Lo3Stapel<Lo3OuderInhoud> buildOuder1Stapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Ouder(3450924321L,
                                                                                         "Jessica",
                                                                                         "Geller",
                                                                                         19170101,
                                                                                         GEM_CODE,
                                                                                         "6030",
                                                                                         "V",
                                                                                         19770101),
                                                                GEBOORTE_AKTE,
                                                                GEBOORTE_HISTORIE,
                                                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0)));
    }

    private Lo3Stapel<Lo3OuderInhoud> buildOuder2Stapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Ouder(3450924321L,
                                                                                         "Jaap",
                                                                                         "Jansen",
                                                                                         19160101,
                                                                                         GEM_CODE,
                                                                                         "6030",
                                                                                         "M",
                                                                                         19770101),
                                                                GEBOORTE_AKTE,
                                                                GEBOORTE_HISTORIE,
                                                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0)));
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijvingStapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Inschrijving(null,
                                                                                                null,
                                                                                                null,
                                                                                                19770202,
                                                                                                GEM_CODE,
                                                                                                0,
                                                                                                1,
                                                                                                19770102123014000L,
                                                                                                true),
                                                                null,
                                                                Lo3Historie.NULL_HISTORIE,
                                                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> buildVerblijfplaatsStapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats(GEM_CODE,
                                                                                                  19770101,
                                                                                                  19770101,
                                                                                                  "Straat",
                                                                                                  14,
                                                                                                  "9654AA",
                                                                                                  "O"),
                                                                null,
                                                                GEBOORTE_HISTORIE,
                                                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0)));
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> buildRNIVerblijfplaatsStapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("1999",
                                                                                                  19770101,
                                                                                                  19770101,
                                                                                                  "Straat",
                                                                                                  14,
                                                                                                  "9654AA",
                                                                                                  "O"),
                                                                null,
                                                                GEBOORTE_HISTORIE,
                                                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0)));
    }
}
