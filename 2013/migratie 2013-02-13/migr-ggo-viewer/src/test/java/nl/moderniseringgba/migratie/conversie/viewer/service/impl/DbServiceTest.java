/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;
import nl.moderniseringgba.migratie.conversie.viewer.util.LogRegelConverter;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {
    private static final Long A_NR = 8172387435L;
    private static final String LO3PL =
            "00697011640110010"
                    + A_NR
                    + "01200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200172012010100000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    @Mock
    private BrpDalService brpDalService;

    @Mock
    private LogRegelConverter logRegelConverter;

    @InjectMocks
    private DbServiceImpl dbService;

    @Test
    public void testZoekBerichtLog() {
        final BerichtLog berichtLog = createBerichtLog(LO3PL);
        Mockito.when(brpDalService.zoekBerichtLogOpAnummer(Matchers.anyLong())).thenReturn(berichtLog);
        final BerichtLog foundBerichtLog = dbService.zoekBerichtLog(A_NR);

        assertNotNull("BerichtLog mag niet null zijn", foundBerichtLog);
        assertEquals("Berichtinhoud moet gelijk zijn", LO3PL, foundBerichtLog.getBerichtData());
    }

    @Test
    public void testHaalLo3PersoonslijstUitBerichtLog() {
        final BerichtLog berichtLog = createBerichtLog(LO3PL);
        final Lo3Persoonslijst lo3Persoonslijst = dbService.haalLo3PersoonslijstUitBerichtLog(berichtLog);
        assertNotNull("Lo3Persoonslijst mag niet null zijn", lo3Persoonslijst);
        assertEquals("A-Nummer moet gelijk zijn", A_NR, lo3Persoonslijst.getActueelAdministratienummer());
    }

    @Test
    public void testHaalLogRegelsUitBerichtLog() {
        final BerichtLog berichtLog = createBerichtLog(LO3PL);
        final List<nl.moderniseringgba.migratie.conversie.model.logging.LogRegel> logRegelList =
                dbService.haalLogRegelsUitBerichtLog(berichtLog);
        assertNotNull("LogRegel list mag niet null zijn", logRegelList);
        assertEquals("Size moet 1 zijn", 1, logRegelList.size());
    }

    @Test
    public void testZoekBrpPersoonsLijst() {
        Mockito.when(brpDalService.zoekPersoonOpAnummer(Matchers.anyLong())).thenReturn(createBrpPersoonslijst());
        final BrpPersoonslijst brpPersoonslijst = dbService.zoekBrpPersoonsLijst(A_NR);
        assertNotNull("BrpPersoonslijst mag niet null zijn", brpPersoonslijst);
    }

    private BerichtLog createBerichtLog(final String pl) {
        final BerichtLog berichtLog =
                new BerichtLog("test_ref", "test_bron", new Timestamp(System.currentTimeMillis()));
        berichtLog.setBerichtData(pl);

        final nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel logRegel =
                new nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel();
        logRegel.setCategorie(1);
        logRegel.setStapel(0);
        logRegel.setVoorkomen(0);
        logRegel.setCode("PRE999");
        logRegel.setOmschrijving("Dit is een testlogregel");
        logRegel.setSeverity(LogSeverity.INFO.getSeverity());
        logRegel.setType(LogType.PRECONDITIE.name());
        berichtLog.addLogRegel(logRegel);

        return berichtLog;
    }

    private BrpPersoonslijst createBrpPersoonslijst() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        return builder.build();
    }
}
