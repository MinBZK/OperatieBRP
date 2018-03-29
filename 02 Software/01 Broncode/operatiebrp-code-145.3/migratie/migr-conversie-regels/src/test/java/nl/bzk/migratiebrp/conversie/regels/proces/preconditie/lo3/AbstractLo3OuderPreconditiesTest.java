/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

/**
 * Preconditie tests voor algemene code ouder1/ouder2.
 */
public abstract class AbstractLo3OuderPreconditiesTest extends AbstractPreconditieTest {

    private static final String GEM_CODE = "1904";
    private static final Lo3String PERSOON_ANUMMER = new Lo3String("1234567890");

    /**
     * Geef de waarde van herkomst.
     * @return herkomst
     */
    abstract Lo3Herkomst getHerkomst();

    abstract Lo3Herkomst getHerkomst(int voorkomen);

    /**
     * Geef de waarde van precondities.
     * @return precondities
     */
    abstract Lo3OuderPrecondities getPrecondities();

    Lo3OuderInhoud.Builder builder() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        builder.anummer(Lo3String.wrap("1069532945"));
        builder.burgerservicenummer(Lo3String.wrap("179543489"));

        builder.voornamen(Lo3String.wrap("Jaap"));
        builder.adellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("P"));
        builder.voorvoegselGeslachtsnaam(Lo3String.wrap("van"));
        builder.geslachtsnaam(Lo3String.wrap("Joppen"));

        builder.geboortedatum(new Lo3Datum(19940104));
        builder.geboorteGemeenteCode(new Lo3GemeenteCode("0514"));
        builder.geboorteLandCode(new Lo3LandCode("6030"));

        builder.geslachtsaanduiding(new Lo3Geslachtsaanduiding("M"));

        builder.familierechtelijkeBetrekking(new Lo3Datum(20010101));

        return builder;
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB013)
    public void testBijzondereSituatieOnbekendeOuder() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        builder.geslachtsnaam(Lo3String.wrap("."));
        builder.familierechtelijkeBetrekking(new Lo3Datum(20010101));
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        getHerkomst()));

        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);

        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB013, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB013)
    @Preconditie(SoortMeldingCode.PRE030)
    public void testBijzondereSituatieOnbekendeOuderGeen8510() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        builder.geslachtsnaam(Lo3String.wrap("."));
        builder.familierechtelijkeBetrekking(new Lo3Datum(20010101));
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, null, 20120101),
                        getHerkomst()));

        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB013, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB013)
    @Preconditie(SoortMeldingCode.PRE030)
    public void testBijzondereSituatieOnbekendeOuderGeen8510GeenAkteWelDocument() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        builder.geslachtsnaam(Lo3String.wrap("."));
        builder.familierechtelijkeBetrekking(new Lo3Datum(20010101));
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Doc(1L, "0518", 20120101, "inhoud"),
                        Lo3StapelHelper.lo3His(null, null, 20120101),
                        getHerkomst()));

        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB013, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB014)
    public void testBijzondereSituatieJuridischGeenOuder() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        getHerkomst()));

        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);

        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB014, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB014)
    public void testBijzondereSituatieJuridischGeenOuderGeen8510() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, null, 20120101),
                        getHerkomst()));

        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB014, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB014)
    public void testBijzondereSituatieJuridischGeenOuderGeen8510GeenAkteWelDocument() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Doc(1L, "0518", 20120101, "inhoud"),
                        Lo3StapelHelper.lo3His(null, null, 20120101),
                        getHerkomst()));

        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB014, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB021)
    public void testBijzondereSituatieZwakkeAdoptieGeenDocumentatieAanwezig() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null, GEM_CODE, 20120101, null),
                        Lo3StapelHelper.lo3His(20120101),
                        getHerkomst()));
        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);
        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB021, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB021)
    public void testBijzondereSituatieZwakkeAdoptie() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null, GEM_CODE, 20120101, "akte (zwak) Haags adoptieverdrag"),
                        Lo3StapelHelper.lo3His(20120101),
                        getHerkomst()));
        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB021, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB021)
    public void testBijzondereSituatieZwakkeAdoptie2() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null, GEM_CODE, 20120101, "akte (zwak) Wet conflictenrecht adoptie"),
                        Lo3StapelHelper.lo3His(20120101),
                        getHerkomst()));
        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB021, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB021)
    public void testBijzondereSituatieGeenZwakkeAdoptie() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null, GEM_CODE, 20120101, "Omschrijving van document"),
                        Lo3StapelHelper.lo3His(20120101),
                        getHerkomst()));
        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);
        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB021, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB021)
    public void testBijzondereSituatieGeenZwakkeAdoptieAkte() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20120101),
                        getHerkomst()));
        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);
        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB021, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB021)
    public void testBijzondereSituatieZwakkeAdoptieHoofdLetters() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null, GEM_CODE, 20120101, "AKTE (ZWAK) WET CONFLICTENRECHT ADOPTIE"),
                        Lo3StapelHelper.lo3His(20120101),
                        getHerkomst()));
        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB021, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB021)
    public void testBijzondereSituatieZwakkeAdoptieKleineLetters() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null, GEM_CODE, 20120101, "akte (zwak) wet conflictenrecht adoptie"),
                        Lo3StapelHelper.lo3His(20120101),
                        getHerkomst()));
        getPrecondities().controleerStapel(stapel, PERSOON_ANUMMER);
        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB021, 1);
    }
}
