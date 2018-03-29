/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Lo3PreconditiesTest extends AbstractLoggingTest {

    private static final Lo3Herkomst HERKOMST = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
    private static final Lo3Datum DATUM_NA_20071126 = new Lo3Datum(20071127);
    private static final Lo3Datum DATUM_VOOR_20071126 = new Lo3Datum(20071125);

    private Lo3PreconditiesTester tester = new Lo3PreconditiesTester(new ConversietabelFactoryImpl());

    @Before
    public void setup() {
        Logging.initContext();
    }

    @After
    public void teardown() {
        Logging.destroyContext();
    }

    @Test
    public void testControleerGroep01Identificatienummers() {
        tester.controleerGroep01Identificatienummers(null, null, DATUM_NA_20071126, HERKOMST, false);
        assertStructuurRegelANummerAanwezig(Logging.getLogging());
        assertStructuurRegelBSNAanwezig(Logging.getLogging());
    }

    @Test
    public void testControleerGroep01IdentificatienummersCat1() {
        tester.controleerGroep01Identificatienummers(null, null, DATUM_NA_20071126, HERKOMST, true);
        assertPreconditie005Gelogd(Logging.getLogging());
        assertStructuurRegelBSNAanwezig(Logging.getLogging());
    }

    @Test
    public void testControleerGroep01IdentificatienummersIncorrectAnummer() {
        tester.controleerGroep01Identificatienummers(Lo3String.wrap("1"), null, DATUM_NA_20071126, HERKOMST, true);
        assertStructuurRegelBSNAanwezig(Logging.getLogging());
        assertStructuurRegelANummerInCorrect(Logging.getLogging());
    }

    @Test
    public void testControleerGroep01IdentificatienummersInCorrectBsn() {
        tester.controleerGroep01Identificatienummers(Lo3String.wrap("1"), Lo3String.wrap("1"), DATUM_NA_20071126, HERKOMST, true);
        assertStructuurRegelANummerInCorrect(Logging.getLogging());
        assertStructuurRegelBSNInCorrect(Logging.getLogging());
    }

    @Test
    public void testControleerGroep01IdentificatienummersAfwezigBsnNietGelogd() {
        tester.controleerGroep01Identificatienummers(null, null, DATUM_VOOR_20071126, HERKOMST, true);
        assertPreconditie005Gelogd(Logging.getLogging());
        assertStructuurRegelBSNAfwezig(Logging.getLogging());
    }

    @Test
    public void testControleerGroep01IdentificatienummersAfwezigBsnNietGelogd2() {
        tester.controleerGroep01Identificatienummers(null, null, null, HERKOMST, true);
        assertPreconditie005Gelogd(Logging.getLogging());
        assertStructuurRegelBSNAfwezig(Logging.getLogging());
    }

    @Test
    public void testControleerGroep81AkteCompleet() {
        final Lo3Documentatie documentatie = Lo3StapelHelper.lo3Akt(1L, "z514", "1");
        tester.controleerGroep81Akte(documentatie, HERKOMST);
        for (final LogRegel logRegel : Logging.getLogging().getRegels()) {
            if (SoortMeldingCode.PRE071.name().equals(logRegel.getSoortMeldingCode().name())) {
                fail("De groep is compleet, de preconditie zou niet moeten optreden.");
            }
        }
    }

    @Test
    public void testControleerGroep81AkteNietCompleet() {
        final Lo3Documentatie documentatie = Lo3StapelHelper.lo3Akt(1L, null, null);
        tester.controleerGroep81Akte(documentatie, HERKOMST);
        assertPreconditie071Gelogd(Logging.getLogging());
        assertAantalErrors(1);
    }

    @Test
    public void testControleerGroep81AkteNummerTests() {
        final String gemeenteCode = "0014";
        Lo3Documentatie documentatie = Lo3StapelHelper.lo3Akt(1L, gemeenteCode, "1");
        tester.controleerGroep81Akte(documentatie, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 0);

        documentatie = Lo3StapelHelper.lo3Akt(1L, gemeenteCode, "2");
        tester.controleerGroep81Akte(documentatie, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 0);

        documentatie = Lo3StapelHelper.lo3Akt(1L, gemeenteCode, "3");
        tester.controleerGroep81Akte(documentatie, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 0);

        documentatie = Lo3StapelHelper.lo3Akt(1L, gemeenteCode, "5");
        tester.controleerGroep81Akte(documentatie, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 0);

        documentatie = Lo3StapelHelper.lo3Akt(1L, gemeenteCode, ".");
        tester.controleerGroep81Akte(documentatie, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 0);

        documentatie = Lo3StapelHelper.lo3Akt(1L, gemeenteCode, "");
        tester.controleerGroep81Akte(documentatie, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 0);
    }

    @Test
    public void testControleerGroep81AkteNummerTestsFout1() {
        final String gemeenteCode = "0014";
        final Lo3Documentatie documentatie = Lo3StapelHelper.lo3Akt(1L, gemeenteCode, "4");
        tester.controleerGroep81Akte(documentatie, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testControleerGroep81AkteNummerTestsFout2() {
        final String gemeenteCode = "0014";
        final Lo3Documentatie documentatie = Lo3StapelHelper.lo3Akt(1L, gemeenteCode, "6");
        tester.controleerGroep81Akte(documentatie, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testControleerGroep82DocumentNietCompleet() {
        tester.controleerGroep82Document(null, null, null, HERKOMST);
        assertPreconditie070Gelogd(Logging.getLogging());
        assertAantalErrors(1);
    }

    @Test
    public void testControleerGroep82DocumentCompleet() {
        tester.controleerGroep82Document(new Lo3GemeenteCode("Z514"), DATUM_NA_20071126, Lo3String.wrap("beschrijving"), HERKOMST);
        for (final LogRegel logRegel : Logging.getLogging().getRegels()) {
            if (SoortMeldingCode.PRE070.name().equals(logRegel.getSoortMeldingCode().name())) {
                fail("De groep is compleet, de preconditie zou niet moeten optreden.");
            }
        }
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE099)
    public void testControleerGroep83Procedure8310NietGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(null, new Lo3Datum(20140101), null);
        tester.controleerGroep83Procedure(lo3Onderzoek, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE099)
    public void testControleerGroep83Procedure8320NietGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10110), null, null);
        tester.controleerGroep83Procedure(lo3Onderzoek, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE099)
    public void testControleerGroep83ProcedureGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10110), new Lo3Datum(20140101), null);
        tester.controleerGroep83Procedure(lo3Onderzoek, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 0);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE098)
    public void testControleerGroep88RNIDeelnemerNull() {
        tester.controleerGroep88RNIDeelnemer(null, HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE098)
    public void testControleerGroep88RNIDeelnemer() {
        tester.controleerGroep88RNIDeelnemer(new Lo3RNIDeelnemerCode("0000"), HERKOMST);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB022)
    public void testBijzondereSituatieLB022() {
        final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder();

        final Lo3Categorie<Lo3PersoonInhoud> cat51 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His("S", 20120101, 20120101), new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_51,
                        0,
                        1));
        final Lo3Categorie<Lo3PersoonInhoud> cat01 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(2), Lo3StapelHelper.lo3His(20120102), HERKOMST);

        final Lo3Stapel<Lo3PersoonInhoud> stapel = Lo3StapelHelper.lo3Stapel(cat51, cat01);
        tester.controleerOnjuist(stapel);

        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB022, 1);
    }

    @Test
    public void testBijzondereSituatieGeenLB022() {
        final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder();

        final Lo3Categorie<Lo3PersoonInhoud> cat51 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His("O", 20120101, 20120101), new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_51,
                        0,
                        1));
        final Lo3Categorie<Lo3PersoonInhoud> cat01 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(2), Lo3StapelHelper.lo3His(20120102), HERKOMST);

        final Lo3Stapel<Lo3PersoonInhoud> stapel = Lo3StapelHelper.lo3Stapel(cat51, cat01);
        tester.controleerOnjuist(stapel);

        assertGeenLogRegels();
    }

    @Test
    public void testPre076IndicatieOnjuist() {
        final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder();

        final Lo3Categorie<Lo3PersoonInhoud> cat51 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His("O", 20120101, 20120101), new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_51,
                        0,
                        1));
        final Lo3Categorie<Lo3PersoonInhoud> cat01 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(2), Lo3StapelHelper.lo3His(20120102), HERKOMST);

        final Lo3Stapel<Lo3PersoonInhoud> stapel = Lo3StapelHelper.lo3Stapel(cat51, cat01);
        tester.controleerOnjuist(stapel);

        assertGeenLogRegels();
    }

    @Test
    public void testPre076IndicatieFoutieveWaarde() {
        final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder();

        final Lo3Categorie<Lo3PersoonInhoud> cat51 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His("X", 20120101, 20120101), new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_51,
                        0,
                        1));
        final Lo3Categorie<Lo3PersoonInhoud> cat01 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(2), Lo3StapelHelper.lo3His(20120102), HERKOMST);

        final Lo3Stapel<Lo3PersoonInhoud> stapel = Lo3StapelHelper.lo3Stapel(cat51, cat01);
        tester.controleerOnjuist(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE076, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB024)
    public void testControleerGeldigheidDatumActueelOk() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel = maakStapelMetGeldigheid(20120103, 20120102);
        tester.controleerGeldigheidDatumActueel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB024, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB024)
    public void testControleerGeldigheidDatumActueelFout() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel = maakStapelMetGeldigheid(20120102, 20120103);
        tester.controleerGeldigheidDatumActueel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB024, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB024)
    public void testControleerGeldigheidDatumActueelOkActueelOnbekend() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel = maakStapelMetGeldigheid(20120100, 20120103);
        tester.controleerGeldigheidDatumActueel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB024, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB024)
    public void testControleerGeldigheidDatumActueelOkHistorieOnbekend() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel = maakStapelMetGeldigheid(20120102, 20120100);
        tester.controleerGeldigheidDatumActueel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB024, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB024)
    public void testControleerGeldigheidDatumActueelOkBeidenOnbekend() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel = maakStapelMetGeldigheid(20120100, 20120100);
        tester.controleerGeldigheidDatumActueel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB024, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB024)
    public void testControleerGeldigheidDatumActueelFoutBeidenOnbekend() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel = maakStapelMetGeldigheid(20120200, 20120300);
        tester.controleerGeldigheidDatumActueel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB024, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB024)
    public void testControleerGeldigheidDatumActueelOkActueelStandaardwaarde() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel = maakStapelMetGeldigheid(0, 20120103);
        tester.controleerGeldigheidDatumActueel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB024, 0);
    }

    private Lo3Stapel<Lo3PersoonInhoud> maakStapelMetGeldigheid(final int actueelIngang, final int historieIngang) {
        final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder();
        final Lo3Categorie<Lo3PersoonInhoud> cat01 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(2), Lo3StapelHelper.lo3His(actueelIngang), HERKOMST);
        final Lo3Categorie<Lo3PersoonInhoud> cat51 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(historieIngang), new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_51,
                        0,
                        1));

        return Lo3StapelHelper.lo3Stapel(cat51, cat01);
    }

    private void assertPreconditie005Gelogd(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if (SoortMeldingCode.PRE005.name().equals(logRegel.getSoortMeldingCode().name())) {
                assertTrue(logRegel.getSoortMeldingCode().isPreconditie());
                assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
                return;
            }
        }
        fail("Preconditie 005 is niet gelogd.");
    }

    private void assertPreconditie070Gelogd(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if (SoortMeldingCode.PRE070.name().equals(logRegel.getSoortMeldingCode().name())) {
                assertTrue(logRegel.getSoortMeldingCode().isPreconditie());
                assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
                return;
            }
        }
        fail("Preconditie 070 is niet gelogd.");
    }

    private void assertPreconditie071Gelogd(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if (SoortMeldingCode.PRE071.name().equals(logRegel.getSoortMeldingCode().name())) {
                assertTrue(logRegel.getSoortMeldingCode().isPreconditie());
                assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
                return;
            }
        }
        fail("Preconditie 071 is niet gelogd.");
    }

    private void assertStructuurRegelANummerAanwezig(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if (Lo3ElementEnum.ELEMENT_0110.equals(logRegel.getLo3ElementNummer())
                    && SoortMeldingCode.STRUC_IDENTIFICATIE.equals(logRegel.getSoortMeldingCode())) {
                assertTrue(logRegel.getSoortMeldingCode().isStructuurFout());
                assertEquals(LogSeverity.INFO, logRegel.getSeverity());
                return;
            }
        }
        fail("Structuurregel in geval van afwezig anummer niet gelogd.");
    }

    private void assertStructuurRegelANummerInCorrect(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if (Lo3ElementEnum.ELEMENT_0110.equals(logRegel.getLo3ElementNummer())
                    && SoortMeldingCode.STRUC_IDENTIFICATIE.equals(logRegel.getSoortMeldingCode())) {
                assertTrue(logRegel.getSoortMeldingCode().isStructuurFout());
                assertEquals(LogSeverity.WARNING, logRegel.getSeverity());
                return;
            }
        }
        fail("Structuurregel in geval van incorrect anummer niet gelogd.");
    }

    private void assertStructuurRegelBSNAanwezig(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if (Lo3ElementEnum.ELEMENT_0120.equals(logRegel.getLo3ElementNummer())
                    && SoortMeldingCode.STRUC_IDENTIFICATIE.equals(logRegel.getSoortMeldingCode())) {
                assertTrue(logRegel.getSoortMeldingCode().isStructuurFout());
                assertEquals(LogSeverity.INFO, logRegel.getSeverity());
                return;
            }
        }
        fail("Structuurregel in geval van afwezig bsn niet gelogd.");
    }

    private void assertStructuurRegelBSNAfwezig(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if (Lo3ElementEnum.ELEMENT_0120.equals(logRegel.getLo3ElementNummer())
                    && SoortMeldingCode.STRUC_IDENTIFICATIE.equals(logRegel.getSoortMeldingCode())) {
                fail("Structuurregel in geval van afwezig bsn is gelogd.");
            }
        }
    }

    private void assertStructuurRegelBSNInCorrect(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if (Lo3ElementEnum.ELEMENT_0120.equals(logRegel.getLo3ElementNummer())
                    && SoortMeldingCode.STRUC_IDENTIFICATIE.equals(logRegel.getSoortMeldingCode())) {
                assertTrue(logRegel.getSoortMeldingCode().isStructuurFout());
                assertEquals(LogSeverity.WARNING, logRegel.getSeverity());
                return;
            }
        }
        fail("Structuurregel in geval van incorrect bsn niet gelogd.");
    }
}
