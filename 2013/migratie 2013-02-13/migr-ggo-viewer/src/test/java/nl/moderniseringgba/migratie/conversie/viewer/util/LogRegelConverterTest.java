/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LogRegelConverterTest {

    private final LogRegelConverter logRegelConverter = new LogRegelConverter();

    @Test
    public void converteerNaarModelLogRegelList() {
        // Maak wat log regels
        final Set<nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel> dbLogRegelSetIN =
                new LinkedHashSet<nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel>();
        dbLogRegelSetIN.add(createDBLogRegel(1, 0, 0, LogSeverity.INFO.getSeverity(), LogType.PRECONDITIE.name(),
                "Dit is de omschrijving van de preconditie", "PRE999"));
        dbLogRegelSetIN.add(createDBLogRegel(9, 1, 1, LogSeverity.ERROR.getSeverity(), LogType.STRUCTUUR.name(),
                "Dit is de omschrijving van de preconditie", "PRE998"));

        // converteer
        final List<nl.moderniseringgba.migratie.conversie.model.logging.LogRegel> modelLogRegelOUT =
                logRegelConverter.converteerNaarModelLogRegelList(dbLogRegelSetIN);

        // test of alle waardes gelijk zijn
        assertInOutSame(dbLogRegelSetIN, modelLogRegelOUT);
    }

    @Test
    public void converteerNaarModelLogRegelListEmptyHerkomst() {
        // Maak wat log regels
        final Set<nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel> dbLogRegelSetIN =
                new LinkedHashSet<nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel>();
        dbLogRegelSetIN.add(createDBLogRegel(1, 0, 0, LogSeverity.INFO.getSeverity(), LogType.PRECONDITIE.name(),
                "Dit is de omschrijving van de preconditie", "PRE999"));

        // converteer
        final List<nl.moderniseringgba.migratie.conversie.model.logging.LogRegel> modelLogRegelOUT =
                logRegelConverter.converteerNaarModelLogRegelList(dbLogRegelSetIN);

        // test of alle waardes gelijk zijn
        assertInOutSame(dbLogRegelSetIN, modelLogRegelOUT);
    }

    private nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel createDBLogRegel(
            final Integer catNr,
            final Integer stapelNr,
            final Integer volgNr,
            final Integer severity,
            final String type,
            final String omschrijving,
            final String code) {
        final nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel dbLogRegel =
                new nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel();
        dbLogRegel.setCategorie(catNr);
        dbLogRegel.setStapel(stapelNr);
        dbLogRegel.setVoorkomen(volgNr);
        dbLogRegel.setSeverity(severity);
        dbLogRegel.setType(type);
        dbLogRegel.setOmschrijving(omschrijving);
        dbLogRegel.setCode(code);
        return dbLogRegel;
    }

    private void assertInOutSame(
            final Set<nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel> dbLogRegelSetIN,
            final List<nl.moderniseringgba.migratie.conversie.model.logging.LogRegel> modelLogRegelOUT) {

        assertNotNull("Set mag niet null zijn", dbLogRegelSetIN);
        assertNotNull("List mag niet null zijn", modelLogRegelOUT);
        assertEquals("Size moet gelijk zijn", dbLogRegelSetIN.size(), modelLogRegelOUT.size());

        int index = 0;
        for (final nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel dbLogRegel : dbLogRegelSetIN) {
            assertLo3Herkomst(modelLogRegelOUT.get(index).getLo3Herkomst(), dbLogRegel);

            assertEquals("Severity moet gelijk zijn", dbLogRegel.getSeverity().intValue(), modelLogRegelOUT
                    .get(index).getSeverity().getSeverity());
            assertEquals("Type moet gelijk zijn", dbLogRegel.getType(), modelLogRegelOUT.get(index).getType().name());
            assertEquals("Omschrijving moet gelijk zijn", dbLogRegel.getOmschrijving(), modelLogRegelOUT.get(index)
                    .getOmschrijving());
            assertEquals("Code moet gelijk zijn", dbLogRegel.getCode(), modelLogRegelOUT.get(index).getCode());
            index++;
        }
    }

    private void assertLo3Herkomst(
            final Lo3Herkomst lo3Herkomst,
            final nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel dbLogRegel) {
        if (dbLogRegel.getCategorie() != null || dbLogRegel.getStapel() != null || dbLogRegel.getVoorkomen() != null) {
            assertEquals("Categorie moet gelijk zijn", dbLogRegel.getCategorie().intValue(), lo3Herkomst
                    .getCategorie().getCategorieAsInt());
            assertEquals("Stapel moet gelijk zijn", dbLogRegel.getStapel().intValue(), lo3Herkomst.getStapel());
            assertEquals("Voorkomen moet gelijk zijn", dbLogRegel.getVoorkomen().intValue(),
                    lo3Herkomst.getVoorkomen());
        } else {
            assertNull(lo3Herkomst);
        }
    }
}
