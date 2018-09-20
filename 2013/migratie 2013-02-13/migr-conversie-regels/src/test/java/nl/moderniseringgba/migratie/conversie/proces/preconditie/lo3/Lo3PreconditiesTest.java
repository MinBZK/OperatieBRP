/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;
import nl.moderniseringgba.migratie.conversie.proces.AbstractLoggingTest;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Lo3PreconditiesTest extends AbstractLoggingTest {

    private static final Lo3Herkomst HERKOMST = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
    private static final Lo3Datum DATUM_NA_20071126 = new Lo3Datum(20071127);
    private static final Lo3Datum DATUM_VOOR_20071126 = new Lo3Datum(20071125);
    private static final Lo3PreconditiesTester TESTER = new Lo3PreconditiesTester();

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
        TESTER.controleerGroep01Identificatienummers(null, null, DATUM_NA_20071126, HERKOMST, false);
        assertStructuurRegelANummerAanwezig(Logging.getLogging());
        assertStructuurRegelBSNAanwezig(Logging.getLogging());
    }

    @Test
    public void testControleerGroep01IdentificatienummersCat1() {
        TESTER.controleerGroep01Identificatienummers(null, null, DATUM_NA_20071126, HERKOMST, true);
        assertPreconditie005Gelogd(Logging.getLogging());
        assertStructuurRegelBSNAanwezig(Logging.getLogging());
    }

    @Test
    public void testControleerGroep01IdentificatienummersIncorrectAnummer() {
        TESTER.controleerGroep01Identificatienummers(1L, null, DATUM_NA_20071126, HERKOMST, true);
        assertStructuurRegelBSNAanwezig(Logging.getLogging());
        assertStructuurRegelANummerInCorrect(Logging.getLogging());
    }

    @Test
    public void testControleerGroep01IdentificatienummersInCorrectBsn() {
        TESTER.controleerGroep01Identificatienummers(1L, 1L, DATUM_NA_20071126, HERKOMST, true);
        assertStructuurRegelANummerInCorrect(Logging.getLogging());
        assertStructuurRegelBSNInCorrect(Logging.getLogging());
    }

    @Test
    public void testControleerGroep01IdentificatienummersAfwezigBsnNietGelogd() {
        TESTER.controleerGroep01Identificatienummers(null, null, DATUM_VOOR_20071126, HERKOMST, true);
        assertPreconditie005Gelogd(Logging.getLogging());
        assertStructuurRegelBSNAfwezig(Logging.getLogging());
    }

    @Test
    public void testControleerGroep01IdentificatienummersAfwezigBsnNietGelogd2() {
        TESTER.controleerGroep01Identificatienummers(null, null, null, HERKOMST, true);
        assertPreconditie005Gelogd(Logging.getLogging());
        assertStructuurRegelBSNAfwezig(Logging.getLogging());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBijzondereSituatieLB022() {
        final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder();

        final Lo3Categorie<Lo3PersoonInhoud> cat51 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His("S", 20120101, 20120101),
                        Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        final Lo3Categorie<Lo3PersoonInhoud> cat01 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20120102), Lo3StapelHelper.lo3Akt(2),
                        HERKOMST);

        final Lo3Stapel<Lo3PersoonInhoud> stapel = Lo3StapelHelper.lo3Stapel(cat51, cat01);
        TESTER.controleerOnjuist(stapel);

        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB022);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBijzondereSituatieGeenLB022() {
        final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder();

        final Lo3Categorie<Lo3PersoonInhoud> cat51 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His("O", 20120101, 20120101),
                        Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        final Lo3Categorie<Lo3PersoonInhoud> cat01 =
                Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20120102), Lo3StapelHelper.lo3Akt(2),
                        HERKOMST);

        final Lo3Stapel<Lo3PersoonInhoud> stapel = Lo3StapelHelper.lo3Stapel(cat51, cat01);
        TESTER.controleerOnjuist(stapel);

        assertAantalInfos(0);
    }

    private void assertPreconditie005Gelogd(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if (Precondities.PRE005.name().equals(logRegel.getCode())) {
                assertEquals(LogType.PRECONDITIE, logRegel.getType());
                assertEquals(LogSeverity.ERROR, logRegel.getSeverity());
                return;
            }
        }
        fail("Preconditie 005 is niet gelogd.");
    }

    private void assertStructuurRegelANummerAanwezig(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if ("Element 01.10: A-nummer is verplicht in groep 01: Identificatienummers.".equals(logRegel
                    .getOmschrijving())) {
                assertEquals(LogType.STRUCTUUR, logRegel.getType());
                assertEquals(LogSeverity.INFO, logRegel.getSeverity());
                return;
            }
        }
        fail("Structuurregel in geval van afwezig anummer niet gelogd.");
    }

    private void assertStructuurRegelANummerInCorrect(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if ("Element 01.10: A-nummer voldoet niet aan de inhoudelijke voorwaarden.".equals(logRegel
                    .getOmschrijving())) {
                assertEquals(LogType.STRUCTUUR, logRegel.getType());
                assertEquals(LogSeverity.WARNING, logRegel.getSeverity());
                return;
            }
        }
        fail("Structuurregel in geval van incorrect anummer niet gelogd.");
    }

    private void assertStructuurRegelBSNAanwezig(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if (("Element 01.20: Burgerservicenummer is verplicht (indien de ingangsdatum "
                    + "geldigheid op of na 26-11-2007 ligt) in groep 01: Identificatienummers.").equals(logRegel
                    .getOmschrijving())) {
                assertEquals(LogType.STRUCTUUR, logRegel.getType());
                assertEquals(LogSeverity.INFO, logRegel.getSeverity());
                return;
            }
        }
        fail("Structuurregel in geval van afwezig bsn niet gelogd.");
    }

    private void assertStructuurRegelBSNAfwezig(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if (("Element 01.20: Burgerservicenummer is verplicht (indien de ingangsdatum "
                    + "geldigheid op of na 26-11-2007 ligt) in groep 01: Identificatienummers.").equals(logRegel
                    .getOmschrijving())) {
                fail("Structuurregel in geval van afwezig bsn is gelogd.");
            }
        }
    }

    private void assertStructuurRegelBSNInCorrect(final Logging logging) {
        for (final LogRegel logRegel : logging.getRegels()) {
            if ("Element 01.20: Burgerservicenummer voldoet niet aan de inhoudelijke voorwaarden.".equals(logRegel
                    .getOmschrijving())) {
                assertEquals(LogType.STRUCTUUR, logRegel.getType());
                assertEquals(LogSeverity.WARNING, logRegel.getSeverity());
                return;
            }
        }
        fail("Structuurregel in geval van incorrect bsn niet gelogd.");
    }

    private static final class Lo3PreconditiesTester extends Lo3Precondities {
    }
}
