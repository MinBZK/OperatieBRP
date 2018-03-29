/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Melding;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3Severity;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3SoortMelding;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomen;

@RunWith(MockitoJUnitRunner.class)
public class LogRegelConverterTest {

    private static final Lo3Bericht DUMMY_BERICHT = new Lo3Bericht(
            "DUMMY",
            Lo3BerichtenBron.INITIELE_VULLING,
            new Timestamp(System.currentTimeMillis()),
            "DUMMY_DATA",
            true);

    private final LogRegelConverter logRegelConverter = new LogRegelConverter();

    @Test
    public void converteerDBNaarGgoFoutRegelList() {
        // Maak wat log regels
        final Set<Lo3Voorkomen> dbLogRegelSetIN = new LinkedHashSet<>();
        dbLogRegelSetIN.add(createDBLogRegel("01", 0, 0, Lo3Severity.INFO, Lo3SoortMelding.PRE001));
        dbLogRegelSetIN.add(createDBLogRegel("09", 1, 1, Lo3Severity.ERROR, Lo3SoortMelding.PRE002));

        // converteer
        final List<GgoFoutRegel> ggoLogRegelOUT = logRegelConverter.converteerDBNaarGgoFoutRegelList(dbLogRegelSetIN);

        // test of alle waardes gelijk zijn
        assertInOutSame(dbLogRegelSetIN, ggoLogRegelOUT);
    }

    @Test
    public void converteerDBNaarGgoFoutRegelListEmptyHerkomst() {
        // Maak wat log regels
        final Set<Lo3Voorkomen> dbLogRegelSetIN = new LinkedHashSet<>();
        dbLogRegelSetIN.add(createDBLogRegel("01", 0, 0, Lo3Severity.INFO, Lo3SoortMelding.PRE001));
        dbLogRegelSetIN.add(createDBLogRegel("01", 0, 0, Lo3Severity.INFO, Lo3SoortMelding.PRE001));

        // converteer
        final List<GgoFoutRegel> ggoLogRegelOUT = logRegelConverter.converteerDBNaarGgoFoutRegelList(dbLogRegelSetIN);

        // test of alle waardes gelijk zijn
        assertInOutSame(dbLogRegelSetIN, ggoLogRegelOUT);
    }

    @Test
    public void converteerModelNaarGgoFoutRegelList() {
        // Maak wat log regels
        final Set<LogRegel> modelLogRegelSetIN = new LinkedHashSet<>();
        modelLogRegelSetIN.add(createModelLogRegel(1, 0, 0, LogSeverity.INFO, SoortMeldingCode.PRE001));
        modelLogRegelSetIN.add(createModelLogRegel(9, 1, 1, LogSeverity.ERROR, SoortMeldingCode.PRE002));

        // converteer
        final List<GgoFoutRegel> ggoLogRegelOUT = logRegelConverter.converteerModelNaarGgoFoutRegelList(modelLogRegelSetIN);

        // test of alle waardes gelijk zijn
        assertInOutSame(modelLogRegelSetIN, ggoLogRegelOUT);
    }

    private Lo3Voorkomen createDBLogRegel(
            final String catNr,
            final Integer stapelNr,
            final Integer volgNr,
            final Lo3Severity severity,
            final Lo3SoortMelding code) {
        final Lo3Voorkomen voorkomen = new Lo3Voorkomen(DUMMY_BERICHT, catNr);
        voorkomen.setStapelvolgnummer(stapelNr);
        voorkomen.setVoorkomenvolgnummer(volgNr);
        final Lo3Melding melding = new Lo3Melding(voorkomen, code, severity);
        voorkomen.addMelding(melding);
        return voorkomen;
    }

    private LogRegel createModelLogRegel(
            final Integer catNr,
            final Integer stapelNr,
            final Integer volgNr,
            final LogSeverity severity,
            final SoortMeldingCode code) {
        final Lo3Herkomst lo3Herkomst = new Lo3Herkomst(Lo3CategorieEnum.getLO3Categorie(catNr), stapelNr, volgNr);
        return new LogRegel(lo3Herkomst, severity, code, null);
    }

    private void assertInOutSame(final Set<? extends Object> logRegelSetIN, final List<GgoFoutRegel> ggoLogRegelOUT) {
        assertNotNull("Set mag niet null zijn", logRegelSetIN);
        assertNotNull("List mag niet null zijn", ggoLogRegelOUT);
        assertEquals("Size moet gelijk zijn", logRegelSetIN.size(), ggoLogRegelOUT.size());

        final Iterator<GgoFoutRegel> ggoLogRegelOutIter = ggoLogRegelOUT.iterator();
        GgoFoutRegel logRegelOut = ggoLogRegelOutIter.next();
        for (final Object logRegel : logRegelSetIN) {
            Integer catNr = null;
            Integer stapelNr = null;
            Integer volgNr = null;
            String severity = null;
            String type = "";
            String code = "";
            String omschrijving = "";
            if (logRegel instanceof Lo3Voorkomen) {
                final Lo3Voorkomen dbVoorkomen = (Lo3Voorkomen) logRegel;
                final Lo3Melding dbMelding = dbVoorkomen.getMeldingen().iterator().next();
                catNr = Integer.parseInt(dbVoorkomen.getCategorie());
                stapelNr = dbVoorkomen.getStapelvolgnummer();
                volgNr = dbVoorkomen.getVoorkomenvolgnummer();
                severity = dbMelding.getLogSeverity().toString();
                type = dbMelding.getSoortMelding().getCategorieMelding().toString();
                code = dbMelding.getSoortMelding().getCode();
                omschrijving = dbMelding.getSoortMelding().getOmschrijving();
            } else if (logRegel instanceof LogRegel) {
                final LogRegel modelLogRegel = (LogRegel) logRegel;
                catNr = modelLogRegel.getLo3Herkomst().getCategorie().getCategorieAsInt();
                stapelNr = modelLogRegel.getLo3Herkomst().getStapel();
                volgNr = modelLogRegel.getLo3Herkomst().getVoorkomen();
                severity = modelLogRegel.getSeverity().toString();
                final Lo3SoortMelding lo3SoortMelding = Lo3SoortMelding.valueOf(modelLogRegel.getSoortMeldingCode().toString());
                type = lo3SoortMelding.getCategorieMelding().toString();
                code = lo3SoortMelding.getCode();
                omschrijving = lo3SoortMelding.getOmschrijving();
            } else {
                fail("Onjuist type");
            }

            assertLo3Herkomst(logRegelOut.getHerkomst(), catNr, stapelNr, volgNr);

            assertEquals("Severity moet gelijk zijn", severity, logRegelOut.getSeverity().toString());
            assertEquals("Type moet gelijk zijn", type, logRegelOut.getType().toUpperCase());
            assertEquals("Omschrijving moet gelijk zijn", omschrijving, logRegelOut.getOmschrijving());
            assertEquals("Code moet gelijk zijn", code, logRegelOut.getCode());
            if (ggoLogRegelOutIter.hasNext()) {
                logRegelOut = ggoLogRegelOutIter.next();
            }
        }
    }

    private void assertLo3Herkomst(final GgoVoorkomen herkomstOut, final Integer catNr, final Integer stapelNr, final Integer volgNr) {
        if (catNr != null || stapelNr != null || volgNr != null) {
            assertEquals("Categorie moet gelijk zijn", catNr != null ? catNr : 0, herkomstOut.getCategorieNr());
            assertEquals("Stapel moet gelijk zijn", stapelNr != null ? stapelNr : 0, herkomstOut.getStapelNr());
            assertEquals("Voorkomen moet gelijk zijn", volgNr != null ? volgNr : 0, herkomstOut.getVoorkomenNr());
        } else {
            assertNull(herkomstOut);
        }
    }
}
